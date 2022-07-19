package type;

import java.lang.reflect.Array;

/**
 * 使用 Class 对象作为类型标识是更好的设计
 *
 * @author xzhao9
 * @since 2022-07-19 15:35
 **/
public class GenericArrayWithTypeToken<T> {

    private final T[] array;

    // 构造器中传入Class<t>对象，用Array.newInstance()使其作为数组元素的组件类型
    // 这样创建出的数组的元素类型就是T，但返回的是Object对象
    public GenericArrayWithTypeToken(Class<T> type, int sz) {
        // 因为返回的是Object数组，这里需要转型
        array = (T[]) Array.newInstance(type, sz);
    }

    public void put(int index, T item) {
        array[index] = item;
    }

    // 因为元素实际类型是T，所以其他操作不需要转型
    public T get(int index) {
        return array[index];
    }

    // Expose the underlying representation:
    public T[] rep() {
        return array;
    }

    public static void main(String[] args) {
        GenericArrayWithTypeToken<Integer> gai = new GenericArrayWithTypeToken<>(Integer.class, 10);
        // This now works:
        Integer[] ia = gai.rep();
    }
}
