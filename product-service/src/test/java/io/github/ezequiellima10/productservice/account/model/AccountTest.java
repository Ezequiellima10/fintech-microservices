package io.github.ezequiellima10.productservice.account.model;

import io.github.ezequiellima10.productservice.account.exception.AccountClosedException;
import io.github.ezequiellima10.productservice.account.exception.InsufficientFundsException;
import io.github.ezequiellima10.productservice.account.exception.InvalidAmountException;
import io.github.ezequiellima10.productservice.account.exception.NonZeroBalanceException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private final Long holderId = 1L;


    @Test
    void withdraw_savingsWholeBalance_leavesZeroBalance() {
        // given
        Account account = new SavingsAccount(holderId, "mi.caja");
        account.deposit(new BigDecimal("100"));

        // when
        account.withdraw(new BigDecimal("100"));

        // then
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    void withdraw_savingsMoreThanBalance_throwsInsufficientFundsException() {
        // given
        Account account = new SavingsAccount(holderId, "mi.caja");
        account.deposit(new BigDecimal("100"));

        // when / then
        assertThrows(InsufficientFundsException.class, () -> account.withdraw(new BigDecimal("101")));

        assertEquals(new BigDecimal("100"), account.getBalance());
    }

    @Test
    void withdraw_checkingUpToOverdraft_leavesNegativeBalance() {
        // given
        Account account = new CheckingAccount(holderId, "mi.corriente", new BigDecimal("50"));
        account.deposit(new BigDecimal("100"));

        // when
        account.withdraw(new BigDecimal("150"));

        // then
        assertEquals(new BigDecimal("-50"), account.getBalance());
    }

    @Test
    void withdraw_checkingBeyondOverdraft_throwsInsufficientFundsException() {
        // given
        Account account = new CheckingAccount(holderId, "mi.corriente", new BigDecimal("50"));
        account.deposit(new BigDecimal("100"));

        // when / then
        assertThrows(InsufficientFundsException.class, () -> account.withdraw(new BigDecimal("151")));

        assertEquals(new BigDecimal("100"), account.getBalance());
    }


    @Test
    void deposit_zeroAmount_throwsInvalidAmountException() {
        // given
        Account account = new SavingsAccount(holderId, "mi.caja");

        // when / then
        assertThrows(InvalidAmountException.class, () -> account.deposit(BigDecimal.ZERO));

        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    void deposit_closedAccount_throwsAccountClosedException() {
        // given
        Account account = new SavingsAccount(holderId, "mi.caja");
        account.close();

        // when / then
        assertThrows(AccountClosedException.class, () -> account.deposit(new BigDecimal("100")));

        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    void close_nonZeroBalance_throwsNonZeroBalanceException() {
        // given
        Account account = new SavingsAccount(holderId, "mi.caja");
        account.deposit(new BigDecimal("100"));

        // when / then
        assertThrows(NonZeroBalanceException.class, account::close);

        assertTrue(account.isActive());
    }
}
