# 数据库

## MySQL

### 软件包

- **MySQL** - MySQL服务器
- **MySQL-client** - MySQL 客户端程序，用于连接并操作Mysql服务器
- **MySQL-devel** - 库和包含文件，编译其它MySQL客户端
- **MySQL-shared** - 软件包，包含某些语言和应用程序需要动态装载的共享库
- **MySQL-bench** - MySQL数据库服务器的基准和性能测试工具。

[安装MySQL](https://learnku.com/articles/38526)

### 指令

```shell


#设置权限
chown mysql:mysql -R /var/lib/mysql
# chown [  -f ] [ -h ] [  -R ] Owner [ :Group ] { File ... | Directory ... }
# -R 处理指定目录以及其子目录下的所有文件

#初始化
mysqld --initialize

#启动
systemctl start mysqld

#查看运行状态
systemctl status mysqld

#退出
quit/exit
```

### SQL通用语法

- 可以单行或多行书写，分号结尾
- SQL语句不区分大小写，关键字建议大写
- 注释
  - 单行注释： -- 注释内容
  - 单行注释，MySQL独有：#注释内容
  - 多行注释：/* 注释 */

### SQL分类

- DDL：操作**数据库、表**等
  - 查询数据库 `SHOW DATABASES;`
  - 创建数据库
    - 创建数据库 `CREATE DATABASE 数据库名;`
    - 不存在则创建 `CREATE DATABASE IF NOT EXISTS 数据库名;`
  - 删除数据库
    - 删除 `DROP DATABASE 数据库名;`
    - 存在则删除 `DROP DATABASE IF EXISTS 数据库名;`
  - 使用数据库
    - 查看当前使用的数据库 `SELECT DATABASE();`
    - 使用数据库 `USE 数据库名称;`
  - 查询表
    - 查当前数据库下所有表 `SHOW TABLES;`
    - 查询表结构 `DESC 表名称;`
  - 创建表`CREATE TABLE 表名(字段1 数据类型,字段2 数据类型，字段3 数据类型);`
    - 数据类型：数值类型、日期和时间、字符串类型
  - 删除表
    - 删除表`DROP TABLE 表名;`
    - 存在则删除`DROP TABLE IF EXISTS 表名;`
  - 修改表
    - 修改表名`ALTER TABLE 表名 RENAME TO 新表名;`
    - 添加一列 `ALTER TABLE 表名 ADD 列名 数据类型;`
    - 修改数据类型 `ALTER TABLE 表名 MODIFY 列名 新数据类型;`
    - 修改列名和数据类型 `ALTER TABLE 表名 CHANGE 列名 新列名 新数据类型;`
    - 删除列 `ALTER TABLE 表名 DROP 列名;`

- DML：对表中**数据增删改**
  - 添加数据
    - 给指定列添加数据`INSERT INTO 表名((列名1,列名2,…) VALUES(值1,值2,…);`
    - 给全部列添加数据 `INSERT INTO 表名 VALUES(值1,值2,…);`
    - 批量添加数据 
      - `INSERT INTO 表名(列名1,列名2,…) VALUES(值1,值2,…),(值1,值2,…),(值1,值2,…)…;`
      - `INSERT INTO 表名 VALUES(值1,值2,…),(值1,值2,…),(值1,值2,…)…;`
  - 修改数据 `UPDATE 表名 SET 列名1=值1,列名2=值2,… [WHERE 条件];`
    - **如果UPDATE中没有加WHERE语句，会把表中所有数据都修改**
  - 删除数据 `DELETE FROM 表名 [WHERE 条件];`
    - 如果不加WHERE，会把所有数据删除

- DQL：对表中**数据查询**

  - ```sql
    SELECT
    	字段列表
    FROM
    	表名条件
    WHERE
    	条件列表
    GROUP BY
    	分组字段
    HAVING
    	分组后条件
    ORDER BY
    	排序字段
    LIMIT
    	分页限定
    OFFSET
    	数据偏移量
    ```

  - ```shell
    # 用一个或者多个表，表之间使用逗号分割，并使用WHERE语句来设定查询条件
    # 用星号（*）来代替其他字段，SELECT语句会返回表的所有字段数据
    # 用 WHERE 语句来包含任何条件
    # 用 LIMIT 属性来设定返回的记录数	SELECT 字段列表 FROM 表名 LIMIT 起始索引,查询条目数;
    # 通过OFFSET指定SELECT语句开始查询的数据偏移量。默认情况下偏移量为0
    SELECT column_name,column_name 
    FROM table_name [WHERE Clause][LIMIT N][ OFFSET M]
    
    # WHERE子句
    # 可以使用 AND 或者 OR 指定一个或多个任何条件
    # 操作符：=（等号）、<>（不等于）、!=（不等于）、>、<、>=、<=
    SELECT field1,...,fieldN FROM table_name1,...
    [WHERE condition1 [AND [OR]] condition2.....
    #MySQL 的 WHERE 子句的字符串比较是不区分大小写的。 可以使用 BINARY 关键字来设定 WHERE 子句的字符串比较区分大小写
    select * from nowcoder_tbl where binary nowcoder_author='牛客';
    
    # LIKE子句
    # LIKE子句中使用百分号 %字符来表示任意字符，用下划线 _ 表示任意单个字符
    # 如果没有使用百分号 %, LIKE 子句与等号 = 的效果是一样的
    SELECT field1,...fieldN FROM table_name 
    WHERE field1 LIKE '%value' [AND [OR]] filed2 = 'somevalue'
    
    # UNION 操作符
    # UNION 操作符用于连接两个以上的 SELECT 语句的结果组合到一个结果集合中,多个 SELECT 语句会删除重复的数据
    # expression1, expression2, ... expression_n: 要检索的列
    # tables: 要检索的数据表
    # WHERE conditions: 可选， 检索条件
    # DISTINCT: 可选，删除结果集中重复的数据
    # ALL: 可选，返回所有结果集，包含重复数据
    SELECT expression1, ... expression_n FROM tables [WHERE conditions] 
    UNION [ALL | DISTINCT] 
    SELECT expression1, ... expression_n FROM tables [WHERE conditions];
    
    # ORDER BY 子句
    # ASC 或 DESC 关键字来设置查询结果是按升序或降序排列
    # 可以添加 WHERE...LIKE 子句来设置条件
    SELECT field1, field2,...fieldN FROM table_name1, table_name2... 
    ORDER BY field1 [ASC [DESC][默认 ASC]], [field2...] [ASC [DESC]]
    
    # [GROUP BY 语句根据一个或多个列对结果集进行分组]
    # 分组的列上我们可以使用 COUNT, SUM, AVG，MAX，MIN等聚合函数 填充到“function”
    # WITH ROLLUP 可以实现在分组统计数据基础上再进行相同的统计
    # null值不参与所有聚合函数运算
    # WHERE在分组之前进行限定，HAVING在分组后对结果过滤
    # 执行顺序：WHERE -> 聚合函数 -> HAVING
    # 所以HAVING可以对聚合函数进行判断
    SELECT function(column_name) FROM table_name;
    
    SELECT column_name, function(column_name) FROM table_name
    WHERE column_name operator value GROUP BY column_name;
    ```

  - ```shell
    #GROUP BY 举例
    # 数据表：
    mysql> SELECT * FROM employee_tbl;
    +----+--------+---------------------+--------+
    | id | name   | date                | signin|
    +----+--------+---------------------+--------+
    |  1 | 小明   | 2016-04-22 15:25:33 |      1 |
    |  2 | 小王   | 2016-04-20 15:25:47 |      3 |
    |  3 | 小丽   | 2016-04-19 15:26:02 |      2 |
    |  4 | 小王   | 2016-04-07 15:26:14 |      4 |
    |  5 | 小明   | 2016-04-11 15:26:40 |      4 |
    |  6 | 小明   | 2016-04-04 15:26:54 |      2 |
    +----+--------+---------------------+--------+
    6 rows in set (0.00 sec)
    
    
    # 使用 GROUP BY 语句 将数据表按名字进行分组，并统计每个人有多少条记录
    mysql> SELECT name, COUNT(*) FROM   employee_tbl GROUP BY name;
    +--------+----------+
    | name   | COUNT(*) |
    +--------+----------+
    | 小明   |        3 |
    | 小王   |        2 |
    | 小丽   |        1 |
    +--------+----------+
    3 rows in set (0.01 sec)
    
    
    # 使用 WITH ROLLUP,将以上的数据表按名字进行分组，再统计每个人登录的次数
    # 其中记录 NULL 表示所有人的登录次数
    mysql> SELECT name, SUM(signin) as signin_count FROM  employee_tbl GROUP BY name WITH ROLLUP;
    +--------+--------------+
    | name   | signin_count |
    +--------+--------------+
    | 小丽   |            2 |
    | 小明   |            7 |
    | 小王   |            7 |
    | NULL   |           16 |
    +--------+--------------+
    4 rows in set (0.01 sec)
    
    # 可以使用 coalesce 来设置一个可以取代 NUll 的名称
    # coalesce 语法：select coalesce(a,b,c);
    # 如果a==null,则选择b；如果b==null,则选择c；如果a!=null,则选择a；如果a b c 都为null ，则返回为null（没意义）
    mysql> SELECT coalesce(name, '总数'), SUM(signin) as signin_count FROM  employee_tbl GROUP BY name WITH ROLLUP;
    +--------------------------+--------------+
    | coalesce(name, '总数')   | signin_count |
    +--------------------------+--------------+
    | 小丽                     |            2 |
    | 小明                     |            7 |
    | 小王                     |            7 |
    | 总数                     |           16 |
    +--------------------------+--------------+
    4 rows in set (0.00 sec)
    ```

  - ```shell
    #修改字段默认值
    mysql> ALTER TABLE testalter_tbl ALTER i SET DEFAULT 1000;
    mysql> SHOW COLUMNS FROM testalter_tbl;
    +-------+---------+------+-----+---------+-------+
    | Field | Type    | Null | Key | Default | Extra |
    +-------+---------+------+-----+---------+-------+
    | c     | char(1) | YES  |     | NULL    |       |
    | i     | int(11) | YES  |     | 1000    |       |
    +-------+---------+------+-----+---------+-------+
    2 rows in set (0.00 sec)
    ```

  - ```shell
    #正则表达式
    # 查找name字段中以'st'为开头的所有数据：
    mysql> SELECT name FROM person_tbl WHERE name REGEXP '^st';
    
    # 查找name字段中以'ok'为结尾的所有数据：
    mysql> SELECT name FROM person_tbl WHERE name REGEXP 'ok$';
    
    # 查找name字段中包含'mar'字符串的所有数据：
    mysql> SELECT name FROM person_tbl WHERE name REGEXP 'mar';
    
    # 查找name字段中以元音字符开头或以'ok'字符串结尾的所有数据：
    mysql> SELECT name FROM person_tbl WHERE name REGEXP '^[aeiou]|ok$';
    ```

- DCL：对数据库进行**权限控制**

### NULL值处理

MySQL提供了三大运算符：

- **IS NULL:** 当列的值是 NULL,此运算符返回 true
- **IS NOT NULL:** 当列的值不为 NULL, 运算符返回 true
- **<=>:** 比较操作符（不同于=运算符），当比较的的两个值为 NULL 时返回 true



- 不能使用 = NULL 或 != NULL 在列中查找 NULL 值 。
- NULL 值与任何其它值的比较（即使是 NULL）永远返回 false，即 NULL = NULL 返回false
- 处理 NULL 使用 IS NULL 和 IS NOT NULL 运算符



```Bash
select * , columnName1+ifnull(columnName2,0) from tableName;
```


- columnName1，columnName2 为 int 型，当 columnName2 中，有值为 null 时，columnName1+columnName2=null
- ifnull(columnName2,0) 把 columnName2 中 null 值转为 0

### 约束

> 作用于表中列上的规则，用于限制表中加入的数据

分类

- 非空约束 NOT NULL
- 唯一约束 UNIQUE
- 主键约束 PRIMARY KEY （非空且唯一）
- 检查约束 CHECK （MySQL不支持）
- 默认约束 DEFAULT
- 外键约束 FOREIGN KEY

外键约束：

- 用于让两个表建立连接，保证数据一致性和完整性

- 语法

  - 添加约束

    ```sql
    -- 创建表时添加外键约束
    CREARE TABLE表名(
    	列名 数据类型,
        ……
        [CONSTRAINT] [外键名称] FOREIGN KEY(外键的列名) REFERENCES 主表(主表列名)
    );
    -- 建完表后添加外键约束
    ALTER TABLE 表名 ADD CONSTRAINT 外键名称 FOREIRGN KEY (外键字段名) REFERENCES 主表名(主表字段名);
    ```

  - 删除约束`ALTER TABLE 表名 DROP FOREIGN KEY 外键名称;`

### SQL连接（多表）

可以在 SELECT, UPDATE 和 DELETE 语句中使用 Mysql 的 JOIN 来联合多表查询

JOIN 按照功能大致分为三类：

- **INNER JOIN（内连接,或等值连接）：**获取两个表中字段匹配关系的记录
- **LEFT JOIN（左连接）：**获取左表所有记录，即使右表没有对应匹配的记录
- **RIGHT JOIN（右连接）：**获取右表所有记录，即使左表没有对应匹配的记录

数据表

```Bash
mysql> select * from tcount_tbl;
+-----------------+----------------+
| nowcoder_author | nowcoder_count |
+-----------------+----------------+
| 牛客教程        |             10 |
| NOWCODER.COM    |             20 |
| Google          |             22 |
+-----------------+----------------+
3 rows in set (0.00 sec)

mysql> select * from nowcoder_tbl;
+-------------+----------------+-----------------+-----------------+
| nowcoder_id | nowcoder_title | nowcoder_author | submission_date |
+-------------+----------------+-----------------+-----------------+
|           1 | 学习 PHP       | 牛客教程          | 2017-04-12      |
|           2 | 学习 MySQL     | 牛客教程          | 2017-04-12      |
|           3 | 学习 Java      | NOWCODER.COM    | 2015-05-01      |
|           4 | 学习 Python    | NOWCODER.COM    | 2016-03-06      |
|           5 | 学习 C         | FK              | 2017-04-05      |
+-------------+----------------+-----------------+-----------------+
5 rows in set (0.00 sec)

```

- 例.INNER JOIN查询

读取nowcoder_tbl表中所有nowcoder_author字段在tcount_tbl表对应的nowcoder_count字段值

```Bash
# 也可以省略 INNER 使用 JOIN，效果一样
mysql> SELECT a.nowcoder_id, a.nowcoder_author, b.nowcoder_count FROM nowcoder_tbl a INNER JOIN tcount_tbl b ON a.nowcoder_author = b.nowcoder_author;
+-------------+-----------------+----------------+
| nowcoder_id | nowcoder_author | nowcoder_count |
+-------------+-----------------+----------------+
|           1 | 牛客教程        |             10 |
|           2 | 牛客教程        |             10 |
|           3 | NOWCODER.COM    |             20 |
|           4 | NOWCODER.COM    |             20 |
+-------------+-----------------+----------------+
4 rows in set (0.00 sec)

# 等价于
mysql> SELECT a.nowcoder_id, a.nowcoder_author, b.nowcoder_count FROM nowcoder_tbl a,tcount_tbl b where a.nowcoder_author=b.nowcoder_author;
+-------------+-----------------+----------------+
| nowcoder_id | nowcoder_author | nowcoder_count |
+-------------+-----------------+----------------+
|           1 | 牛客教程        |             10 |
|           2 | 牛客教程        |             10 |
|           3 | NOWCODER.COM    |             20 |
|           4 | NOWCODER.COM    |             20 |
+-------------+-----------------+----------------+
4 rows in set (0.00 sec)

```

- 例.LEFT JOIN

使用 LEFT JOIN，该语句会读取左边的数据表 nowcoder_tbl 的所有选取的字段数据，即便在右侧表 tcount_tbl中 没有对应的 nowcoder_author 字段值

```Bash
mysql> SELECT a.nowcoder_id, a.nowcoder_author, b.nowcoder_count FROM nowcoder_tbl a LEFT JOIN tcount_tbl b ON a.nowcoder_author = b.nowcoder_author;
+-------------+-----------------+----------------+
| nowcoder_id | nowcoder_author | nowcoder_count |
+-------------+-----------------+----------------+
|           1 | 牛客教程        |             10 |
|           2 | 牛客教程        |             10 |
|           3 | NOWCODER.COM    |             20 |
|           4 | NOWCODER.COM    |             20 |
|           5 | FK              |           NULL |
+-------------+-----------------+----------------+
5 rows in set (0.00 sec)
```

- 例.RIGHT JOIN

```Bash
mysql> SELECT a.nowcoder_id, a.nowcoder_author, b.nowcoder_count FROM nowcoder_tbl a RIGHT JOIN tcount_tbl b ON a.nowcoder_author = b.nowcoder_author;
+-------------+-----------------+----------------+
| nowcoder_id | nowcoder_author | nowcoder_count |
+-------------+-----------------+----------------+
|           1 | 牛客教程        |             10 |
|           2 | 牛客教程        |             10 |
|           3 | NOWCODER.COM    |             20 |
|           4 | NOWCODER.COM    |             20 |
|        NULL | NULL            |             22 |
+-------------+-----------------+----------------+
5 rows in set (0.00 sec)
```

### 事务

- 在 MySQL 中只有使用了 Innodb 数据库引擎的数据库或表才支持事务
- 事务处理可以用来维护数据库的完整性，保证成批的 SQL 语句要么全部执行，要么全部不执行
- 事务用来管理 insert,update,delete 语句

**事务**必须满足的四个条件：

- **原子性：**一个事务（transaction）中的所有操作，**要么全部完成，要么全部不完成**，不会结束在中间某个环节。事务在执行过程中发生错误，会被回滚（Rollback）到事务开始前的状态，就像这个事务从来没有执行过一样。
- **一致性：**在事务开始之前和事务结束以后，数据库的完整性没有被破坏。这表示写入的资料必须完全符合所有的预设规则，这包含资料的精确度、串联性以及后续数据库可以自发性地完成预定的工作。
- **隔离性：**数据库允许多个并发事务同时对其数据进行读写和修改的能力，隔离性可以防止多个事务并发执行时由于交叉执行而导致数据的不一致。事务隔离分为不同级别，包括读未提交（Read uncommitted）、读提交（read committed）、可重复读（repeatable read）和串行化（Serializable）。
- **持久性：**事务处理结束后，对数据的修改就是永久的，即便系统故障也不会丢失。

> 在 MySQL 命令行的默认设置下，事务都是自动提交的，即执行 SQL 语句后就会马上执行 COMMIT 操作。因此要显式地开启一个事务务须使用命令 BEGIN 或 START TRANSACTION，或者执行命令 SET AUTOCOMMIT=0，用来禁止使用当前会话的自动提交


#### 事务控制语句

- BEGIN 或 START TRANSACTION 显式地开启一个事务；
- COMMIT / COMMIT WORK。提交事务，已对数据库进行的所有修改成为永久性的；
- ROLLBACK / ROLLBACK WORK。回滚会结束用户的事务，并撤销正在进行的所有未提交的修改；
- SAVEPOINT identifier，SAVEPOINT 允许在事务中创建一个保存点，一个事务中可以有多个 SAVEPOINT；
- RELEASE SAVEPOINT identifier 删除一个事务的保存点，当没有指定的保存点时，执行该语句会抛出一个异常；
- ROLLBACK TO identifier 把事务回滚到标记点；
- SET TRANSACTION 用来设置事务的隔离级别。InnoDB 存储引擎提供事务的隔离级别有READ UNCOMMITTED、READ COMMITTED、REPEATABLE READ 和 SERIALIZABLE。

#### 处理事务的两种主要方法

1. 用 BEGIN, ROLLBACK, COMMIT来实现
   - **BEGIN** 开始一个事务
   - **ROLLBACK** 事务回滚
   - **COMMIT** 事务确认
2. 直接用 SET 来改变 MySQL 的自动提交模式
   - **SET AUTOCOMMIT=0** 禁止自动提交
   - **SET AUTOCOMMIT=1** 开启自动提交

```Bash
mysql> create table nowcoder_transaction_test( id int(5) ) engine=innodb;
Query OK, 0 rows affected (0.04 sec)

mysql> select * from nowcoder_transaction_test;
Empty set (0.00 sec)

mysql> begin; #开始事务
Query OK, 0 rows affected (0.00 sec)

mysql> insert into nowcoder_transaction_test values(5);
Query OK, 1 row affected (0.01 sec)

mysql> insert into nowcoder_transaction_test values(6);
Query OK, 1 row affected (0.00 sec)

mysql> commit; #提交事务
Query OK, 0 rows affected (0.01 sec)

mysql> select * from nowcoder_transaction_test;
+------+
| id   |
+------+
|    5 |
|    6 |
+------+
2 rows in set (0.00 sec)

mysql> begin; #开始事务
Query OK, 0 rows affected (0.00 sec)

mysql> insert into nowcoder_transaction_test values(7);
Query OK, 1 row affected (0.01 sec)

mysql> rollback; #回滚
Query OK, 0 rows affected (0.01 sec)

mysql> select * from nowcoder_transaction_test;
+------+
| id   |
+------+
|    5 |
|    6 |
+------+
2 rows in set (0.00 sec)

```

### 索引

- 索引分单列索引和组合索引。单列索引，即一个索引只包含单个列，一个表可以有多个单列索引，但这不是组合索引。组合索引，即一个索引包含多个列
- 创建索引时，你需要确保该索引是应用在 SQL 查询语句的条件(一般作为 WHERE 子句的条件)
- 实际上，索引也是一张表，该表保存了主键与索引字段，并指向实体表的记录

> 过多的使用索引将会造成滥用。因此索引也会有它的缺点：虽然索引大大提高了查询速度，同时却会降低更新表的速度，如对表进行INSERT、UPDATE和DELETE。因为更新表时，MySQL不仅要保存数据，还要保存一下索引文件。
> 建立索引会占用磁盘空间的索引文件。


#### 普通索引

[未完待续（就先，学到这儿吧……](https://www.nowcoder.com/tutorial/10006/3aab8cb0cbb94e608de595c9084b71fb)

## JDBC

去看代码吧……
