package com.atguigu.gulimall.member.dao;

import com.atguigu.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author sunny
 * @email
 * @date 2021-11-14 19:30:45
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
