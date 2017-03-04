package ua.rd.cm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.Role;
import ua.rd.cm.repository.RoleRepository;
import ua.rd.cm.repository.specification.role.RoleById;
import ua.rd.cm.repository.specification.role.RoleByName;
import ua.rd.cm.services.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role find(Long id) {
        return roleRepository.findBySpecification(new RoleById(id)).get(0);
    }

    @Override
    @Transactional
    public void save(Role role) {
        roleRepository.save(role);
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
