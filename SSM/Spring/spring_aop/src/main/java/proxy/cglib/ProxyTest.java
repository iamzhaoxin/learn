package proxy.cglib;

import controller.UserController;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/8 22:02
 */
public class ProxyTest {
    public static void main(String[] args) {
        //目标对象
        final UserController userController = new UserController();

        // 基于cglib动态生成代理对象:
        // 1. 创建增强器
        Enhancer enhancer = new Enhancer();
        // 2. 设置父类(目标)
        enhancer.setSuperclass(UserController.class);
        // 3. 设置回调
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                //执行前置的增强功能
                long startTimer = System.currentTimeMillis();
                // 执行原本的业务功能
                Object result = method.invoke(userController, objects);
                //执行后置的增强功能
                long endTimer = System.currentTimeMillis();
                System.out.println(method.getName() + "方法耗时：" + (endTimer - startTimer) / 1000.0 + "s");

                return result;
            }
        });
        // 4. 创建代理对象
        UserController userControllerNew = (UserController) enhancer.create();

        userControllerNew.save();
    }
}
