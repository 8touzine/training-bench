package engine;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import org.eighttouzin.engine.MemberClassifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import training_bench_connect.public$.members.Envelope;
import training_bench_connect.public$.members.Key;
import training_bench_connect.public$.members.KeyAndEnvelope;

import java.util.ArrayList;
import java.util.List;

public class MemberClassifierTest {

    @Test
    public void shouldEnrichValueAndCombineWithKey() throws Exception {
        MemberClassifier memberClassifier = new MemberClassifier();
        List<KeyAndEnvelope> result = new ArrayList<>();

        Collector<KeyAndEnvelope> collector = new Collector<KeyAndEnvelope>() {
            @Override
            public void collect(KeyAndEnvelope keyAndEnvelope) {
                result.add(keyAndEnvelope);
            }

            @Override
            public void close() {
            }
        };

        Key key = new Key();
        key.setId(1);
        Envelope envelope = new Envelope();
        envelope.setOp("c");

        Tuple2<Key, Envelope> testRecord = new Tuple2<>(key, envelope);
        memberClassifier.flatMap(testRecord, collector);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1, result.get(0).getKey().getId());
        Assertions.assertEquals("CREATE_EVENT", result.get(0).getEnvelope().getOp());


    }
}
