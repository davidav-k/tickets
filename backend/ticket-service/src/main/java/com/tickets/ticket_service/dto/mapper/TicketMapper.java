package com.tickets.ticket_service.dto.mapper;

import com.tickets.ticket_service.domain.TicketStatus;
import com.tickets.ticket_service.dto.EventResponse;
import com.tickets.ticket_service.dto.TicketRequest;
import com.tickets.ticket_service.dto.TicketResponse;
import com.tickets.ticket_service.entity.Ticket;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring",
        imports = {TicketStatus.class},
        builder = @Builder(disableBuilder = true))
public abstract class TicketMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "row", source = "row")
    @Mapping(target = "seat", source = "seat")
    @Mapping(target = "holderFullName", expression = "java(request.firstName() + \" \" + request.lastName())")
    @Mapping(target = "purchaseDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", constant = "ACTIVE")
    public abstract Ticket toEntity(TicketRequest request);

    @Mapping(target = "id", source = "ticket.id")
    @Mapping(target = "eventId", source = "ticket.eventId")
    @Mapping(target = "eventTitle", source = "event.title")
    @Mapping(target = "hallId", source = "event.hallResponse.id")
    @Mapping(target = "hallName", source = "event.hallResponse.name")
    @Mapping(target = "eventTime", source = "event.startDateTime")
    @Mapping(target = "purchaseDate", source = "ticket.purchaseDate")
    @Mapping(target = "row", source = "ticket.row")
    @Mapping(target = "seat", source = "ticket.seat")
    @Mapping(target = "holderFullName", source = "ticket.holderFullName")
    @Mapping(target = "ticketStatus", expression = "java(ticket.getStatus().name())")
    public abstract TicketResponse toDto(Ticket ticket, EventResponse event);


}
