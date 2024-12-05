package com.neoleaptask.NeoleapTask.config;


import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.neoleaptask.NeoleapTask.model.Order;
import com.neoleaptask.NeoleapTask.serializer.OrderSerializer;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class HazelcastConfig {
    @Bean
    public Config hazelcast() {
        Config config = new Config();
        config.setInstanceName("spring-boot-hazelcast");

        config.getNetworkConfig().getJoin().getMulticastConfig()
                .setEnabled(false);
        config.getNetworkConfig().getJoin().getKubernetesConfig()
                .setEnabled(false);

        var usersConfig = new MapConfig("orders");
        usersConfig.setTimeToLiveSeconds(300);
        usersConfig.setStatisticsEnabled(true);
        usersConfig.setEvictionConfig(new EvictionConfig()
                .setSize(5000)
                .setEvictionPolicy(EvictionPolicy.LRU)
                .setMaxSizePolicy(MaxSizePolicy.PER_NODE));
        usersConfig.setNearCacheConfig(null);

        config.addMapConfig(usersConfig);
        config.getSerializationConfig()
                .addSerializerConfig(
                        new SerializerConfig()
                                .setTypeClass(Order.class)
                                .setImplementation(new OrderSerializer()));
        return config;
    }

}