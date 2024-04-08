package com.luxoft.bankapp.domain;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

public abstract class AbstractAccount implements Account, Comparable<Account> {
	
	private int id;
	protected double balance;
	public int compareTo(Account other) {
		if (this.balance - other.getBalance() < 0) {
			return -1;
		}

		if (this.balance - other.getBalance() > 0) {
			return 1;
		}

		return 0;
	}


	public AbstractAccount(int id, double balance) {
		this.id = id;
		this.balance = balance;
	}

	@Override
	public void deposit(final double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Cannot deposit a negative amount");
		}
		this.balance += amount;
	}

	@Override
	public void withdraw(final double amount) throws NotEnoughFundsException {
		if (amount < 0) {
			throw new IllegalArgumentException("Cannot withdraw a negative amount");
		}
		
		if (amount > maximumAmountToWithdraw()) {
			throw new NotEnoughFundsException(id, balance, amount, "Requested amount exceeds the maximum amount to withdraw");
		}
		
		this.balance -= amount;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public double getBalance() {
		return balance;
	}

	@Override
	public boolean equals(Object obj) {
		return ((AbstractAccount) obj).id == this.id;
	}


	@Override
	public int hashCode() {
		return Integer.hashCode(this.id);
	}
}
