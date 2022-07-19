package utilities;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;


/**
 * 断言
 * Preconditions
 * Objects
 * assert key word(statement)
 * @Author: 赵鑫
 * @Date: 2022/7/1
 */
public class PreconditionsTest {

    /**
     * 判断非空
     */
    @Test(expected=NullPointerException.class)
    public void testCheckNotNull(){
        checkNotNull(null);
    }
    private void checkNotNull(final List<String> list) {
        Preconditions.checkNotNull(list);
    }

    /**
     * 带message的判断非空
     * （assertThat用instanceOf判断实例类型
     */
    @Test
    public void testCheckNotNullWithMessage(){
        try{
            checkNotNullWithMessage(null);
        }catch (Exception e){
            assertThat(e,instanceOf(NullPointerException.class));
            assertThat(e.getMessage(),equalTo("The list should not be null"));
        }
    }
    private void checkNotNullWithMessage(final List<String> list) {
        Preconditions.checkNotNull(list,"The list should not be null");
    }

    /**
     * 带参数的message
     */
    @Test
    public void testCheckNotNullWithFormatMessage(){
        try{
            checkNotNullWithFormatMessage(null);
        }catch (Exception e){
            assertThat(e,instanceOf(NullPointerException.class));
            assertThat(e.getMessage(),equalTo("The list should not be null, thread: 2"));
        }
    }
    private void checkNotNullWithFormatMessage(final List<String> list) {
        Preconditions.checkNotNull(list,"The list should not be null, thread: %s",2);
    }

    /**
     * 判断变量
     */
    @Test
    public void testCheckArguments(){
        final String type="A";
        try{
            Preconditions.checkArgument(type.equals("B"),"The state is illegal");
            fail("state error,should not process to here");
        }catch (Exception e){
            assertThat(e,instanceOf(IllegalArgumentException.class));
        }
    }

    /**
     * 下标索引
     */
    @Test
    public void testCheckIndex(){
        try {
            List<String> list = ImmutableList.of();
            Preconditions.checkElementIndex(10,list.size());
        }catch(Exception e){
            assertThat(e,instanceOf(IndexOutOfBoundsException.class));
        }
    }

    /**
     * 使用Objects类断言
     */
    @Test(expected = NullPointerException.class)
    public void testByObjects(){
        Objects.requireNonNull(null);
    }

    /**
     * s使用assert断言
     */
    @Test
    public void testAssertWithMessage(){
        try{
            List<String> list=null;
            assert list!=null : "The list should not be null";
        }catch (Error e){
            assertThat(e,instanceOf(AssertionError.class));
            assertThat(e.getMessage(),equalTo("The list should not be null"));
        }
    }

}
