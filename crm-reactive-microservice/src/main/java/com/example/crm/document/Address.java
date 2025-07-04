package com.example.crm.document;

import java.util.List;

public class Address {
	private AddressType type;
	private String city;
	private String country;
	private List<String> lines;
	private String zipCode;

	public Address() {
	}

	public AddressType getType() {
		return type;
	}

	public void setType(AddressType type) {
		this.type = type;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return "Address [type=" + type + ", city=" + city + ", country=" + country + ", lines=" + lines + ", zipCode="
				+ zipCode + "]";
	}

}
