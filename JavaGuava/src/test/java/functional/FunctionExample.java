package functional;

/**
 * Function：用于转换的接口。主要是用apply方法把input转换为另外一个对象。一般都是用匿名内部类的方式使用
 *      一般单一对象转换不需要用Function，针对集合的可以考虑使用
 *      Functions针对Function提供了一些工具类
 *          compose:组合Function
 *                  用compose组合Function1(把A转换为B)和Function2(B转换为C)，从而得到一个Function实例，把A转换为C
 *          forMap:从一个map构建一个Function
 * Predicate：用于过滤的接口
 *
 * Supplier：创建对象实例的接口，实现创建型的设计模式
 *      memoize：双重校验加锁创建单例
 *      memoizeWithExpiration：获取单例的同时，超时失效重新获取
 * 类似 Java8
 * @Author: 赵鑫
 * @Date: 2022/7/1
 */
public class FunctionExample {

    // https://www.bilibili.com/video/BV1R4411s7GX?p=6&share_source=copy_web
}
