package com.magic.promotion.initUser.domain;

import java.util.Date;

import com.magic.promotion.util.enumUtil.ActiveEnum;
import com.magic.promotion.util.enumUtil.InviteStatusEnum;

public class InitUser {
    private Integer id;

    private String agentId;

    private String phone;

    private String addPerson;

    private Date addTime;

    private ActiveEnum status;
    
    private InviteStatusEnum inviteStatus;
    
    private String name;
    
    private String paraAgent;

    public String getParaAgent() {
		return paraAgent;
	}

	public void setParaAgent(String paraAgent) {
		this.paraAgent = paraAgent;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InviteStatusEnum getInviteStatus() {
		return inviteStatus;
	}

	public void setInviteStatus(InviteStatusEnum inviteStatus) {
		this.inviteStatus = inviteStatus;
	}
    
    
}