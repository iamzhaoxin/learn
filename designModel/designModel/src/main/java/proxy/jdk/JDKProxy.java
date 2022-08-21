package proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: 赵鑫
 * @Date: 2022/8/21
 */
public class JDKProxy {
    public static void main(String[] args) {
        RealSubjectClass realSubjectClass = new RealSubjectClass();
        /*
         * 形参:
         * loader – the class loader to define the proxy class
         * interfaces – the list of interfaces for the proxy class to implement
         * invocationHandler – the invocation handler to dispatch method invocations to
         */
        Subject proxy=(Subject) Proxy.newProxyInstance(
                realSubjectClass.getClass().getClassLoader(),
                realSubjectClass.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("before advice");
                        System.out.println("'proxy' is proxyClass, which is hardly use in this method");
                        System.out.println("'method' is the method to perform");
                        Object invokeResult = method.invoke(realSubjectClass,args);
                        System.out.println("after advice");
                        return invokeResult;
                    }
                }
        );
        String result = proxy.method("hello");
        System.out.println(result);
    }

}

interface Subject {
    String method(String args);
}

class RealSubjectClass implements Subject {

    @Override
    public String method(String args) {
        System.out.println("running method in RealSubjectClass, args: " + args);
        return "return fom RealSubjectClass";
    }
}
