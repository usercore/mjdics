package com.magic.promotion.priviledges.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magic.promotion.priviledges.dao.UserGroupMapper;
import com.magic.promotion.priviledges.domain.UserGroup;
import com.magic.util.PagePO;

@Service("userGroupService")
public class UserGroupServiceImpl  {
	@Autowired
	UserGroupMapper userGroupMapper;
	public int countByExample(UserGroup example){
    	return userGroupMapper.countByExample(example);
    }

   public  int deleteByPrimaryKey(Integer id){
	   return userGroupMapper.deleteByPrimaryKey(id);
   }

    public int insert(UserGroup record){
    	return userGroupMapper.insert(record);
    }


    public List<UserGroup> selectByExample(UserGroup example,PagePO page){
    	List<UserGroup> userGroupList = userGroupMapper.selectByExample(example, page);
    	return userGroupList;
    }

    public int updateByPrimaryKeySelective(UserGroup record){
    	return userGroupMapper.updateByPrimaryKeySelective(record);
    }


	public UserGroup selectByPrimaryKey(Integer id) {
		return userGroupMapper.selectByPrimaryKey(id);
	}
    
}
