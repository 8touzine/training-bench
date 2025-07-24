package org.eighttouzin.engine;

import lombok.var;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import training_bench_connect.public$.members.Envelope;
import training_bench_connect.public$.members.Key;
import training_bench_connect.public$.members.KeyAndEnvelope;

public class MemberClassifier implements FlatMapFunction<Tuple2<Key, Envelope>, KeyAndEnvelope> {


    @Override
    public void flatMap(Tuple2<Key, Envelope> keyEnvelopeTuple2, Collector<KeyAndEnvelope> collector) throws Exception {
        String typeEvent = keyEnvelopeTuple2.f1.getOp();
        switch (typeEvent){
            case "c":
                keyEnvelopeTuple2.f1.setOp("CREATE_EVENT");
                break;
            case "u":
                keyEnvelopeTuple2.f1.setOp("UPDATE_EVENT");
                break;
            case "r":
                keyEnvelopeTuple2.f1.setOp("DELETE_EVENT");
                break;
            default:
                throw new Exception("error on event Type");
        }
        KeyAndEnvelope keyAndEnvelope = new KeyAndEnvelope();
        keyAndEnvelope.setKey(keyEnvelopeTuple2.f0);
        keyAndEnvelope.setEnvelope(keyEnvelopeTuple2.f1);
        collector.collect(keyAndEnvelope);
    }
}
