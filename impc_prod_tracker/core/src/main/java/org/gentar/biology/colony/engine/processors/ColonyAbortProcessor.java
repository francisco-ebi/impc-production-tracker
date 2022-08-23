package org.gentar.biology.colony.engine.processors;

import org.gentar.biology.colony.Colony;
import org.gentar.biology.colony.engine.ColonyStateSetter;
import org.gentar.biology.outcome.Outcome;
import org.gentar.biology.plan.Plan;
import org.gentar.biology.plan.starting_point.PlanStartingPoint;
import org.gentar.statemachine.AbstractProcessor;
import org.gentar.statemachine.ProcessData;
import org.gentar.statemachine.ProcessEvent;
import org.gentar.statemachine.TransitionEvaluation;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class with the logic to move a Colony to the state "Colony Aborted"
 */
@Component
public class ColonyAbortProcessor extends AbstractProcessor
{
    public ColonyAbortProcessor(ColonyStateSetter colonyStateSetter)
    {
        super(colonyStateSetter);
    }

    @Override
    public TransitionEvaluation evaluateTransition(ProcessEvent transition, ProcessData data)
    {
        TransitionEvaluation transitionEvaluation = new TransitionEvaluation();
        transitionEvaluation.setTransition(transition);
        boolean areAllDependantPlansAborted = areAllDependantPlansAborted((Colony) data);
        transitionEvaluation.setExecutable(areAllDependantPlansAborted);
        if (!areAllDependantPlansAborted)
        {
            transitionEvaluation.setNote("The colony is used in plans that are not aborted");
        }
        return transitionEvaluation;
    }

    private boolean areAllDependantPlansAborted(Colony colony)
    {
        boolean result = true;
        List<Plan> plans = getDependantPlans(colony);
        if (!plans.isEmpty())
        {
            result = plans.stream().allMatch(x -> x.getProcessDataStatus().getIsAbortionStatus());
        }
        return result;
    }

    // Get a list of plans that depend on plan because the colony is their starting point
    private List<Plan> getDependantPlans(Colony colony)
    {
        List<Plan> dependantPlans = new ArrayList<>();
        Outcome outcome = colony.getOutcome();
        Set<PlanStartingPoint> planStartingPoints = outcome.getPlanStartingPoints();
        if (planStartingPoints != null)
        {
            planStartingPoints.forEach(x -> dependantPlans.add(x.getPlan()));
        }
        return dependantPlans;
    }
}
