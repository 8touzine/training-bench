package org.eighttouzin;

import lombok.AllArgsConstructor;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.eighttouzin.engine.MemberClassifier;
import training_bench_connect.public$.members.Envelope;
import training_bench_connect.public$.members.Key;

@AllArgsConstructor
public class DataStreamPipeline {

    private final StreamExecutionEnvironment env;
    private final DataStream<Tuple2<Key, Envelope>> members;


    public JobExecutionResult execute() throws Exception{

        SingleOutputStreamOperator<Envelope>
                classifiedMember =
                members
                .keyBy(member -> member.f0.getId())
                .process(new MemberClassifier())
                        .addSink();


    }
}
