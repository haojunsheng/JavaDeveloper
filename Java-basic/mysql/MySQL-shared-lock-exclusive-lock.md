在[MySQL中的行级锁,表级锁,页级锁](http://www.hollischuang.com/archives/914)中介绍过，行级锁是Mysql中锁定粒度最细的一种锁，行级锁能大大减少数据库操作的冲突。行级锁分为共享锁和排他锁两种，本文将详细介绍共享锁及排他锁的概念、使用方式及注意事项等。

## 共享锁(Share Lock)

**共享锁又称读锁，是读取操作创建的锁**。**其他用户可以并发读取数据，但任何事务都不能对数据进行修改**（获取数据上的排他锁），直到已释放所有共享锁。

如果事务T对数据A加上共享锁后，则其他事务只能对A再加共享锁，不能加排他锁。**获准共享锁的事务只能读数据，不能修改数据。**

### 用法

```
SELECT ... LOCK IN SHARE MODE;
```

在查询语句后面增加`LOCK IN SHARE MODE`，Mysql会对查询结果中的每行都加共享锁，当没有其他线程对查询结果集中的任何一行使用排他锁时，可以成功申请共享锁，否则会被阻塞。其他线程也可以读取使用了共享锁的表，而且这些线程读取的是同一个版本的数据。

## 排他锁（eXclusive Lock）

排他锁又称写锁，如果事务T对数据A加上排他锁后，则其他事务不能再对A加任任何类型的封锁。**获准排他锁的事务既能读数据，又能修改数据。**

### 用法

```
SELECT ... FOR UPDATE;
```

在查询语句后面增加`FOR UPDATE`，Mysql会对查询结果中的每行都加排他锁，当没有其他线程对查询结果集中的任何一行使用排他锁时，可以成功申请排他锁，否则会被阻塞。

## 意向锁

意向锁是表级锁，其设计目的主要是为了在一个事务中揭示下一行将要被请求锁的类型。InnoDB中的两个表锁：

意向共享锁（IS）：表示事务准备给数据行加入共享锁，也就是说一个数据行加共享锁前必须先取得该表的IS锁

意向排他锁（IX）：类似上面，表示事务准备给数据行加入排他锁，说明事务在一个数据行加排他锁前必须先取得该表的IX锁。

**意向锁是InnoDB自动加的，不需要用户干预。**

对于insert、update、delete，InnoDB会自动给涉及的数据加排他锁（X）；对于一般的Select语句，InnoDB不会加任何锁，事务可以通过以下语句给显示加共享锁或排他锁。

共享锁：`SELECT ... LOCK IN SHARE MODE;`

排他锁：`SELECT ... FOR UPDATE;`

## 参考资料：

[MySQL表级锁与行级锁](http://linux.it.net.cn/m/view.php?aid=11089)

[MySQL锁的用法之行级锁](http://blog.csdn.net/tigernorth/article/details/7948539)

[Mysql中那些锁机制之InnoDB](http://www.2cto.com/database/201508/429967.html)