package com.tickets.eventservice.dto.mapper;

import com.tickets.eventservice.dto.EventRequest;
import com.tickets.eventservice.dto.EventResponse;
import com.tickets.eventservice.entity.Event;
import com.tickets.eventservice.entity.Hall;
import com.tickets.eventservice.service.HallService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring",
        uses = {HallMapper.class},
        builder = @org.mapstruct.Builder(disableBuilder = true))
public abstract class EventMapper {

    @Autowired
    protected HallService hallService;

    @Mapping(target = "hallResponse", source = "hall")
    public abstract EventResponse toDto(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "hall", ignore = true)
    public abstract Event toEntity(EventRequest eventRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "hall", ignore = true)
    public abstract void updateEntityFromDto(EventRequest eventRequest, @MappingTarget Event existingEvent);

    @AfterMapping
    protected void setHall(EventRequest source, @MappingTarget Event target) {
        if (source.hallId() != null) {
            Hall hall = hallService.getHallById(source.hallId());
            target.setHall(hall);
        }
    }
}