package com.magic.promotion.inviteCode.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.magic.promotion.inviteCode.domain.InviteCode;
import com.magic.util.PagePO;

public interface InviteCodeMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(InviteCode record);

	List<InviteCode> selectByExample(@Param("example") InviteCode example,@Param("page") PagePO page);

	int countByExample(@Param("example") InviteCode example);

	InviteCode selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(InviteCode record);
}