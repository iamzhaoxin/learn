package collections;

import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author xzhao9
 * @since 2022-07-13 17:29
 **/
public class IntsTest {
    @Test
    public void asList(){
        int[] array=new int[]{1,2,3};
        // 将整个数组转化为list的第一个元素
        System.out.println(Arrays.asList(array));
        int[] firstElementOfList = Arrays.asList(array).get(0);
        System.out.println(Arrays.toString(firstElementOfList));
        // 转化为list多个元素,注意：返回的是不可变list，无法使用add函数添加元素
        System.out.println(Ints.asList(array));
    }
}
