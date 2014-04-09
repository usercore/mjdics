package com.magic.promotion.sysParam.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magic.promotion.sysParam.dao.SysParamMapper;
import com.magic.promotion.sysParam.domain.SysParam;
import com.magic.util.PagePO;

@Service("sysParamService")
public class SysParamServiceImpl  {
	@Autowired
	SysParamMapper sysParamMapper;
	public int countByExample(SysParam example){
    	return sysParamMapper.countByExample(example);
    }

   public  int deleteByPrimaryKey(Integer id){
	   return sysParamMapper.deleteByPrimaryKey(id);
   }

    public int insert(SysParam record){
    	return sysParamMapper.insert(record);
    }


    public List<SysParam> selectByExample(@Param("example") SysParam example,@Param("page")PagePO page){
    	List<SysParam> sysParamList = sysParamMapper.selectByExample(example, page);
    	return sysParamList;
    }

    public int updateByPrimaryKeySelective(SysParam record){
    	return sysParamMapper.updateByPrimaryKeySelective(record);
    }


	public SysParam selectByPrimaryKey(Integer id) {
		return sysParamMapper.selectByPrimaryKey(id);
	}
    public SysParam selectByName(String name){
    	return sysParamMapper.selectByName(name);
    }
    
    public int updateByName(SysParam sysParam){
    	return sysParamMapper.updateByName(sysParam);
    }
}
