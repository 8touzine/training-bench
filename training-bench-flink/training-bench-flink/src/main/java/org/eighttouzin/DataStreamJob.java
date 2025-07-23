package org.eighttouzin;

import org.apache.avro.generic.GenericRecord;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.GlobalConfiguration;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.eighttouzin.configuration.Config;
import org.eighttouzin.util.AvroDeserialiszationSchema;
import training_bench_connect.public$.members.Envelope;
import training_bench_connect.public$.members.Key;
import training_bench_connect.public$.members.Value;


public class DataStreamJob {

    private static Configuration config;

    public static void main(String[] args) {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        config = Config.setup();

        KafkaSource<Tuple2<Key, Envelope>> source = KafkaSource.<Tuple2<Key, Envelope>>builder()
                .setBootstrapServers(config.getString("bootstrap.servers", ""))
                .setTopics(config.getString("topic.member.in", ""))
                .setGroupId(config.getString("group.id", ""))
                .setDeserializer(new AvroDeserialiszationSchema(config))
                .build();





    }
}