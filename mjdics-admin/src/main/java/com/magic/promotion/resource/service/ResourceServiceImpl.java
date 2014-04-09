package com.magic.promotion.resource.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magic.promotion.resource.dao.ResourceMapper;
import com.magic.promotion.resource.domain.Resource;
import com.magic.util.PagePO;

@Service("resourceService")
public class ResourceServiceImpl  {
	@Autowired
	ResourceMapper resourceMapper;
	public int countByExample(Resource example){
    	return resourceMapper.countByExample(example);
    }

   public  int deleteByPrimaryKey(Integer id){
	   return resourceMapper.deleteByPrimaryKey(id);
   }

    public int insert(Resource record){
    	return resourceMapper.insert(record);
    }


    public List<Resource> selectByExample(@Param("example") Resource example,@Param("page")PagePO page){
    	List<Resource> resourceList = resourceMapper.selectByExample(example, page);
    	return resourceList;
    }

    public int updateByPrimaryKeySelective(Resource record){
    	return resourceMapper.updateByPrimaryKeySelective(record);
    }


	public Resource selectByPrimaryKey(Integer id) {
		return resourceMapper.selectByPrimaryKey(id);
	}
    
}
