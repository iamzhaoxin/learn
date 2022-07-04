package collections;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.fail;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/4
 */
public class ImmutableCollectionsTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testOf(){
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
        assertThat(list,notNullValue());
        list.add(3);
        fail();
    }

    @Test
    public void testCopy(){
        Integer[] array={1,2,3,4,5};
        assertThat(ImmutableList.copyOf(array).toString(),is("[1, 2, 3, 4, 5]"));
    }

    @Test
    public void testBuilder(){
        ImmutableList<Integer> list=ImmutableList.<Integer>builder()
                .add(1)
                .add(2,3,4).addAll(Arrays.asList(5,6))
                .build();
        assertThat(list.toString(),is("[1, 2, 3, 4, 5, 6]"));
    }

    // ImmutableMap can also use chained mode to build.

    /**
     * JDK-sort: null-value can not be sorted
     */
    @Test(expected=NullPointerException.class)
    public void testJDKOrderIssue(){
        List<Integer> list = Arrays.asList(1, 5, null, 3, 8, 2);
        Collections.sort(list);
    }

    /**
     * guava can do it
     */
    @Test
    public void testOrderNaturalByNullFirst(){
        List<Integer> list = Arrays.asList(1, 5, null, 3, 8, 2);
        Collections.sort(list, Ordering.natural().nullsFirst());
        assertThat(Ordering.natural().nullsFirst().isOrdered(list),is(true));
    }


}
