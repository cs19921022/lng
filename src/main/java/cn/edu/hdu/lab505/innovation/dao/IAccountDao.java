package cn.edu.hdu.lab505.innovation.dao;

import cn.edu.hdu.lab505.innovation.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.innovation.domain.Account;

import java.util.List;

/**
 * Created by hhx on 2016/11/19.
 */
public interface IAccountDao extends ICurdDaoSupport<Account> {
    Account getByAccount(String username);

    List<Account> getByName(String name);
}
