package com.wirebarley.domain.role.service;

import com.wirebarley.domain.role.model.entity.Role;
import com.wirebarley.domain.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role selectDefaultRole() {
        return roleRepository.selectDefaultRole();
    }
}
