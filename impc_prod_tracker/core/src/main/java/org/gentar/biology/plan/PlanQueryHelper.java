package org.gentar.biology.plan;

import org.gentar.biology.colony.Colony;
import org.gentar.biology.colony.engine.ColonyState;
import org.gentar.biology.outcome.Outcome;
import org.gentar.biology.outcome.type.OutcomeTypeName;
import org.gentar.biology.plan.attempt.phenotyping.PhenotypingAttempt;
import org.gentar.biology.plan.attempt.phenotyping.stage.PhenotypingStage;
import org.gentar.biology.specimen.Specimen;
import org.gentar.biology.status.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Helper class with common queries for a plan.
 */
public class PlanQueryHelper
{
    /**
     * Get all the outcomes of a given type for a plan.
     * @param plan Plan object.
     * @param type {@link OutcomeTypeName} object with the type of the outcome.
     * @return A list of {@link Outcome} of type "type".
     */
    public static List<Outcome> getOutcomesByPlanAndType(Plan plan, OutcomeTypeName type)
    {
        List<Outcome> outcomes = new ArrayList<>();
        Set<Outcome> outcomesSet = plan.getOutcomes();
        if (outcomesSet != null)
        {
            outcomesSet.forEach(x -> {
                if (x.getOutcomeType().getName().equals(type.getLabel()))
                {
                    outcomes.add(x);
                }
            });
        }
        return outcomes;
    }

    /**
     * Gets all the colonies for a plan.
     * @param plan The plan to query.
     * @return List of {@link Colony} corresponding to outcomes that have a colony in the plan.
     */
    public static List<Colony> getColoniesByPlan(Plan plan)
    {
        List<Colony> colonies = new ArrayList<>();
        List<Outcome> outcomes = getOutcomesByPlanAndType(plan, OutcomeTypeName.COLONY);
        outcomes.forEach(x -> {
            if (x.getColony() != null)
            {
                colonies.add(x.getColony());
            }
        });
        return colonies;
    }

    public static boolean areAllColoniesByPlanAborted(Plan plan)
    {
        boolean result = true;
        List<Colony> colonies = getColoniesByPlan(plan);
        if (!colonies.isEmpty())
        {
            result = colonies.stream().allMatch(x -> x.getStatus().getIsAbortionStatus());
        }
        return result;
    }

    public static boolean areAnyColoniesByPlanGenotypeConfirmed(Plan plan)
    {
        boolean result = false;
        List<Colony> colonies = getColoniesByPlan(plan);
        if (!colonies.isEmpty())
        {
            result = colonies.stream().anyMatch(x -> x.getStatus().getName().equals(ColonyState.GenotypeConfirmed.getName()));
        }
        return result;
    }

    /**
     * Gets all the specimens for a plan.
     * @param plan The plan to query.
     * @return List of {@link Specimen} corresponding to outcomes that have a specimen in the plan.
     */
    public static List<Specimen> getSpecimensByPlan(Plan plan)
    {
        List<Specimen> specimens = new ArrayList<>();
        List<Outcome> outcomes = getOutcomesByPlanAndType(plan, OutcomeTypeName.SPECIMEN);
        outcomes.forEach(x -> {
            if (x.getSpecimen() != null)
            {
                specimens.add(x.getSpecimen());
            }
        });
        return specimens;
    }

    public static boolean areAllSpecimensByPlanAborted(Plan plan)
    {
        boolean result = true;
        List<Specimen> specimens = getSpecimensByPlan(plan);
        if (!specimens.isEmpty())
        {
            result = specimens.stream().allMatch(x -> x.getStatus().getIsAbortionStatus());
        }
        return result;
    }

    /**
     * Gets a list of Phenotyping Stages for a plan
     * @param plan Plan to check.
     * @return List of {@link PhenotypingStage} in the plan.
     */
    public static List<PhenotypingStage> getPhenotypingStages(Plan plan)
    {
        List<PhenotypingStage> phenotypingStages = new ArrayList<>();
        PhenotypingAttempt phenotypingAttempt = plan.getPhenotypingAttempt();
        Set<PhenotypingStage> phenotypingStagesSet =
            phenotypingAttempt == null ? null : phenotypingAttempt.getPhenotypingStages();
        if (phenotypingStagesSet != null)
        {
            phenotypingStages.addAll(phenotypingStagesSet);
        }
        return phenotypingStages;
    }

    /**
     * Gets a list of Phenotyping Stages statuses in a plan.
     * @param plan Plan to check.
     * @return List of {@link Status} corresponding to statuses in the phenotyping stages in this plan.
     */
    public static List<Status> getPhenotypingStagesStatuses(Plan plan)
    {
        List<Status> phenotypingStagesStatuses = new ArrayList<>();
        List<PhenotypingStage> phenotypingStages = getPhenotypingStages(plan);
        phenotypingStages.forEach(x -> phenotypingStagesStatuses.add(x.getStatus()));
        return phenotypingStagesStatuses;
    }
}
