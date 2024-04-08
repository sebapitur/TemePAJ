package com.luxoft.bankapp.domain;

import java.util.*;
import java.util.stream.Collectors;

public class Client implements Comparable<Client> {

	private String name;

	public String getCity() {
		return city;
	}

	private String city;
	private Gender gender;
	private Set<Account> accounts = new HashSet<Account>();

	public Client(String name, Gender gender) {
		this.name = name;
		this.gender = gender;
	}

	public Client(String name, Gender gender, String city) {
		this.name = name;
		this.gender = gender;
		this.city = city;
	}


	public void addAccount(final Account account) {
		accounts.add(account);
	}

	public String getName() {
		return name;
	}

	public Gender getGender() {
		return gender;
	}

	public List<Account> getAccounts() {
		return Collections.unmodifiableList(accounts.stream().collect(Collectors.toList()));
	}

	public String getClientGreeting() {
		if (gender != null) {
			return gender.getGreeting() + " " + name;
		} else {
			return name;
		}
	}

	@Override
	public String toString() {
		return getClientGreeting();
	}

	@Override
	public boolean equals(Object o) {
		return ((Client)o).name.equals(this.name) && ((Client)o).gender.equals((this.gender));
	}

	public int compareTo(Client other) {
		return this.name.compareTo(other.name);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		return result;
	}
}