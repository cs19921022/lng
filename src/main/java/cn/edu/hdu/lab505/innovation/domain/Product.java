package cn.edu.hdu.lab505.innovation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by hhx on 2016/11/19.
 */
public class Product implements Serializable {
    private int id;
    private String name;
    private String specification;
    private String imei;
    private String type;
    private String region;
    private String address;
    private String longitude;
    private String latitude;

    private Account account;

    private SensorData data = new SensorData();

    public SensorData getData() {
        return data;
    }

    public void setData(SensorData data) {
        this.data = data;
    }

    public Product() {
    }

    public Product(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    public Account getAccount() {
        return account;
    }


    public void setAccount(Account account) {
        this.account = account;
    }

    public Product(int id, String name, String specification, String imei, String type, String region, String address, String longitude, String latitude) {
        this.id = id;
        this.name = name;
        this.specification = specification;
        this.imei = imei;
        this.type = type;
        this.region = region;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
