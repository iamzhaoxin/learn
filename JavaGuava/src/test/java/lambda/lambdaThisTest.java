package lambda;

import org.junit.Test;

/**
 * lambda表达式中的this是外部类的this
 * 匿名内部类的this就是内部类的this
 *
 * @author xzhao9
 * @since 2022-07-15 14:28
 **/
public class lambdaThisTest {

    @Test
    public void anonymous_inner_class_this_Test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("In anonymous inner class this: " + this);
            }

            @Override
            public String toString() {
                return "是内部类的this";
            }
        }).start();

        new Thread(() -> {
            System.out.println("In lambda this: " + this);
        }).start();
    }

    @Override
    public String toString(){
        return "外部类";
    }
}
