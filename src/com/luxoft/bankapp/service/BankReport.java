package com.luxoft.bankapp.service;
import java.util.AbstractMap.SimpleEntry;
import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.Client;

import javax.swing.*;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class BankReport {
    int getNumberOfClients(Bank bank) {
        return bank.getClients().size();
    }

    int getNumberOfAccounts(Bank bank) {
        return (int) bank.getClients().stream().
                flatMap(client -> client.getAccounts().stream()).count();
    }

    SortedSet<Client> getClientsSorted(Bank bank) {
        return bank.getClients().stream().
                sorted(Comparator.comparing(Client::getName)).
                collect(Collectors.toCollection(TreeSet::new));
    }

    double getTotalSumInAccounts(Bank bank) {
        return bank.getClients().stream().
                flatMap(client -> client.getAccounts().stream()).
                map(Account::getBalance).reduce(Double::sum).
                orElse(-1.0);
    }

    SortedSet<Account> getAccountsSortedBySum(Bank bank) {
        return bank.getClients().stream().
                flatMap(client -> client.getAccounts().stream()).
                sorted(Comparator.comparing(Account::getBalance)).
                collect(Collectors.toCollection(TreeSet::new));
    }

    double getBankCreditSum(Bank bank) {
        // TODO see what it means
        return 0.0;
    }

    Map<Client, Collection<Account>> getCustomerAccounts(Bank bank) {
        final HashMap<Client, Collection<Account>> clientAccountMapping = new HashMap<>();
        bank.getClients().forEach(client -> clientAccountMapping.put(client, client.getAccounts()));

        return clientAccountMapping;
    }

    Map<String, List<Client>> getClientsByCity(Bank bank) {
        final HashMap<String, List<Client>> cityClientMapping = new HashMap<>();
        bank.getClients().forEach(client -> {
            if (!cityClientMapping.containsKey(client.getCity())) {
                cityClientMapping.put(client.getCity(), new LinkedList<>(Arrays.asList(new Client[]{client})));
            } else {
                var sameCityClients = cityClientMapping.get(client.getCity());
                sameCityClients.add(client);
                cityClientMapping.put(client.getCity(), sameCityClients);
            }
        });

        return cityClientMapping;
    }
}

