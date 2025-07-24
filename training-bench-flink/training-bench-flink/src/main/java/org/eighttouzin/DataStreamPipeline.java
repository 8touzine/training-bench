package org.eighttouzin;

import lombok.AllArgsConstructor;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.table.KafkaDynamicSink;
import org.eighttouzin.engine.MemberClassifier;
import training_bench_connect.public$.members.Envelope;
import training_bench_connect.public$.members.Key;
import training_bench_connect.public$.members.KeyAndEnvelope;

@AllArgsConstructor
public class DataStreamPipeline {

    private final StreamExecutionEnvironment env;
    private final DataStream<Tuple2<Key, Envelope>> members;


    public JobExecutionResult execute() throws Exception{

        SingleOutputStreamOperator<KeyAndEnvelope>
                classifiedMember =
                members
                .flatMap(new MemberClassifier());



        return env.execute("event enrichement and sorting");

    }
}
