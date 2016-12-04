package cn.edu.hdu.lab505.innovation.service;

import cn.edu.hdu.lab505.innovation.common.ICurdServiceSupport;
import cn.edu.hdu.lab505.innovation.domain.Account;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import java.util.List;

/**
 * Created by hhx on 2016/11/19.
 */
public interface IAccountService extends ICurdServiceSupport<Account> {
    String login(String account, String password) throws AccountNotFoundException, FailedLoginException;

    void logout(String token);

    void updateIgnorePassword(Account entity);

    Account getAccountInfo(String token) throws CredentialExpiredException;

    int createAccount(Account account);

    void changePassword(Account account);

    List<Account> findByName(String name);
}
