package com.magic.promotion.alipayRecords.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.magic.promotion.util.enumUtil.AlipayTradeStatusEnum;
import com.magic.promotion.util.enumUtil.AlipayTradeTypeEnum;

public class AlipayRecords {
    private Integer id;

    private String foreignKey;

    private BigDecimal money;

    private AlipayTradeStatusEnum status;

    private AlipayTradeTypeEnum type;

    private String addPerson;

    private Date addTime;

    private String tradeNo;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey == null ? null : foreignKey.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }


    public String getAddPerson() {
        return addPerson;
    }

    public void setAddPerson(String addPerson) {
        this.addPerson = addPerson == null ? null : addPerson.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

	public AlipayTradeStatusEnum getStatus() {
		return status;
	}

	public void setStatus(AlipayTradeStatusEnum status) {
		this.status = status;
	}

	public AlipayTradeTypeEnum getType() {
		return type;
	}

	public void setType(AlipayTradeTypeEnum type) {
		this.type = type;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
    
    
}