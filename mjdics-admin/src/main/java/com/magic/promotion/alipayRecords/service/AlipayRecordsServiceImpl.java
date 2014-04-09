package com.magic.promotion.alipayRecords.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magic.promotion.alipayRecords.dao.AlipayRecordsMapper;
import com.magic.promotion.alipayRecords.domain.AlipayRecords;
import com.magic.util.PagePO;

@Service("alipayRecordsService")
public class AlipayRecordsServiceImpl  {
	@Autowired
	AlipayRecordsMapper alipayRecordsMapper;
	
	public int countByExample(AlipayRecords example){
    	return alipayRecordsMapper.countByExample(example);
    }

   public  int deleteByPrimaryKey(Integer id){
	   return alipayRecordsMapper.deleteByPrimaryKey(id);
   }

    public int insert(AlipayRecords record){
    	return alipayRecordsMapper.insert(record);
    }
    

    public List<AlipayRecords> selectByExample(@Param("example") AlipayRecords example,@Param("page")PagePO page){
    	List<AlipayRecords> alipayRecordsList = alipayRecordsMapper.selectByExample(example, page);
    	return alipayRecordsList;
    }

    public int updateByPrimaryKeySelective(AlipayRecords record){
    	return alipayRecordsMapper.updateByPrimaryKeySelective(record);
    }


	public AlipayRecords selectByPrimaryKey(Integer id) {
		return alipayRecordsMapper.selectByPrimaryKey(id);
	}

	
}
