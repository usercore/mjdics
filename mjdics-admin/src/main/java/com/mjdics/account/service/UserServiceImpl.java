package com.mjdics.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magic.promotion.util.PagePO;
import com.mjdics.account.dao.UserMapper;
import com.mjdics.account.domain.User;

@Service("userService")
public class UserServiceImpl {
	@Autowired
	UserMapper userMapper;
	
	public int countByExample(User example) {
		return userMapper.countByExample(example);
	}

	public int deleteByPrimaryKey(Integer id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	public int insert(User record) {
		return userMapper.insert(record);
	}

	public List<User> selectByExample(User example, PagePO page) {
		return userMapper.selectByExample(example, page);
	}

	public User selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKey(User record) {
		return userMapper.updateByPrimaryKey(record);
	}

}
