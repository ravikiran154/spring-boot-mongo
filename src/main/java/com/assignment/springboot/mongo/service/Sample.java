package com.assignment.springboot.mongo.service;

public class Sample {

	private String mrn;
	
	private String date;


	public String getMrn() {
		return mrn;
	}

	public void setMrn(String mrn) {
		this.mrn = mrn;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Sample [mrn=" + mrn + ", date=" + date + "]";
	}
	
	
	
	
}
