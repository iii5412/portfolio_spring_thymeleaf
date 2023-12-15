package com.portfolio.main.account.role.repository;

import com.portfolio.main.account.role.repository.mapperDto.RoleMapperDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<RoleMapperDto> findAllRoles();
}
