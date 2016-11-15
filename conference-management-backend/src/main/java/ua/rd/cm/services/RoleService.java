package ua.rd.cm.services;

import ua.rd.cm.domain.Role;

import java.util.List;

/**
 * @author Mariia Lapovska
 */
public interface RoleService {

    Role find(Long id);

    void save(Role user);

    List<Role> findAll();

    List<Role> getByName(String name);
}
