package cn.edu.hdu.lab505.innovation.quartz.job;

import cn.edu.hdu.lab505.innovation.service.ISensorDataService;
import cn.edu.hdu.lab505.innovation.util.SpringUtil;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by hhx on 2016/12/4.
 */
public class MoveDataJob extends QuartzJobBean {

    private ISensorDataService sensorDataService;
    private static final Logger LOGGER = Logger.getLogger(MoveDataJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        sensorDataService = (ISensorDataService) SpringUtil.getBean("sensorDataService");
        sensorDataService.moveDataToHistory();
        LOGGER.info("data move success");
    }
}
