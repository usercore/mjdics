package com.magic.promotion.priviledges.dao;

import com.magic.promotion.priviledges.domain.GroupPriviledges;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GroupPriviledgesMapper {
    int countByExample(GroupPriviledges example);

    int deleteByGroupId(int groupId);

    int insert(GroupPriviledges record);

    List<GroupPriviledges> selectByExample(GroupPriviledges example);

    int updateByExampleSelective(@Param("record") GroupPriviledges record, @Param("example") GroupPriviledges example);
    
    List<GroupPriviledges>  selectByGroupId(int groupId);

}