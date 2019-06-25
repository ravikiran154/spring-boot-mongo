package com.assignment.springboot.mongo.model;

import java.util.List;

public class SearchCriteria {

	List<SearchEntry>searchEntries;

	public List<SearchEntry> getSearchEntries() {
		return searchEntries;
	}

	public void setSearchEntries(List<SearchEntry> searchEntries) {
		this.searchEntries = searchEntries;
	}
	
}
