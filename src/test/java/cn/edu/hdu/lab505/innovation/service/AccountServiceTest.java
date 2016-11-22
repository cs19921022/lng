package cn.edu.hdu.lab505.innovation.service;

import cn.edu.hdu.lab505.innovation.domain.domain.Account;
import cn.edu.hdu.lab505.innovation.domain.domain.Product;
import cn.edu.hdu.lab505.innovation.domain.domain.Role;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    @Transactional
    @Rollback(false)
    public void test() {
        Account account=accountService.get(1);
        Product product = new Product();
        product.setAccount(account);
        product.setImei("2158730427");
        product.setName("test");
        productService.insert(product);
    }
}
