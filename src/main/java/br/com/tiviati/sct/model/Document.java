package br.com.tiviati.sct.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "document")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(nullable = false, name = "tipoDocumento")
    @JsonProperty("tipoDocumento")
    private String documentType;

    @Column(nullable = false, name = "descricao")
    @JsonProperty("descricao")
    private String description;

    @Column(name = "dataInclusao", updatable = false)
    @CreationTimestamp
    @JsonProperty(value = "dataInclusao", access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(type = "string", pattern = "dd/MM/yyyy HH:mm:ss", example = "20/08/2024 23:10:25")
    private Date inclusionDate;

    @Column(name = "dataAtualizacao")
    @UpdateTimestamp
    @JsonProperty(value = "dataAtualizacao", access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(type = "string", pattern = "dd/MM/yyyy HH:mm:ss", example = "20/08/2024 23:10:25")
    private Date updateDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiary_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Beneficiary beneficiary;
}
