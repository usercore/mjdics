package com.magic.promotion.initUser.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magic.promotion.initUser.dao.InitUserMapper;
import com.magic.promotion.initUser.domain.InitUser;
import com.magic.promotion.applyCash.service.ApplyCashServiceImpl;
import com.magic.promotion.card.service.CardServiceImpl;
import com.magic.promotion.inviteCode.service.InviteCodeServiceImpl;
import com.magic.promotion.sysParam.service.SysParamServiceImpl;
import com.magic.promotion.user.service.UserServiceImpl;
import com.magic.promotion.util.enumUtil.ActiveEnum;
import com.magic.util.PagePO;

@Service("initUserService")
public class InitUserServiceImpl  {
	@Autowired
	InitUserMapper initUserMapper;
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
	public int countByExample(InitUser example){
    	return initUserMapper.countByExample(example);
    }

   public  int deleteByPrimaryKey(Integer id){
	   return initUserMapper.deleteByPrimaryKey(id);
   }

    public int insert(InitUser record){
    	return initUserMapper.insert(record);
    }


    public List<InitUser> selectByExample(@Param("example") InitUser example,@Param("page")PagePO page){
    	List<InitUser> initUserList = initUserMapper.selectByExample(example, page);
    	return initUserList;
    }

    public int updateByPrimaryKeySelective(InitUser record){
    	return initUserMapper.updateByPrimaryKeySelective(record);
    }

    public int updateStatusByPhone(String phone,ActiveEnum status){
    	return initUserMapper.updateStatusByPhone(phone,status);
    }

	public InitUser selectByPrimaryKey(Integer id) {
		return initUserMapper.selectByPrimaryKey(id);
	}
}
