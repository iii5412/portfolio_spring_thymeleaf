package com.portfolio.main.account.role.repository;

import com.portfolio.main.account.role.dto.mapperdto.RoleMapperDto;
import com.portfolio.main.account.role.repository.dto.FindRolesParamDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<RoleMapperDto> findRoles();
    List<RoleMapperDto> findRoles(FindRolesParamDto findRolesParamDto);

}
