package ua.rd.cm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.Role;
import ua.rd.cm.repository.RoleRepository;
import ua.rd.cm.repository.specification.role.RoleById;
import ua.rd.cm.repository.specification.role.RoleByName;
import ua.rd.cm.services.RoleService;
import ua.rd.cm.services.exception.ResourceNotFoundException;

import java.util.List;

import static ua.rd.cm.services.exception.ResourceNotFoundException.ROLE_NOT_FOUND;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role find(Long id) {
        List<Role> roles = roleRepository.findBySpecification(new RoleById(id));
        if (roles.isEmpty()) {
            throw new ResourceNotFoundException(ROLE_NOT_FOUND);
        }
        return roles.get(0);
    }

    @Override
    @Transactional
    public void save(Role role) {
        roleRepository.saveRole(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.findBySpecification(new RoleByName(name)).get(0);
    }
}
