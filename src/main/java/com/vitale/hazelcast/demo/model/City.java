package com.vitale.hazelcast.demo.model;

import com.google.common.base.MoreObjects;

import com.vitale.hazelcast.demo.model.Country.Builder;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;


@KeySpace(ConstUtils.KEYSPACE_MAP_NAME_CITY)
public class City implements Serializable, Comparable<City> {

	@Id
	private String name;
	private String country;
	private Long population;



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getPopulation() {
		return population;
	}

	public void setPopulation(Long population) {
		this.population = population;
	}


	public static class Builder {

		private String name;
		private String country;
		private Long population;

		public Builder() {
		}

		public  Builder withName(String name) {
			this.name = name;
			return this;
		}

		public  Builder withCountry(String country) {
			this.country = country;
			return this;
		}

		public  Builder withPopulation(Long population) {
			this.population = population;
			return this;
		}
		public City build() throws Exception {
			if (name == null) {
				throw new Exception("No name found");
			}
			City city = new City();
			city.setCountry(country);
			city.setName(name);
			city.setPopulation(population);
			return city;
		}
	}


	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).omitNullValues()
				.add("name", getName())
				.add("country", getCountry())
				.add("population", getPopulation())
				.toString();
	}


	// Sort by name then country
	@Override
	public int compareTo(City that) {
		int nameCompare = this.name.compareTo(that.getName());
		return (nameCompare != 0 ? nameCompare : this.country.compareTo(that.getCountry()));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		City city = (City) o;
		return  Objects.equals(name, city.name) && Objects.equals(country, city.country)
				&& Objects.equals(population, city.population);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, country, population);
	}


}
