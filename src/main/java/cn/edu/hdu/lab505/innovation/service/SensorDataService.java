package cn.edu.hdu.lab505.innovation.service;

import cn.edu.hdu.lab505.innovation.common.AbstractCurdServiceSupport;
import cn.edu.hdu.lab505.innovation.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.innovation.common.Page;
import cn.edu.hdu.lab505.innovation.dao.IProductDao;
import cn.edu.hdu.lab505.innovation.dao.ISensorDataDao;
import cn.edu.hdu.lab505.innovation.domain.DataBean;
import cn.edu.hdu.lab505.innovation.domain.Product;
import cn.edu.hdu.lab505.innovation.domain.SensorData;
import cn.edu.hdu.lab505.innovation.service.Exception.ImeiNotFoundException;
import cn.edu.hdu.lab505.innovation.service.Exception.SensorDataIndexOutOfBoundsException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by hhx on 2016/11/24.
 */
@Service
public class SensorDataService extends AbstractCurdServiceSupport<SensorData> implements ISensorDataService {
    private static final Logger LOGGER = Logger.getLogger(SensorDataService.class);
    @Autowired
    private ISensorDataDao sensorDataDao;
    @Autowired
    private IProductDao productDao;

    private static final long MONTH = 1000 * 60 * 60 * 24 * 31;

    @Override
    protected ICurdDaoSupport<SensorData> getCurdDao() {
        return sensorDataDao;
    }

    @Override
    @Transactional
    public void moveDataToHistory() {
        final int pageSize = 100000;
        int start = 0;
        Date date = new Date(System.currentTimeMillis() - MONTH);
        Page<SensorData> page = sensorDataDao.findSensorDataLeDate("active", date, start, start + pageSize);
        while (page.getList().size() > 0) {
            List<Long> list = new ArrayList<>(pageSize);
            for (SensorData sensorData : page.getList()) {
                list.add(sensorData.getId());
            }
            sensorDataDao.batchInsert("history", page.getList());
            sensorDataDao.batchDelete("active", list);
            start += pageSize;
            page = sensorDataDao.findSensorDataLeDate("active", date, start, start + pageSize);

        }
    }

    @Override
    @Transactional
    public void addSensorData(String imei, String[] arrays) throws ImeiNotFoundException {
        Product product = productDao.getByImei(imei);
        if (product == null) {
            throw new ImeiNotFoundException();
        }
        SensorData sensorData = new SensorData();
        Class<SensorData> sensorDataClass = (Class<SensorData>) sensorData.getClass();
        Field[] fields = sensorDataClass.getDeclaredFields();
        for (int i = 1; i < fields.length - 2; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            try {
                if ((i >= 7 && i <= 13) || (i >= 20 && i <= 21) || i == 19) {
                    Method method = sensorDataClass.getMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()), String.class);
                    method.invoke(sensorData, arrays[i - 1]);
                } else {
                    Method method = sensorDataClass.getMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()), Float.class);
                    //method.invoke(sensorData, Float.valueOf((arrays[i - 1]==null)?"0":(arrays[i - 1])));
                    if (arrays[i - 1] != null) {
                        method.invoke(sensorData, Float.valueOf(arrays[i - 1]));
                    }
                }
            } catch (NoSuchMethodException e) {
                LOGGER.error(e);
            } catch (IllegalAccessException e) {
                LOGGER.error(e);
            } catch (InvocationTargetException e) {
                LOGGER.error(e);
            }


        }
        sensorData.setDate(new Date());
        sensorData.setProductId(product.getId());
        sensorDataDao.insert(sensorData);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DataBean> findSerialData(int productId, int sensorNo, Date start, Date limit) throws SensorDataIndexOutOfBoundsException {
        Field[] fields = SensorData.class.getDeclaredFields();
        List<DataBean> list = null;
        try {
            String fieldName = fields[sensorNo].getName();
            list = sensorDataDao.findSerialData(productId, fieldName, start, limit);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new SensorDataIndexOutOfBoundsException();
        }
        return list;
    }
}
