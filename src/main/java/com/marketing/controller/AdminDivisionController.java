package com.marketing.controller;

import com.marketing.dto.request.DivisionRequest;
import com.marketing.dto.response.DivisionResponse;
import com.marketing.dto.response.PageResponse;
import com.marketing.service.DivisionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/divisions")
@RequiredArgsConstructor
public class AdminDivisionController {

    private final DivisionService divisionService;

    /**
     * Get all divisions with pagination
     * GET /api/admin/divisions?page=0&size=10
     */
    @GetMapping
    public ResponseEntity<PageResponse<DivisionResponse>> getAllDivisions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(divisionService.getAllDivisionsPaginated(page, size));
    }

    /**
     * Get all divisions without pagination (for reordering)
     * GET /api/admin/divisions/all
     */
    @GetMapping("/all")
    public ResponseEntity<List<DivisionResponse>> getAllDivisionsNoPagination() {
        return ResponseEntity.ok(divisionService.getAllDivisions());
    }

    /**
     * Get single division by ID
     * GET /api/admin/divisions/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<DivisionResponse> getDivisionById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(divisionService.getDivisionById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create new division
     * POST /api/admin/divisions
     */
    @PostMapping
    public ResponseEntity<?> createDivision(@Valid @RequestBody DivisionRequest request) {
        try {
            DivisionResponse response = divisionService.createDivision(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Update existing division
     * PUT /api/admin/divisions/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDivision(
            @PathVariable String id,
            @Valid @RequestBody DivisionRequest request) {
        try {
            DivisionResponse response = divisionService.updateDivision(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Delete division
     * DELETE /api/admin/divisions/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDivision(@PathVariable String id) {
        try {
            divisionService.deleteDivision(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Reorder divisions
     * POST /api/admin/divisions/reorder
     * Body: { "divisionIds": ["IND_001", "IND_002", "IND_003"] }
     */
    @PostMapping("/reorder")
    public ResponseEntity<Void> reorderDivisions(@RequestBody Map<String, List<String>> payload) {
        try {
            List<String> divisionIds = payload.get("divisionIds");
            divisionService.reorderDivisions(divisionIds);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Toggle status (active/inactive)
     * PATCH /api/admin/divisions/{id}/toggle-status
     */
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<DivisionResponse> toggleStatus(@PathVariable String id) {
        try {
            DivisionResponse response = divisionService.toggleStatus(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}