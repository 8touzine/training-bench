package org.eighttouzin;

import lombok.AllArgsConstructor;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
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
    private final KafkaSource<Tuple2<Key, Envelope>> source;
    private  KafkaSink<KeyAndEnvelope> createdMemberSink;
    private  KafkaSink<KeyAndEnvelope> changedMemberSink;
    private  KafkaSink<KeyAndEnvelope> deletedEventSink;


    public JobExecutionResult execute() throws Exception{

        DataStream<Tuple2<Key, Envelope>> members = env.fromSource(
                source, WatermarkStrategy.noWatermarks(), "member-source"
        );


        SingleOutputStreamOperator<KeyAndEnvelope>
                classifiedMember =
                members
                .flatMap(new MemberClassifier())
                        .uid("enrich eventType field");

        DataStream<KeyAndEnvelope> memberCreated = classifiedMember
                .filter(member -> member.getEnvelope().getOp().equals("CREATE_EVENT"))
                .uid("member with create event");

        memberCreated.sinkTo(createdMemberSink)
                .uid("sink to created member topic");

        DataStream<KeyAndEnvelope> memberChanged = classifiedMember
                .filter(member -> member.getEnvelope().getOp().equals("CHANGE_EVENT"))
                .uid("member with change event");

        memberChanged.sinkTo(changedMemberSink)
                .uid("sink to changed member topic");

        DataStream<KeyAndEnvelope> memberDelete = classifiedMember
                .filter(member -> member.getEnvelope().getOp().equals("DELETE_EVENT"))
                .uid("member with delete event");

        memberDelete.sinkTo(deletedEventSink)
                .uid("sink to deleted member topic");



        return env.execute("event enrichement and sorting");

    }
}
