package cn.edu.hdu.lab505.innovation.dao;

import cn.edu.hdu.lab505.innovation.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.innovation.domain.domain.Product;

import java.util.List;

/**
 * Created by hhx on 2016/11/19.
 */
public interface IProductDao extends ICurdDaoSupport<Product> {
    List<Product> findByAccountId(int id);

    Product getByImei(String imei);
}
