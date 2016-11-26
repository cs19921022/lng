package cn.edu.hdu.lab505.innovation.controller.user;

import cn.edu.hdu.lab505.innovation.domain.Account;
import cn.edu.hdu.lab505.innovation.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by hhx on 2016/11/19.
 */
@Path("account")

@Consumes(MediaType.APPLICATION_JSON)
public class AccountCtrl {
    @Autowired
    private IAccountService accountService;

    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public String login(Account account) throws AccountNotFoundException, FailedLoginException {
        return accountService.login(account.getUsername(), account.getPassword());

    }

    @GET
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public String logout(@QueryParam("token") String token) {
        accountService.logout(token);
        return "logout success";
    }

    @GET
    @Path("accountInfo")
    @Produces(MediaType.APPLICATION_JSON)
    public Account getAccountInfo(@QueryParam("token") String token) throws CredentialExpiredException {
        return accountService.getAccountInfo(token);
    }

    @PUT
    @Path("/changePassword")
    public void changePassword(@HeaderParam("token") String token, Account account) throws CredentialExpiredException {
        Account a = accountService.getAccountInfo(token);
        if (a.getId() != account.getId()) {
            throw new CredentialExpiredException();
        }
        accountService.changePassword(account);
    }
}
