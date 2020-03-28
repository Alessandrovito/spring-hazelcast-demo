package com.vitale.hazelcast.demo.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import com.hazelcast.core.IMap;
import com.vitale.hazelcast.demo.model.ConstUtils;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hazelcast.HazelcastKeyValueAdapter;
import org.springframework.data.hazelcast.repository.config.EnableHazelcastRepositories;
import org.springframework.data.keyvalue.core.KeyValueOperations;
import org.springframework.data.keyvalue.core.KeyValueTemplate;

import javax.annotation.PostConstruct;

@Configuration
@EnableHazelcastRepositories("com.vitale.hazelcast.demo.repository")
public class HazelCastConfig {

	@Bean
	HazelcastInstance hazelcastInstance() {
		return Hazelcast.getOrCreateHazelcastInstance(hazelCastConfig());
	}

	public Config hazelCastConfig(){
		Config config = new Config();
		config.setInstanceName("hazelcast-instance")
		.addMapConfig(
				new MapConfig()
				.setName(ConstUtils.MAP_NAME_MYMAP)
				.setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
				.setEvictionPolicy(EvictionPolicy.LRU)
				.setTimeToLiveSeconds(-1))

		.addMapConfig(
				new MapConfig()
				.setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
				.setName(ConstUtils.KEYSPACE_MAP_NAME_CITY)
				.setInMemoryFormat(InMemoryFormat.BINARY)
				.setEvictionPolicy(EvictionPolicy.LRU)
				.setTimeToLiveSeconds(-1))
		.addMapConfig(
				new MapConfig()
				.setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
				.setName(ConstUtils.KEYSPACE_MAP_NAME_COUNTRY)
				.setInMemoryFormat(InMemoryFormat.BINARY)
				.setEvictionPolicy(EvictionPolicy.LRU)
				.setTimeToLiveSeconds(1000)); 

		config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
		config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true).addMember("127.0.0.1");
		System.out.println("config " + config);
		return config;
	}



	@Bean
	public KeyValueOperations keyValueTemplate() {
		return new KeyValueTemplate(new HazelcastKeyValueAdapter(hazelcastInstance()));
	}

	@Bean
	public HazelcastKeyValueAdapter hazelcastKeyValueAdapter(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance) {
		return new HazelcastKeyValueAdapter(hazelcastInstance);
	}

}
