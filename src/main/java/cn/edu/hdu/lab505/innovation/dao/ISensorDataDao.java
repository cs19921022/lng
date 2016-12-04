package cn.edu.hdu.lab505.innovation.dao;

import cn.edu.hdu.lab505.innovation.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.innovation.common.Page;
import cn.edu.hdu.lab505.innovation.domain.DataBean;
import cn.edu.hdu.lab505.innovation.domain.SensorData;

import java.util.Date;
import java.util.List;

/**
 * Created by hhx on 2016/11/24.
 */
public interface ISensorDataDao extends ICurdDaoSupport<SensorData> {

    List<SensorData> findLatestByProductIds(Integer... ids);

    List<DataBean> findSerialData(int productId, String field, Date start, Date limit);

    void batchDelete(String entityName, List<Long> list);


    void batchInsert(String entityName, List<SensorData> list);

    Page<SensorData> findSensorDataLeDate(String entityName, Date date, int start, int limit);
}
