package com.magic.promotion.issue.domain;

import java.util.Date;

import com.magic.promotion.util.enumUtil.IssueStatusEnum;

public class Issue {
    private Integer id;

    private String game;

    private String issue;

    private Date addTime;

    private Date startTime;

    private Date endTime;

    private Date awardTime;

    private String awardCode;

    private IssueStatusEnum status;

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

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(Date awardTime) {
        this.awardTime = awardTime;
    }

    public String getAwardCode() {
        return awardCode;
    }

    public void setAwardCode(String awardCode) {
        this.awardCode = awardCode == null ? null : awardCode.trim();
    }

	public IssueStatusEnum getStatus() {
		return status;
	}

	public void setStatus(IssueStatusEnum status) {
		this.status = status;
	}
}