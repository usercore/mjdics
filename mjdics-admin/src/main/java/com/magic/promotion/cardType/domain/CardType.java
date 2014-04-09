package com.magic.promotion.cardType.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.magic.promotion.util.enumUtil.CardTypeEnum;
import com.magic.promotion.util.enumUtil.CardTypeStatusEnum;

public class CardType {
    private Integer id;

    private BigDecimal money;
    
    private String name;

    private BigDecimal price;

    private BigDecimal webPrice;

    private BigDecimal firstCommision;

    private BigDecimal secCommision;
    
    private BigDecimal saleCommision;
    
    private BigDecimal saleCardCommision;    

    private Integer prizeCount;    

    private BigDecimal lotteryMoney;

    private Integer betCount;

    private String betAddress;

    private String resourceId;

    private CardTypeStatusEnum status;
    
    private CardTypeEnum type;    

    private String addPerson;

    private Date addTime;

    private String updatePersion;

    private Date updateTime;
    
    private List resourceListId; 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

	public void setSaleCommision(BigDecimal saleCommision) {
		this.saleCommision = saleCommision;
	}


    public BigDecimal getSaleCardCommision() {
		return saleCardCommision;
	}

	public void setSaleCardCommision(BigDecimal saleCardCommision) {
		this.saleCardCommision = saleCardCommision;
	}

	public Integer getPrizeCount() {
		return prizeCount;
	}

	public void setPrizeCount(Integer prizeCount) {
		this.prizeCount = prizeCount;
	}

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


    public CardTypeStatusEnum getStatus() {
		return status;
	}

	public void setStatus(CardTypeStatusEnum status) {
		this.status = status;
	}

	public CardTypeEnum getType() {
		return type;
	}

	public void setType(CardTypeEnum type) {
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

	public List getResourceListId() {
		return resourceListId;
	}

	public void setResourceListId(List resourceListId) {
		this.resourceListId = resourceListId;
	}
}