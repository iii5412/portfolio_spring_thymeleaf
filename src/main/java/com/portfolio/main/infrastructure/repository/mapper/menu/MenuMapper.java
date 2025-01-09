package com.portfolio.main.infrastructure.repository.mapper.menu;

import com.portfolio.main.infrastructure.repository.mapper.menu.dto.MenuMapperDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 이 인터페이스는 메뉴에 대한 MyBatis 매퍼입니다.
 * MyBatis를 사용하여 DB와 메뉴 데이터 사이의 상호 작용을 지원합니다.
 *
 * @Mapper : MyBatis의 매퍼를 나타내며, 이를 통해 MyBatis가 이 인터페이스를 SQL 매핑을 위한 인터페이스로 사용하게 됩니다.
 */
@Mapper
public interface MenuMapper {
    /**
     * 메뉴 계층 구조를 나타내는 MenuMapperDto 개체 목록을 검색합니다.
     *
     * @return 계층 구조의 메뉴 세부정보가 포함된 MenuMapperDto 개체 목록
     */
    List<MenuMapperDto> findHierarchyMenus();
}
