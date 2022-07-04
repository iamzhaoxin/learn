package collections;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/4
 */
public class FluentIterableTest {

    private FluentIterable<String> build(){
        ArrayList<String> list= Lists.newArrayList("Alex","Wang","Guava","Scala");
        // list -> FluentIterable
        return FluentIterable.from(list);
    }

    @Test
    public void testFilter(){
        FluentIterable<String> fit=build();
        assertThat(fit.size(),equalTo(4));
        // use filter, return a new FluentIterable
        FluentIterable<String> newFIt=fit.filter(e->e!=null&&e.length()>4);
        assertThat(fit.size(),equalTo(4));
        assertThat(newFIt.size(),equalTo(2));
    }

    @Test
    public void testAppend(){
        FluentIterable<String> fit=build();
        assertThat(fit.size(),is(4));
        // append a list
        ArrayList<String> appendList=Lists.newArrayList("APPEND");
        FluentIterable<String>  appendFIt=fit.append(appendList);
        assertThat(appendFIt.size(),is(5));
        // append string
        appendFIt=appendFIt.append("APPEND2");
        assertThat(appendFIt.size(),is(6));
    }

    @Test
    public void testMatch(){
        FluentIterable<String> fit=build();
        // whether all match
        boolean result=fit.allMatch(e->e!=null&&e.length()>=4);
        assertThat(result,is(true));
        // if any of them match
        result=fit.anyMatch((e->e!=null&&e.length()==5));
        assertThat(result,is(true));
        // return the first matched element
        Optional<String> optional=fit.firstMatch(e->e!=null&&e.length()==5);
        assertThat(optional.get(),is("Guava"));
    }

    @Test
    public void testFirst_Last(){
        FluentIterable<String> fit=build();
        Optional<String> optional=fit.first();
        // assert not null
        assert optional.isPresent();
        assertThat(optional.get(),is("Alex"));
        optional=fit.last();
        assertThat(optional.get(),is("Scala"));
    }

    @Test
    public void testCopyIn(){
        FluentIterable<String> fit=build();
        //ArrayList<String> list= (ArrayList<String>) Arrays.asList("Java");
        ArrayList<String> list=Lists.newArrayList("Java");

        // copy the FluentIterable into a new collection
        list=fit.copyInto(list);
        assertThat(list.size(),is(5));
    }

    @Test
    public void testCycle(){
        FluentIterable<String> fit=build();
        // loop string, limited in length(otherwise infinite
        FluentIterable<String> cycle=fit.cycle().limit(20);
        assertThat(cycle.get(0),equalTo(cycle.get(4)));
        assertThat(cycle.get(4),equalTo(cycle.get(8)));
        assertThat(cycle.get(8),equalTo(cycle.get(12)));
        assertThat(cycle.get(12),equalTo(cycle.get(16)));
    }

    @Test
    public void testTransformAndConcat(){
        FluentIterable<String> fit=build();
        List<Integer> list=Lists.newArrayList(1,2,3);

        // transform every element to something by function(such as turn into a list), then concat them
        FluentIterable<Integer> result=fit.transformAndConcat(e->list);
        // 4*3=12
        assertThat(result.size(),is(12));
    }

    @Test
    public void testJoin(){
        FluentIterable<String> fit=build();
        String result=fit.join(Joiner.on(","));
        assertThat(result,is("Alex,Wang,Guava,Scala"));
    }

}
