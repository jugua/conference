package ua.rd.cm.dto.converter;

import java.util.stream.Collectors;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import ua.rd.cm.domain.*;
import ua.rd.cm.dto.CreateConferenceDto;

public class CreateConferenceToConference implements Converter<CreateConferenceDto, Conference> {

    @Override
    public Conference convert(MappingContext<CreateConferenceDto, Conference> mappingContext) {
        Conference destination = mappingContext.getDestination();
        CreateConferenceDto source = mappingContext.getSource();

        destination.setTopics(source.getTopicIds().stream().map(i -> {
            Topic topic = new Topic();
            topic.setId(i);
            return topic;
        }).collect(Collectors.toList()));

        destination.setTypes(source.getTypeIds().stream().map(i -> {
            Type type = new Type();
            type.setId(i);
            return type;
        }).collect(Collectors.toList()));

        destination.setLanguages(source.getLanguageIds().stream().map(i -> {
            Language language = new Language();
            language.setId(i);
            return language;
        }).collect(Collectors.toList()));

        destination.setLevels(source.getLevelIds().stream().map(i -> {
            Level level = new Level();
            level.setId(i);
            return level;
        }).collect(Collectors.toList()));

        destination.setOrganisers(source.getOrganiserIds().stream().map(i -> {
            User user = new User();
            user.setId(i);
            return user;
        }).collect(Collectors.toList()));
        return destination;
    }
}
