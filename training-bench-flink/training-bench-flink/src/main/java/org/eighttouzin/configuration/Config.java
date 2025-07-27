package org.eighttouzin.configuration;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.GlobalConfiguration;


public class Config {

    //private static final String membersInTopic = "training_bench_connect.public.members";
    //private static final String memberOutTopic = "training_bench_connect.public.members.events";

    public static Configuration setup(){
        String flinkConfDir = System.getenv("FLINK_CONF_DIR");
        //check if local or flink deployement
        if(flinkConfDir == null){
            flinkConfDir =  "conf";
        }
        System.out.println("flikdir=  " + flinkConfDir);

        return GlobalConfiguration.loadConfiguration(flinkConfDir);
    }

}
