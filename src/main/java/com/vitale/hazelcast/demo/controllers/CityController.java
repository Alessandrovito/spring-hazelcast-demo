package com.vitale.hazelcast.demo.controllers;


import com.vitale.hazelcast.demo.model.City;
import com.vitale.hazelcast.demo.repository.CityRepository;
import com.vitale.hazelcast.demo.repository.CountryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController {

	private final Logger logger = LoggerFactory.getLogger(CityController.class);

	@Autowired
	CityRepository cityRepository;


	@Autowired
	CountryRepository countryRepository;

	@GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody  List<City> getCity() {
		List<City> cities = StreamSupport.stream(cityRepository.findAll().spliterator(), false).collect(Collectors.toList());
		logger.info("Get all cities = {}", cities );
		return cities;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public City save(@RequestBody City city) {
		logger.info("Save  City {}", city);
		return cityRepository.save(city);
	}

	@GetMapping(value ="/{id}")
	public City getCityById(@PathVariable(value ="id") String id) {
		logger.info("Get City by id {}", id);
		return cityRepository.findById(id).get();
	}

	@GetMapping(params = {"contain"})
	public @ResponseBody  List<City> getCityContain(@RequestParam(value = "contain", required = true) String contain) {
		List<City> cities = cityRepository.findByNameContaining(contain);
		logger.info("Find contain {} - Cities : {}", contain, cities);
		return cities;
	}

	@GetMapping(params = {"like"})
	public @ResponseBody  List<City> getCityLike(@RequestParam(value = "like", required = true) String like) {
		List<City> cities = cityRepository.citiesWithNameIsLike(like);
		logger.info("Find like {} - Cities : {}", like, cities);
		return cities;
	}

	@GetMapping(params = {"byCountry"})
	public @ResponseBody  List<City> getCitybyCountry(@RequestParam(value = "byCountry", required = true) String byCountry) {
		List<City> cities = cityRepository.thisUseQueryToFindCountry(byCountry);
		logger.info("Find byCountry {} - Cities : {}", byCountry, cities);
		return cities;
	}

	@GetMapping(params = {"peopleMin"})
	public @ResponseBody  List<City> getCityWithPeopleGreaterThan(@RequestParam(value = "peopleMin", required = true) Long peopleMin) {
		List<City> cities = cityRepository.findByPopulationGreaterThanEqual(peopleMin);
		logger.info("Get all cities with population greater than {} = {}", peopleMin, cities );
		return cities;
	}



}
