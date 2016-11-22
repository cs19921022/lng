package cn.edu.hdu.lab505.innovation.domain.data;

/**
 * Created by hhx on 2016/11/19.
 */
public class Data {
    private long timestamp;
    private String value;

    public Data() {
    }

    public Data(long timestamp, String value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
