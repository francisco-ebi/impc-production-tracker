package uk.ac.ebi.impc_prod_tracker.data.experiment.project;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import uk.ac.ebi.impc_prod_tracker.data.BaseEntity;
import uk.ac.ebi.impc_prod_tracker.data.biology.allele_type.AlleleType;
import uk.ac.ebi.impc_prod_tracker.data.biology.intented_mouse_gene.IntendedMouseGene;
import uk.ac.ebi.impc_prod_tracker.data.experiment.assignment_status.AssignmentStatus;
import uk.ac.ebi.impc_prod_tracker.data.experiment.plan.Plan;
import uk.ac.ebi.impc_prod_tracker.data.experiment.project_priority.ProjectPriority;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
@Data
@Entity
public class Project extends BaseEntity
{
    @Id
    @SequenceGenerator(name = "projectSeq", sequenceName = "PROJECT_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projectSeq")
    private Long id;

    @Column(unique = true)
    @NotNull
    private String tpn;

    @Column(unique = true)
    private Long imitsMiPlanId;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany
    @JoinColumn(name = "project_id")
    private Set<Plan> plans = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    private AssignmentStatus assignmentStatus;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    private ProjectPriority projectPriority;

    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
        name = "project_intention_rel",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "allele_type_id"))
    private Set<AlleleType> projectIntentions;

    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
        name = "project_mouse_gene",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "mouse_gene_id"))
    private Set<IntendedMouseGene> intendedMouseGenes;
}


