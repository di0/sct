package br.com.tiviati.sct.controller;

import br.com.tiviati.sct.model.Beneficiary;
import br.com.tiviati.sct.model.Document;
import br.com.tiviati.sct.service.SctService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

import static br.com.tiviati.sct.commons.Constants.API_KEY_HEADER_NAME;

@RestController
@RequestMapping("/sct")
@Tag(name = "Sistema de Cadastro Tivia (SCT)", description = "RESTful API para manipular beneficiarios e " +
        "seus respectivos documentos.")
@SecurityRequirement(name = API_KEY_HEADER_NAME)
public class SctController {
    @Autowired
    private SctService sctService;

    @GetMapping("/beneficiaries")
    @Operation(summary = "Get all beneficiaries.", description = "Get a list of all registered beneficiaries.")
    public ResponseEntity<List<Beneficiary>> findAll() {
        return ResponseEntity.ok(sctService.findAll());
    }

    @GetMapping("/beneficiaries/{id}")
    @Operation(summary = "Get a beneficiary by id.", description = "Get a specific beneficiary based on it's id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Beneficiary found."),
            @ApiResponse(responseCode = "404", description = "Beneficiary not found.")
    })
    public ResponseEntity<Beneficiary> findById(final @PathVariable Long id) {
        return ResponseEntity.ok(sctService.findById(id));
    }

    @PostMapping("/beneficiaries")
    @Operation(summary = "Create a new beneficiary.", description = "Create a new beneficiary and returns the" +
            " created beneficiary.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Beneficiary created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid beneficiary data provided.")
    })
    public ResponseEntity<Beneficiary> create(final @RequestBody Beneficiary beneficiary) {
        Beneficiary newBeneficiary = sctService.create(beneficiary);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newBeneficiary.getId())
                .toUri();
        return ResponseEntity.created(location).body(newBeneficiary);
    }

    @PutMapping("/beneficiaries/{id}")
    @Operation(summary = "Update a beneficiary.", description = "Update an existing beneficiary based on it's id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Beneficiary updated successfully."),
            @ApiResponse(responseCode = "404", description = "Beneficiary not found."),
            @ApiResponse(responseCode = "422", description = "Invalid beneficiary data provided.")
    })
    public ResponseEntity<Beneficiary> update(final @PathVariable Long id, final @RequestBody Beneficiary beneficiary) {
        return ResponseEntity.ok(sctService.update(id, beneficiary));
    }

    @DeleteMapping("/beneficiaries/{id}")
    @Operation(summary = "Delete a beneficiary.", description = "Delete an existing beneficiary based on it's id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Beneficiary deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Beneficiary not found.")
    })
    public ResponseEntity<Void> delete(final @PathVariable Long id) {
        sctService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/beneficiaries/{id}/documents")
    @Operation(summary = "Get all documents.", description = "Get all documents by beneficiary id.")
    public ResponseEntity<Set<Document>> getDocumentsByBeneficiaryId(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(sctService.findAllDocumentsByBeneficiaryId(id));
    }
}
