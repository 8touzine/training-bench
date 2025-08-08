package trainging.bench.API.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConf {

    @Value("${member.created.index}")
    private String memberCreatedIndex;

    @Bean(name = "memberCreatedIndexName")
    public String memberCreatedIndexName(){
        return memberCreatedIndex;
    }

}
