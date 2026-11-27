package io.github.ezequiellima10.productservice.account.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("SAVINGS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SavingsAccount extends Account {

    public SavingsAccount(Long holderId, String alias) {
        super(holderId, alias);
    }

    @Override
    protected boolean canWithdraw(BigDecimal amount) {

        return getBalance().compareTo(amount) >= 0;
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.SAVINGS;}
}
