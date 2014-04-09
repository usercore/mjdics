package com.magic.promotion.applyCash.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.magic.promotion.util.enumUtil.ApplyCashStatusEnum;

public class ApplyCash {
    private Integer id;

    private String agentId;

    private String name;

    private BigDecimal money;

    private String bankCard;

    private String bank;

    private ApplyCashStatusEnum status;

    private String addPerson;

    private Date addTime;

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
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }


    public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard == null ? null : bankCard.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }


    public ApplyCashStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ApplyCashStatusEnum status) {
		this.status = status;
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
}