package cn.edu.hdu.lab505.innovation.zbox;

import org.apache.log4j.Logger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by hhx on 2016/11/20.
 */
public class DataQueueManager {
    private BlockingQueue<byte[]> queue;
    private static final int capacity = 100;
    private volatile static DataQueueManager dataQueueManager;
    private static final Logger LOGGER = Logger.getLogger(DataQueueManager.class);

    private DataQueueManager() {
        this.queue = new ArrayBlockingQueue<byte[]>(capacity);
    }

    public void put(byte[] bytes) {
        try {
            queue.put(bytes);
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
    }

    public byte[] take() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            LOGGER.error(e);
            return null;
        }
    }

    public static DataQueueManager getInstance() {
        if (dataQueueManager == null) {
            synchronized (DataQueueManager.class) {
                if (dataQueueManager == null) {
                    dataQueueManager = new DataQueueManager();
                }
            }
        }
        return dataQueueManager;
    }
}
