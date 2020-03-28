package com.vitale.hazelcast.demo.controllers;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;
import com.vitale.hazelcast.demo.model.City;
import com.vitale.hazelcast.demo.model.ConstUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping(path = "/mymaps", produces = MediaType.APPLICATION_JSON_VALUE)
public class MyMapController {

	private final Logger logger = LoggerFactory.getLogger(MyMapController.class);
	private final HazelcastInstance hazelcastInstance;
	private final IMap<String, String> hazelcastMap;


	@Autowired
	MyMapController(HazelcastInstance hazelcastInstance) {
		this.hazelcastInstance = hazelcastInstance;
		this.hazelcastMap= hazelcastInstance.getMap(ConstUtils.MAP_NAME_MYMAP); // get map from hazelcast
	}

	@PostMapping
	public String writeDataToHazelcast(@RequestParam String key, @RequestParam String value) {
		hazelcastMap.put(key, value);
		return "Data is stored.";
	}

	@GetMapping(params = {"key"})
	public String readDataFromHazelcast(@RequestParam(value = "key", required = true) String key) {
		return hazelcastMap.get(key);
	}

	@GetMapping
	public Map<String, String> readAllDataFromHazelcast() {
		return hazelcastMap;
	}

}
