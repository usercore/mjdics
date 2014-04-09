package com.magic.promotion.inviteCode.domain;

import java.util.Date;

import com.magic.promotion.util.enumUtil.ActiveEnum;
import com.magic.promotion.util.enumUtil.AgentTypeEnum;

public class InviteCode {
    private Integer id;

    private String agentId;

    private String phone;

    private String inviteCode;

    private AgentTypeEnum type;

    private String addPerson;

    private Date addTime;
    
    private ActiveEnum status;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode == null ? null : inviteCode.trim();
    }



	public AgentTypeEnum getType() {
		return type;
	}

	public void setType(AgentTypeEnum type) {
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

	public ActiveEnum getStatus() {
		return status;
	}

	public void setStatus(ActiveEnum status) {
		this.status = status;
	}
    
    
}