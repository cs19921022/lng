package cn.edu.hdu.lab505.innovation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hhx on 2016/11/24.
 */
@Entity
@Table(name = "t_sensorData", indexes = {@Index(columnList = "product_id,date")})
public class SensorData implements Serializable {
    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;
    @Column
    private Float storageLiquidLevel1;//储罐液位1
    @Column
    private Float storageLiquidLevel2;//储罐液位2
    @Column
    private Float storagePressure1;//储罐压力1
    @Column
    private Float storagePressure2;//储罐压力2
    @Column
    private Float gasifyPressure;//气化后压力
    @Column
    private Float gasifyTemperature;//气化后温度
    @Column
    private String outValve1;//出口电磁阀1
    @Column
    private String outValve2;//出口电磁阀2
    @Column
    private String inValve1;//进口电磁阀1
    @Column
    private String inValve2;//进口电磁阀1
    @Column
    private String storageValve1;//储罐增压阀1
    @Column
    private String storageValve2;//储罐增压阀1
    @Column
    private String flowCutValve;//流量计切断阀
    @Column
    private Float totalFlow;//累计流量
    @Column
    private Float instantFlow;//瞬时流量
    @Column
    private Float temperature1;//温度1
    @Column
    private Float pressure;//压力
    @Column
    private Float temperature2;//温度2
    @Column
    private Float temperature3;//温度3
    @Column
    private String fireDetector;//火焰探测
    @Column
    private String combustibleGasDetector;//可燃气体探测
    @Column
    private Float longitude;//GPS的经度
    @Column
    private Float latitude;//GPS的纬度
    @Column
    private Float massFlow;//质量流量
    @Column
    private Float volumeFlow;//体积流量
    @Column
    private Float gaugedVolumeFlow;//校准体积流量
    @Column
    private Float density;//密度
    @Column
    private Float referenceDensity;//参考密度
    @Column
    private Float temperature;//温度
    @Column
    private Float pressure2;//压力
    @Column
    private Float totalMassFlow;//累计质量流量
    @Column
    private Float totalVolumeFlow;//累积体积流量
    @Column
    private Float totalGaugedVolumeFlow;//累积校准体积流量

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",
            foreignKey = @ForeignKey(name = "PRODUCT_ID_FK"))
    @JsonIgnore
    private Product product;


    @Column
    private Date date;


    public SensorData() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Float getStorageLiquidLevel1() {
        return storageLiquidLevel1;
    }

    public void setStorageLiquidLevel1(Float storageLiquidLevel1) {
        this.storageLiquidLevel1 = storageLiquidLevel1;
    }

    public Float getStorageLiquidLevel2() {
        return storageLiquidLevel2;
    }

    public void setStorageLiquidLevel2(Float storageLiquidLevel2) {
        this.storageLiquidLevel2 = storageLiquidLevel2;
    }

    public Float getStoragePressure1() {
        return storagePressure1;
    }

    public void setStoragePressure1(Float storagePressure1) {
        this.storagePressure1 = storagePressure1;
    }

    public Float getStoragePressure2() {
        return storagePressure2;
    }

    public void setStoragePressure2(Float storagePressure2) {
        this.storagePressure2 = storagePressure2;
    }

    public Float getGasifyPressure() {
        return gasifyPressure;
    }

    public void setGasifyPressure(Float gasifyPressure) {
        this.gasifyPressure = gasifyPressure;
    }

    public Float getGasifyTemperature() {
        return gasifyTemperature;
    }

    public void setGasifyTemperature(Float gasifyTemperature) {
        this.gasifyTemperature = gasifyTemperature;
    }

    public String getOutValve1() {
        return outValve1;
    }

    public void setOutValve1(String outValve1) {
        this.outValve1 = outValve1;
    }

    public String getOutValve2() {
        return outValve2;
    }

    public void setOutValve2(String outValve2) {
        this.outValve2 = outValve2;
    }

    public String getInValve1() {
        return inValve1;
    }

    public void setInValve1(String inValve1) {
        this.inValve1 = inValve1;
    }

    public String getInValve2() {
        return inValve2;
    }

    public void setInValve2(String inValve2) {
        this.inValve2 = inValve2;
    }

    public String getStorageValve1() {
        return storageValve1;
    }

    public void setStorageValve1(String storageValve1) {
        this.storageValve1 = storageValve1;
    }

    public String getStorageValve2() {
        return storageValve2;
    }

    public void setStorageValve2(String storageValve2) {
        this.storageValve2 = storageValve2;
    }

    public String getFlowCutValve() {
        return flowCutValve;
    }

    public void setFlowCutValve(String flowCutValve) {
        this.flowCutValve = flowCutValve;
    }

    public Float getTotalFlow() {
        return totalFlow;
    }

    public void setTotalFlow(Float totalFlow) {
        this.totalFlow = totalFlow;
    }

    public Float getInstantFlow() {
        return instantFlow;
    }

    public void setInstantFlow(Float instantFlow) {
        this.instantFlow = instantFlow;
    }

    public Float getTemperature1() {
        return temperature1;
    }

    public void setTemperature1(Float temperature1) {
        this.temperature1 = temperature1;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    public Float getTemperature2() {
        return temperature2;
    }

    public void setTemperature2(Float temperature2) {
        this.temperature2 = temperature2;
    }

    public Float getTemperature3() {
        return temperature3;
    }

    public void setTemperature3(Float temperature3) {
        this.temperature3 = temperature3;
    }

    public String getFireDetector() {
        return fireDetector;
    }

    public void setFireDetector(String fireDetector) {
        this.fireDetector = fireDetector;
    }

    public String getCombustibleGasDetector() {
        return combustibleGasDetector;
    }

    public void setCombustibleGasDetector(String combustibleGasDetector) {
        this.combustibleGasDetector = combustibleGasDetector;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getMassFlow() {
        return massFlow;
    }

    public void setMassFlow(Float massFlow) {
        this.massFlow = massFlow;
    }

    public Float getVolumeFlow() {
        return volumeFlow;
    }

    public void setVolumeFlow(Float volumeFlow) {
        this.volumeFlow = volumeFlow;
    }

    public Float getGaugedVolumeFlow() {
        return gaugedVolumeFlow;
    }

    public void setGaugedVolumeFlow(Float gaugedVolumeFlow) {
        this.gaugedVolumeFlow = gaugedVolumeFlow;
    }

    public Float getDensity() {
        return density;
    }

    public void setDensity(Float density) {
        this.density = density;
    }

    public Float getReferenceDensity() {
        return referenceDensity;
    }

    public void setReferenceDensity(Float referenceDensity) {
        this.referenceDensity = referenceDensity;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getPressure2() {
        return pressure2;
    }

    public void setPressure2(Float pressure2) {
        this.pressure2 = pressure2;
    }

    public Float getTotalMassFlow() {
        return totalMassFlow;
    }

    public void setTotalMassFlow(Float totalMassFlow) {
        this.totalMassFlow = totalMassFlow;
    }

    public Float getTotalVolumeFlow() {
        return totalVolumeFlow;
    }

    public void setTotalVolumeFlow(Float totalVolumeFlow) {
        this.totalVolumeFlow = totalVolumeFlow;
    }

    public Float getTotalGaugedVolumeFlow() {
        return totalGaugedVolumeFlow;
    }

    public void setTotalGaugedVolumeFlow(Float totalGaugedVolumeFlow) {
        this.totalGaugedVolumeFlow = totalGaugedVolumeFlow;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
