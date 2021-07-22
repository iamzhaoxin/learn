package com.lagou.time;

import net.minidev.json.JSONUtil;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.eventtime.*;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.FoldFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.ConfigConstants;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;

import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;

public class WatermarkDemo {
    /**
     * 1、获取数据源
     * <p>
     * 2、转化
     * <p>
     * 3、声明水印（watermark）
     * <p>
     * 4、分组聚合，调用window的操作
     * <p>
     * 5、保存处理结果
     */
    public static void main(String[] args) throws Exception {
        /*ParameterTool parameters = ParameterTool.fromArgs(args);
        String local_path = parameters.get("local_path",null);  //指定参数名：local_path
        //读取配置文件
        ParameterTool paramFromProps = ParameterTool.fromPropertiesFile(local_path);*/

        Configuration config = new Configuration();
//        config.setBoolean(ConfigConstants.LOCAL_START_WEBSERVER, true);
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(config);
        ExecutionConfig c = env.getConfig();
        c.setAutoWatermarkInterval(500);

        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);
        env.enableCheckpointing(10);
        env.setStateBackend(new FsStateBackend("file:////tmp/lucasdb"));


//        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
//        env.getConfig().setAutoWatermarkInterval(1000L);
//        env.setParallelism(1);

//        String hostname = "hdp-1";
        String hostname = "linux121";

        DataStreamSource<String> dataFromSocket = env.socketTextStream(hostname, 7777);

        /*Properties props = new Properties();
        props.setProperty("bootstrap.servers","hdp-2:9092");
        props.setProperty("group.id","mygg");
        FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<>("animalone", new SimpleStringSchema(), props);
        DataStreamSource<String> dataFromKafka = env.addSource(kafkaConsumer);*/

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        //只执行一遍
//        System.out.println("----------------------" + data.toString());
        SingleOutputStreamOperator<Tuple2<String, Long>> maped = dataFromSocket.map(new MapFunction<String, Tuple2<String, Long>>() {
            @Override
            public Tuple2<String, Long> map(String value) throws Exception {
                String[] split = value.split(",");
                return new Tuple2<String, Long>(split[0], Long.valueOf(split[1]));
            }
        });

        System.out.println("parallel:" + env.getParallelism());

        SingleOutputStreamOperator<Tuple2<String, Long>> watermarks = maped.assignTimestampsAndWatermarks(new WatermarkStrategy<Tuple2<String, Long>>() {
            @Override
            public WatermarkGenerator<Tuple2<String, Long>> createWatermarkGenerator(WatermarkGeneratorSupplier.Context context) {
                return new WatermarkGenerator<Tuple2<String, Long>>() {
                    //初始值
                    private long maxTimeStamp = 0L;
                    //                    private long maxTimeStamp = Long.MIN_VALUE;
                    long maxOutOfOrderness = 1000;

                    @Override
                    public void onEvent(Tuple2<String, Long> event, long eventTimestamp, WatermarkOutput output) {
                        maxTimeStamp = Math.max(maxTimeStamp, event.f1);
                        System.out.println("maxTimeStamp:" + maxTimeStamp + "...format:" + sdf.format(maxTimeStamp));//+ "...parallel:" + env.getParallelism()
                    }

                    @Override
                    public void onPeriodicEmit(WatermarkOutput output) {
//                        System.out.println(".....onPeriodicEmit....");

                        Watermark watermark = new Watermark(maxTimeStamp - maxOutOfOrderness);
//                        System.out.println("水印时间："+watermark.getTimestamp() + "...: " + (maxTimeStamp - maxOutOfOrderness));
                        output.emitWatermark(watermark);
                    }
                };
            }
        }.withTimestampAssigner(new SerializableTimestampAssigner<Tuple2<String, Long>>() {
            @Override
            public long extractTimestamp(Tuple2<String, Long> element, long recordTimestamp) {
                return element.f1;
            }
        }));

        KeyedStream<Tuple2<String, Long>, String> keyed = watermarks.keyBy(value -> value.f0);
//        System.out.println("...keyed:" + keyed);

        WindowedStream<Tuple2<String, Long>, String, TimeWindow> windowed = keyed.window(TumblingEventTimeWindows.of(Time.seconds(5)));
//        WindowedStream<Tuple2<String, Long>, String, TimeWindow> windowed = keyed.timeWindow(Time.seconds(5));

        /*windowed.fold("Start:", new FoldFunction<Tuple2<String, String>, String>() {
            @Override
            public String fold(String s, Tuple2<String, String> o) throws Exception {
                return s + " - " + o.f1;
            }
        }).print();*/

        /*windowed.aggregate(new AggregateFunction<Tuple2<String, Long>, Object, Object>() {
            @Override
            public Object createAccumulator() {
                return null;
            }

            @Override
            public Object add(Tuple2<String, Long> value, Object accumulator) {
                return null;
            }

            @Override
            public Object getResult(Object accumulator) {
                return null;
            }

            @Override
            public Object merge(Object a, Object b) {
                return null;
            }
        })*/

        /*SingleOutputStreamOperator<String> result = windowed.apply(new WindowFunction<Tuple2<String, Long>, String, String, TimeWindow>() {
            @Override
            public void apply(String s, TimeWindow window, Iterable<Tuple2<String, Long>> input, Collector<String> out) throws Exception {
                System.out.println(window.getStart() + "..." + window.getEnd());
                out.collect(window.getStart() + ".." + window.getEnd());
            }
        });*/

        SingleOutputStreamOperator<String> result = windowed.apply(new WindowFunction<Tuple2<String, Long>, String, String, TimeWindow>() {
            @Override
            public void apply(String s, TimeWindow window, Iterable<Tuple2<String, Long>> input, Collector<String> out) throws Exception {

                System.out.println("..." + sdf.format(window.getStart()));
                String key = s;
                Iterator<Tuple2<String, Long>> iterator = input.iterator();
                ArrayList<Long> list = new ArrayList<>();
                while (iterator.hasNext()) {
                    Tuple2<String, Long> next = iterator.next();
                    list.add(next.f1);
                }
                Collections.sort(list);
                String result = "key:" + key + "..." + "list.size:" + list.size() + "...list.first:" + sdf.format(list.get(0)) + "...list.last:" + sdf.format(list.get(list.size() - 1)) + "...window.start:" + sdf.format(window.getStart()) + "..window.end:" + sdf.format(window.getEnd());
                out.collect(result);
            }
        });

        result.print();
        env.execute("name2");
    }

}


















