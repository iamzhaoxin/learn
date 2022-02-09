package aop.anno;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/9 11:21
 */
@Component("myAspectAnno")
@Aspect //标注为切面类
public class MyAspect {

    /*
        注解通知的类型
            @Before
            @AfterReturning
            @Around
            @AfterThrowing
            @After
     */
    //注意 value的值是PointCut
    @Before(value = "execution(* controller..*.*(..))")
    public void before() {
        System.out.println("注解方式的前置增强");
    }

    //切点表达式的抽取
    @Pointcut("execution(* controller..*.*(..))")
    public void pointCut1() {
    }

    @Before("pointCut1()")
    public void before2() {
        System.out.println("注解方式的前置增强");
    }

    @Before("aop.anno.MyAspect.pointCut1()")
    public void before3() {
        System.out.println("注解方式的前置增强");
    }
}
