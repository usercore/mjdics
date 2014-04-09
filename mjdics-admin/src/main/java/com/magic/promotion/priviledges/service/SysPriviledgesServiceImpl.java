package com.magic.promotion.priviledges.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magic.promotion.priviledges.dao.SysPriviledgesMapper;
import com.magic.promotion.priviledges.domain.SysPriviledges;
import com.magic.promotion.priviledges.domain.SysPriviledgesEdit;
import com.magic.util.PagePO;

@Service("sysPriviledgesService")
public class SysPriviledgesServiceImpl  {
	@Autowired
	SysPriviledgesMapper sysPriviledgesMapper;
	public int countByExample(SysPriviledges example){
    	return sysPriviledgesMapper.countByExample(example);
    }

   public  int deleteByPrimaryKey(Integer id){
	   return sysPriviledgesMapper.deleteByPrimaryKey(id);
   }

    public int insert(SysPriviledges record){
    	return sysPriviledgesMapper.insert(record);
    }


    public List<SysPriviledges> selectByExample( SysPriviledges example,PagePO page){
    	List<SysPriviledges> sysPriviledgesList = sysPriviledgesMapper.selectByExample(example, page);
    	return sysPriviledgesList;
    }

    public int updateByPrimaryKeySelective(SysPriviledges record){
    	return sysPriviledgesMapper.updateByPrimaryKeySelective(record);
    }


	public SysPriviledges selectByPrimaryKey(Integer id) {
		return sysPriviledgesMapper.selectByPrimaryKey(id);
	}
	 public List<SysPriviledges> selectSysPriviledgesByGroupId(int groupId){
		 return sysPriviledgesMapper.selectSysPriviledgesByGroupId(groupId);
	 }
	 
	public List<SysPriviledgesEdit> selectEditByExample( SysPriviledges example,PagePO page){
	    	return sysPriviledgesMapper.selectEditByExample(example, page);
	}
}
