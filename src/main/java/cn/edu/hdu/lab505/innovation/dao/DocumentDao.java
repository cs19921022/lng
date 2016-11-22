package cn.edu.hdu.lab505.innovation.dao;

import cn.edu.hdu.lab505.innovation.domain.data.Data;
import cn.edu.hdu.lab505.innovation.domain.data.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hhx on 2016/11/19.
 */
@Repository
public class DocumentDao extends BasicDAO<Document, ObjectId> implements IDocumentDao {
    @Autowired
    protected DocumentDao(Datastore ds) {
        super(ds);
    }

    @Override
    public Document findLatestByProductId(int productId) {
        ensureIndexes();
        Query<Document> query = getDatastore().createQuery(getEntityClass());
        query.limit(1);
        query.order("-start");
        query.and(query.criteria("productId").equal(productId));
        List<Document> list = find(query).asList();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<Document> findByProductIdLimit(int productId, long start, long limit) {
        ensureIndexes();
        Query<Document> query = getDatastore().createQuery(getEntityClass());
        query.and(query.criteria("productId").equal(productId));
        query.filter("start <=", limit);
        query.filter("start >=", start);
        return find(query).asList();
    }

    @Override
    public void update(Document document, String[] arrays) {
        long timestamp = System.currentTimeMillis();
        Datastore datastore = getDatastore();
        UpdateOperations ops = datastore.createUpdateOperations(Document.class);
        ops.add("S1.dataList", new Data(timestamp, arrays[0]));
        ops.add("S2.dataList", new Data(timestamp, arrays[1]));
        ops.add("S3.dataList", new Data(timestamp, arrays[2]));
        ops.add("S4.dataList", new Data(timestamp, arrays[3]));
        ops.add("S5.dataList", new Data(timestamp, arrays[4]));
        ops.add("S6.dataList", new Data(timestamp, arrays[5]));
        ops.add("S7.dataList", new Data(timestamp, arrays[6]));
        ops.add("S8.dataList", new Data(timestamp, arrays[7]));
        ops.add("S9.dataList", new Data(timestamp, arrays[8]));
        ops.add("S10.dataList", new Data(timestamp, arrays[9]));
        ops.add("S11.dataList", new Data(timestamp, arrays[10]));
        ops.add("S12.dataList", new Data(timestamp, arrays[11]));
        ops.add("S13.dataList", new Data(timestamp, arrays[12]));
        ops.add("S14.dataList", new Data(timestamp, arrays[13]));
        ops.add("S15.dataList", new Data(timestamp, arrays[14]));
        ops.add("S16.dataList", new Data(timestamp, arrays[15]));
        ops.add("S17.dataList", new Data(timestamp, arrays[16]));
        ops.add("S18.dataList", new Data(timestamp, arrays[17]));
        ops.add("S19.dataList", new Data(timestamp, arrays[18]));
        ops.add("S20.dataList", new Data(timestamp, arrays[19]));
        ops.add("S21.dataList", new Data(timestamp, arrays[20]));
        ops.add("S22.dataList", new Data(timestamp, arrays[21]));
        ops.add("S23.dataList", new Data(timestamp, arrays[22]));
        ops.add("S24.dataList", new Data(timestamp, arrays[23]));
        ops.add("S25.dataList", new Data(timestamp, arrays[24]));
        ops.add("S26.dataList", new Data(timestamp, arrays[25]));
        ops.add("S27.dataList", new Data(timestamp, arrays[26]));
        ops.add("S28.dataList", new Data(timestamp, arrays[27]));
        ops.add("S29.dataList", new Data(timestamp, arrays[28]));
        ops.add("S30.dataList", new Data(timestamp, arrays[29]));
        ops.add("S31.dataList", new Data(timestamp, arrays[30]));
        ops.add("S32.dataList", new Data(timestamp, arrays[31]));
        ops.add("S33.dataList", new Data(timestamp, arrays[32]));
        datastore.update(document, ops);
    }

    @Override
    public void add(int pid, String[] arrays) {

        Document document = new Document();
        document.setProductId(pid);
        long timestamp = System.currentTimeMillis();
        document.setStart(timestamp);
        document.getS1().add(new Data(timestamp, arrays[0]));
        document.getS2().add(new Data(timestamp, arrays[1]));
        document.getS3().add(new Data(timestamp, arrays[2]));
        document.getS4().add(new Data(timestamp, arrays[3]));
        document.getS5().add(new Data(timestamp, arrays[4]));
        document.getS6().add(new Data(timestamp, arrays[5]));
        document.getS7().add(new Data(timestamp, arrays[6]));
        document.getS8().add(new Data(timestamp, arrays[7]));
        document.getS9().add(new Data(timestamp, arrays[8]));
        document.getS10().add(new Data(timestamp, arrays[9]));
        document.getS11().add(new Data(timestamp, arrays[10]));
        document.getS12().add(new Data(timestamp, arrays[11]));
        document.getS13().add(new Data(timestamp, arrays[12]));
        document.getS14().add(new Data(timestamp, arrays[13]));
        document.getS15().add(new Data(timestamp, arrays[14]));
        document.getS16().add(new Data(timestamp, arrays[15]));
        document.getS17().add(new Data(timestamp, arrays[16]));
        document.getS18().add(new Data(timestamp, arrays[17]));
        document.getS19().add(new Data(timestamp, arrays[18]));
        document.getS20().add(new Data(timestamp, arrays[19]));
        document.getS21().add(new Data(timestamp, arrays[20]));
        document.getS22().add(new Data(timestamp, arrays[21]));
        document.getS23().add(new Data(timestamp, arrays[22]));
        document.getS24().add(new Data(timestamp, arrays[23]));
        document.getS25().add(new Data(timestamp, arrays[24]));
        document.getS26().add(new Data(timestamp, arrays[25]));
        document.getS27().add(new Data(timestamp, arrays[26]));
        document.getS28().add(new Data(timestamp, arrays[27]));
        document.getS29().add(new Data(timestamp, arrays[28]));
        document.getS30().add(new Data(timestamp, arrays[29]));
        document.getS31().add(new Data(timestamp, arrays[30]));
        document.getS32().add(new Data(timestamp, arrays[31]));
        document.getS33().add(new Data(timestamp, arrays[32]));
        save(document);
    }
}
