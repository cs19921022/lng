package cn.edu.hdu.lab505.innovation.zbox.service;

import cn.edu.hdu.lab505.innovation.service.Exception.ImeiNotFoundException;
import cn.edu.hdu.lab505.innovation.service.IProductService;
import cn.edu.hdu.lab505.innovation.service.ISensorDataService;
import cn.edu.hdu.lab505.innovation.util.ByteUtil;
import cn.edu.hdu.lab505.innovation.util.HexToFloatUtil;
import cn.edu.hdu.lab505.innovation.zbox.DataQueueManager;
import cn.edu.hdu.lab505.innovation.zbox.EFuncion;
import cn.edu.hdu.lab505.innovation.zbox.domain.Frame;
import cn.edu.hdu.lab505.innovation.zbox.domain.UpData;
import cn.edu.hdu.lab505.innovation.zbox.support.DataSupport;
import cn.edu.hdu.lab505.innovation.zbox.support.FrameSupport;
import cn.edu.hdu.lab505.innovation.zbox.support.ProtocolSupport;
import cn.edu.hdu.lab505.innovation.zbox.support.UpDataSupport;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by hhx on 2016/11/20.
 */
@Component
public class Consumer implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(Consumer.class);
    private DataQueueManager dataQueueManager;
    @Autowired
    protected ISensorDataService sensorDataService;
    @Autowired
    private ProtocolSupport protocolSupport;
    @Autowired
    private DataSupport dataSupport;

    public Consumer() {
        this.dataQueueManager = DataQueueManager.getInstance();
    }

    @Transactional
    private void insertData(byte[] bytes) {
        dataSupport.reset();
        dataSupport.setContent(bytes);
        List<DataSupport.DataFrame> dataFrameList = dataSupport.getDataFrameList();
        byte[] imei = protocolSupport.getTerminal();
        String[] arrays = new String[33];
        for (int i = 0; i < dataFrameList.size(); i++) {
            DataSupport.DataFrame dataFrame = dataFrameList.get(i);
            byte dataId = dataFrame.getDataId();
            byte[] data = dataFrame.getData();
            if ((dataId > 0 && dataId < 7) || (dataId > 13 && dataId < 20) || (dataId > 21 && dataId < 34)) {
                try {
                    if(dataId==19){
                        arrays[dataId - 1]=ByteUtil.byteToString(data);
                    }else{
                        arrays[dataId - 1] = String.valueOf(ByteUtil.toFloat(data));
                    }
                } catch (Exception e) {
                    LOGGER.error(e);
                }

            } else {
                //整形
                if (data[0] == 0x00) {
                    arrays[dataId - 1] = "关";
                } else if (data[0] == 0x01) {
                    arrays[dataId - 1] = "开";
                }
            }
        }
        try {
            sensorDataService.addSensorData(ByteUtil.byteToString(imei), arrays);
        } catch (ImeiNotFoundException e) {
            LOGGER.info(e);
        }
    }


    @Override
    public void run() {
        LOGGER.info("消费者线程启动");
        while (true) {

            protocolSupport.reset();
            byte[] bytes = dataQueueManager.take();
            protocolSupport.setByteArray(bytes);
            boolean isCorrect = protocolSupport.isCorrect();
            if (!isCorrect) {
                LOGGER.info("数据错误，丢弃!");
                continue;
            }
            byte[] function = protocolSupport.getFunction();
            if (function[0] == 0x00) {
                //try {
                    insertData(protocolSupport.getContent());
                //} catch (Exception e) {
                  //  LOGGER.error(e);
                //}

            }
        }
    }
}
