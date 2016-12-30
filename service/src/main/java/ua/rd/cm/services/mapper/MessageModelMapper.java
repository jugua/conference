package ua.rd.cm.services.mapper;

import ua.rd.cm.domain.User;

import java.util.Map;

public interface MessageModelMapper {

    Map<String, Object> prepareModel(User receiver);
}
