package cn.edu.hdu.lab505.innovation.controller.user;

/**
 * Created by hhx on 2016/11/20.
 */

import cn.edu.hdu.lab505.innovation.common.Page;
import cn.edu.hdu.lab505.innovation.domain.Account;
import cn.edu.hdu.lab505.innovation.domain.DataBean;
import cn.edu.hdu.lab505.innovation.domain.Product;
import cn.edu.hdu.lab505.innovation.service.Exception.SensorDataIndexOutOfBoundsException;
import cn.edu.hdu.lab505.innovation.service.IProductService;
import cn.edu.hdu.lab505.innovation.service.ISensorDataService;
import net.sf.ehcache.Cache;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductCtrl {
    private static final Logger LOGGER = Logger.getLogger(ProductCtrl.class);
    @Autowired
    private IProductService productService;
    @Autowired
    private ISensorDataService sensorDataService;
    @Autowired
    private Cache tokenCache;

    @Path("getProduct")
    @GET
    public Page<Product> get(@QueryParam("accountId") int id, @QueryParam("start") int start, @QueryParam("limit") int limit) {
        return productService.findByAccountId(id, start, limit);
    }

    @Path("{productId:\\d+}/data/{sensorNo:\\d+}")
    @GET
    public List<DataBean> findSerialData(@PathParam("productId") int productId, @PathParam("sensorNo") int sensorNo, @QueryParam("start") String startString, @QueryParam("limit") String limitString) throws SensorDataIndexOutOfBoundsException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = null;
        Date limit = null;
        try {
            start = format.parse(startString);
            limit = format.parse(limitString);
        } catch (ParseException e) {
            LOGGER.error(e);
        }
        return sensorDataService.findSerialData(productId, sensorNo, start, limit);

    }

    @Path("findProduct/{name}")
    @GET
    public List<Product> findProduct(@PathParam("name") String name, @HeaderParam("token") String token) {
        Account account = (Account) tokenCache.get(token).getObjectValue();
        return (List<Product>) productService.findByName(account, name);
    }

}
