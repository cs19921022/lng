package cn.edu.hdu.lab505.innovation.dao;

import cn.edu.hdu.lab505.innovation.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.innovation.domain.Account;

/**
 * Created by hhx on 2016/11/19.
 */
public interface IAccountDao extends ICurdDaoSupport<Account> {
    public Account getByAccount(String username);
}
