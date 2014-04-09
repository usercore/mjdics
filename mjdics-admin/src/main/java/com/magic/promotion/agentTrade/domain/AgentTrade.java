package com.magic.promotion.agentTrade.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.magic.promotion.util.enumUtil.AgentTradeTypeEnum;

public class AgentTrade {
    private Integer id;

    private String agentId;

    private BigDecimal money;

    private AgentTradeTypeEnum type;

    private String serial;
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss") 
    private Date addTime;

    private String addPerson;

    private BigDecimal remainMoney;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }


    public AgentTradeTypeEnum getType() {
		return type;
	}

	public void setType(AgentTradeTypeEnum type) {
		this.type = type;
	}

	public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial == null ? null : serial.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getAddPerson() {
        return addPerson;
    }

    public void setAddPerson(String addPerson) {
        this.addPerson = addPerson == null ? null : addPerson.trim();
    }

    public BigDecimal getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(BigDecimal remainMoney) {
        this.remainMoney = remainMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}