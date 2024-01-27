package com.javabootcamp.fintechbank.accounts;

import com.javabootcamp.fintechbank.accounts.models.create_account.CreateAccountRequest;
import com.javabootcamp.fintechbank.accounts.models.get_account.AccountResponse;
import com.javabootcamp.fintechbank.accounts.models.deposit.DepositRequest;
import com.javabootcamp.fintechbank.accounts.models.transfer.TransferMoneyRequest;
import com.javabootcamp.fintechbank.accounts.models.with_draw.WithdrawRequest;
import com.javabootcamp.fintechbank.entities.Account;
import com.javabootcamp.fintechbank.exceptions.BadRequestException;
import com.javabootcamp.fintechbank.exceptions.InternalServerException;
import com.javabootcamp.fintechbank.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponse createAccount(CreateAccountRequest createAccountRequest) {
        Account account = new Account();

        account.setType(createAccountRequest.type().getType());
        account.setName(createAccountRequest.name());
        account.setBalance(createAccountRequest.balance());

        accountRepository.save(account);

        return new AccountResponse(account.getNo(), account.getType(), account.getName(), account.getBalance());
    }

    public List<AccountResponse> getAccounts() {
        return accountRepository
                .findAll()
                .stream()
                .map(acc -> new AccountResponse(acc.getNo(), acc.getType(), acc.getName(), acc.getBalance()))
                .toList();
    }

    public AccountResponse depositAccount(Integer accountNo, DepositRequest depositRequest) {
        Optional<Account> optionalAccount = accountRepository.findById(accountNo);
        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found");
        }

        Account account = optionalAccount.get();
        Double newBalance = account.getBalance() + depositRequest.amount();
        account.setBalance(newBalance);

        try {
            accountRepository.save(account);
        } catch (Exception e) {
            throw new InternalServerException("Failed to deposit");
        }
        return new AccountResponse(account.getNo(), account.getType(), account.getName(), account.getBalance());
    }

    public AccountResponse withdrawAccount(Integer accountNo, WithdrawRequest withdrawRequest) {
        Optional<Account> optionalAccount = accountRepository.findById(accountNo);
        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found");
        }

        Account account = optionalAccount.get();
        Double withDrawAmount = withdrawRequest.amount();
        Double balance = account.getBalance();

        if (withDrawAmount > balance) {
            throw new BadRequestException("Insufficient funds!");
        }
        Double newBalance = balance - withDrawAmount;
        account.setBalance(newBalance);

        try {
            accountRepository.save(account);
        } catch (Exception e) {
            throw new InternalServerException("Failed to withdraw");
        }
        return new AccountResponse(account.getNo(), account.getType(), account.getName(), account.getBalance());
    }

    @Transactional
    public AccountResponse transferMoney(Integer accountNo, Integer targetAccountNo, TransferMoneyRequest transferAccountRequest) {
        Optional<Account> optionalAccount = accountRepository.findById(accountNo);
        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found");
        }

        Account account = optionalAccount.get();

        Optional<Account> optionalTargetAccount = accountRepository.findById(targetAccountNo);
        if (optionalTargetAccount.isEmpty()) {
            throw new NotFoundException("Target account not found");
        }

        Account targetAccount = optionalTargetAccount.get();

        // adjust balance
        Double transferAmount = transferAccountRequest.amount();
        Double balance = account.getBalance();

        if (transferAmount > balance) {
            throw new BadRequestException("Insufficient funds!");
        }

        Double newAccountBalance = balance - transferAmount;
        account.setBalance(newAccountBalance);

        accountRepository.save(account);

        // adjust target balance
        Double targetBalance = targetAccount.getBalance();

        Double newTargetAccountBalance = targetBalance + transferAmount;
        targetAccount.setBalance(newTargetAccountBalance);

        accountRepository.save(targetAccount);

        return new AccountResponse(account.getNo(), account.getType(), account.getName(), account.getBalance());
    }
}
