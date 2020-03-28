package com.vitale.hazelcast.demo.controllers;

import com.vitale.hazelcast.demo.model.Country;
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
@RequestMapping(path = "/countries",produces = MediaType.APPLICATION_JSON_VALUE)
public class CountryController {

	private final Logger logger = LoggerFactory.getLogger(CountryController.class);

	@Autowired
	CountryRepository countryRepository;

	@GetMapping
	public @ResponseBody  List<Country> getCard() {
		List<Country> countries = StreamSupport.stream(countryRepository.findAll().spliterator(), false).collect(Collectors.toList());
		logger.info("Get all cards = {}", countries );
		return countries;
	}

	@PostMapping
	public Country save(@RequestBody Country country) {
		logger.info("Save  country {}", country);
		return countryRepository.save(country);
	}

	@GetMapping(value ="/{id}")
	public Country getCountryById(@PathVariable(value ="id") String id) {
		return countryRepository.findById(id).get();
	}

	@GetMapping(params = {"like"})
	public @ResponseBody  List<Country> getCountryLike(@RequestParam(value = "like", required = true) String like) {
		List<Country> countries = countryRepository.findByNameContaining(like);
		return countries;
	}
}
