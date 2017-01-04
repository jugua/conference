package ua.rd.cm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.rd.cm.domain.Status;
import ua.rd.cm.repository.StatusRepository;
import ua.rd.cm.repository.specification.status.StatusById;
import ua.rd.cm.repository.specification.status.StatusByName;

import java.util.List;

@Service
public class SimpleStatusService implements StatusService {

    private StatusRepository statusRepository;
    private static final String NEW = "New";
    private static final String IN_PROGRESS = "In Progress";
    private static final String REJECTED = "Rejected";
    private static final String APPROVED= "Approved";

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

//    public boolean canChangeAvailability(Status oldStatus,Status newStatus){
//
//    }
}
