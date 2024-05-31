package br.com.sapucaians.services;

import br.com.sapucaians.detail.AccountDetail;
import br.com.sapucaians.dto.AccountDTO;
import br.com.sapucaians.exception.causable.ErrDateTransfer;
import br.com.sapucaians.jwt.TokenService;
import br.com.sapucaians.model.Account;
import br.com.sapucaians.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final ModelMapper mapper;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    AccountService() {
        mapper = new ModelMapper();
    }

    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String login(String email, String password) {
        Optional<Account> accountOpt = repository.findByEmail(email);

        if (accountOpt.isPresent()) {
            var authToken = new UsernamePasswordAuthenticationToken(email, password);
            Authentication auth = authenticationManager.authenticate(authToken);
            var response = ((AccountDetail) auth.getPrincipal()).getAccount();

            System.out.println(response);

            if (response.isPresent()) {
                return tokenService.generateToken(accountOpt.get());
            }

            throw new ErrDateTransfer("Senha não autorizada", HttpStatus.UNAUTHORIZED);
        }

        throw new ErrDateTransfer("Usuário não encontrado", HttpStatus.NOT_FOUND);
    }


    public AccountDTO save(Account account) {
        account.setPassword(getPasswordEncoder().encode(account.getPassword()));
        return mapper.map(repository.save(account), AccountDTO.class);
    }

    public AccountDTO update(AccountDTO accountDTO) {
        Optional<Account> account_ = repository.findById(accountDTO.getId());

        if (account_.isPresent()) {
            Account account = Account.builder().
                    id(account_.get().getId()).
                    email(accountDTO.getEmail()).
                    password(account_.get().getPassword()).
                    phone(accountDTO.getPhone()).
                    name(accountDTO.getName()).
                    addresses(account_.get().getAddresses()).
                    photoUrl(accountDTO.getPhotoUrl()).build();

            return mapper.map(repository.save(account), AccountDTO.class);
        }
        throw new ErrDateTransfer("", HttpStatus.NOT_FOUND);
    }

    public void deleteAccount(Long id) {
        repository.deleteById(id);
    }

    public AccountDTO getById(Long id) {
        Optional<Account> account_ = repository.findById(id);
        var result = account_.map(account -> mapper.map(account, AccountDTO.class)).orElse(null);

        if (result == null) {
            throw new ErrDateTransfer("", HttpStatus.NOT_FOUND);
        }

        return result;
    }

    public AccountDTO getByEmail(String email) {
        var result = repository.findByEmail(email).map(account -> mapper.map(account, AccountDTO.class)).orElse(null);

        if (result == null) {
            throw new ErrDateTransfer("", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    public List<Account> getAll() {
        return repository.findAll();
    }

    public boolean isExist(String email) {
        var exist = repository.existByEmail(email);
        return (exist > 0);
    }
}
