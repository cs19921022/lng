package cn.edu.hdu.lab505.innovation.service;

import cn.edu.hdu.lab505.innovation.common.ICurdServiceSupport;
import cn.edu.hdu.lab505.innovation.common.Page;
import cn.edu.hdu.lab505.innovation.domain.Account;
import cn.edu.hdu.lab505.innovation.domain.Product;

import java.util.List;

/**
 * Created by hhx on 2016/11/19.
 */
public interface IProductService extends ICurdServiceSupport<Product> {

    void updateIgnoreImei(Product product);

    Page<Product> findByAccountId(int id, int start, int limit);


    List<Product> findByName(Account account, String name);
}
