package com.excilys.ebi.spring.dbunit.config;

public class DataSetFormatOptions {

	private boolean columnSensing;
	private String dtdLocation;
	private boolean dtdMetadata;
	private boolean caseSensitiveTableNames;

	private DataSetFormatOptions() {
	}

	public boolean isColumnSensing() {
		return columnSensing;
	}

	public String getDtdLocation() {
		return dtdLocation;
	}

	public boolean isDtdMetadata() {
		return dtdMetadata;
	}

	public boolean isCaseSensitiveTableNames() {
		return caseSensitiveTableNames;
	}

	public static DataSetFormatOptions.Builder newFormatOptions() {
		return new Builder();
	}

	public static class Builder {

		private DataSetFormatOptions options = new DataSetFormatOptions();

		private Builder() {
		}

		public DataSetFormatOptions.Builder withColumnSensing(boolean columnSensing) {
			options.columnSensing = columnSensing;
			return this;
		}

		public DataSetFormatOptions.Builder withDtdLocation(String dtdLocation) {
			options.dtdLocation = dtdLocation;
			return this;
		}

		public DataSetFormatOptions.Builder withDtdMetadata(boolean dtdMetadata) {
			options.dtdMetadata = dtdMetadata;
			return this;
		}

		public DataSetFormatOptions.Builder withCaseSensitiveTableNames(boolean caseSensitiveTableNames) {
			options.caseSensitiveTableNames = caseSensitiveTableNames;
			return this;
		}

		public DataSetFormatOptions build() {
			return options;
		}
	}
}