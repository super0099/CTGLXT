package com.yxm.dao;

import com.yxm.po.SysInvoice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDao {
    Integer InsertInvoice(SysInvoice sysInvoice);

    Integer selectInvoiceCount();

    List<SysInvoice> selectInvoiceAll(@Param("page") Integer page, @Param("limit")Integer limit,
                                      @Param("buyerName")String buyerName, @Param("invoiceNumber")String invoiceNumber);

    SysInvoice selectInvoiceById(Integer invoiceId);
}
