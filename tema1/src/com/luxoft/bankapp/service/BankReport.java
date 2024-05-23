package com.luxoft.bankapp.service;

import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.Client;

import java.util.*;

public class BankReport {
    public int getNumberOfClients(Bank bank) {
        return bank.getClients().size();
    }

    public int getNumberOfAccounts(Bank bank) {
        int count = 0;
        for (Client client : bank.getClients()) {
            count += client.getAccounts().size();
        }
        return count;
    }

    public SortedSet<Client> getClientsSorted(Bank bank) {
        SortedSet<Client> sortedClients = new TreeSet<>(Comparator.comparing(Client::getName));
        sortedClients.addAll(bank.getClients());
        return sortedClients;
    }

    public double getTotalSumInAccounts(Bank bank) {
        double totalSum = 0;
        for (Client client : bank.getClients()) {
            for (Account account : client.getAccounts()) {
                totalSum += account.getBalance();
            }
        }
        return totalSum;
    }

    public SortedSet<Account> getAccountsSortedBySum(Bank bank) {
        SortedSet<Account> sortedAccounts = new TreeSet<>(Comparator.comparing(Account::getBalance));
        for (Client client : bank.getClients()) {
            sortedAccounts.addAll(client.getAccounts());
        }
        return sortedAccounts;
    }

    public double getBankCreditSum(Bank bank) {
        double totalCreditSum = 0;
        for (Client client : bank.getClients()) {
            for (Account account : client.getAccounts()) {
                totalCreditSum += account.maximumAmountToWithdraw() - account.getBalance();
            }
        }
        return totalCreditSum;
    }

    public Map<Client, Collection<Account>> getCustomerAccounts(Bank bank) {
        Map<Client, Collection<Account>> clientAccountMapping = new HashMap<>();
        for (Client client : bank.getClients()) {
            clientAccountMapping.put(client, client.getAccounts());
        }
        return clientAccountMapping;
    }

    public Map<String, List<Client>> getClientsByCity(Bank bank) {
        Map<String, List<Client>> cityClientMapping = new HashMap<>();
        for (Client client : bank.getClients()) {
            String city = client.getCity();
            if (!cityClientMapping.containsKey(city)) {
                cityClientMapping.put(city, new LinkedList<>(Arrays.asList(client)));
            } else {
                cityClientMapping.get(city).add(client);
            }
        }
        return cityClientMapping;
    }

    public void getStatistics(Bank bank) {
        System.out.println("Total number of clients " + this.getNumberOfClients(bank));
        System.out.println("Total number of accounts " + this.getNumberOfAccounts(bank));
        System.out.println("Sorted list of clients " + this.getClientsSorted(bank));
        System.out.println("Total sum in accounts " + this.getTotalSumInAccounts(bank));
        System.out.println("Account list sorted by sum " + this.getAccountsSortedBySum(bank));
        System.out.println("Bank credit sum " + this.getBankCreditSum(bank));
        System.out.println("All customer accounts " + this.getCustomerAccounts(bank));
        System.out.println("Clients by city " + this.getClientsByCity(bank));
    }
}
