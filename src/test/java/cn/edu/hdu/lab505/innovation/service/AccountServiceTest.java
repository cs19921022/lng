package cn.edu.hdu.lab505.innovation.service;

import cn.edu.hdu.lab505.innovation.dao.ISensorDataDao;
import cn.edu.hdu.lab505.innovation.domain.Account;
import cn.edu.hdu.lab505.innovation.domain.Product;
import cn.edu.hdu.lab505.innovation.domain.Role;
import cn.edu.hdu.lab505.innovation.domain.SensorData;
import cn.edu.hdu.lab505.innovation.service.Exception.SensorDataIndexOutOfBoundsException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hhx on 2016/11/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AccountServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IProductService productService;
    @Autowired
    private ISensorDataService sensorDataService;
    @Autowired
    private ISensorDataDao sensorDataDao;

    @Test
    @Transactional
    @Rollback(false)
    public void test() throws IOException {
       /* byte[] bytes = { 0x7E, 0x00, 0x01, 0x10, 0x78, 0x34,
                0x12, 0x06, 0x03, 0x35, 0x35, 0x00, 0x00, (byte) 0x86,0x01, 0x11, (byte) 0xDB, 0x01, 0x01, 0x00, 0x04, (byte) 0xED,
                (byte) 0x88, 0x1C, 0x44, (byte) 0xDB, 0x01, 0x03, 0x00, 0x04,(byte) 0xED, (byte) 0xBE, 0x06, 0x40, (byte) 0xDB, 0x01, 0x04, 0x00,
                0x04, 0x6C, (byte) 0xE2, (byte) 0xD9, 0x40, (byte) 0xDB, 0x01, 0x07,0x00, 0x01, 0x01, (byte) 0xDB, 0x01, 0x08, 0x00, 0x01,
                0x01, (byte) 0xDB, 0x01, 0x09, 0x00, 0x01, 0x00, (byte) 0xDB,0x01, 0x0A, 0x00, 0x01, 0x00, (byte) 0xDB, 0x01, 0x0B,
                0x00, 0x01, 0x00, (byte) 0xDB, 0x01, 0x0C, 0x00, 0x01,0x00, (byte) 0xDB, 0x01, 0x0D, 0x00, 0x01, 0x00, (byte) 0xDB,
                0x01, 0x16, 0x00, 0x04, (byte) 0xF9, 0x0D, (byte) 0xF1, 0x42,(byte) 0xDB, 0x01, 0x17, 0x00, 0x04, (byte) 0x8E, (byte) 0x83, (byte) 0xF7,
                0x41, (byte) 0xDB, 0x01, 0x18, 0x00, 0x04, 0x03, (byte) 0xA2,0x02, 0x46, (byte) 0xDB, 0x01, 0x19, 0x00, 0x04, (byte) 0xB6,
                0x00, (byte) 0x94, 0x41, (byte) 0xDB, 0x01, 0x1B, 0x00, 0x04,0x50, (byte) 0x8F, (byte) 0xE2, 0x43, (byte) 0xDB, 0x01, 0x1D, 0x00,
                0x04, (byte) 0xEB, (byte) 0xA4, 0x0C, (byte) 0xC3, (byte) 0xDB, 0x01, 0x1F,0x00, 0x04, 0x22, 0x39, (byte) 0x84, 0x4A, 0x70, 0x7E };*/

        //// ProtocolSupport protocolSupport=new ProtocolSupport(bytes);

        // DataSupport dataSupport=new DataSupport(protocolSupport.getContent());
        // List<DataSupport.DataFrame> dataFrameList=dataSupport.getDataFrameList();
        // float f = ByteBuffer.wrap(new byte[]{0x41,  0x36,0x00,0x00}).getFloat();

    }

    @Test
    //@Transactional
    @Rollback(false)
    public void roleTest() {
        Role role = new Role(1, "admin");
        Role role1 = new Role(2, "user");
        roleService.insert(role);
        roleService.insert(role1);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void accountTest() {
        Account account = new Account();
        Role role = new Role(1, "admin");
        Set<Role> list = new HashSet<>();
        list.add(role);
        account.setUsername("www");
        account.setPassword("1234");
        account.setRoles(list);
        accountService.insert(account);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void productTest() {
        Product product = new Product();
        product.setName("fsfd");
        product.setImei("1078341206");
        Account account = new Account(1);
        product.setAccount(account);
        productService.insert(product);
    }

    @Test
    public void testField() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        SensorData sensorData = new SensorData();
        Class<SensorData> sensorDataClass = (Class<SensorData>) sensorData.getClass();
        Field[] fields = sensorDataClass.getDeclaredFields();
        String n = fields[0].getName();

        Method methods = sensorDataClass.getMethod("set" + n.substring(0, 1).toUpperCase() + n.substring(1, n.length()), long.class);
        methods.invoke(sensorData, 1);
        System.out.println(sensorData.getId());
    }

    @Test
    public void testFindData() throws SensorDataIndexOutOfBoundsException, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String s1 = "2016-11-25 14:54:44";
        String s2 = "2016-12-25 14:58:49";

        sensorDataService.findSerialData(1, 1, simpleDateFormat.parse(s1), simpleDateFormat.parse(s2));
        System.out.println();

    }


}
