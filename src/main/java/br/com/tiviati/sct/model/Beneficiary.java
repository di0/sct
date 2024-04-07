package br.com.tiviati.sct.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "beneficiary")
@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Beneficiary {
    public Beneficiary() { /* 'Reflection' para JPA. */ }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(nullable = false, name = "nome", length = 150)
    @JsonProperty("nome")
    private String name;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, name = "telefone")
    @JsonProperty("telefone")
    private String contactNumber;

    @Column(nullable = false, name = "dataNascimento")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Schema(type = "string", pattern = "dd/MM/yyyy", example = "14/08/1985")
    @JsonProperty("dataNascimento")
    private Date birthDate;

    @Column(name = "dataInclusao", updatable = false)
    @CreationTimestamp
    @JsonProperty(value = "dataInclusao", access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(type = "string", pattern = "dd/MM/yyyy HH:mm:ss", example = "20/08/2024 23:10:25")
    private Date inclusionDate;

    @Column(name = "dataAtualizacao")
    @UpdateTimestamp
    @JsonProperty(value = "dataAtualizacao", access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:MM:ss")
    @Schema(type = "string", pattern = "dd/MM/yyyy HH:mm:ss", example = "20/08/2024 23:10:25")
    private Date updateDate;

    @OneToMany(mappedBy = "beneficiary", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Document> documents;

    @Override
    public String toString() {
        return
                "Beneficiary {" +
                        "id = " + id +
                        ", name = '" + name + '\'' +
                        '}';
    }
}
