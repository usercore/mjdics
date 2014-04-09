package com.magic.promotion.issue.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.magic.promotion.issue.domain.Issue;
import com.magic.util.PagePO;

public interface IssueMapper {
    
	int deleteByPrimaryKey(Integer id);

	int insert(Issue record);

	List<Issue> selectByExample(@Param("example") Issue example,@Param("page") PagePO page);

	int countByExample(@Param("example") Issue example);

	Issue selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Issue record);
	
	int updateByExampleSelective(@Param("record") Issue record, @Param("example") Issue example);
	
	Issue currnetIssue(@Param("game") String game,@Param("cdate") Date cdate);
	
	List<Issue> curDateBeforeIssue(@Param("record") Issue record,@Param("cdate") Date cdate);
}