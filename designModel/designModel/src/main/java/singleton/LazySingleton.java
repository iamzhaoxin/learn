package singleton;

/**
 * volatile
 *  - 没有原子性，所以不能 i++操作，但可以=true/false
 *  - 可见性： 多个线程可见
 *  - 有序性： 禁止对指令重排序
 *
 * @Author: 赵鑫
 * @Date: 2022/7/21
 */
public class LazySingleton {
    private volatile static LazySingleton instance;
    private LazySingleton(){}

    public static LazySingleton getInstance(){
        if(instance==null){
            synchronized (LazySingleton.class){
                if(instance==null){
                    instance=new LazySingleton();
                    /*
                    字节码 初始化过程：分配空间->初始化->引用赋值
                    可能重排序为：分配空间->引用赋值->初始化。如果在引用赋值后，有另一个线程调用，但还没有完成初始化，可能会导致空指针异常NPE
                     */
                }
            }
        }
        return instance;
    }
}
