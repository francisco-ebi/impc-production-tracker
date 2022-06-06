package org.gentar.biology.targ_rep.allele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.gentar.BaseEntity;
import org.gentar.biology.targ_rep.genbank_file.TargRepGenbankFile;
import org.gentar.biology.targ_rep.allele.mutation_method.TargRepMutationMethod;
import org.gentar.biology.targ_rep.allele.mutation_subtype.TargRepMutationSubtype;
import org.gentar.biology.targ_rep.allele.mutation_type.TargRepMutationType;
import org.gentar.biology.targ_rep.gene.TargRepGene;


/**
 * TargRepAllele.
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@Data
@Entity
public class TargRepAllele extends BaseEntity {
    @Id
    @SequenceGenerator(name = "targRepAlleleSeq", sequenceName = "TARG_REP_ALLELE_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "targRepAlleleSeq")
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @NotNull
    @ManyToOne(targetEntity = TargRepGene.class)
    private TargRepGene gene;

    @NotNull
    //    @Value("Default GRCm38")
    private String assembly;

    @NotNull
    private String chromosome;

    @NotNull
    private String strand;

    private Integer homologyArmStart;

    private Integer homologyArmEnd;

    private Integer loxpStart;

    private Integer loxpEnd;

    private Integer cassetteStart;

    private Integer cassetteEnd;

    private String cassette;

    private String backbone;

    private String subtypeDescription;

    private String floxedStartExon;

    private String floxedEndExon;

    private Integer projectDesignId;

    private String reporter;

    @NotNull
    @ManyToOne
    private TargRepMutationMethod mutationMethod;

    @NotNull
    @ManyToOne
    private TargRepMutationType mutationType;

    @ManyToOne
    private TargRepMutationSubtype mutationSubtype;

    private String cassetteType;

    private Integer intron;

    private String type;

    @NotNull
    @Column(columnDefinition = "boolean default false")
    private Boolean hasIssue;

    @Column(columnDefinition = "TEXT")
    private String issueDescription;

    @Column(columnDefinition = "TEXT")
    private String sequence;

    private String taqmanCriticalDelAssayId;

    private String taqmanUpstreamDelAssayId;

    private String taqmanDownstreamDelAssayId;

    private String wildtypeOligosSequence;

    @OneToOne
    private TargRepGenbankFile alleleGenbankFile;

    @OneToOne
    private TargRepGenbankFile vectorGenbankFile;
}
