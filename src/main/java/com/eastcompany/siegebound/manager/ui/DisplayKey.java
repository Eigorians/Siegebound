package com.eastcompany.siegebound.manager.ui;

import java.util.UUID;

public class DisplayKey {
	private final String text;
	private final UUID id;

	DisplayKey(String text, UUID id) {
		this.text = text;
		this.id = id;
	}

	String getText() {
		return text;
	}

	UUID getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof DisplayKey))
			return false;
		DisplayKey other = (DisplayKey) o;
		return text.equals(other.text) && id.equals(other.id);
	}

	@Override
	public int hashCode() {
		int result = text.hashCode();
		result = 31 * result + id.hashCode();
		return result;
	}
}