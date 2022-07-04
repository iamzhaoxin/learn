package collections;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/4
 */
public class SetsTest {
    @Test
    public void testCreate(){
        HashSet<Integer> hashSet = Sets.newHashSet(1, 2, 3);
        assertThat(hashSet.size(),is(3));

        ArrayList<Integer> arrayList = Lists.newArrayList(1, 1, 2, 3);
        // remove duplicate elements from the Iterable object
        HashSet<Integer> newHashSet = Sets.newHashSet(arrayList);
        assertThat(newHashSet.size(),is(3));
    }
}
