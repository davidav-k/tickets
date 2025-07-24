package com.tickets.eventservice.dto.mapper;

import com.tickets.eventservice.dto.HallRequest;
import com.tickets.eventservice.dto.HallResponse;
import com.tickets.eventservice.entity.Hall;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true))
public interface HallMapper {


    HallResponse toDto(Hall hall);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "events", ignore = true)
    Hall toEntity(HallRequest hallRequest);

    List<HallResponse> toDtoList(List<Hall> halls);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "events", ignore = true)
    void updateEntityFromDto(HallRequest hallRequest, @MappingTarget Hall hall);
}
