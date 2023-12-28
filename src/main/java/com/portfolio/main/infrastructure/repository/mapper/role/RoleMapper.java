package com.portfolio.main.infrastructure.repository.mapper.role;

import com.portfolio.main.infrastructure.repository.mapper.role.dto.FindRolesParamDto;
import com.portfolio.main.infrastructure.repository.mapper.role.dto.RoleMapperDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<RoleMapperDto> findRoles();
    List<RoleMapperDto> findRoles(FindRolesParamDto findRolesParamDto);

}
