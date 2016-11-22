package cn.edu.hdu.lab505.innovation.listen;

import cn.edu.hdu.lab505.innovation.util.SpringUtil;
import cn.edu.hdu.lab505.innovation.zbox.service.Consumer;
import cn.edu.hdu.lab505.innovation.zbox.service.Producer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * Application Lifecycle Listener implementation class UDPListener
 */

public class UDPListener implements ServletContextListener {

    /**
     * Default constructor.
     */
    public UDPListener() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */

    public void contextInitialized(ServletContextEvent sce) {


        Producer producer = new Producer();
       // Consumer consumer = new Consumer();
        Consumer consumer= (Consumer) SpringUtil.getBean("consumer");
        Thread pThread = new Thread(producer);
        Thread cThread = new Thread(consumer);
        pThread.start();
        cThread.start();
    }

}
