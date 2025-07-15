package com.tickets.ticket_service.controller;

import com.tickets.ticket_service.domain.ApiResponse;
import com.tickets.ticket_service.dto.CreateHallRequest;
import com.tickets.ticket_service.dto.HallResponse;
import com.tickets.ticket_service.dto.UserResponse;
import com.tickets.ticket_service.service.HallService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/tickets/halls")
@RequiredArgsConstructor
public class HallController {

    private final HallService hallService;

    @Schema(description = "Request to create a new hall")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<HallResponse>> createHall(@Valid @RequestBody CreateHallRequest request) {
        log.info("Creating a new hall named: {}", request.name());
        HallResponse hallResponse = hallService.saveHall(request);

        return ResponseEntity.ok(
                ApiResponse.success("/api/halls", "Hall created successfully", hallResponse)
        );
    }

    @Schema(description = "Request to delete a hall by ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteHall(@PathVariable Long id) {
        log.info("Deleting hall with ID: {}", id);
        hallService.deleteHall(id);
        return ResponseEntity.ok(
                ApiResponse.success("/api/halls", "Hall deleted successfully", "Hall with ID " + id + " has been deleted")
        );
    }

    @Schema(description = "Request to get all halls")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CASHIER')")
    public ResponseEntity<ApiResponse<Page<HallResponse>>> getAllHalls(){
        log.info("Fetching all halls");
        Page<HallResponse> halls = hallService.getAllHalls();
        return ResponseEntity.ok(
                ApiResponse.success("/api/halls", "All halls retrieved successfully", halls)
        );
    }

    @Schema(description = "Request to get a hall by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CASHIER')")
    public ResponseEntity<ApiResponse<HallResponse>> getHallById(@PathVariable Long id) {
        log.info("Fetching hall with ID: {}", id);

        HallResponse hall = hallService.getHallById(id);
        return ResponseEntity.ok(
                ApiResponse.success("/api/halls/" + id, "Hall found", hall)
        );

    }

   @GetMapping("/{id}/creator")
   @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<ApiResponse<UserResponse>> getHallCreator(@PathVariable Long id) {
       UserResponse creator = hallService.getHallCreator(id);
       return ResponseEntity.ok(
               ApiResponse.success("/tickets/halls/" + id + "/creator", "Hall creator found", creator)
       );
   }



}
