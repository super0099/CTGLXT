package com.yxm.po;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SysInvoice implements Serializable {
    //id,invoiceCode,invoiceNumber,clearing,buyerName,buyerPhone,commodity,invoiceType,quantity,price,invoiceSum,tax,taxSum,sumBig,sumSmall,sellerName,sellerPhone,remark,gathering,operation
    private Integer id;
    private String invoiceCode;
    private String invoiceNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date clearing;
    private String buyerName;
    private String buyerPhone;
    private String commodity;
    private String invoiceType;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal invoiceSum;
    private BigDecimal tax;
    private BigDecimal taxSum;
    private String sumBig;
    private String sumSmall;
    private String sellerName;
    private String sellerPhone;
    private String remark;
    private String gathering;
    private String operation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getClearing() {
        return clearing;
    }

    public void setClearing(Date clearing) {
        this.clearing = clearing;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getInvoiceSum() {
        return invoiceSum;
    }

    public void setInvoiceSum(BigDecimal invoiceSum) {
        this.invoiceSum = invoiceSum;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTaxSum() {
        return taxSum;
    }

    public void setTaxSum(BigDecimal taxSum) {
        this.taxSum = taxSum;
    }

    public String getSumBig() {
        return sumBig;
    }

    public void setSumBig(String sumBig) {
        this.sumBig = sumBig;
    }

    public String getSumSmall() {
        return sumSmall;
    }

    public void setSumSmall(String sumSmall) {
        this.sumSmall = sumSmall;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGathering() {
        return gathering;
    }

    public void setGathering(String gathering) {
        this.gathering = gathering;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "SysInvoice{" +
                "id=" + id +
                ", invoiceCode='" + invoiceCode + '\'' +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", clearing=" + clearing +
                ", buyerName='" + buyerName + '\'' +
                ", buyerPhone='" + buyerPhone + '\'' +
                ", commodity='" + commodity + '\'' +
                ", invoiceType='" + invoiceType + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", invoiceSum=" + invoiceSum +
                ", tax=" + tax +
                ", taxSum=" + taxSum +
                ", sumBig='" + sumBig + '\'' +
                ", sumSmall='" + sumSmall + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", sellerPhone='" + sellerPhone + '\'' +
                ", remark='" + remark + '\'' +
                ", gathering='" + gathering + '\'' +
                ", operation='" + operation + '\'' +
                '}';
    }
}
