package basic.arrays;

import java.util.Arrays;

/**
 * @author zhaoxin
 */
public class Comparator{
    public static void main(String[] args) {
        Integer[] a={12,34,15,36};
        Arrays.sort(a, new java.util.Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -(o1-o2);
            }
        });
        //System.out.println(a);会输出地址
        System.out.println(Arrays.toString(a));
    }
}
