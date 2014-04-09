package com.magic.promotion.sysParam.domain;

import java.util.Date;

import com.magic.promotion.util.enumUtil.SysParamStatusEnum;

public class SysParam {
    private Integer id;

    private String name;

    private String value;

    private String remark;

    private SysParamStatusEnum status;

    private String addPersion;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public SysParamStatusEnum getStatus() {
		return status;
	}

	public void setStatus(SysParamStatusEnum status) {
		this.status = status;
	}

	public String getAddPersion() {
        return addPersion;
    }

    public void setAddPersion(String addPersion) {
        this.addPersion = addPersion == null ? null : addPersion.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}