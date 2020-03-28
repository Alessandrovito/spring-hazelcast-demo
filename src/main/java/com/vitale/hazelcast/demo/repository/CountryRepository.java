package com.vitale.hazelcast.demo.repository;

import java.util.List;
import org.springframework.data.hazelcast.repository.HazelcastRepository;

import com.vitale.hazelcast.demo.model.Country;

public interface CountryRepository extends HazelcastRepository<Country, String> {

	List<Country> findByNameContaining(String name);
}
