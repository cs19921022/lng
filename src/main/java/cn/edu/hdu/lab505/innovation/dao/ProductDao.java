package cn.edu.hdu.lab505.innovation.dao;

import cn.edu.hdu.lab505.innovation.common.AbstractHibernateCurdDaoSupport;
import cn.edu.hdu.lab505.innovation.common.Page;
import cn.edu.hdu.lab505.innovation.domain.Product;
import org.apache.commons.collections.map.HashedMap;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hhx on 2016/11/19.
 */
@Repository
public class ProductDao extends AbstractHibernateCurdDaoSupport<Product> implements IProductDao {
    @Override
    public Page<Product> findByAccountId(int id, int start, int limit) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Product.class);
        detachedCriteria.createAlias("account", "a");
        detachedCriteria.add(Restrictions.eq("a.id", id));
        return findPage(detachedCriteria, start, limit);
    }

    @Override
    public Product getByImei(String imei) {
       /* DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Product.class);
        detachedCriteria.add(Restrictions.eq("imei", imei));
        List<Product> list = (List<Product>) getHibernateTemplate().findByCriteria(detachedCriteria);
        if (list.isEmpty()) {
            return null;

        } else {
            return list.get(0);
        }*/
        Map<String, Object> map = new HashedMap();
        map.put("imei", imei);
        return getByMap(map);
    }

    private Product getByMap(Map<String, Object> map) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Product.class);
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            detachedCriteria.add(Restrictions.eq(key, map.get(key)));
        }
        List<Product> list = (List<Product>) getHibernateTemplate().findByCriteria(detachedCriteria);
        if (list.isEmpty()) {
            return null;

        } else {
            return list.get(0);
        }
    }

    @Override
    public List<Product> getByName(String name) {

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Product.class);
        detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        //detachedCriteria.add(Restrictions.eq("name", name));
        List<Product> list = (List<Product>) getHibernateTemplate().findByCriteria(detachedCriteria);
        if (list.isEmpty()) {
            return null;

        } else {
            return list;
        }
    }

    @Override
    public List<Product> getByName(int accountId, String name) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Product.class);
        detachedCriteria.createAlias("account", "a");
        detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        detachedCriteria.add(Restrictions.eq("a.id", accountId));
        List<Product> list = (List<Product>) getHibernateTemplate().findByCriteria(detachedCriteria);
        if (list.isEmpty()) {
            return null;

        } else {
            return list;
        }
    }
}
