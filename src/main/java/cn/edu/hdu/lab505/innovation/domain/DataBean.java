package cn.edu.hdu.lab505.innovation.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hhx on 2016/11/25.
 */
public class DataBean implements Serializable {
    private Date date;
    private Object value;

    public DataBean(Date date, Object value) {
        this.date = date;
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
