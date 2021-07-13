package myutils

import java.text.SimpleDateFormat

import com.mongodb.{BasicDBObject, DBCollection, ServerAddress}
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.{MongoClient, MongoCredential, MongoDB}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import org.apache.flink.types.{Row, RowKind}
//import org.mongodb.scala.MongoDatabase

import scala.collection.mutable

class MongoSink extends RichSinkFunction[Tuple2[Boolean, Row]] {
  var collection: DBCollection = null
  var mongoClient: MongoClient = _

  override def open(parameters: Configuration): Unit = {
    //    val mongodb: MongoDB = createDatabase("localhost", 27017, "51job测试")
    val m: MongoDB = createDatabase("hdp-1", 27017, "admin", "admin", "123456")

    collection = m.getCollection("myCollection")
  }

  override def close(): Unit = {
    mongoClient.close()
  }

  override def invoke(value: (Boolean, Row), context: SinkFunction.Context[_]): Unit = {
    //    val strings: Array[String] = value._2.toString.split(",")
    //    var k: String = strings(0) + strings(1)
    val province: String = value._2.getField(1).toString
    val city: String = value._2.getField(0).toString
    val key: String = province + city
    val kind: RowKind = value._2.getKind
    val shortStr: String = kind.shortString()
    println("key:" + key + "...shortStr:" + shortStr + "..value:" + value._2)

    collection.insert(MongoDBObject(key -> value._2.toString))
  }

  //  override def invoke(value: (String, String, String, String), context: SinkFunction.Context[_]): Unit = {
  //    collection.insert(MongoDBObject("1" -> value._1, "2" -> value._2, "3"->value._3, "4" -> value._4))
  ////    collection.insert(MongoDBObject("name" -> "Jack%d".format(i), "email" -> "jack%d@sina.com".format(i), "age" -> i % 25, "birthDay" -> new SimpleDateFormat("yyyy-MM-dd").parse("2016-03-25")))
  //  }

  //  无权限验证连接
  def createDatabase(url: String, port: Int, dbName: String): MongoDB = {
    MongoClient(url, port).getDB(dbName)
  }

  //验证连接权限
  def createDatabase(url: String, port: Int, dbName: String, loginName: String, password: String): MongoDB = {
    var server = new ServerAddress(url, port)
    //注意：MongoCredential中有6种创建连接方式，这里使用MONGODB_CR机制进行连接。如果选择错误则会发生权限验证失败
    var credentials = MongoCredential.createCredential(loginName, dbName, password.toCharArray)
    mongoClient = MongoClient(server, List(credentials))

    mongoClient.getDB(dbName)
  }
}


object MainM {
  def main(args: Array[String]): Unit = {
    val m: MongoDB = createDatabase("hdp-1", 27017, "admin", "admin", "123456")
    val myCollection: DBCollection = m.getCollection("myCollection")
    myCollection.insert(MongoDBObject("name" -> "lucas"))
    //    testInsert
    //    testUpdate01
    //    testSelect
    //    testDelete
  }


  def testDelete(): Unit = {
    var query = MongoDBObject("name" -> "user1", "email" -> "user1@test.com")
    println("=========删除之前============")
    collection.find(query).forEach(x => println(x))
    //该参数只是一个查询条件，符合该条件的所有集合都将被删除
    collection.remove(query)
    //    collection.findAndRemove()
    println("=========删除之前============")
    collection.find(query).forEach(x => println(x))
  }


  def testUpdate01(): Unit = {
    var query = MongoDBObject("name" -> "Jack1", "email" -> "jack1@sina.com")
    var value = MongoDBObject("name" -> "Jack1", "email" -> "user1@test.com123456")
    println("=========更新之前============")
    var query02 = MongoDBObject("name" -> "user1")
    collection.find(query).forEach(x => println(x))
    //    query:根据此条件进行查询  value：把查询出来结果集的第一条数据设置为value
    collection.update(query, value)
    println("=========更新之后============")
    collection.find(query).forEach(x => println(x))
  }

  def testUpdate02(): Unit = {
    var query = MongoDBObject("name" -> "Jack1", "email" -> "jack1@sina.com")
    var value = new BasicDBObject("$set", new BasicDBObject("email", "user1@test.com"))
    //     var value = MongoDBObject("$set",MongoDBObject("name" -> "user1", "email" -> "user1@test.com123"))
    println("=========更新之前============")
    var query02 = MongoDBObject("name" -> "Jack1")
    collection.find(query02).forEach(x => println(x))
    collection.update(query, value, true, true)
    println("=========更新之后============")
    collection.find(query02).forEach(x => println(x))
  }

  def testSelect(): Unit = {
    println("=========查询所有数据===================")
    collection.find().forEach(x => println(x))
    //    println("=========查询name = “Jack1”  同时email=“jack1@sina.com”===================")
    //    collection.find(MongoDBObject("name" -> "Jack1", "email" -> "jack1@sina.com")).limit(3).forEach(x => println(x))
    //    //    注意此处不能使用put添加其他查询条件，因为put返回的是HashMap,此处应该使用append进行添加查询条件
    //    //  var query = new BasicDBObject("name",new BasicDBObject("$in",("user145","user155"))).put("qty",new BasicDBObject("$in",(25.0,105.0)))  该方法错误
    //    //    查询条件为： (name in ("user145","user155")) && (qty in (25.0,105.0))
    //    println("=========查询 (name in (\"user145\",\"user155\")) && (qty in (25.0,105.0))===================")
    //    var query = new BasicDBObject("name", new BasicDBObject("$in", ("user145", "user155"))).append("qty", new BasicDBObject("$in", (25.0, 105.0)))
    //    collection.find(query).forEach(x => println(x))
    //    println("=========查询 start >= 10 && end<= 80 的数据===================")
    //    var query02 = new BasicDBObject("start", new BasicDBObject("$gte", 10)).append("end", new BasicDBObject("$lte", 80))
    //    collection.find(query02).forEach(x => println(x))
  }

  def testInsert(): Unit = {
    for (i <- 1 to 100)
    //      注意与saved的区别
      collection.insert(MongoDBObject("name" -> "Jack%d".format(i), "email" -> "jack%d@sina.com".format(i), "age" -> i % 25, "birthDay" -> new SimpleDateFormat("yyyy-MM-dd").parse("2016-03-25")))
  }

  //  var collection = createDatabase("localhost", 27017, "mytest", "user", "123456").getCollection("user")
  var collection = createDatabase("localhost", 27017, "51job测试").getCollection("user")

  //验证连接权限
  def createDatabase(url: String, port: Int, dbName: String, loginName: String, password: String): MongoDB = {
    var server = new ServerAddress(url, port)
    //注意：MongoCredential中有6种创建连接方式，这里使用MONGODB_CR机制进行连接。如果选择错误则会发生权限验证失败
    var credentials = MongoCredential.createCredential(loginName, dbName, password.toCharArray)
    var mongoClient = MongoClient(server, List(credentials))
    mongoClient.getDB(dbName)
  }

  //  无权限验证连接
  def createDatabase(url: String, port: Int, dbName: String): MongoDB = {
    MongoClient(url, port).getDB(dbName)
  }
}


































