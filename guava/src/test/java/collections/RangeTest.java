package collections;

import com.google.common.collect.BoundType;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.TreeRangeMap;
import org.checkerframework.dataflow.qual.TerminatesExecution;
import org.junit.Test;

import java.util.NavigableMap;
import java.util.TreeMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/4
 */
public class RangeTest {

    /**
     * also hava:
     * .closed()
     * .open()
     * .OpenClosed()
     * ...
     */
    @Test
    public void testClosedOpenRange(){
        Range<Integer> range = Range.closedOpen(0, 9);

        assertThat(range.contains(9),is(false));
        assertThat(range.lowerEndpoint(),is(0));
    }

    @Test
    public void testGreaterThan(){
        assertThat(Range.greaterThan(10).contains(Integer.MAX_VALUE),is(true));
    }

    @Test
    public void testMapRange(){
        TreeMap<String,Integer> treeMap= Maps.newTreeMap();

        treeMap.put("B",1);
        treeMap.put("A",3);
        treeMap.put("C",4);
        assertThat(treeMap.toString(),is("{A=3, B=1, C=4}"));

        NavigableMap<String,Integer> result=Maps.subMap(treeMap,Range.openClosed("B","C"));
        assertThat(result.toString(),is("{C=4}"));
    }

    @Test
    public void testOther(){
        assertThat(Range.atLeast(2).toString(),is("[2..+∞)"));
        assertThat(Range.lessThan(10).toString(),is("(-∞..10)"));
        assertThat(Range.atMost(5).toString(),is("(-∞..5]"));
        assertThat(Range.all().toString(),is("(-∞..+∞)"));
        assertThat(Range.downTo(10, BoundType.CLOSED).toString(),is("[10..+∞)"));
        assertThat(Range.upTo(10,BoundType.OPEN).toString(),is("(-∞..10)"));
    }

    @Test
    public void testRangeMap(){
        TreeRangeMap<Integer, String> treeRangeMap = TreeRangeMap.create();
        treeRangeMap.put(Range.closedOpen(0,60),"flunk");
        treeRangeMap.put(Range.closedOpen(60,80),"well");
        treeRangeMap.put(Range.closed(80,100),"great");

        assertThat(treeRangeMap.get(77),is("well"));
    }
}
