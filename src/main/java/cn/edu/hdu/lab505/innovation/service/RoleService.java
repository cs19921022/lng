package cn.edu.hdu.lab505.innovation.service;

import cn.edu.hdu.lab505.innovation.common.AbstractCurdServiceSupport;
import cn.edu.hdu.lab505.innovation.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.innovation.dao.IRoleDao;
import cn.edu.hdu.lab505.innovation.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hhx on 2016/11/19.
 */
@Service
public class RoleService extends AbstractCurdServiceSupport<Role> implements IRoleService {
    @Autowired
    private IRoleDao roleDao;

    protected ICurdDaoSupport<Role> getCurdDao() {
        return roleDao;
    }
}
