package collections;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * ArrayTable
 * TreeBaseTable
 * HashBaseTable
 * ImmutableTable
 *
 * @Author: 赵鑫
 * @Date: 2022/7/4
 */
public class TableTest {

    /**
     * like: Map<P,Map<V,T>>
     */
    @Test
    public void test(){
        Table<String,String,String> table= HashBasedTable.create();
        table.put("Language","Java","1.8");
        table.put("Language","Scala","2.3");
        table.put("Database","Oracle","12C");
        table.put("Database","Mysql","7.0");

        Map<String, String> rowLanguage = table.row("Language");
        assertThat(rowLanguage.containsKey("Java"),is(true));
        assertThat(table.column("Java").get("Language"),is("1.8"));
    }


}
