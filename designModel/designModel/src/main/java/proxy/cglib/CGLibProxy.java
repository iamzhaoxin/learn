package proxy.cglib;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: 赵鑫
 * @Date: 2022/8/21
 */
public class CGLibProxy {
    public static void main(String[] args) {
        Enhancer enhancer=new Enhancer();   //类似JDK代理中的Proxy类
        enhancer.setSuperclass(RealSubjectClass.class); //设置父类的字节码对象
//        enhancer.setCallback(new RealSubjectInterceptor()); //设置回调函数
        enhancer.setCallback(new RealSubjectInterceptorAnother()); //设置回调函数
        RealSubjectClass subClassProxy = (RealSubjectClass) enhancer.create();   //创建代理对象
        String result = subClassProxy.method("hello");
        System.out.println(result);
    }
}

class RealSubjectInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("before advice");
        Object invokeResult = methodProxy.invokeSuper(obj,args);    // here is different
        System.out.println("after advice");
        return invokeResult;
    }
}

class RealSubjectInterceptorAnother implements  MethodInterceptor{
    private RealSubjectClass realSubjectClass=new RealSubjectClass();   // here is different
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before advice");
        Object invokeResult = method.invoke(realSubjectClass,objects);  // here is different
        System.out.println("after advice");
        return invokeResult;
    }
}

class RealSubjectClass {

    public String method(String args) {
        System.out.println("running method in RealSubjectClass, args: " + args);
        return "return fom RealSubjectClass";
    }
}

