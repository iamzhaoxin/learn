package type;

import java.util.StringTokenizer;

/**
 * @author xzhao9
 * @since 2022-07-19 15:37
 **/
interface FactoryI<T> {
    T create();
}
class Foo2<T> {
    private final T x;
    /*
     * <F extends FactoryI<T>>
     * 放置了一个约束，即构造参数的类型F必须是继承自FactoryI<T>的
     */
    public <F extends FactoryI<T>> Foo2(F factory) {
        x = factory.create();
    }
    @Override
    public String toString(){
        return x.getClass().getName();
    }
}
class IntegerFactory implements FactoryI<Integer> {
    public Integer create() {
        return new Integer(0);
    }
}
class Widget {
    public static class Factory implements FactoryI<Widget> {
        public Widget create() {
            return new Widget();
        }
    }
}
public class FactoryConstraint {
    public static void main(String[] args) {
        System.out.println(new Foo2<>(new IntegerFactory())); //java.lang.Integer
        System.out.println(new Foo2<>(new Widget.Factory()));  //type.Widget


    }
}