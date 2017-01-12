package ua.rd.cm.services;

import ua.rd.cm.domain.TalkStatus;

import java.util.List;

@Deprecated
public interface StatusService {

    TalkStatus find(Long id);

    void save(TalkStatus status);

    TalkStatus getByName(String name);

    List<TalkStatus> findAll();

}
