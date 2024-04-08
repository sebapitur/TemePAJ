package com.luxoft.bankapp.service;
import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.Client;

import java.util.*;
import java.util.stream.Collectors;

public class BankReportStreams {
    public int getNumberOfClients(Bank bank) {
        return bank.getClients().size();
    }

    public int getNumberOfAccounts(Bank bank) {
        return (int) bank.getClients().stream().
                flatMap(client -> client.getAccounts().stream()).count();
    }

    public SortedSet<Client> getClientsSorted(Bank bank) {
        return bank.getClients().stream().
                sorted(Comparator.comparing(Client::getName)).
                collect(Collectors.toCollection(TreeSet::new));
    }

    public double getTotalSumInAccounts(Bank bank) {
        return bank.getClients().stream().
                flatMap(client -> client.getAccounts().stream()).
                map(Account::getBalance).reduce(Double::sum).
                orElse(-1.0);
    }

    public SortedSet<Account> getAccountsSortedBySum(Bank bank) {
        return bank.getClients().stream().
                flatMap(client -> client.getAccounts().stream()).
                sorted(Comparator.comparing(Account::getBalance)).
                collect(Collectors.toCollection(TreeSet::new));
    }

    public double getBankCreditSum(Bank bank) {
        return bank.getClients().stream().flatMap(client -> client.getAccounts().stream()).map(
                account -> (account.maximumAmountToWithdraw() - account.getBalance())
        ).reduce(Double::sum).orElse(-1.0);
    }

    public Map<Client, Collection<Account>> getCustomerAccounts(Bank bank) {
        return bank.getClients().stream().collect(Collectors.toMap(
                client -> client,
                Client::getAccounts,
                (existingValue, newValue) -> existingValue,
                HashMap::new
        ));
    }

    public Map<String, List<Client>> getClientsByCity(Bank bank) {
        return bank.getClients()
                .stream()
                .collect(Collectors.groupingBy(
                        Client::getCity,
                        HashMap::new,
                        Collectors.toCollection(ArrayList::new)
                ));
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

