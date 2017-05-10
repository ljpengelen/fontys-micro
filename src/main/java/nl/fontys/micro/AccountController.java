package nl.fontys.micro;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    private static final StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
    private final AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public AccountDto create(@RequestBody AccountDto accountDto) {
        Account account = accountRepository.create(accountDto.getUsername(), encryptor.encryptPassword(accountDto.getPassword()));
        return new AccountDto(account.getId(), account.getUsername());
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public AccountDto update(@PathVariable int id, @RequestBody AccountWithPasswordDto accountWithPasswordDto) throws AccountNotFoundException, InvalidCredentialsException {
        Account oldAccount = accountRepository.get(id);
        if (oldAccount == null)
            throw new AccountNotFoundException();

        if (!encryptor.checkPassword(accountWithPasswordDto.getCurrentPassword(), oldAccount.getEncryptedPassword()))
            throw new InvalidCredentialsException();

        Account newAccount = accountRepository.update(id, accountWithPasswordDto.getUsername(), encryptor.encryptPassword(accountWithPasswordDto.getPassword()));
        return new AccountDto(newAccount.getId(), newAccount.getUsername());
    }
}
