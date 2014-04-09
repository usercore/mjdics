package com.magic.promotion.chipinSerial.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magic.promotion.chipinSerial.dao.ChipinSerialMapper;
import com.magic.promotion.chipinSerial.domain.ChipinSerial;
import com.magic.util.PagePO;

@Service("chipinSerialService")
public class ChipinSerialServiceImpl  {
	@Autowired
	ChipinSerialMapper chipinSerialMapper;
	public int countByExample(ChipinSerial example){
    	return chipinSerialMapper.countByExample(example);
    }

   public  int deleteByPrimaryKey(Integer id){
	   return chipinSerialMapper.deleteByPrimaryKey(id);
   }

    public int insert(ChipinSerial record){
    	return chipinSerialMapper.insert(record);
    }


    public List<ChipinSerial> selectByExample(@Param("example") ChipinSerial example,@Param("page")PagePO page){
    	List<ChipinSerial> chipinSerialList = chipinSerialMapper.selectByExample(example, page);
    	return chipinSerialList;
    }

    public int updateByPrimaryKeySelective(ChipinSerial record){
    	return chipinSerialMapper.updateByPrimaryKeySelective(record);
    }


	public ChipinSerial selectByPrimaryKey(Integer id) {
		return chipinSerialMapper.selectByPrimaryKey(id);
	}
    
}
