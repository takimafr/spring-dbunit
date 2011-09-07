package com.excilys.ebi.spring.dbunit;

public class DbUnitException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8549304565604595681L;

	public DbUnitException() {
	}

	public DbUnitException(String s) {
		super(s);
	}

	public DbUnitException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public DbUnitException(Throwable throwable) {
		super(throwable);
	}
}
