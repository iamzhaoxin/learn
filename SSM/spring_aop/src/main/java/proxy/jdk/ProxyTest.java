package proxy.jdk;

import service.UserService;
import service.impl.UserServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/8 21:21
 */
public class ProxyTest {
    public static void main(String[] args) {
        //目标对象
        final UserServiceImpl userService = new UserServiceImpl();

        /*
            notice
                public static Object newProxyInstance(ClassLoader loader,  Class< ?>[] interfaces, InvocationHandler h)
                    参数一：类加载器，负责加载代理类到内存中使用。
                    参数二：获取被代理对象实现的全部接口。代理要为全部接口的全部方法进行代理
                    参数三：代理的核心处理逻辑
                    返回值: 动态生成的代理对象
         */
        UserService _userService = (UserService) Proxy.newProxyInstance(
                userService.getClass().getClassLoader(),
                userService.getClass().getInterfaces(),
                new InvocationHandler() {
                    /**
                     *
                     * @param proxy 代理对象本身。一般不管
                     * @param method 正在被代理的方法
                     * @param args 被代理方法，应该传入的参数
                     * @return 把业务功能方法执行的结果返回给调用者
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //执行前置的增强功能
                        long startTimer = System.currentTimeMillis();
                        // 执行原本的业务功能
                        Object result = method.invoke(userService, args);
                        //执行后置的增强功能
                        long endTimer = System.currentTimeMillis();
                        System.out.println(method.getName() + "方法耗时：" + (endTimer - startTimer) / 1000.0 + "s");

                        return result;
                    }
                }
        );

        //调用 代理对象 的方法
        _userService.save();
    }
}
