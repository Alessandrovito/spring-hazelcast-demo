package com.vitale.hazelcast.demo;

import java.util.stream.StreamSupport;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.vitale.hazelcast.demo.model.City;
import com.vitale.hazelcast.demo.model.Country;
import com.vitale.hazelcast.demo.repository.CityRepository;
import com.vitale.hazelcast.demo.repository.CountryRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HazelcastdemoApplication.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext
class HazelcastdemoApplicationTests {
	private static final Logger LOG = LoggerFactory.getLogger(HazelcastdemoApplicationTests.class);

	@Autowired
	CityRepository cityRepository;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	private WebTestClient webClient;

	@Test
	public void testCityCount() {
		LOG.info("********* COUNT CITIES ***********");
		long count = cityRepository.count();
		LOG.info("count = " + count);
		Iterable<City> allCities = cityRepository.findAll();
		Assert.assertEquals(count, 7);
		Assert.assertNotNull(allCities);
		StreamSupport.stream(allCities.spliterator(), false).forEach(c ->   LOG.info("City : {}",c.toString()));
	}

	@Test
	public void testCountryCount() {
		LOG.info("********* COUNT COUNTRIES ***********");
		long count = countryRepository.count();
		LOG.info("count = " + count);
		Iterable<Country> allCountry = countryRepository.findAll();
		Assert.assertEquals(count, 3);
		Assert.assertNotNull(allCountry);
		StreamSupport.stream(allCountry.spliterator(), false).forEach(c ->   LOG.info("Country : {}",c.toString()));
	}

	@Test
	public void testCityByName() {
		LOG.info("********* TEST ByName***********");

		webClient.get()
		.uri("/cities/Rome")
		.header(HttpHeaders.ACCEPT, "application/json")
		.exchange()
		.expectStatus().isOk()
		.expectBody(City.class)
		.consumeWith(result -> {
			Assert.assertEquals(result.getResponseBody().getName(), "Rome");
			Assert.assertEquals(result.getResponseBody().getCountry(), "Italy");
		});;	  
	}

	@Test
	public void testCityByNameLike() {
		LOG.info("********* TEST ByNameLike***********");

		webClient.get()
		.uri("/cities?like=%om%")
		.header(HttpHeaders.ACCEPT, "application/json")
		.exchange()
		.expectStatus().isOk()
		.expectBodyList(City.class)
		.consumeWith(result -> {
			Assert.assertEquals(result.getResponseBody().get(0).getName(), "Rome");
			Assert.assertEquals(result.getResponseBody().get(0).getCountry(), "Italy");
		});;	  
	}

	@Test
	public void testCityByPeopleMin() {
		LOG.info("********* TEST ByPeopleMin ***********");

		webClient.get()
		.uri("/cities?peopleMin=10840001")
		.header(HttpHeaders.ACCEPT, "application/json")
		.exchange()
		.expectStatus().isOk()
		.expectBodyList(City.class)
		.consumeWith(result -> {
			Assert.assertEquals(result.getResponseBody().size(), 3);
		});  
	}    


}
