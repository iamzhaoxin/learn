package basic.exception;

/**
 * @author zhaoxin
 */
public class ExceptionDemo {

    public static void main(String[] args){
        checkAge(-34);
    }

    public static void checkAge(int age) throws AgeIllegalException {
        if (age < 0 || age > 200) {
            //抛出一个异常给调用者
            //throw：方法内部创建一个异常对象并抛出
            //throws：用在方法申明上，抛出方法内部的异常
            throw new AgeIllegalException(age + " is illegal");
        } else {
            System.out.println("年龄合法");
        }
    }
}
//输出报错：
// -34 is illegal