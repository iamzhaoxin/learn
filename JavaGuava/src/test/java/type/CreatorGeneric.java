package type;

/**
 * 模板设计模式
 * 具体类型的创建放到了子类继承父类时，在 create 方法中创建实际的类型并返回。
 *
 * @author xzhao9
 * @since 2022-07-19 15:42
 **/
abstract class GenericWithCreate<T> {
    final T element;
    GenericWithCreate() { element = create(); }
    abstract T create();
}
class X {}
class Creator extends GenericWithCreate<X> {
    X create() { return new X(); }
    void f() {
        System.out.println(element.getClass().getSimpleName());
    }
}
public class CreatorGeneric {
    public static void main(String[] args) {
        Creator c = new Creator();
        c.f();
    }
}