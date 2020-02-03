package com.altran.toolsbox.util;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class GenericResponse<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean error;

	private T value;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}
