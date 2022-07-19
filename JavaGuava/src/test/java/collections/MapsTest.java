package collections;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/4
 */
public class MapsTest {

    @Test
    public void testCreate() {
        ArrayList<String> valuesList = Lists.newArrayList("1", "2", "3");
        // element of list is the value of map, use function to generate the key of value
        ImmutableMap<String, String> immutableMap = Maps.uniqueIndex(valuesList, v -> v + "_key");
        assertThat(immutableMap.toString(), is("{1_key=1, 2_key=2, 3_key=3}"));

        // element of set is key, generate opposite value
        Map<String, String> asMap = Maps.asMap(Sets.newHashSet("1", "2", "3"), k -> k + "_value");
        assertThat(asMap.toString(), is("{1=1_value, 2=2_value, 3=3_value}"));
    }

    @Test
    public void testTransform() {
        Map<String, String> map = Maps.asMap(Sets.newHashSet("1", "2", "3"), k -> k + "_value");
        // transform the entries or values
        Map<String, String> transformValues = Maps.transformValues(map, v -> v + "_transform");
        assertThat(transformValues.toString(), is("{1=1_value_transform, 2=2_value_transform, 3=3_value_transform}"));
    }

    @Test
    public void testFilter() {
        Map<String, String> map = Maps.asMap(Sets.newHashSet("1", "2", "3"), k -> k + "_value");
        // filter the entries or values or keys
        Map<String, String> filterMap = Maps.filterKeys(map, k -> Lists.newArrayList("1", "2").contains(k));
        assertThat(filterMap.toString(), is("{1=1_value, 2=2_value}"));
    }

    @Test
    public void testLinkedListMultimap() {
        HashMap<String, String> hashMap = Maps.newHashMap();
        hashMap.put("1", "1");
        hashMap.put("1", "2");
        assertThat(hashMap.size(), is(1));

        // multimap: each key may be associated with multiple values
        // like: Map<T,List<P>>
        LinkedListMultimap<String, String> linkedListMultimap = LinkedListMultimap.create();
        linkedListMultimap.put("1", "1");
        linkedListMultimap.put("1", "2");
        assertThat(linkedListMultimap.size(), is(2));
    }

    /**
     * BiMap
     * A bimap is a map that preserves the uniqueness of its values as well as that of its keys
     * <p>
     * HashBiMap
     * allow null keys and values
     */
    @Test
    public void testBiMap() {
        HashBiMap<String, String> hashBiMap = HashBiMap.create();
        hashBiMap.put("1", "2");
        hashBiMap.put("1", "3");
        assertThat(hashBiMap.size(), is(1));

        try {
            hashBiMap.put("2", "3");
            fail();
        } catch (Exception e) {
            assertThat(e.getClass(), is(IllegalArgumentException.class));
        }
    }

    @Test
    public void testBiMapInverse() {
        HashBiMap<String, String> hashBiMap = HashBiMap.create();
        hashBiMap.put("1", "4");
        hashBiMap.put("2", "5");
        hashBiMap.put("3", "6");

        BiMap<String, String> inverseBiMap = hashBiMap.inverse();
        assertThat(inverseBiMap.containsKey("4"),is(true));
        assertThat(inverseBiMap.containsKey("5"),is(true));
        assertThat(inverseBiMap.containsKey("6"),is(true));
    }

    /**
     * force put key-value pair with duplicate value in BiMap
     */
    @Test
    public void testCreateAndForcePut(){
        HashBiMap<String, String> hashBiMap = HashBiMap.create();
        hashBiMap.put("1", "2");

        hashBiMap.forcePut("3","2");
        assertThat(hashBiMap.containsKey("3"),is(true));

    }
}
