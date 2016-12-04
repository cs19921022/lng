package cn.edu.hdu.lab505.innovation.dao;

import cn.edu.hdu.lab505.innovation.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.innovation.common.Page;
import cn.edu.hdu.lab505.innovation.domain.Product;

import java.util.List;

/**
 * Created by hhx on 2016/11/19.
 */
public interface IProductDao extends ICurdDaoSupport<Product> {
    Page<Product> findByAccountId(int id, int start, int limit);

    Product getByImei(String imei);

    List<Product> getByName(String name);

    List<Product> getByName(int accountId, String name);
}
