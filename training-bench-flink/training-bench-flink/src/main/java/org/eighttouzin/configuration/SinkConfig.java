package org.eighttouzin.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericFixed;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificRecord;
import org.apache.avro.util.Utf8;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.formats.avro.registry.confluent.ConfluentRegistryAvroSerializationSchema;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.jetbrains.annotations.NotNull;
import training_bench_connect.public$.members.Envelope;
import training_bench_connect.public$.members.KeyAndEnvelope;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SinkConfig {
    private static Configuration config = Config.setup();


    private static void setupRegistry(Map<String, String> registerConf){
        registerConf.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,
                config.getString("schema.registry.url", ""));
        registerConf.put(AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS, "true");
    }


    private static byte [] getBytes(SpecificRecord record) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SpecificRecord specificRecord = record;
        Schema schema = specificRecord.getSchema();

        GenericRecord genericRecord = (GenericRecord)specificRecord;
        Map<String, Object> map = new HashMap<>();

        for (Schema.Field field : schema.getFields()){
            Object value = genericRecord.get(field.name());
            if(Objects.equals(field.name(), "source")){
                continue;
            }
            if ( value instanceof Utf8){
                value = value.toString();
            } else if (value instanceof ByteBuffer){
                ByteBuffer buffer = (ByteBuffer) value;
                buffer.mark();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                buffer.reset();
                value = Base64.getEncoder().encodeToString(bytes);
            } else if (value instanceof SpecificRecord){
                value = mapper.readValue(getBytes((SpecificRecord) value), Map.class); //recursion
            } else if (value instanceof GenericFixed) {
                value = Base64.getEncoder().encodeToString(((GenericFixed) value).bytes());
            }

            map.put(field.name(), value);

        }
        return mapper.writeValueAsBytes(map);
    }

    public static KafkaSink getCreateSink(){
        Map<String, String> registeryConfig = new HashMap<>();
        setupRegistry(registeryConfig);
        return  KafkaSink.<KeyAndEnvelope>builder()
                .setBootstrapServers(config.getString("bootstrap.servers", ""))
                .setRecordSerializer(new KafkaRecordSerializationSchema<KeyAndEnvelope>() {

                private final SerializationSchema<String> keySerializer = new SimpleStringSchema();

                private final SerializationSchema<Envelope> valueSerializer = new SerializationSchema<Envelope>() {

                    @Override
                    public byte[] serialize(Envelope envelope) {
                        try {
                            return getBytes(envelope);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException("json error " + e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };

                    @NotNull
                    @Override
                    public ProducerRecord<byte[], byte[]> serialize(KeyAndEnvelope keyAndEnvelope,
                                                                    KafkaSinkContext kafkaSinkContext,
                                                                    Long aLong) {
                        byte[] keyBytes = keySerializer.serialize(keyAndEnvelope.getKey().toString());
                        byte[] valueBytes = valueSerializer.serialize(keyAndEnvelope.getEnvelope());

                         return new ProducerRecord<>(
                                 config.getString("topic.member.out.created", ""),
                                 null,
                                 aLong,
                                 keyBytes,
                                 valueBytes
                         );
                     }
                    })
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
                                        config.getString("schema.registry.url", "")
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
                                        config.getString("schema.registry.url", "")
                                )
                        )
                        .build())
                .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .build();

    }


}
