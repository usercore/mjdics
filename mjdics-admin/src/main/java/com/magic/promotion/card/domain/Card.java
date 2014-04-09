package com.magic.promotion.card.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.magic.promotion.util.enumUtil.CardEnum;
import com.magic.promotion.util.enumUtil.CommStatusEnum;

public class Card {
    private Integer id;

    private Integer applyId;
    
    private Integer makeId;
    
    private String alipayId;

    private String cardId;
    
    private String userId;

    private String password;

    private Integer cardTypeId;

    private CardEnum status;

    private String agentId;

    private BigDecimal money;
    
    private String name;    

    private BigDecimal price;

    private BigDecimal webPrice;

    private BigDecimal firstCommision;

    private BigDecimal secCommision;
    
    private BigDecimal saleCommision;
    
    private BigDecimal saleCardCommision;

//    private IsAndNotEnum allowBack;
    private Integer prizeCount;

    private BigDecimal lotteryMoney;

    private Integer betCount;

    private Integer buyCount;

    private String betAddress;

    private String resourceId;

    private String addPerson;

    private Date addTime;

    private String updatePersion;

    private Date updateTime;

    private String batch;
    
    private Integer num;
    
    private String nums;  //投注号码
    
    private String gameCode;
    
    private String issue;
    
    private String alipayIdMoney;
    
    public String getNums() {
		return nums;
	}

	public void setNums(String nums) {
		this.nums = nums;
	}

	private CommStatusEnum commStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getApplyId() {
		return applyId;
	}

	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}

	public Integer getMakeId() {
		return makeId;
	}

	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}

	public String getAlipayId() {
		return alipayId;
	}

	public void setAlipayId(String alipayId) {
		this.alipayId = alipayId;
	}

	public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(Integer cardTypeId) {
        this.cardTypeId = cardTypeId;
    }


    public CardEnum getStatus() {
		return status;
	}

	public void setStatus(CardEnum status) {
		this.status = status;
	}

	public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getWebPrice() {
        return webPrice;
    }

    public void setWebPrice(BigDecimal webPrice) {
        this.webPrice = webPrice;
    }

    public BigDecimal getFirstCommision() {
        return firstCommision;
    }

    public void setFirstCommision(BigDecimal firstCommision) {
        this.firstCommision = firstCommision;
    }

    public BigDecimal getSecCommision() {
        return secCommision;
    }

    public void setSecCommision(BigDecimal secCommision) {
        this.secCommision = secCommision;
    }

    public BigDecimal getSaleCommision() {
		return saleCommision;
	}

	public BigDecimal getSaleCardCommision() {
		return saleCardCommision;
	}

	public void setSaleCardCommision(BigDecimal saleCardCommision) {
		this.saleCardCommision = saleCardCommision;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setSaleCommision(BigDecimal saleCommision) {
		this.saleCommision = saleCommision;
	}



    public Integer getPrizeCount() {
		return prizeCount;
	}

	public void setPrizeCount(Integer prizeCount) {
		this.prizeCount = prizeCount;
	}

//	public IsAndNotEnum getAllowBack() {
//		return allowBack;
//	}
//
//	public void setAllowBack(IsAndNotEnum allowBack) {
//		this.allowBack = allowBack;
//	}

	public BigDecimal getLotteryMoney() {
        return lotteryMoney;
    }

    public void setLotteryMoney(BigDecimal lotteryMoney) {
        this.lotteryMoney = lotteryMoney;
    }

    public Integer getBetCount() {
        return betCount;
    }

    public void setBetCount(Integer betCount) {
        this.betCount = betCount;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public String getBetAddress() {
        return betAddress;
    }

    public void setBetAddress(String betAddress) {
        this.betAddress = betAddress == null ? null : betAddress.trim();
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
    	this.resourceId = resourceId == null ? null : resourceId.trim();
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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch == null ? null : batch.trim();
    }

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public CommStatusEnum getCommStatus() {
		return commStatus;
	}

	public void setCommStatus(CommStatusEnum commStatus) {
		this.commStatus = commStatus;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getAlipayIdMoney() {
		return alipayIdMoney;
	}

	public void setAlipayIdMoney(String alipayIdMoney) {
		this.alipayIdMoney = alipayIdMoney;
	}


}