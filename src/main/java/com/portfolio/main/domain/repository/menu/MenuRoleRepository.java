package com.portfolio.main.domain.repository.menu;

import com.portfolio.main.domain.model.menu.MenuRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRoleRepository extends JpaRepository<MenuRole, MenuRole.MenuRoleId> {

    void deleteByMenuId(Long menuId);

    List<MenuRole> findByMenuId(Long menuId);
}
