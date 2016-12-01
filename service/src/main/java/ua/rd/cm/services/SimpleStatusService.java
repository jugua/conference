package ua.rd.cm.services;

import ua.rd.cm.domain.Status;
import ua.rd.cm.repository.StatusRepository;
import ua.rd.cm.repository.specification.status.StatusById;
import ua.rd.cm.repository.specification.status.StatusByName;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Olha_Melnyk
 */
public class SimpleStatusService implements StatusService {

    private StatusRepository statusRepository;

    @Inject
    public SimpleStatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public Status find(Long id) {
        return statusRepository.findBySpecification(new StatusById(id)).get(0);
    }

    @Override
    public void save(Status status) {
        statusRepository.saveStatus(status);
    }

    @Override
    public List<Status> getByName(String name) {
        return statusRepository.findBySpecification(new StatusByName(name));
    }

    @Override
    public List<Status> findAll() {
        return statusRepository.findAll();
    }
}
