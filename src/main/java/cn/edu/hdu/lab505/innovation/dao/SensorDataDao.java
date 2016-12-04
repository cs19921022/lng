package cn.edu.hdu.lab505.innovation.dao;

import cn.edu.hdu.lab505.innovation.common.AbstractHibernateCurdDaoSupport;
import cn.edu.hdu.lab505.innovation.common.Page;
import cn.edu.hdu.lab505.innovation.domain.DataBean;
import cn.edu.hdu.lab505.innovation.domain.SensorData;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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
    private static final String ACTIVE_ENTITY = "active";
    private static final String HISTORY_ENTITY = "history";

    @Override
    public void insert(SensorData entity) {
        getHibernateTemplate().save("active", entity);
    }

    /**
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
                    //String sql="SELECT * FROM (SELECT * FROM t_sensorData ORDER BY date DESC)  t GROUP BY product_id HAVING product_id in (:ids)";
                    String sql = "SELECT * FROM t_sensordata WHERE id IN (SELECT MAX(id) FROM t_sensordata  GROUP BY product_id HAVING product_id in (:ids)) ";
                    SQLQuery sqlQuery = session.createSQLQuery(sql);
                    sqlQuery.setParameterList("ids", ids);
                    List<SensorData> list = sqlQuery.addEntity(ACTIVE_ENTITY).list();
                    sList.addAll(list);
                    List<Integer> inHistory = new ArrayList<Integer>();
                    for (Integer i : ids) {
                        boolean isInHistory = true;
                        for (SensorData sensorData : list) {
                            if (i == sensorData.getProductId()) {
                                isInHistory = false;
                                break;
                            }
                        }
                        if (isInHistory == true) {
                            inHistory.add(i);
                        }
                    }
                    if (!inHistory.isEmpty()) {
                        sql = "SELECT * FROM t_sensordata_history WHERE id IN (SELECT MAX(id) FROM t_sensordata  GROUP BY product_id HAVING product_id in (:ids)) ";
                        sqlQuery = session.createSQLQuery(sql);
                        sqlQuery.setParameterList("ids", inHistory);
                        sList.addAll(sqlQuery.addEntity(ACTIVE_ENTITY).list());
                    }
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
                    String sqlFindMinDate = "SELECT MIN(date) FROM t_sensordata WHERE product_id = ?";
                    SQLQuery queryMinDate = session.createSQLQuery(sqlFindMinDate);
                    queryMinDate.setInteger(0, productId);
                    List<Timestamp> dateList = queryMinDate.list();
                    Date minDate = null;
                    List<Object[]> list = new LinkedList<Object[]>();
                    if (!dateList.isEmpty() && dateList.get(0) != null) {
                        Timestamp timestamp = dateList.get(0);
                        minDate = new Date(timestamp.getTime());
                    }
                    boolean findInHistory = true;
                    if (minDate != null) {
                        if (start.getTime() > minDate.getTime()) {
                            findInHistory = false;
                        }
                    }
                    if (findInHistory) {
                        String sql = "SELECT date ," + field + " FROM t_sensordata_history WHERE date >= ? AND date <= ? AND product_id = ? ORDER BY date ASC";
                        SQLQuery query = session.createSQLQuery(sql);
                        query.setTimestamp(0, start);
                        query.setTimestamp(1, limit);
                        query.setInteger(2, productId);
                        list.addAll(query.list());
                    }
                    if (minDate != null) {
                        String sql = "SELECT date ," + field + " FROM t_sensordata WHERE date >= ? AND date <= ? AND product_id = ? ORDER BY date ASC";
                        SQLQuery query = session.createSQLQuery(sql);
                        query.setTimestamp(0, start);
                        query.setTimestamp(1, limit);
                        query.setInteger(2, productId);
                        list.addAll(query.list());
                    }
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

    @Override
    public void batchDelete(String entityName, List<Long> list) {
        getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Boolean>() {
            @Override
            public Boolean doInHibernate(Session session) throws HibernateException {
                try {
                    for (int i = 0; i < list.size(); i++) {
                        session.delete(entityName, new SensorData(list.get(i)));
                        if (i % 50 == 0) {
                            session.flush();
                            session.clear();
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error(e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public void batchInsert(String entityName, List<SensorData> list) {
        getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Boolean>() {
            @Override
            public Boolean doInHibernate(Session session) throws HibernateException {
                try {
                    for (int i = 0; i < list.size(); i++) {
                        session.save(entityName, list.get(i));
                        if (i % 50 == 0) {
                            session.flush();
                            session.clear();
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error(e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public Page<SensorData> findSensorDataLeDate(String entityName, Date date, int start, int limit) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forEntityName(entityName);
        detachedCriteria.add(Restrictions.le("date", date));
        return findPage(detachedCriteria, start, limit);
    }
}
