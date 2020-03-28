package com.vitale.hazelcast.demo.repository;

import com.vitale.hazelcast.demo.model.City;

import java.util.List;


import org.springframework.data.hazelcast.repository.HazelcastRepository;
import org.springframework.data.hazelcast.repository.query.Query;

public interface CityRepository extends HazelcastRepository<City, String> {


	@Query("name like %s")
	List<City> citiesWithNameIsLike(String name);

	@Query("country=%s")
	List<City> thisUseQueryToFindCountry(String country);

	List<City> findByNameContaining(String name);

	List<City> findByPopulationGreaterThanEqual(Long people);




}
