package basic.lambda;

/**
 * @author zhaoxin
 */
public class Lambda {
    public static void main(String[] args) {
        //初始
        Swimming s = new Swimming() {
            @Override
            public void swim(String name) {
                System.out.println(name + "swimming");
            }
        };
        go(s, "小明");

        //Lambda
        go((String name) -> {
            System.out.println(name + "swimming");
        }, "小明");

        //Lambda简化：只有一个参数，参数类型省略，()省略
        //Lambda简化：只有一行代码，大括号省略，分号省略
        go(name -> System.out.println(name + "swimming"), "小明");
    }

    public static void go(Swimming s, String name) {
        s.swim(name);
    }
}

@FunctionalInterface //一旦加上这个注解，就必须是函数式接口，里面只能有一个抽象方法
interface Swimming {
    void swim(String name);
}
