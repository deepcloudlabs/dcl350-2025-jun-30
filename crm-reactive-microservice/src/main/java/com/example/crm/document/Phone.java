package com.example.crm.document;

public class Phone {
	private PhoneType type;
	private String country;
	private String number;
	private String extension;

	public Phone() {
	}

	public PhoneType getType() {
		return type;
	}

	public void setType(PhoneType type) {
		this.type = type;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Override
	public String toString() {
		return "Phone [type=" + type + ", country=" + country + ", number=" + number + ", extension=" + extension + "]";
	}

}
