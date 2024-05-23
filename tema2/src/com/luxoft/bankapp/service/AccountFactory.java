package com.luxoft.bankapp.service;

import com.luxoft.bankapp.domain.AbstractAccount;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.SavingAccount;
import com.luxoft.bankapp.domain.AccountType;
public class AccountFactory {
    public static AbstractAccount newAccount(int id, String accountType) {
        if (accountType == null) {
            return null;
        }

        if (accountType.toUpperCase().equals(AccountType.SAVING_ACCOUNT.toString())) {
            return new SavingAccount(id, 0.0);
        } else if (accountType.toUpperCase().equals(AccountType.CHECKING_ACCOUNT.toString())) {
            return new CheckingAccount(id, 0.0, 1.0);
        } else {
            return null;
        }
    }
}