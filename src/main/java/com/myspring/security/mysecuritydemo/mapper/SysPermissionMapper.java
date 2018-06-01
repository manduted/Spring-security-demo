package com.myspring.security.mysecuritydemo.mapper;

import com.myspring.security.mysecuritydemo.domain.SysPermission;

import java.util.List;

public interface SysPermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    List<SysPermission> findByAdminUserId(int userid);
    List<SysPermission> findAll();
}