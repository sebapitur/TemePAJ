package com.luxoft.bankapp.tests;


import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.SavingAccount;
import com.luxoft.bankapp.service.AccountFactory;
import org.junit.Test;
import static org.junit.Assert.assertFalse;


public class TestAccountFactory {

    @Test
    public void testAccountFactoryChecking() {
        String accountType = "sAviNg";
        assert AccountFactory.newAccount(0, accountType) instanceof SavingAccount;
        assertFalse(AccountFactory.newAccount(0, accountType) instanceof CheckingAccount);

    }

    @Test
    public void testAccountFactorySaving() {
        String accountType = "CHEcking";
        assert AccountFactory.newAccount(0, accountType) instanceof CheckingAccount;
        assertFalse(AccountFactory.newAccount(0, accountType) instanceof SavingAccount);
    }
}
