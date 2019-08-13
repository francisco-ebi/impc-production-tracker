package uk.ac.ebi.impc_prod_tracker.data.biology.attempt.phenotyping_attempt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.ac.ebi.impc_prod_tracker.data.BaseEntity;
import uk.ac.ebi.impc_prod_tracker.data.biology.attempt.Attempt;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@Getter
@Setter
@Entity
public class PhenotypingAttempt extends BaseEntity
{
    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne
    @MapsId
    private Attempt attempt;

    private Long imitsPhenotypeAttemptId;

    private Long imitsPhenotypingProductionId;

    private LocalDateTime phenotypingExperimentsStarted;

    private Boolean phenotypingStarted;

    private Boolean phenotypingComplete;

    private Boolean doNotCountTowardsCompleteness;
}
