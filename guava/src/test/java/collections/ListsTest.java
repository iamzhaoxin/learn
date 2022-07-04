package collections;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/4
 */
public class ListsTest {

    @Test
    public void testCartesianProduct() {
        // Cartesian
        List<List<String>> result = Lists.cartesianProduct(
                Lists.newArrayList("1", "2"),
                Lists.newArrayList("A", "B")
        );
        assertThat(result.toString(), is("[[1, A], [1, B], [2, A], [2, B]]"));
    }

    @Test
    public void testTransform() {
        ArrayList<String> sourceList = Lists.newArrayList("Scala", "Guava", "Lists");
        // transform element of collection
        List<String> transformResult = Lists.transform(sourceList, e -> e.toUpperCase());
        assertThat(transformResult.toString(), is("[SCALA, GUAVA, LISTS]"));
    }

    @Test
    public void testReverse() {
        List<String> reverse = Lists.reverse(Lists.newArrayList("Scala", "Guava", "Lists"));
        assertThat(reverse.toString(), is("[Lists, Guava, Scala]"));
    }
}
