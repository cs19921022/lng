package cn.edu.hdu.lab505.innovation.controller.user;

/**
 * Created by hhx on 2016/11/20.
 */

import cn.edu.hdu.lab505.innovation.domain.domain.Product;
import cn.edu.hdu.lab505.innovation.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductCtrl {
    @Autowired
    private IProductService productService;

    @Path("getProduct")
    @GET
    public List<Product> get(@QueryParam("accountId") int id) {
        return productService.findByAccountId(id);
    }


}
