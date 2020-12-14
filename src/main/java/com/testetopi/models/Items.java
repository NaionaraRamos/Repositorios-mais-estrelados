package com.testetopi.models;

public class Items {

	private int id;
	private String full_name;
	private int stargazers_count;
	private int forks_count;
	private Owner owner;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public int getStargazers_count() {
		return stargazers_count;
	}
	public void setStargazers_count(int stargazers_count) {
		this.stargazers_count = stargazers_count;
	}
	public int getForks_count() {
		return forks_count;
	}
	public void setForks_count(int forks_count) {
		this.forks_count = forks_count;
	}
	
	public Owner getOwner() {
		return owner;
	}
	
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
}