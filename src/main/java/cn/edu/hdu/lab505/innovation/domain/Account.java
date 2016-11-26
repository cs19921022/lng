package cn.edu.hdu.lab505.innovation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hhx on 2016/11/19.
 */
@Entity
@Table(name = "t_account", indexes = {@Index(columnList = "id,username")})
public class Account implements Serializable {
    @Id
    @GeneratedValue
    private int id;//客户编号
    @Column(unique = true, nullable = false)
    private String username;//登录名
    @Column(nullable = false)
    private String password;//登录密码

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Role> roleList = new ArrayList<Role>();
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Product> productList = new ArrayList<Product>();
    @Column
    private String name;//客户名称
    @Column
    private String shortName;//客户简称
    @Column
    private String region;//所属区域
    @Column
    private String investmentScale;//投资规模
    @Column
    private String investmentMethod;//产品投资方式
    @Column
    private String center;//是否中心站
    @Column
    private String settlementMode;//结算模式
    @Column
    private String deliveryMethod;//配送方式
    @Column
    private String earlyWarningThreshold;//预存款预警值
    @Column
    private String linkman;//联系人
    @Column
    private String contact;//联系电话
    @Column
    private String signedClient;//签约客户
    @Column
    private String status;//客户状态
    @Column
    private String type;//客户类型
    @Column
    private String progress;//客户进度
    @Column
    private String salesman;//业务员
    @Column
    private String industry;//行业
    @Column
    private String address;//公司地址
    @Column
    private String contractUnitPrice;//当前合同执行单价
    @Column
    private String unit;//单位
    @Column
    private String remark;//备注
    @Column
    private String operator;//操作人
    @Column
    private Date operationTime;//操作时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getInvestmentScale() {
        return investmentScale;
    }

    public void setInvestmentScale(String investmentScale) {
        this.investmentScale = investmentScale;
    }

    public String getInvestmentMethod() {
        return investmentMethod;
    }

    public void setInvestmentMethod(String investmentMethod) {
        this.investmentMethod = investmentMethod;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getSettlementMode() {
        return settlementMode;
    }

    public void setSettlementMode(String settlementMode) {
        this.settlementMode = settlementMode;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getEarlyWarningThreshold() {
        return earlyWarningThreshold;
    }

    public void setEarlyWarningThreshold(String earlyWarningThreshold) {
        this.earlyWarningThreshold = earlyWarningThreshold;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getSignedClient() {
        return signedClient;
    }

    public void setSignedClient(String signedClient) {
        this.signedClient = signedClient;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContractUnitPrice() {
        return contractUnitPrice;
    }

    public void setContractUnitPrice(String contractUnitPrice) {
        this.contractUnitPrice = contractUnitPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Account() {
    }

    public Account(int id) {
        this.id = id;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
