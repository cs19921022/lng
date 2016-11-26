package cn.edu.hdu.lab505.innovation.service;

import cn.edu.hdu.lab505.innovation.common.AbstractCurdServiceSupport;
import cn.edu.hdu.lab505.innovation.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.innovation.common.Page;
import cn.edu.hdu.lab505.innovation.dao.IAccountDao;
import cn.edu.hdu.lab505.innovation.dao.IProductDao;
import cn.edu.hdu.lab505.innovation.dao.ISensorDataDao;
import cn.edu.hdu.lab505.innovation.domain.Account;
import cn.edu.hdu.lab505.innovation.domain.Product;
import cn.edu.hdu.lab505.innovation.domain.Role;
import cn.edu.hdu.lab505.innovation.domain.SensorData;
import cn.edu.hdu.lab505.innovation.service.Exception.ImeiNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hhx on 2016/11/19.
 */

@Service
public class ProductService extends AbstractCurdServiceSupport<Product> implements IProductService {
    private static final Logger LOGGER = Logger.getLogger(ProductService.class);
    @Autowired
    private IProductDao productDao;
    @Autowired
    private IAccountDao accountDao;

    @Autowired
    private ISensorDataDao sensorDataDao;


    @Override
    @Transactional
    public Page<Product> findByAccountId(int id, int start, int limit) {
        Account account = accountDao.get(id);
        List<Role> roles = account.getRoleList();
        Page<Product> page = null;
        boolean isAdmin = false;
        for (Role r : roles
                ) {
            if (r.getName().equals("admin")) {
                isAdmin = true;
            }
        }
        if (isAdmin) {
            page = productDao.findPage(start, limit);
        } else {
            page = productDao.findByAccountId(id, start, limit);
        }
        List<Product> list=page.getList();
        Integer[] productIds = new Integer[list.size()];
        for (int i = 0; i < list.size(); i++) {
            productIds[i] = list.get(i).getId();
        }
        List<SensorData> dataList = sensorDataDao.findLatestByProductIds(productIds);
        if (dataList.isEmpty()) {
            return page;
        }
        for (int i = 0; i < list.size(); i++) {
            int originId = list.get(i).getId();
            for (SensorData sensorData : dataList) {
                int pid = sensorData.getProduct().getId();
                if (pid == originId) {
                    list.get(i).setData(sensorData);
                }
            }
        }
        return page;
    }

    @Override
    @Transactional
    public void insert(Product entity) {
        try {
            super.insert(entity);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateKeyException(entity.getImei() + " duplicate");
        }

    }

    protected ICurdDaoSupport<Product> getCurdDao() {
        return productDao;
    }

    @Override
    @Transactional
    public void updateIgnoreImei(Product product) {
        Product origin = get(product.getId());
        origin.setAddress(product.getAddress());
        origin.setLatitude(product.getLatitude());
        origin.setLongitude(product.getLongitude());
        origin.setName(product.getName());
        origin.setRegion(product.getRegion());
        origin.setSpecification(product.getSpecification());
        origin.setType(product.getType());
        update(origin);
    }


}
