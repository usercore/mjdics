package com.mjdics.sysManager.domain;

import java.util.Date;

import com.magic.promotion.util.enumUtil.TradeTypeEnum;

public class TradeType {
    private Integer id;

    private String name;

    private String typeId;

    private TradeTypeEnum type;

    private String addPerson;

    private Date addTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }


    public TradeTypeEnum getType() {
		return type;
	}

	public void setType(TradeTypeEnum type) {
		this.type = type;
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