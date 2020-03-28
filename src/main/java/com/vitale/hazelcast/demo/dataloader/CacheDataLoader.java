package com.vitale.hazelcast.demo.dataloader;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.vitale.hazelcast.demo.model.City;
import com.vitale.hazelcast.demo.model.Country;
import com.vitale.hazelcast.demo.repository.CityRepository;
import com.vitale.hazelcast.demo.repository.CountryRepository;

@Component
public class CacheDataLoader {

	@Qualifier("hazelcastInstance")
	@Autowired
	HazelcastInstance hazelcastInstance;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	CountryRepository countryRepository;

	@PostConstruct
	public void loadDataToHazelCast() throws Exception{
		IMap map = hazelcastInstance.getMap("mymap");
		map.put("1000","value");
		map.put("2000","othervalue");
		System.err.println("******** DATA LOADED TO HAZELCAST **********");

		cityRepository.save(new City.Builder().withCountry("Italy").withName("Rome").withPopulation(5434000L).build());
		cityRepository.save(new City.Builder().withCountry("Italy").withName("Venice").withPopulation(1334000L).build());
		cityRepository.save(new City.Builder().withCountry("United States").withName("New York").withPopulation(21045000L).build());
		cityRepository.save(new City.Builder().withCountry("Japan").withName("Tokyo").withPopulation(38505000L).build());
		cityRepository.save(new City.Builder().withCountry("United Kingdom").withName("London").withPopulation(10840000L).build());
		cityRepository.save(new City.Builder().withCountry("France").withName("Paris").withPopulation(10960000L).build());
		cityRepository.save(new City.Builder().withCountry("Spain").withName("Madrid").withPopulation(6345000L).build());
		City cityRes = cityRepository.findById("Rome").get();
		System.out.println("city of Rome = " + cityRes);
		System.err.println("******** DATA REPO CITY LOADED TO HAZLECAST **********");
		
		countryRepository.save(new Country.Builder().withCapital("Rome").withName("Italy").withPopulation(64580000L).build());
		countryRepository.save(new Country.Builder().withCapital("Moscow").withName("Russia").withPopulation(145934462L).build());
		countryRepository.save(new Country.Builder().withCapital("Pechino").withName("China").withPopulation(1439323776L).build());
		Country countryRes = countryRepository.findById("Italy").get();
		System.out.println("country of Italy = " + countryRes);
		System.err.println("******** DATA REPO COUNTRY LOADED TO HAZLECAST **********");
	}
}
