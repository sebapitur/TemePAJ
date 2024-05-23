package com.luxoft.bankapp.main;

import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.Client;
import com.luxoft.bankapp.domain.Gender;
import com.luxoft.bankapp.domain.SavingAccount;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;
import com.luxoft.bankapp.service.BankReport;
import com.luxoft.bankapp.service.BankReportStreams;
import com.luxoft.bankapp.service.BankService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.security.cert.TrustAnchor;

public class BankApplication {
	
	private static Bank bank;
	
	public static void main(String[] args) {
		bank = new Bank();

		modifyBank();
		printBalance();
		BankService.printMaximumAmountToWithdraw(bank);

		for (String arg: args) {
			if (arg.equals("-statistics")) {
				String command = "";
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(System.in));
				BankReportStreams bankReportStreams = new BankReportStreams();
				BankReport bankReport = new BankReport();
				while(true) {
					try {
						System.out.println("Enter a new command:");
						command = reader.readLine();
					} catch (IOException e) {
						System.out.println(e);
						return;
					}
					if (command.equals("display statistic")) {
						System.out.println("Basic statistics");
						bankReport.getStatistics(bank);
						System.out.println("Stream statistics");
						bankReportStreams.getStatistics(bank);
					} else if (command.equals("exit")) {
						break;
					} else {
						Class<?> basicCls = bankReport.getClass();
						Class<?> clsStreams = bankReportStreams.getClass();
                        try {
							System.out.println("Result w/o streams for " + command + ": " + basicCls.getMethod(command, Bank.class).invoke(bankReport, bank));
							System.out.println("Result with streams for " + command + ": " + clsStreams.getMethod(command, Bank.class).invoke(bankReportStreams, bank));
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
							System.err.println("Command does not exist, please use a valid one");
                        }
                    }
				}
			}
		}

	}
	
	private static void modifyBank() {
		Client client1 = new Client("John", Gender.MALE, "Paris");
		Client client2 = new Client("Mona", Gender.FEMALE, "Berlin");
		Account account1 = new SavingAccount(1, 100);
		Account account2 = new CheckingAccount(2, 100, 20);
		Account account3 = new SavingAccount(1, 300);

		client1.addAccount(account1);
		client1.addAccount(account2);
		client2.addAccount(account3);
		
		try {
		   BankService.addClient(bank, client1);
		} catch(ClientExistsException e) {
			System.out.format("Cannot add an already existing client: %s%n", client1.getName());
	    }


		try {
			BankService.addClient(bank, client2);
		} catch(ClientExistsException e) {
			System.out.format("Cannot add an already existing client: %s%n", client1.getName());
		}

		account1.deposit(100);
		try {
		  account1.withdraw(10);
		} catch (OverdraftLimitExceededException e) {
	    	System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
	    } catch (NotEnoughFundsException e) {
	    	System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
	    }
		
		try {
		  account2.withdraw(90);
		} catch (OverdraftLimitExceededException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
	    } catch (NotEnoughFundsException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
	    }
		
		try {
		  account2.withdraw(100);
		} catch (OverdraftLimitExceededException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
	    } catch (NotEnoughFundsException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
	    }
		
		try {
		  BankService.addClient(bank, client1);
		} catch(ClientExistsException e) {
		  System.out.format("Cannot add an already existing client: %s%n", client1);
	    } 
	}
	
	private static void printBalance() {
		System.out.format("%nPrint balance for all clients%n");
		for(Client client : bank.getClients()) {
			System.out.println("Client: " + client);
			for (Account account : client.getAccounts()) {
				System.out.format("Account %d : %.2f%n", account.getId(), account.getBalance());
			}
		}
	}

}
