package org.eighttouzin.util;

import org.apache.avro.generic.GenericRecord;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class AvroDeserialiszationSchema implements DeserializationSchema<GenericRecord> {
    @Override
    public GenericRecord deserialize(byte[] bytes) throws IOException {
        return null;
    }

    @Override
    public boolean isEndOfStream(GenericRecord genericRecord) {
        return false;
    }

    @Override
    public TypeInformation<GenericRecord> getProducedType() {
        return null;
    }
}
