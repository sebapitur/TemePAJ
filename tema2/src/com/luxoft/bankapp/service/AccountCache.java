package com.luxoft.bankapp.service;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.luxoft.bankapp.domain.AbstractAccount;
import com.luxoft.bankapp.domain.AccountType;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.SavingAccount;

public class AccountCache {


    private static final String SAVING_ACCOUNT_KEY = AccountType.SAVING_ACCOUNT.toString();
    private static final String CHECKING_ACCOUNT_KEY = AccountType.CHECKING_ACCOUNT.toString();

    private static Map<String, AbstractAccount> cacheMap = new HashMap<>();

    static {
        SavingAccount savingAccount = new SavingAccount(0, 0);
        CheckingAccount checkingAccount = new CheckingAccount(0, 0, 0);
        cacheMap.put(SAVING_ACCOUNT_KEY, savingAccount);
        cacheMap.put(CHECKING_ACCOUNT_KEY, checkingAccount);
    }

    public static AbstractAccount getAccount(String accountType) {
        AbstractAccount cachedAccount = cacheMap.get(accountType);

        if (cachedAccount == null) {
            throw new RuntimeException("Cannot create account of unknown type");
        }

        return cachedAccount.clone();
    }

    public static void loadCache(AbstractAccount savingAccount, AbstractAccount checkingAccount) {
        cacheMap.put(SAVING_ACCOUNT_KEY, savingAccount);
        cacheMap.put(CHECKING_ACCOUNT_KEY, checkingAccount);
    }

}
