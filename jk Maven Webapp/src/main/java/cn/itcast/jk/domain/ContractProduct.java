package cn.itcast.jk.domain;

import java.math.BigDecimal;

public class ContractProduct {
    private String id;
    private String contractId;
    private String factoryId;
    private String factoryName;
    private String productNo;
    private String productImage;
    private String productDesc;
    private BigDecimal cnumber;
    private BigDecimal outNumber;
    private String loadingRate;
    private BigDecimal boxNum;
    private String packingUnit;
    private BigDecimal price;
    private BigDecimal amount;
    private BigDecimal finished;
    private String exts;
    private BigDecimal orderNo;



    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CONTRACT_PRODUCT_C.CONTRACT_ID
     *
     * @return the value of CONTRACT_PRODUCT_C.CONTRACT_ID
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public String getContractId() {
        return contractId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CONTRACT_PRODUCT_C.CONTRACT_ID
     *
     * @param contractId the value for CONTRACT_PRODUCT_C.CONTRACT_ID
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CONTRACT_PRODUCT_C.FACTORY_ID
     *
     * @return the value of CONTRACT_PRODUCT_C.FACTORY_ID
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public String getFactoryId() {
        return factoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CONTRACT_PRODUCT_C.FACTORY_ID
     *
     * @param factoryId the value for CONTRACT_PRODUCT_C.FACTORY_ID
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CONTRACT_PRODUCT_C.FACTORY_NAME
     *
     * @return the value of CONTRACT_PRODUCT_C.FACTORY_NAME
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public String getFactoryName() {
        return factoryName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CONTRACT_PRODUCT_C.FACTORY_NAME
     *
     * @param factoryName the value for CONTRACT_PRODUCT_C.FACTORY_NAME
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CONTRACT_PRODUCT_C.PRODUCT_NO
     *
     * @return the value of CONTRACT_PRODUCT_C.PRODUCT_NO
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public String getProductNo() {
        return productNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CONTRACT_PRODUCT_C.PRODUCT_NO
     *
     * @param productNo the value for CONTRACT_PRODUCT_C.PRODUCT_NO
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CONTRACT_PRODUCT_C.PRODUCT_IMAGE
     *
     * @return the value of CONTRACT_PRODUCT_C.PRODUCT_IMAGE
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public String getProductImage() {
        return productImage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CONTRACT_PRODUCT_C.PRODUCT_IMAGE
     *
     * @param productImage the value for CONTRACT_PRODUCT_C.PRODUCT_IMAGE
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CONTRACT_PRODUCT_C.PRODUCT_DESC
     *
     * @return the value of CONTRACT_PRODUCT_C.PRODUCT_DESC
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public String getProductDesc() {
        return productDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CONTRACT_PRODUCT_C.PRODUCT_DESC
     *
     * @param productDesc the value for CONTRACT_PRODUCT_C.PRODUCT_DESC
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CONTRACT_PRODUCT_C.CNUMBER
     *
     * @return the value of CONTRACT_PRODUCT_C.CNUMBER
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public BigDecimal getCnumber() {
        return cnumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CONTRACT_PRODUCT_C.CNUMBER
     *
     * @param cnumber the value for CONTRACT_PRODUCT_C.CNUMBER
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public void setCnumber(BigDecimal cnumber) {
        this.cnumber = cnumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CONTRACT_PRODUCT_C.OUT_NUMBER
     *
     * @return the value of CONTRACT_PRODUCT_C.OUT_NUMBER
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public BigDecimal getOutNumber() {
        return outNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CONTRACT_PRODUCT_C.OUT_NUMBER
     *
     * @param outNumber the value for CONTRACT_PRODUCT_C.OUT_NUMBER
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public void setOutNumber(BigDecimal outNumber) {
        this.outNumber = outNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CONTRACT_PRODUCT_C.LOADING_RATE
     *
     * @return the value of CONTRACT_PRODUCT_C.LOADING_RATE
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public String getLoadingRate() {
        return loadingRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CONTRACT_PRODUCT_C.LOADING_RATE
     *
     * @param loadingRate the value for CONTRACT_PRODUCT_C.LOADING_RATE
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public void setLoadingRate(String loadingRate) {
        this.loadingRate = loadingRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CONTRACT_PRODUCT_C.BOX_NUM
     *
     * @return the value of CONTRACT_PRODUCT_C.BOX_NUM
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public BigDecimal getBoxNum() {
        return boxNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CONTRACT_PRODUCT_C.BOX_NUM
     *
     * @param boxNum the value for CONTRACT_PRODUCT_C.BOX_NUM
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public void setBoxNum(BigDecimal boxNum) {
        this.boxNum = boxNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CONTRACT_PRODUCT_C.PACKING_UNIT
     *
     * @return the value of CONTRACT_PRODUCT_C.PACKING_UNIT
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public String getPackingUnit() {
        return packingUnit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CONTRACT_PRODUCT_C.PACKING_UNIT
     *
     * @param packingUnit the value for CONTRACT_PRODUCT_C.PACKING_UNIT
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public void setPackingUnit(String packingUnit) {
        this.packingUnit = packingUnit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CONTRACT_PRODUCT_C.PRICE
     *
     * @return the value of CONTRACT_PRODUCT_C.PRICE
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CONTRACT_PRODUCT_C.PRICE
     *
     * @param price the value for CONTRACT_PRODUCT_C.PRICE
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CONTRACT_PRODUCT_C.AMOUNT
     *
     * @return the value of CONTRACT_PRODUCT_C.AMOUNT
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CONTRACT_PRODUCT_C.AMOUNT
     *
     * @param amount the value for CONTRACT_PRODUCT_C.AMOUNT
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CONTRACT_PRODUCT_C.FINISHED
     *
     * @return the value of CONTRACT_PRODUCT_C.FINISHED
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public BigDecimal getFinished() {
        return finished;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CONTRACT_PRODUCT_C.FINISHED
     *
     * @param finished the value for CONTRACT_PRODUCT_C.FINISHED
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public void setFinished(BigDecimal finished) {
        this.finished = finished;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CONTRACT_PRODUCT_C.EXTS
     *
     * @return the value of CONTRACT_PRODUCT_C.EXTS
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public String getExts() {
        return exts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CONTRACT_PRODUCT_C.EXTS
     *
     * @param exts the value for CONTRACT_PRODUCT_C.EXTS
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public void setExts(String exts) {
        this.exts = exts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CONTRACT_PRODUCT_C.ORDER_NO
     *
     * @return the value of CONTRACT_PRODUCT_C.ORDER_NO
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public BigDecimal getOrderNo() {
        return orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CONTRACT_PRODUCT_C.ORDER_NO
     *
     * @param orderNo the value for CONTRACT_PRODUCT_C.ORDER_NO
     *
     * @mbggenerated Tue Aug 12 10:21:44 CST 2014
     */
    public void setOrderNo(BigDecimal orderNo) {
        this.orderNo = orderNo;
    }
}