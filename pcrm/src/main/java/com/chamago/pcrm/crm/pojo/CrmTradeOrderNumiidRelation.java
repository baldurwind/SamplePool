package com.chamago.pcrm.crm.pojo;

import com.chamago.pcrm.common.pojo.BasePojo;

public class CrmTradeOrderNumiidRelation extends BasePojo {
    private Long tid;

    private byte[] allrow;

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public byte[] getAllrow() {
        return allrow;
    }

    public void setAllrow(byte[] allrow) {
        this.allrow = allrow;
    }
}