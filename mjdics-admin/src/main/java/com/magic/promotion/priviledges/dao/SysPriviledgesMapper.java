package com.magic.promotion.priviledges.dao;

import com.magic.promotion.priviledges.domain.SysPriviledges;
import com.magic.promotion.priviledges.domain.SysPriviledgesEdit;
import com.magic.util.PagePO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysPriviledgesMapper {
	   int countByExample(@Param("example")SysPriviledges example);

	    int deleteByPrimaryKey(Integer id);

	    int insert(SysPriviledges record);

	    List<SysPriviledges> selectByExample(@Param("example") SysPriviledges example,@Param("page") PagePO page);

	    SysPriviledges selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(SysPriviledges record);
	    
	    List<SysPriviledges>  selectSysPriviledgesByGroupId(int groupId);
	    
	    List<SysPriviledgesEdit> selectEditByExample(@Param("example") SysPriviledges example,@Param("page") PagePO page);

	    
}