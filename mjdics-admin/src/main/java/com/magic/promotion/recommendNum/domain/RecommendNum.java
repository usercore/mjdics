package com.magic.promotion.recommendNum.domain;

import java.util.Date;

import com.magic.promotion.util.enumUtil.ActiveAndInvalidEnum;

public class RecommendNum {
    private Integer id;

    private String game;

    private String issue;

    private String manner;

    private String betNum;

    private ActiveAndInvalidEnum status;

    private Date addTime;

    private String addPerson;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getManner() {
        return manner;
    }

    public void setManner(String manner) {
        this.manner = manner == null ? null : manner.trim();
    }

    public String getBetNum() {
        return betNum;
    }

    public void setBetNum(String betNum) {
        this.betNum = betNum == null ? null : betNum.trim();
    }


    public ActiveAndInvalidEnum getStatus() {
		return status;
	}

	public void setStatus(ActiveAndInvalidEnum status) {
		this.status = status;
	}

	public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getAddPerson() {
        return addPerson;
    }

    public void setAddPerson(String addPerson) {
        this.addPerson = addPerson == null ? null : addPerson.trim();
    }
}