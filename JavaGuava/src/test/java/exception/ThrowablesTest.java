package exception;

import com.google.common.base.Throwables;
import org.junit.Test;

/**
 *
 * getCausalChain 得到异常链
 * getRootCause 得到原始异常（最早的throw点）
 * propagete 将Exception包装成RuntimeError或Error，简化异常处理
 *
 * @author xzhao9
 * @since 2022-07-14 12:44
 **/
public class ThrowablesTest {
    @Test
    public void throwablesTest(){
        Object object=new Object();
        try{
            throw new IndexOutOfBoundsException();
        }catch (IllegalArgumentException e){
            // 对IllegalArgumentException的异常处理
        }catch (Throwable t){
            // 如果是IndexOutOfBoundsException，则throw
            Throwables.throwIfInstanceOf(t,IndexOutOfBoundsException.class);
            // 否则包装成RuntimeException
            throw new RuntimeException(t);
        }
    }
}
