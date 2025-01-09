package com.portfolio.main.domain.service.account.role;

import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.domain.model.account.exception.RoleNotFoundException;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.infrastructure.repository.mapper.role.RoleMapper;
import com.portfolio.main.domain.repository.account.RoleRepository;
import com.portfolio.main.infrastructure.repository.mapper.role.dto.RoleMapperDto;
import com.portfolio.main.infrastructure.repository.mapper.role.dto.FindRolesParamDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 사용자 역할에 관한 기능을 처리하는 서비스 클래스
 * 이 클래스는 역할을 찾거나, 특정 레벨 이상의 모든 역할을 찾는 등의 역할과 관련된 다양한 메서드를 제공합니다.
 *
 */
@Service
@AllArgsConstructor
public class RoleService {

//    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
//    public List<RoleMapperDto> findAllFlat() {
//        return roleMapper.findRoles();
//    }
    /**
     * 특정 레벨 이상의 모든 역할을 조회합니다.
     *
     * @param level 대상 역할의 최소 레벨
     * @return 조회한 역할 리스트
     */
    public List<Role> findFlattenedAboveLevel(Integer level) {
        final List<Role> allFlat = findAllFlat();
        return allFlat.stream().filter(role -> role.getLevel() <= level).toList();
    }
    /**
     * RoleCode를 통해 특정 역할을 조회합니다.
     * 만약 해당하는 역할이 없다면 RoleNotFoundException을 발생시킵니다.
     *
     * @param roleCode 조회할 역할의 코드
     * @return 조회한 역할
     */
    public Role findByRoleCode(RoleCode roleCode) {
        final Role role = roleRepository.findByRoleCode(roleCode).orElseThrow(RoleNotFoundException::new);
        role.setLevels(1);
        return role;
    }

//    public RoleMapperDto findByRoleCodeFlat(RoleCode roleCode) {
//        final Role role = roleRepository.findByRoleCode(roleCode).orElseThrow(RoleNotFoundException::new);
//        return roles.isEmpty() ? null : roles.get(0);
//    }

//    public RoleMapperDto findByIdFlat(Long id) {
//        final Role role = roleRepository.findById(id).orElseThrow(RoleNotFoundException::new);
//        role.setLevels(1);
//        return
//    }
    /**
     * 모든 역할을 조회합니다.
     *
     * @return 조회한 역할 리스트
     */
    public List<Role> findAll() {
        final List<Role> roleList = roleRepository.findAll().stream()
                .filter(role -> !role.hasUpperRole())
                .toList();

        roleList.forEach(role -> role.setLevels(1));

        return roleList;
    }
    /**
     * Id를 통해 특정 역할을 조회합니다.
     *
     * @param id 조회할 역할의 Id
     * @return 조회한 역할
     */
    public Role findById(Long id) {
        final Role role = roleRepository.findById(id).orElseThrow(RoleNotFoundException::new);
        role.setLevels(1);
        return role;
    }
    /**
     * 모든 역할을 Flat한 형태로 조회합니다.
     * 이 메소드는 객체 간의 관계를 최대한 단순화하여 역할 정보를 조회합니다.
     *
     * @return 조회한 역할 리스트
     */
    public List<Role> findAllFlat() {
        final List<Role> topRoles = findAll();

        List<Role> flattenedRoles = new ArrayList<>();
        for(Role role : topRoles) {
            addRoleAndDescendants(flattenedRoles, role);
        }

        return flattenedRoles;
    }
    /**
     * 특정 역할과 그 역할의 자식 역할을 조회하여 리스트에 추가합니다.
     * 이 메소드는 주어진 역할부터 시작하여 그 후손 역할들을 재귀적으로 찾아서 리스트에 추가합니다.
     *
     * @param flattenedRoles 역할이 추가될 리스트
     * @param role 후손 역할을 찾을 기준 역할
     */
    private void addRoleAndDescendants(List<Role> flattenedRoles, Role role){
        flattenedRoles.add(role);

        for(Role childRole : role.getChildRoles()){
            addRoleAndDescendants(flattenedRoles, childRole);
        }
    }
}
