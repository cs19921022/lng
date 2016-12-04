package cn.edu.hdu.lab505.innovation.controller.admin;

import cn.edu.hdu.lab505.innovation.domain.Account;
import cn.edu.hdu.lab505.innovation.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by hhx on 2016/11/19.
 */
@Path("account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountCtrl {
    @Autowired
    private IAccountService accountService;

    @PUT
    @Path("/update")
    public void updateAccount(Account account) {
        accountService.updateIgnorePassword(account);
    }

    @POST
    @Path("create")
    public void createAccount(Account account) {
        accountService.createAccount(account);
        Response.status(Response.Status.CREATED);
    }

    @DELETE
    @Path("{id:\\d+}/delete")
    public void deleteAccount(@PathParam("id") int id) {
        accountService.deleteById(id);
    }

    @GET
    @Path("findAll")
    public List<Account> findAllAccount() {
        return accountService.findAll();
    }

    @Path("findAccount/{name}")
    @GET
    public List<Account> findAccount(@PathParam("name") String name) {

        return accountService.findByName(name);
    }
}
