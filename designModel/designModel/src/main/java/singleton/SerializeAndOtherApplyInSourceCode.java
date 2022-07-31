package singleton;

/**
 * 除了枚举类型，其他单例类型在序列化时
 *  - 继承Serialize接口
 *  - 还要添加“版本号”和“ReSolve”函数，否则在反序列化时会创建新的实例
 *
 * 单例模式在源码中的应用：
 *  - Spring & JDK
 *      - java.lang.Runtime
 *      - org.springframework.aop.framework.ProxyFactoryBean
 *      - org.springframework.beans.factory.support.DefaultSingletonBeanRegistry
 *      - org.springframework.core.ReactiveAdapterRegistry
 *  - Tomcat
 *      - org.apache.catalina.webresources.TomcatURLStreamHandlerFactory
 *  - 反序列化指定数据源
 *      - java.util.Currency
 *
 * @Author: 赵鑫
 * @Date: 2022/7/31
 */
public class SerializeAndOtherApplyInSourceCode {

}
