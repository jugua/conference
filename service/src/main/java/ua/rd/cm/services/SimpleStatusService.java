package ua.rd.cm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.rd.cm.domain.Status;
import ua.rd.cm.repository.StatusRepository;
import ua.rd.cm.repository.specification.status.StatusById;
import ua.rd.cm.repository.specification.status.StatusByName;

import java.util.List;

@Deprecated
@Service
public class SimpleStatusService implements StatusService {

    private StatusRepository statusRepository;

    @Autowired
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
    public Status getByName(String name) {
        List<Status> list = statusRepository.findBySpecification(new StatusByName(name));
        if (list.isEmpty()) return null;
        else return list.get(0);
    }

    @Override
    public List<Status> findAll() {
        return statusRepository.findAll();
    }

}
