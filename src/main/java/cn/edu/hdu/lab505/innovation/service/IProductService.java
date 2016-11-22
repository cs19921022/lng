package cn.edu.hdu.lab505.innovation.service;

import cn.edu.hdu.lab505.innovation.common.ICurdServiceSupport;
import cn.edu.hdu.lab505.innovation.domain.domain.Product;
import cn.edu.hdu.lab505.innovation.service.Exception.ImeiNotFoundException;

import java.util.List;

/**
 * Created by hhx on 2016/11/19.
 */
public interface IProductService extends ICurdServiceSupport<Product> {

    void updateIgnoreImei(Product product);

    List<Product> findByAccountId(int id);

    void addSensorData(String imei, String[] arrays) throws ImeiNotFoundException;
}
