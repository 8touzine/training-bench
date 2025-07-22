package org.eighttouzin;

import org.apache.avro.generic.GenericRecord;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.GlobalConfiguration;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.eighttouzin.configuration.Config;
import org.eighttouzin.util.AvroDeserialiszationSchema;
import training_bench_connect.public$.members.Value;


public class DataStreamJob {

    private static Configuration config;

    public static void main(String[] args) {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        config = Config.setup();

        KafkaSource<GenericRecord> source = KafkaSource.<GenericRecord>builder()
                .setBootstrapServers(config.getString("bootstrap.servers", ""))
                .setGroupId(config.getString("group.id", ""))
                .setValueOnlyDeserializer(new AvroDeserialiszationSchema())
                .setTopics(config.getString("topic.member.in", ""))
                .build();





    }
}