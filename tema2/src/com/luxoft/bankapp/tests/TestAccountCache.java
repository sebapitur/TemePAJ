package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.domain.AbstractAccount;
import com.luxoft.bankapp.domain.AccountType;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.SavingAccount;
import com.luxoft.bankapp.service.AccountCache;
import org.junit.Test;

import static org.junit.Assert.assertNotSame;

public class TestAccountCache {
    @Test
    public void testDefaultInstance() {
        SavingAccount account = (SavingAccount) AccountCache.getAccount(AccountType.SAVING_ACCOUNT.toString());
        assert account.getBalance() == 0.0;
    }
    @Test
    public void testDifferentInstance() {
        CheckingAccount account1 = (CheckingAccount) AccountCache.getAccount(AccountType.CHECKING_ACCOUNT.toString());
        CheckingAccount account2 = (CheckingAccount) AccountCache.getAccount(AccountType.CHECKING_ACCOUNT.toString());
        assertNotSame(account1, account2);

        // test if the object fields are logically equal
        assert account1.getBalance() == account2.getBalance();
        assert account1.getOverdraft().equals(account2.getOverdraft());
    }

    @Test
    public void testCacheLoad() {
        AbstractAccount savingAccount = new SavingAccount(1, 1000);
        AbstractAccount checkingAccount = new CheckingAccount(2, 2000, 500);
        AccountCache.loadCache(savingAccount, checkingAccount);
        assert AccountCache.getAccount(AccountType.SAVING_ACCOUNT.toString()).getBalance() == 1000;
        CheckingAccount cachedCheckingAccount = (CheckingAccount) AccountCache.getAccount(AccountType.CHECKING_ACCOUNT.toString());
        assert cachedCheckingAccount.getBalance() == 2000;
        assert cachedCheckingAccount.getOverdraft() == 500;
    }
}
