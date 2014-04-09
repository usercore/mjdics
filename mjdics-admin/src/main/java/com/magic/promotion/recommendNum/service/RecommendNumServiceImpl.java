package com.magic.promotion.recommendNum.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magic.promotion.recommendNum.dao.RecommendNumMapper;
import com.magic.promotion.recommendNum.domain.RecommendNum;
import com.magic.promotion.applyCash.service.ApplyCashServiceImpl;
import com.magic.promotion.card.service.CardServiceImpl;
import com.magic.promotion.inviteCode.service.InviteCodeServiceImpl;
import com.magic.promotion.sysParam.service.SysParamServiceImpl;
import com.magic.promotion.user.service.UserServiceImpl;
import com.magic.promotion.util.enumUtil.ActiveEnum;
import com.magic.util.PagePO;

@Service("recommendNumService")
public class RecommendNumServiceImpl  {
	@Autowired
	RecommendNumMapper recommendNumMapper;
	@Autowired
	ApplyCashServiceImpl applyCashService;
	@Autowired
	InviteCodeServiceImpl inviteCodeService;
	@Autowired
	SysParamServiceImpl sysParamService;
	@Autowired
	UserServiceImpl userService;
	@Autowired
	CardServiceImpl cardService;
	public int countByExample(RecommendNum example){
    	return recommendNumMapper.countByExample(example);
    }

   public  int deleteByPrimaryKey(Integer id){
	   return recommendNumMapper.deleteByPrimaryKey(id);
   }

    public int insert(RecommendNum record){
    	return recommendNumMapper.insert(record);
    }


    public List<RecommendNum> selectByExample(@Param("example") RecommendNum example,@Param("page")PagePO page){
    	List<RecommendNum> recommendNumList = recommendNumMapper.selectByExample(example, page);
    	return recommendNumList;
    }

    public int updateByPrimaryKeySelective(RecommendNum record){
    	return recommendNumMapper.updateByPrimaryKeySelective(record);
    }

    public int updateStatusByPhone(String phone,ActiveEnum status){
    	return recommendNumMapper.updateStatusByPhone(phone,status);
    }

	public RecommendNum selectByPrimaryKey(Integer id) {
		return recommendNumMapper.selectByPrimaryKey(id);
	}
}
