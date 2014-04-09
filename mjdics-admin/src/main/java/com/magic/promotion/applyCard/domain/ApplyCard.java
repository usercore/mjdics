package com.magic.promotion.applyCard.domain;

import java.util.Date;

import com.magic.promotion.util.enumUtil.ApplyCardStatusEnum;

public class ApplyCard {
    private Integer id;

    private String agentId;

    private Integer amount;

    private String cardScope;

    private ApplyCardStatusEnum status;

    private String addPerson;

    private Date addTime;

    private String updatePersion;

    private Date updateTime;
    
    private String nameId;
    
    private String cardTypeId;
    
    private String cardTypeName;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCardScope() {
        return cardScope;
    }

    public void setCardScope(String cardScope) {
        this.cardScope = cardScope == null ? null : cardScope.trim();
    }


    public ApplyCardStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ApplyCardStatusEnum status) {
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

    public String getUpdatePersion() {
        return updatePersion;
    }

    public void setUpdatePersion(String updatePersion) {
        this.updatePersion = updatePersion == null ? null : updatePersion.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public String getCardTypeId() {
		return cardTypeId;
	}

	public void setCardTypeId(String cardTypeId) {
		this.cardTypeId = cardTypeId;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}
}