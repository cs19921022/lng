package cn.edu.hdu.lab505.innovation.controller.admin;

import cn.edu.hdu.lab505.innovation.domain.Product;
import cn.edu.hdu.lab505.innovation.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by hhx on 2016/11/20.
 */
@Path("product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductCtrl {
    @Autowired
    private IProductService productService;

    @Path("add")
    @POST
    public void add(Product product) {
        productService.insert(product);
    }

    @Path("{id}/delete")
    @DELETE
    public void delete(@PathParam("id") int id) {
        productService.deleteById(id);
    }

    @Path("{id}/update")
    @PUT
    public void update(@PathParam("id") int id, Product product) {
        product.setId(id);
        productService.updateIgnoreImei(product);
    }


}
