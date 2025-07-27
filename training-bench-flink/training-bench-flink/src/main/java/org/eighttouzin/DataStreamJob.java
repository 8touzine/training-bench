package org.eighttouzin;

import org.apache.avro.generic.GenericRecord;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.GlobalConfiguration;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.eighttouzin.configuration.Config;
import org.eighttouzin.configuration.SinkConfig;
import org.eighttouzin.util.AvroDeserialiszationSchema;
import training_bench_connect.public$.members.Envelope;
import training_bench_connect.public$.members.Key;
import training_bench_connect.public$.members.KeyAndEnvelope;
import training_bench_connect.public$.members.Value;


public class DataStreamJob {

    private static Configuration config;

    private static KafkaSink<KeyAndEnvelope> createdMemberSink;
    private static KafkaSink<KeyAndEnvelope> changedMemberSink;
    private static KafkaSink<KeyAndEnvelope> deletedEventSink;

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        config = Config.setup();

        createdMemberSink = SinkConfig.getCreateSink();
        changedMemberSink = SinkConfig.getChangeeSink();
        deletedEventSink = SinkConfig.getDeletedSink();

        System.out.println("bootstrap kafka: " + config.getString("bootstrap.servers", ""));

        KafkaSource<Tuple2<Key, Envelope>> source = KafkaSource.<Tuple2<Key, Envelope>>builder()
                .setBootstrapServers(config.getString("bootstrap.servers", "localhost:29092"))
                .setTopics(config.getString("topic.member.in", ""))
                .setGroupId(config.getString("group.id", ""))
                .setDeserializer(new AvroDeserialiszationSchema(config))
                .setStartingOffsets(OffsetsInitializer.earliest())
                .build();


        DataStreamPipeline job = new DataStreamPipeline(
                env,
                source,
                createdMemberSink,
                changedMemberSink,
                deletedEventSink);


        job.execute();
    }


}