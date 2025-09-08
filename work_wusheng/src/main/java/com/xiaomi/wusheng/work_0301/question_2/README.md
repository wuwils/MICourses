# 3月1日

# 问题2

> 在一个高并发的Java Web应用中，出现了性能瓶颈，经过初步分析发现内存经常不够用，导致发生FullGC。请综合运用JVM内存模型、垃圾回收算法、垃圾收集器选择以及JM参数调优等方面，设计出一套性能优化方案，并说明理由。
> 

# 性能优化方案

## 一、堆内存分析

```bash
	jps

	jmap -histo pid > pid.jmap
	
	cat pid.jmap | sort -k4 | grep 类名
```

- 保存当前堆内存快照，按第4列（内存占用大小）对数据进行**升序排序**，可以直观地看出实例数异常增多或占用内存过高的类，再回到代码中查看并分析这个类的具体实现与使用。

---

## **二、JVM内存模型优化**

- **固定堆大小**：把初始堆（`-Xms`）和最大堆（`-Xmx`）设成一样，比如8GB（`-Xms8g -Xmx8g`），避免堆内存动态调整带来的开销。
- **新生代与老年代比例**：
    - **新生代扩容**：把新生代和老年代的比例调成1:1（`-XX:NewRatio=1`），这样新生代大概有4GB，适合高并发场景下短生命周期对象的快速回收。
    - **Survivor区优化**：把Eden区和Survivor区的比例调成8:1:1（`-XX:SurvivorRatio=8`），这样Eden区大概有3.2GB，避免Survivor区太小导致对象过早进入老年代。
- **对象晋升阈值**：把对象晋升到老年代的存活次数阈值调高到15次（`-XX:MaxTenuringThreshold=15`），让对象在年轻代多待一会儿，减少老年代的压力。
- **元空间固定大小**：把元空间的大小固定为256MB（`-XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m`），避免元空间动态扩容触发Full GC。
- **限制堆外内存**：把NIO Direct Buffer的内存限制在1GB（`-XX:MaxDirectMemorySize=1g`），防止堆外内存泄漏。

---

## **三、垃圾回收算法优化**

### **分代收集算法**

- **年轻代（Minor GC）**：
    - 使用**复制算法**，把Eden区和Survivor区的存活对象复制到另一个Survivor区，效率高且没有内存碎片。
    - 优化对象分配：把大于1MB的对象直接放到老年代（`-XX:PretenureSizeThreshold=1m`），避免大对象在年轻代频繁复制
- **老年代（Full GC）**：
    - 使用**标记-清除-整理算法**，减少内存碎片，但停顿时间会比较长。

### **并发标记与回收**

- **并发标记**：在应用运行的同时，标记存活对象（比如G1的并发标记阶段）。
- **并发清理**：在标记完成后，和应用一起清理垃圾对象（比如CMS的并发清理阶段）。

---

## **四、垃圾收集器选择**

### **G1收集器**

- **适用场景**：堆内存比较大（>4GB），对延迟要求比较高。
- **核心优势**：
    - 分区管理（Region），避免内存碎片。
    - 可以设置目标停顿时间（`XX:MaxGCPauseMillis`）。
- **关键参数**：
    - `XX:+UseG1GC`：启用G1收集器。
    - `XX:MaxGCPauseMillis=200`：目标暂停时间200ms。
    - `XX:InitiatingHeapOccupancyPercent=45`：老年代占用45%时触发并发标记。

### **CMS收集器**

- **适用场景**：堆内存比较小（<4GB），对延迟要求比较高。
- **核心优势**：
    - 并发标记与清理，减少STW时间。
- **关键参数**：
    - `XX:+UseConcMarkSweepGC -XX:+UseParNewGC`：启用CMS收集器。
    - `XX:CMSInitiatingOccupancyFraction=70`：老年代占用70%时触发CMS。
    - `XX:+CMSParallelRemarkEnabled`：并行标记减少停顿。

### **Parallel收集器**

- **适用场景**：对吞吐量要求高，对延迟不敏感。
- **核心优势**：
    - 多线程并行回收，吞吐量高。
- **关键参数**：
    - `XX:+UseParallelGC -XX:+UseParallelOldGC`：启用Parallel收集器。
    - `XX:ParallelGCThreads=4`：根据CPU核数调整并行线程数。

---

## **五、JVM参数调优**

### **GC日志与监控**

- **实时监控工具**：
    - `jstat -gcutil <PID> 1000`：每秒输出GC统计信息。
    - `jcmd <PID> VM.native_memory summary`：查看堆外内存使用情况。

### **避免Full GC的关键参数**

- **禁用显式GC**：`XX:+DisableExplicitGC`，禁止`System.gc()`触发Full GC。
- **元空间保护**：`XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m`，避免元空间动态扩容触发Full GC。
- **大对象直接晋升**：`XX:PretenureSizeThreshold=1m`，把大于1MB的对象直接放到老年代。

### **线程与内存分配优化**

- **线程栈大小**：`Xss512k`，把线程栈内存调小到512KB（默认1MB）。
- **本地内存分配**：`XX:MaxDirectMemorySize=1g`，把NIO Direct Buffer的内存限制在1GB。