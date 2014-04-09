package com.magic.promotion.betInfo.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.magic.promotion.util.enumUtil.BetStatusEnum;
import com.magic.promotion.util.enumUtil.PrizeStatusEnum;

public class BetInfo {
    private Integer id;

    private String schemeId;

    private Integer serial;

    private String cardId;

    private String userId;

    private String game;

    private String issue;

    private BigDecimal money;

    private String manner;

    private Integer mutiple;

    private Integer ticketSum;

    private String num;

    private BetStatusEnum status;

    private String batch;

    private String province;

    private String city;

    private String terminalId;

    private String agencyId;

    private Date addTime;

    private String hotlineId;

    private String cpkey;

    private String stationId;

    private String node;

    private Date hotlineTime;

    private BigDecimal prize;

    private PrizeStatusEnum prizeStatus;
    
    private String errorCode;
    
    
    private String errorMsg;

    
    private Integer prizeGrade;
    
    public Integer getPrizeGrade() {
		return prizeGrade;
	}

	public void setPrizeGrade(Integer prizeGrade) {
		this.prizeGrade = prizeGrade;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId == null ? null : schemeId.trim();
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game == null ? null : game.trim();
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue == null ? null : issue.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getManner() {
        return manner;
    }

    public void setManner(String manner) {
        this.manner = manner == null ? null : manner.trim();
    }

    public Integer getMutiple() {
        return mutiple;
    }

    public void setMutiple(Integer mutiple) {
        this.mutiple = mutiple;
    }

    public Integer getTicketSum() {
        return ticketSum;
    }

    public void setTicketSum(Integer ticketSum) {
        this.ticketSum = ticketSum;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch == null ? null : batch.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId == null ? null : terminalId.trim();
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId == null ? null : agencyId.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getHotlineId() {
        return hotlineId;
    }

    public void setHotlineId(String hotlineId) {
        this.hotlineId = hotlineId == null ? null : hotlineId.trim();
    }

    public String getCpkey() {
        return cpkey;
    }

    public void setCpkey(String cpkey) {
        this.cpkey = cpkey == null ? null : cpkey.trim();
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node == null ? null : node.trim();
    }

    public Date getHotlineTime() {
        return hotlineTime;
    }

    public void setHotlineTime(Date hotlineTime) {
        this.hotlineTime = hotlineTime;
    }

    public BigDecimal getPrize() {
        return prize;
    }

    public void setPrize(BigDecimal prize) {
        this.prize = prize;
    }


	public BetStatusEnum getStatus() {
		return status;
	}

	public void setStatus(BetStatusEnum status) {
		this.status = status;
	}

	public PrizeStatusEnum getPrizeStatus() {
		return prizeStatus;
	}

	public void setPrizeStatus(PrizeStatusEnum prizeStatus) {
		this.prizeStatus = prizeStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
    
    
}