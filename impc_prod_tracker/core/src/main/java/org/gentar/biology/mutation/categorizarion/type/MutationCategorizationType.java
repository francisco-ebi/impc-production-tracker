package org.gentar.biology.mutation.categorizarion.type;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gentar.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
@Data
@Entity
public class MutationCategorizationType extends BaseEntity
{
    @Id
    @SequenceGenerator(name = "mutationCategorizationTypeSeq", sequenceName = "MUTATION_CATEGORIZATION_TYPE_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mutationCategorizationTypeSeq")
    private Long id;

    @NotNull
    private String name;

}
