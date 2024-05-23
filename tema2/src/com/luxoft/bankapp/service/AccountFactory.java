package com.luxoft.bankapp.service;

import com.luxoft.bankapp.domain.AbstractAccount;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.SavingAccount;

public class AccountFactory {

    public static AbstractAccount newAccount(int id, String accountType) {
        if (accountType == null) {
            return null;
        }
        switch (accountType.toUpperCase()) {
            case "CHECKING":
                return new CheckingAccount(id, 0.0, 1.0);
            case "SAVING":
                return new SavingAccount(id, 0.0);
            default:
                return null;
        }
    }
}