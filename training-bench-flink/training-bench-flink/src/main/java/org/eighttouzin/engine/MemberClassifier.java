package org.eighttouzin.engine;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import training_bench_connect.public$.members.Envelope;
import training_bench_connect.public$.members.Key;

public class MemberClassifier extends KeyedProcessFunction<Integer, Tuple2<Key, Envelope>, Envelope> {


    @Override
    public void processElement(Tuple2<Key, Envelope> keyEnvelopeTuple2,
                               KeyedProcessFunction<Integer, Tuple2<Key, Envelope>,
                                       Envelope>.Context context,
                               Collector<Envelope> collector) throws Exception {

    }
}
