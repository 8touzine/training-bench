package trainging.bench.API.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;



@Document(indexName = "#{@memberCreatedIndexName}")
public class MemberEvent {
    @Id
    private String id;
    @JsonProperty("op")
    private String op;
    @JsonProperty("ts_us")
    private long tsUs = 0;
    @JsonProperty("ts_ns")
    private long tsNs = 0;
    @JsonProperty("ts_Ms")
    private long tsMs = 0;
    @JsonProperty("after")
    private After after;

    @Data//getters setters
    public static class After{
        @JsonProperty("id")
        private long id;
        @JsonProperty("email")
        private String email;
        @JsonProperty("isactive")
        private boolean isActive;
        @JsonProperty("nom")
        private String nom;
        @JsonProperty("timestamp")
        private long timestamp;
    }


}
