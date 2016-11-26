package cn.edu.hdu.lab505.innovation.dao;

import cn.edu.hdu.lab505.innovation.common.AbstractHibernateCurdDaoSupport;
import cn.edu.hdu.lab505.innovation.domain.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hhx on 2016/11/19.
 */
@Repository
public class AccountDao extends AbstractHibernateCurdDaoSupport<Account> implements IAccountDao {
    @Override
    public Account getByAccount(String username) {
        List<Account> list = getHibernateTemplate().findByExample(new Account(username, null));
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
