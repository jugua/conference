package ua.rd.cm.services;

import ua.rd.cm.domain.Status;
import java.util.List;

/**
 * @author Olha_Melnyk
 */
public interface StatusService {

    Status find(Long id);

    void save(Status status);

    List<Status> getByName(String name);

    List<Status> findAll();

}
