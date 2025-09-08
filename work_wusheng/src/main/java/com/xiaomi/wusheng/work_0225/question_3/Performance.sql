/*
此文件包含了 Performance.sql 的全部内容
测试结果保存在 src/main/resources/work_0225/question_3/
*/



-- 订单表 orders (2000万行)
CREATE TABLE orders (
	order_id BIGINT PRIMARY KEY,
	user_id INT,
	product_id INT,
	status VARCHAR(20),  -- 如'completed', 'pending'
	create_time DATETIME,
	amount DECIMAL(10,2),
	INDEX idx_user(user_id),
	INDEX idx_status_time(status, create_time)
);

-- 商品表 products(500万行)
CREATE TABLE products (
	product_id INT PRIMARY KEY,
	category_id INT,
	name VARCHAR(200),
	price DECIMAL(8,2),
	INDEX idx_cat(category_id)
);



/*
 * 问题1：查询结果执行时出现了 filesort，消耗时间超过 2 秒。
 *
 * 分析：filesort 是 MySQL 用来对查询结果进行排序的一种方法，但它是低效的，因为它会导致全表扫描、排序等操作，影响查询性能。
 * 根本原因：现有索引无法同时满足WHERE条件和ORDER BY排序需求。
 * */
EXPLAIN SELECT * FROM orders
WHERE user_id = 10086 AND status = 'completed'
ORDER BY create_time DESC
LIMIT 50;

/*
 * 解决方案：创建新联合索引。
 *
 * 分析：
 * 	索引顺序遵循等值查询(user_id)、等值查询(status)、范围排序(create_time)。
 * 	user_id和status作为查询条件出现在索引的前面，确保能快速过滤符合条件的记录。
 * 	create_time DESC作为排序字段，放在复合索引的最后。MySQL在使用索引时会自动按索引顺序进行排序，因此可以避免文件排序。
 * 	覆盖索引避免回表，执行计划显示Using index condition，并且Using filesort 消失。
 * */
CREATE INDEX idx_user_status_time ON orders(user_id, status, create_time DESC);

EXPLAIN SELECT * FROM orders
WHERE user_id = 10086 AND status = 'completed'
ORDER BY create_time DESC
LIMIT 50;



/*
 * 问题2：订单表的分页查询方式存在性能问题，特别是在数据量很大的情况下（如偏移量 100000），查询速度非常慢。
 *
 * 分析：当LIMIT语句使用大偏移量（如 100000）时，MySQL需要扫描大量的记录才能跳过这些记录，导致性能非常低。即使有索引，MySQL依然需要遍历大量记录来计算偏移。
 * */
SELECT * FROM orders
WHERE status = 'completed'
ORDER BY create_time DESC
LIMIT 100000, 20;

/*解决方案：业务分页方案设计（基于create_time和order_id的游标分页）
public List<Order> paginateOrders(LocalDateTime lastTime, long lastId, int pageSize) {
    String sql = """
        SELECT * FROM orders
        WHERE status='completed'
        AND (create_time < ? OR (create_time = ? AND order_id < ?))
        ORDER BY create_time DESC, order_id DESC
        LIMIT ?""";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setTimestamp(1, Timestamp.valueOf(lastTime));
        pstmt.setTimestamp(2, Timestamp.valueOf(lastTime));
        pstmt.setLong(3, lastId);
        pstmt.setInt(4, pageSize);
        // 执行查询...
    }
}
*	数据量级从扫描100020行到仅扫描20行
*	分页响应时间从12秒到50ms以内
**/
-- 通过延迟关联重写SQL
SELECT o.*
FROM orders o
JOIN (
  SELECT order_id
  FROM orders
  WHERE status='completed'
  ORDER BY create_time DESC
  LIMIT 100000,20
) AS tmp USING(order_id);



/*
 * 分析问题3.1：统计查询查询了特定类别的商品，并且使用了GROUP BY进行聚合计算。
 * 由于没有适当的索引支持，查询可能需要进行全表扫描，尤其是在数据量较大的情况下，查询效率较低。
 * */
SELECT category_id, AVG(price)
FROM products
WHERE category_id IN (5, 7, 9) AND price > 50
GROUP BY category_id;

/*
 * 解决方案：为了加速WHERE条件和GROUP BY操作，应该在category_id和price字段上创建复合索引idx_cat_price。
 * 复合索引idx_cat_price可以帮助MySQL快速定位符合条件的记录，并且加速GROUP BY操作。
 * 复合索引idx_cat_price覆盖WHERE category_id IN (5, 7, 9) AND price > 50，直接通过索引完成分组统计，避免回表。
 * */
CREATE INDEX idx_cat_price ON products(category_id, price);


/*
 * 分析问题3.2：执行批量更新时，MySQL 会对受影响的行加锁。
 * 由于category_id = 5可能覆盖多个记录，且可能与其他事务存在竞争，导致死锁问题，特别是当存在gap lock（间隙锁和insert intention lock（插入意图锁时。
 * */
UPDATE products SET price = price * 0.9 WHERE category_id = 5;

/*
 * 解决方案：
 * 1、按批次更新products表中的记录，而不是一次性更新所有记录，通过限制每次更新的行数来减少锁的竞争。
 *    每次更新1000条，循环执行直到完成。
 * */
UPDATE products
SET price = price * 0.9
WHERE category_id = 5
LIMIT 1000;

/*
 * 解决方案：
 * 2、通过使用适当的事务隔离级别（如READ COMMITTED）来避免死锁，确保每个更新操作的事务尽量小，减少锁的持有时间。
 *    调整隔离级别为READ-COMMITTED（需评估业务影响），或强制使用主键扫描（需验证执行计划）。
 * */
SET GLOBAL transaction_isolation = 'READ-COMMITTED';
UPDATE products FORCE INDEX(PRIMARY)
SET price = price*0.9
WHERE category_id=5;









