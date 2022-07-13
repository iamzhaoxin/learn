package utilities;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.junit.Test;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/1
 */
public class ObjectsExample {

    // https://www.bilibili.com/video/BV1R4411s7GX?p=4&share_source=copy_web

    @Test
    public void equalNullable(){
        String a=null;
        String b="1";
        //System.out.println(a.equals(b)); // 不能调用null的equals方法
        System.out.println(Objects.equal(a,b));
    }

    @Test
    public void firstNonNull(){
        System.out.println(MoreObjects.firstNonNull(null,1));
        // System.out.println(MoreObjects.firstNonNull(null,null)); //空指针报错
    }
}
