package com.vitale.hazelcast.demo.model;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@KeySpace(ConstUtils.KEYSPACE_MAP_NAME_COUNTRY)
public class Country implements Serializable{

	@Id
	String name;
	String capital;
	Long population;



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getCapital() {
		return capital;
	}



	public void setCapital(String capital) {
		this.capital = capital;
	}



	public Long getPopulation() {
		return population;
	}



	public void setPopulation(Long population) {
		this.population = population;
	}


	public static class Builder {

		private String name;
		private String capital;
		private Long population;

		public Builder() {
		}

		public  Builder withName(String name) {
			this.name = name;
			return this;
		}

		public  Builder withCapital(String capital) {
			this.capital = capital;
			return this;
		}

		public  Builder withPopulation(Long population) {
			this.population = population;
			return this;
		}
		public Country build() throws Exception {
			if (name == null) {
				throw new Exception("No name found");
			}
			Country country = new Country();
			country.setCapital(capital);
			country.setName(name);
			country.setPopulation(population);
			return country;
		}
	}



	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).omitNullValues()
				.add("name", getName())
				.add("capital", getCapital())
				.add("population", getPopulation())
				.toString();
	}

}
