package com.magic.promotion.agent.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.magic.promotion.util.enumUtil.AgentTypeEnum;

public class Agent {
    private Integer id;

    private String agentId;

    private String pass;

    private String name;

    private String tel;

    private String email;

    private String address;

    private String paraAgent;

    private BigDecimal money = new BigDecimal(0);
    
    private BigDecimal freezeMoney = new BigDecimal(0);
    
    private BigDecimal commission = new BigDecimal(0);
    
    private BigDecimal salesCardCommission = new BigDecimal(0);

    private String bankCard;

    private String bank;

    private Integer status;

    private AgentTypeEnum type;

    private Integer regChannel;

    private String addPerson;

    private Date addTime;

    private Integer version = 0;

    private Integer groupId ;
    
    
    private int bussCount;//业务员数
    
    private int userCount;
    
    private BigDecimal userByCardMoney = new BigDecimal(0);
    
    private int promotionCount;
    
    private int initUserCount;
    
    private int inviteUserCount;//邀请用户数
    
    private int buyCardUserCount;//购卡用户数
    
    private int agentCount;//代销商数
    
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass == null ? null : pass.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getParaAgent() {
        return paraAgent;
    }

    public void setParaAgent(String paraAgent) {
        this.paraAgent = paraAgent == null ? null : paraAgent.trim();
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    

    public AgentTypeEnum getType() {
		return type;
	}

	public void setType(AgentTypeEnum type) {
		this.type = type;
	}

	public Integer getRegChannel() {
        return regChannel;
    }

    public void setRegChannel(Integer regChannel) {
        this.regChannel = regChannel;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

	public BigDecimal getFreezeMoney() {
		return freezeMoney;
	}

	public void setFreezeMoney(BigDecimal freezeMoney) {
		this.freezeMoney = freezeMoney;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public int getBussCount() {
		return bussCount;
	}

	public void setBussCount(int bussCount) {
		this.bussCount = bussCount;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public BigDecimal getUserByCardMoney() {
		return userByCardMoney;
	}

	public void setUserByCardMoney(BigDecimal userByCardMoney) {
		this.userByCardMoney = userByCardMoney;
	}

	public int getPromotionCount() {
		return promotionCount;
	}

	public void setPromotionCount(int promotionCount) {
		this.promotionCount = promotionCount;
	}

	public int getInitUserCount() {
		return initUserCount;
	}

	public void setInitUserCount(int initUserCount) {
		this.initUserCount = initUserCount;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getSalesCardCommission() {
		return salesCardCommission;
	}

	public void setSalesCardCommission(BigDecimal salesCardCommission) {
		this.salesCardCommission = salesCardCommission;
	}

	public int getInviteUserCount() {
		return inviteUserCount;
	}

	public void setInviteUserCount(int inviteUserCount) {
		this.inviteUserCount = inviteUserCount;
	}

	public int getBuyCardUserCount() {
		return buyCardUserCount;
	}

	public void setBuyCardUserCount(int buyCardUserCount) {
		this.buyCardUserCount = buyCardUserCount;
	}

	public int getAgentCount() {
		return agentCount;
	}

	public void setAgentCount(int agentCount) {
		this.agentCount = agentCount;
	}
    
    
}