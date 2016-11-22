package cn.edu.hdu.lab505.innovation.dao;

import cn.edu.hdu.lab505.innovation.domain.data.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.DAO;

import java.util.List;

/**
 * Created by hhx on 2016/11/19.
 */
public interface IDocumentDao extends DAO<Document, ObjectId> {

    Document findLatestByProductId(int productId);

    List<Document> findByProductIdLimit(int productId, long start, long limit);

    void update(Document document, String[] arrays);

    void add(int pid, String[] arrays);
}
