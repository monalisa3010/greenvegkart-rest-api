package com.mycart.model.api;

public class ApiError {

	private Object error;

	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}

	public ApiError(Object error) {
		super();
		this.error = error;
	}

	public ApiError() {
		super();
	}

}
