package com.magic.promotion.applyCash.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magic.promotion.applyCash.dao.ApplyCashMapper;
import com.magic.promotion.applyCash.domain.ApplyCash;
import com.magic.util.PagePO;

@Service("applyCashService")
public class ApplyCashServiceImpl  {
	@Autowired
	ApplyCashMapper applyCashMapper;
	public int countByExample(ApplyCash example){
    	return applyCashMapper.countByExample(example);
    }

   public  int deleteByPrimaryKey(Integer id){
	   return applyCashMapper.deleteByPrimaryKey(id);
   }

    public int insert(ApplyCash record){
    	return applyCashMapper.insert(record);
    }


    public List<ApplyCash> selectByExample(@Param("example") ApplyCash example,@Param("page")PagePO page){
    	List<ApplyCash> applyCashList = applyCashMapper.selectByExample(example, page);
    	return applyCashList;
    }

    public int updateByPrimaryKeySelective(ApplyCash record){
    	return applyCashMapper.updateByPrimaryKeySelective(record);
    }


	public ApplyCash selectByPrimaryKey(Integer id) {
		return applyCashMapper.selectByPrimaryKey(id);
	}
    
}
