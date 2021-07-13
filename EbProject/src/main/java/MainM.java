import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.IOException;

public class MainM {
    public static void main(String[] args) throws IOException {
//            boolean lagou_area_exist = HbaseUtil.isTableExist("lagou_area");
//            System.out.println(lagou_area_exist);

//            HbaseUtil.createTable("lagou_trade_orders","f1");
//            HbaseUtil.dropTable("lagou_trade_orders");
            HbaseUtil hbaseUtil;
            HbaseUtil.getAllRows("dim_lagou_area");

//        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
//        DataSource<String> data = env.readTextFile("");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> data = env.socketTextStream("", 7777);

        SingleOutputStreamOperator<Tuple2<String, String>> maped = data.map(new MapFunction<String, Tuple2<String, String>>() {
            @Override
            public Tuple2<String, String> map(String value) throws Exception {
                return null;
            }
        });

        /*FlatMapOperator<String, Tuple2<String, String>> mapedd = data.flatMap(new FlatMapFunction<String, Tuple2<String, String>>() {
            @Override
            public void flatMap(String value, Collector<Tuple2<String, String>> out) throws Exception {

            }
        });

        MapOperator<String, Tuple2<String, String>> maped = data.map(new MapFunction<String, Tuple2<String, String>>() {
            @Override
            public Tuple2<String, String> map(String value) throws Exception {
                return null;
            }
        });*/
    }
}
