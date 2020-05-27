package org.gentar.biology.plan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import org.gentar.biology.plan.attempt.breeding.BreedingAttemptDTO;
import org.gentar.biology.plan.attempt.crispr.CrisprAttemptDTO;
import org.gentar.biology.plan.attempt.phenotyping.PhenotypingAttemptDTO;
import org.gentar.biology.plan.plan_starting_point.PlanStartingPointDTO;
import java.util.List;

@Data
/**
 * Basic information about a plan, without any of the information generated by the system. The
 * information here should be editable by the user.
 */
public class PlanBasicDataDTO
{
    // Common information for all plan dtos.
    @JsonUnwrapped
    private PlanCommonDataDTO planCommonDataDTO;

    // Starting point outcome for phenotyping plans
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("phenotypingStartingPoint")
    private PlanStartingPointDTO planStartingPointDTO;

    // Starting point outcomes for breeding plans
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("BreedingStartingPoints")
    private List<PlanStartingPointDTO> planStartingPointDTOS;

    // Crispr attempt information. It will only contain information if the attempt type is crispr.
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("crisprAttempt")
    private CrisprAttemptDTO crisprAttemptDTO;

    // Crispr attempt information. It will only contain information if the attempt type is breeding.
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("breedingAttempt")
    private BreedingAttemptDTO breedingAttemptDTO;

    // Crispr attempt information. It will only contain information if the plan type is phenotyping.
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("phenotypingAttempt")
    private PhenotypingAttemptDTO phenotypingAttemptDTO;
}