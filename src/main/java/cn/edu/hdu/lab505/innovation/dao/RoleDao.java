package cn.edu.hdu.lab505.innovation.dao;

import cn.edu.hdu.lab505.innovation.common.AbstractHibernateCurdDaoSupport;
import cn.edu.hdu.lab505.innovation.domain.Role;
import org.springframework.stereotype.Repository;

/**
 * Created by hhx on 2016/11/19.
 */
@Repository
public class RoleDao extends AbstractHibernateCurdDaoSupport<Role> implements IRoleDao {
}
