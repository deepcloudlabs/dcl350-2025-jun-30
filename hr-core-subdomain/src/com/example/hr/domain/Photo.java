package com.example.hr.domain;

import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

import com.example.ddd.ValueObject;

@ValueObject
public final class Photo {
	private final byte[] values;

	private Photo(byte[] values) {
		this.values = values;
	}

	public static Photo valueOf(byte[] values) {
		Objects.requireNonNull(values, "Photo image content cannot be empty");
		return new Photo(values);
	}

	public static Photo valueOf(String base64Values) {
		Objects.requireNonNull(base64Values, "Photo image content cannot be empty");
		return new Photo(Base64.getDecoder().decode(base64Values));
	}

	public byte[] getValues() {
		return values;
	}

	public String toBase64() {
		return Base64.getEncoder().encodeToString(values);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(values);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Photo other = (Photo) obj;
		return Arrays.equals(values, other.values);
	}

	@Override
	public String toString() {
		return "Photo [values=" + this.toBase64() + "]";
	}

}
