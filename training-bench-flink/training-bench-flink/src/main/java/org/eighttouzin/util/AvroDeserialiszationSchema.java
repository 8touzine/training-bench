package org.eighttouzin.util;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificRecord;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import training_bench_connect.public$.members.Envelope;
import training_bench_connect.public$.members.Key;

import java.util.HashMap;
import java.util.Map;

public class AvroDeserialiszationSchema implements KafkaRecordDeserializationSchema<Tuple2<Key, Envelope>> {


    private KafkaAvroDeserializer keyDeserializer;
    private KafkaAvroDeserializer valueDeserializer;
    private final Configuration config;

    public AvroDeserialiszationSchema(Configuration config){
        this.config = config;
    }


    @Override
    public void open(DeserializationSchema.InitializationContext context) throws Exception {
        Map<String, Object> configuration = new HashMap<>();
        configuration.put("schema.registry.url", config.getString("schema.registery.url", "" +
                "http://schema-registry:8081"));
        configuration.put("specific.avro.reader", true);

        keyDeserializer = new KafkaAvroDeserializer();
        keyDeserializer.configure(configuration, true); //isKey -> true

        valueDeserializer = new KafkaAvroDeserializer();
        valueDeserializer.configure(configuration, false); //isKey -> false


    }


    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> message, Collector<Tuple2<Key, Envelope>> out) {
        Key key = (Key) keyDeserializer.deserialize(message.topic(), message.key());
        Envelope envelope = (Envelope) valueDeserializer.deserialize(message.topic(), message.value());
        out.collect(Tuple2.of(key, envelope));
    }

    @Override
    public TypeInformation<Tuple2<Key, Envelope>> getProducedType() {
        return TypeInformation.of(new TypeHint<Tuple2<Key, Envelope>>() {});
    }
}
