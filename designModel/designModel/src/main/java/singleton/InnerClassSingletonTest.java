package singleton;

/**
 * 3. 静态内部类模式
 * 利用类的加载机制保证线程安全：
 *      jvm加载外部类时，不会主动加载内部类；只有内部类被调用时才加载
 *      如果多个线程同时去初始化一个类，只有一个线程会去执行这个类的<clinit>()方法，
 *      其他线程阻塞等待。执行完<clinit>()方法后，其他线程不会再进入<clinit>()。（同一个加载器下，一个static类只初始化一次，并保证实例化顺序（不会指令重排序）
 *      （这种阻塞等待是隐蔽的，可能造成多个线程阻塞）
 * 只有在实际使用时才会触发类的初始化，所以也是懒加载的一种形式
 *
 * @Author: 赵鑫
 * @Date: 2022/7/21
 */
public class InnerClassSingletonTest {
    public static void main(String[] args) {
        //此时，内部类InnerClassHolder不会初始化

        // 调用getInstance方法时，InnerClassHolder初始化
        InnerClassSingleton.getInstance();
    }
}

class InnerClassSingleton {
    private static class InnerClassHolder{
        private static final InnerClassSingleton instance=new InnerClassSingleton();
    }
    private InnerClassSingleton(){}

    public static InnerClassSingleton getInstance(){
        return InnerClassHolder.instance;
    }
}
