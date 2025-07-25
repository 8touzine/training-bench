package org.eighttouzin.configuration;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.formats.avro.registry.confluent.ConfluentRegistryAvroSerializationSchema;
import training_bench_connect.public$.members.KeyAndEnvelope;

public class SinkConfig {
    private static Configuration config = Config.setup();

    public static KafkaSink getCreateSink(){
        return  KafkaSink.<KeyAndEnvelope>builder()
                .setBootstrapServers(config.getString("bootstrap.servers", ""))
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic(config.getString("topic.member.out.created", ""))// creer manuellement
                        .setValueSerializationSchema(
                                ConfluentRegistryAvroSerializationSchema.forSpecific(
                                        KeyAndEnvelope.class,
                                        config.getString("subject.created", ""),
                                        config.getString("schema.registery.url", "")
                                )
                        )
                        .build())
                .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .build();

    }
    public static KafkaSink getChangeeSink(){
        return  KafkaSink.<KeyAndEnvelope>builder()
                .setBootstrapServers(config.getString("bootstrap.servers", ""))
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic(config.getString("topic.member.out.changed", ""))// creer manuellement
                        .setValueSerializationSchema(
                                ConfluentRegistryAvroSerializationSchema.forSpecific(
                                        KeyAndEnvelope.class,
                                        config.getString("subject.changed", ""),
                                        config.getString("schema.registery.url", "")
                                )
                        )
                        .build())
                .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .build();

    }
    public static KafkaSink getDeletedSink(){
        return  KafkaSink.<KeyAndEnvelope>builder()
                .setBootstrapServers(config.getString("bootstrap.servers", ""))
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic(config.getString("topic.member.out.deleted", ""))// creer manuellement
                        .setValueSerializationSchema(
                                ConfluentRegistryAvroSerializationSchema.forSpecific(
                                        KeyAndEnvelope.class,
                                        config.getString("subject.deleted", ""),
                                        config.getString("schema.registery.url", "")
                                )
                        )
                        .build())
                .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .build();

    }


}
