package com.javabootcamp.fintechbank.accounts;

import com.javabootcamp.fintechbank.accounts.models.create_account.CreateAccountRequest;
import com.javabootcamp.fintechbank.accounts.models.get_account.AccountResponse;
import com.javabootcamp.fintechbank.accounts.models.deposit.DepositRequest;
import com.javabootcamp.fintechbank.accounts.models.transfer.TransferMoneyRequest;
import com.javabootcamp.fintechbank.accounts.models.with_draw.WithdrawRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/accounts")
@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "list all accounts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "list all accounts",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = AccountResponse.class)))
                    })
    })
    @GetMapping("")
    public List<AccountResponse> getAccounts() {
        return accountService.getAccounts();
    }

    @Operation(summary = "จนมาเห็นกับตา จนพาใจมาเจ็บ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ฉีกบ่มีหม่องเย็บ หัวใจที่ให้เจ้า",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccountResponse.class))
                    })
    })
    @PostMapping("")
    public AccountResponse createAccount(@RequestBody @Valid CreateAccountRequest createAccountRequest) {
        return accountService.createAccount(createAccountRequest);
    }

    @Operation(summary = "deposit from an account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "withdraw money from specific account",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccountResponse.class))
                    })
    })
    @PostMapping("/{accountNo}/deposit")
    public AccountResponse depositAccount(
            @PathVariable Integer accountNo,
            @RequestBody @Valid DepositRequest depositRequest
    ) {
        return accountService.depositAccount(accountNo, depositRequest);
    }

    @Operation(summary = "บักคนซั่วจั่งอ้าย มันเอาเหล้ายาปลาปิ้งเป็นใหญ่")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "มันบ่เหมาะกับไผไคแนแต่ไปเลาะหาเซ็นเหล้า",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccountResponse.class))
                    })
    })
    @PostMapping("/{accountNo}/withdraw")
    public AccountResponse withdrawAccount(
            @PathVariable Integer accountNo,
            @RequestBody @Valid WithdrawRequest withdrawRequest
    ) {
        return accountService.withdrawAccount(accountNo, withdrawRequest);
    }

    @PostMapping("/{accountNo}/transfer/{targetAccountNo}")
    public AccountResponse transferMoney(
            @PathVariable Integer accountNo,
            @PathVariable Integer targetAccountNo,
            @RequestBody @Valid TransferMoneyRequest transferMoneyRequest
    ) {
        return accountService.transferMoney(accountNo, targetAccountNo, transferMoneyRequest);
    }
}

