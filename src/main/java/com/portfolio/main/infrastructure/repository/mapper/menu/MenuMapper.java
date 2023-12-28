package com.portfolio.main.infrastructure.repository.mapper.menu;

import com.portfolio.main.infrastructure.repository.mapper.menu.dto.MenuMapperDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<MenuMapperDto> findHierarchyMenus();
}
