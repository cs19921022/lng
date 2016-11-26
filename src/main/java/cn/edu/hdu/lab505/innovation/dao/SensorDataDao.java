package cn.edu.hdu.lab505.innovation.dao;

import cn.edu.hdu.lab505.innovation.common.AbstractHibernateCurdDaoSupport;
import cn.edu.hdu.lab505.innovation.domain.DataBean;
import cn.edu.hdu.lab505.innovation.domain.SensorData;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hhx on 2016/11/24.
 */
@Repository
public class SensorDataDao extends AbstractHibernateCurdDaoSupport<SensorData> implements ISensorDataDao {
    private static final Logger LOGGER = Logger.getLogger(SensorDataDao.class);




    /**
     *
     *
     * @param ids
     * @return
     */
    @Override
    public List<SensorData> findLatestByProductIds(Integer... ids) {
        List<SensorData> sList = new ArrayList<>();

        getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Boolean>() {
            @Override
            public Boolean doInHibernate(Session session) throws HibernateException {
                try {
                    String sql="SELECT * FROM (SELECT * FROM t_sensorData ORDER BY date DESC)  t GROUP BY product_id HAVING product_id in (:ids)";
                    SQLQuery sqlQuery=session.createSQLQuery(sql);
                    sqlQuery.setParameterList("ids",ids);
                    List<SensorData> list=sqlQuery.addEntity(SensorData.class).list();
                    sList.addAll(list);
                } catch (Exception e) {
                    LOGGER.error(e);
                }
                return true;
            }
        });
        return sList;
    }

    @Override
    public List<DataBean> findSerialData(int productId, String field, Date start, Date limit) {
        List<DataBean> dataList = new LinkedList<>();
        getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Boolean>() {
            @Override
            public Boolean doInHibernate(Session session) throws HibernateException {
                try {
                    String hql = "select date ," + field + " from SensorData where date >= ? and date <= ? and product_id = ? order by date asc";
                    Query query = session.createQuery(hql);
                    query.setTimestamp(0, start);
                    query.setTimestamp(1, limit);
                    query.setInteger(2, productId);
                    List<Object[]> list = query.list();
                    for (Object[] values : list) {
                        Object inject0 = null;
                        Object inject1 = null;
                        try {
                            inject0 = values[0];
                            inject1 = values[1];
                        } catch (Exception e) {
                        }
                        if (inject0 != null && inject1 != null) {
                            dataList.add(new DataBean((Date) inject0, inject1));
                        }

                    }
                } catch (Exception e) {
                    LOGGER.error(e);
                }

                return true;
            }
        });
        return dataList;
    }
}
