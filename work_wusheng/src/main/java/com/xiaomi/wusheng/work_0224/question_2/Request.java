package com.xiaomi.wusheng.work_0224.question_2;

import java.util.concurrent.atomic.AtomicLong;

public class Request implements Comparable<Request>{
    private final String deviceName;
    private final int power;
    private final long arrivalTime;
    private final boolean isHighPriority;
    private static final AtomicLong requestIdGenerator = new AtomicLong(0);
    private final long requestId;

    public Request(String deviceName, int power, boolean isHighPriority){
        if (deviceName == null || deviceName.isEmpty()){
            throw new IllegalArgumentException("设备名称不能为空");
        }
        if (power <= 0){
            throw new IllegalArgumentException("功率必须大于0");
        }

        this.deviceName = deviceName;
        this.power = power;
        this.isHighPriority = isHighPriority;
        this.arrivalTime = System.currentTimeMillis();
        this.requestId = requestIdGenerator.incrementAndGet();
    }

    public long getWaitTime(){
        return System.currentTimeMillis() - arrivalTime;
    }

    @Override
    public int compareTo(Request other) {
        if (other == null) {
            throw new IllegalArgumentException("比较对象不能为空");
        }

        if (this.isHighPriority && !other.isHighPriority) {
            return -1;
        } else if (!this.isHighPriority && other.isHighPriority) {
            return 1;
        }

        return Long.compare(this.arrivalTime, other.arrivalTime);
    }

    public int getPower(){
        return power;
    }

    public String getDeviceName(){
        return deviceName;
    }

    public boolean isHighPriority(){
        return isHighPriority;
    }

    public long getRequestId(){
        return requestId;
    }

    @Override
    public String toString(){
        return "DeviceRequest{" +
                "requestId=" + requestId +
                ", deviceName='" + deviceName + '\'' +
                ", power=" + power +
                ", arrivalTime=" + arrivalTime +
                ", isHighPriority=" + isHighPriority +
                '}';
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request that = (Request) o;
        return requestId == that.requestId;
    }

    @Override
    public int hashCode(){
        return Long.hashCode(requestId);
    }
}