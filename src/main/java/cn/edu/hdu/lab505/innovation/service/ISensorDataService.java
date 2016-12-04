package cn.edu.hdu.lab505.innovation.service;

import cn.edu.hdu.lab505.innovation.common.ICurdServiceSupport;
import cn.edu.hdu.lab505.innovation.domain.DataBean;
import cn.edu.hdu.lab505.innovation.domain.SensorData;
import cn.edu.hdu.lab505.innovation.service.Exception.ImeiNotFoundException;
import cn.edu.hdu.lab505.innovation.service.Exception.SensorDataIndexOutOfBoundsException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hhx on 2016/11/24.
 */
public interface ISensorDataService extends ICurdServiceSupport<SensorData> {

    void moveDataToHistory();

    void addSensorData(String imei, String[] arrays) throws ImeiNotFoundException;

    List<DataBean> findSerialData(int productId, int sensorNo, Date start, Date limit) throws SensorDataIndexOutOfBoundsException;
}
