package io.github.ezequiellima10.productservice.account.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CHECKING")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckingAccount extends Account {


    @Column(precision = 19, scale = 2)
    private BigDecimal overdraftLimit;

    public CheckingAccount(Long holderId, String alias, BigDecimal overdraftLimit) {
        super(holderId, alias);
        this.overdraftLimit = overdraftLimit != null ? overdraftLimit : BigDecimal.ZERO;
    }

    @Override
    protected boolean canWithdraw(BigDecimal amount) {

        return getBalance().add(overdraftLimit).compareTo(amount) >= 0;
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.CHECKING;}
}
