package java;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @author xzhao9
 * @since 2022-07-13 13:37
 **/
@Slf4j
public class NumberTest {

    /**
     * 构建Long
     */
    @Test
    public void intOverFlow(){
        long millisOfYears=5000*365*24*3600*1000;   //溢出
        long millisOfYears2=5000*365*24*3600*1000L; //乘1000之前，已经溢出
        long millisOfYears3=5000L*365*24*3600*1000;
    }

    /**
     * BigDecimal 精确度
     */
    @Test
    public void lostAccurate(){
        double a=0.58;
        System.out.println(new BigDecimal(0.58)); //0.5799999,因为0.58本就是不准确的，所以构造出的BigDecimal也是不准的
        //准确构造：valueOf
        BigDecimal x=BigDecimal.valueOf(0.580); //构造时，会去掉后面的0，损失精度
        System.out.println(x);  //0.58
        BigDecimal y=new BigDecimal("0.580"); //不会损失精度
        System.out.println(y);  //0.580
        System.out.println(x.equals(y));    //false, equals比较精度和数值
        System.out.println(x.compareTo(y)); //0, 只比较数值
    }

    /**
     * BigDecimal处理无限循环小数
     */
    @Test
    public void divideTest(){
        BigDecimal a=BigDecimal.valueOf(1);
        BigDecimal b=BigDecimal.valueOf(3);
        //System.out.println(a.divide(b));    //报错，无法整除，需要保留精度
        System.out.println(a.divide(b, MathContext.DECIMAL64));
    }



}
