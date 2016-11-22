package cn.edu.hdu.lab505.innovation.zbox.service;

import cn.edu.hdu.lab505.innovation.service.Exception.ImeiNotFoundException;
import cn.edu.hdu.lab505.innovation.service.IProductService;
import cn.edu.hdu.lab505.innovation.util.ByteUtil;
import cn.edu.hdu.lab505.innovation.util.HexToFloatUtil;
import cn.edu.hdu.lab505.innovation.zbox.DataQueueManager;
import cn.edu.hdu.lab505.innovation.zbox.EFuncion;
import cn.edu.hdu.lab505.innovation.zbox.domain.Frame;
import cn.edu.hdu.lab505.innovation.zbox.domain.UpData;
import cn.edu.hdu.lab505.innovation.zbox.support.FrameSupport;
import cn.edu.hdu.lab505.innovation.zbox.support.UpDataSupport;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hhx on 2016/11/20.
 */
@Component
public class Consumer implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(Consumer.class);
    private DataQueueManager dataQueueManager;
    private FrameSupport frameSupport;
    private UpDataSupport upDataSupport;
    @Autowired
    private IProductService productService;

    public Consumer() {
        this.dataQueueManager = DataQueueManager.getInstance();
        this.frameSupport = new FrameSupport();
        this.upDataSupport = new UpDataSupport();
        // this.productService = new ProductService();
    }

    private void saveData(Frame frame) {
        upDataSupport.reset();
        String content = frame.getInstructionContent();

        upDataSupport.setDataString(content);
        List<UpData> upData = upDataSupport.getUpdataFrame();
        List<String> error = upDataSupport.getErrorId();
        if (error.size() > 0) {
            // 数据项采集失败
            //LOGGER.info("====数据项采集失败=====");
            //  return;
        }
        // 终端标识符，ZBOX板IMEI的后10位
        String imei = frame.getTerminalIidentification();
        String[] arrays = new String[33];
        for (int i = 0; i < upData.size(); i++) {
            String data = upData.get(i).getData();
            byte[] bs = ByteUtil.hexStringToByteArray(data);
            float value = 0f;
            try {
                value = HexToFloatUtil.toFloat(bs);
                arrays[i] = String.valueOf(value);
            } catch (Exception e) {
                arrays[i]=data;
            }

        }
        try {
            productService.addSensorData(imei, arrays);
        } catch (ImeiNotFoundException e) {
            LOGGER.info(e);
        }
    }

    @Override
    public void run() {
        LOGGER.info("消费者线程启动");
        while (true) {
            frameSupport.reset();
            byte[] bytes = dataQueueManager.take();
            frameSupport.setDataByteArr(bytes);
            Frame frame = frameSupport.getFrameStructure();
            boolean isCorrect = frameSupport.isCorrect();
            if (!isCorrect) {
                LOGGER.info("数据错误，丢弃!");
                continue;
            }
            String function = frame.getFunction();
            if (function.equals(EFuncion.R_UP_UPDATA.getValue())) {
                saveData(frame);
            }
        }
    }
}
