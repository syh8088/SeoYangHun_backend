package com.wirebarley;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wirebarley.api.bank.service.BankApiService;
import com.wirebarley.api.bankaccount.service.BankAccountApiService;
import com.wirebarley.api.member.service.MemberApiService;
import com.wirebarley.api.transfertransaction.service.TransferTransactionApiService;
import com.wirebarley.api.wallet.service.WalletApiService;
import com.wirebarley.domain.bank.repository.BankRepository;
import com.wirebarley.domain.bankaccount.repository.BankAccountRepository;
import com.wirebarley.domain.member.repository.MemberRepository;
import com.wirebarley.domain.transfertransaction.repository.TransferTransactionRepository;
import com.wirebarley.domain.wallet.repository.WalletRepository;
import com.wirebarley.domain.wallet.repository.WalletTransactionRepository;
import com.wirebarley.global.util.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public abstract class ControllerTestSupport {

    protected final Snowflake snowflake = new Snowflake();

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected BankRepository bankRepository;

    @Autowired
    protected BankAccountRepository bankAccountRepository;

    @Autowired
    protected WalletRepository walletRepository;

    @Autowired
    protected WalletTransactionRepository walletTransactionRepository;

    @Autowired
    protected TransferTransactionRepository transferTransactionRepository;

    @MockBean
    protected BankAccountApiService bankAccountApiService;

    @MockBean
    protected WalletApiService walletApiService;

    @MockBean
    protected MemberApiService memberApiService;

    @MockBean
    protected BankApiService bankApiService;

    @MockBean
    protected TransferTransactionApiService transferTransactionApiService;
}
