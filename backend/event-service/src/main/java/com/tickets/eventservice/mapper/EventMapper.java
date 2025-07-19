package com.tickets.eventservice.mapper;

import com.tickets.eventservice.dto.EventResponse;
import com.tickets.eventservice.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(source = "hall.name", target = "hallName")
    EventResponse toDto(Event event);
}