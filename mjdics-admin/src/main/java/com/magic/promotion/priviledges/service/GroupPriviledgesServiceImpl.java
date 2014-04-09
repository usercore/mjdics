package com.magic.promotion.priviledges.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magic.promotion.priviledges.dao.GroupPriviledgesMapper;
import com.magic.promotion.priviledges.domain.GroupPriviledges;
import com.magic.util.PagePO;

@Service("groupPriviledgesService")
public class GroupPriviledgesServiceImpl  {
	@Autowired
	GroupPriviledgesMapper groupPriviledgesMapper;
	public int countByExample(GroupPriviledges example){
    	return groupPriviledgesMapper.countByExample(example);
    }

    public int insert(GroupPriviledges record){
    	return groupPriviledgesMapper.insert(record);
    }


	public  List<GroupPriviledges> selectByGroupId(Integer groupId) {
		return groupPriviledgesMapper.selectByGroupId(groupId);
	}
	
	public void updateSysPriviledgesByGroupId(int groupId,String[] privsArr){
		groupPriviledgesMapper.deleteByGroupId(groupId);
		for(int i=0;i<privsArr.length;i++){
			GroupPriviledges groupPriviledges = new GroupPriviledges();
			groupPriviledges.setGroupId(groupId);
			groupPriviledges.setId(Integer.parseInt(privsArr[i]));
			groupPriviledgesMapper.insert(groupPriviledges);
		}
	}
    
}
