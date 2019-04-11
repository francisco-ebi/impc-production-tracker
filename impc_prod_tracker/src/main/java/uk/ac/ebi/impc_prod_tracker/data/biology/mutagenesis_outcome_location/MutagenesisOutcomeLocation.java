package uk.ac.ebi.impc_prod_tracker.data.biology.mutagenesis_outcome_location;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.ac.ebi.impc_prod_tracker.data.BaseEntity;
import uk.ac.ebi.impc_prod_tracker.data.biology.IntentedLocation.IntendedLocation;
import uk.ac.ebi.impc_prod_tracker.data.biology.mutagenesis_outcome.MutagenesisOutcome;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@Getter
@Setter
@Entity
@IdClass(MutagenesisOutcomeLocation.class)
public class MutagenesisOutcomeLocation extends BaseEntity implements Serializable
{
    @Id
    @ManyToOne
    @JoinColumn
    private MutagenesisOutcome mutagenesisOutcome;

    @Id
    @ManyToOne
    @JoinColumn
    private IntendedLocation location;

    private Integer index;
}
