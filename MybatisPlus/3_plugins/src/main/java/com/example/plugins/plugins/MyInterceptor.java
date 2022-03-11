package com.example.plugins.plugins;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Properties;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/11 20:00
 */

//原生插件
@Intercepts({
        @Signature(
                /*
                    notice 可拦截的方法：
                        - 拦截执行器 Executor(update query flushStatements commit rollback getTransaction close isClosed)
                        - 拦截参数 ParameterHandler(getParameterObject setParameters)
                        - 拦截结果集 ResultSetHandler(handleResultSets handleOutputParameters)
                        - 拦截SQL语法构建 (prepare parameterize batch update query)
                 */
                type = Executor.class,
                method = "update",
                args = {MappedStatement.class,Object.class
                })
})
public class MyInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // notice 拦截方法，匹配“method”中设定的方法时执行
        // notice 编写业务逻辑的位置
        System.out.println("拦截到你了！");
        // notice return的值是invocation.proceed()
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        // notice 创建target对象的代理对象，目的是将当前拦截器加入该对象中（共执行四次plugin函数，对应四种可拦截的方法）
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        // 属性设置
        Interceptor.super.setProperties(properties);
    }
}
