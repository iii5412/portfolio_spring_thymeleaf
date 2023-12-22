package com.portfolio.main.menu.repository;

import com.portfolio.main.menu.dto.mapperdto.MenuMapperDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<MenuMapperDto> findHierarchyMenus();
}
