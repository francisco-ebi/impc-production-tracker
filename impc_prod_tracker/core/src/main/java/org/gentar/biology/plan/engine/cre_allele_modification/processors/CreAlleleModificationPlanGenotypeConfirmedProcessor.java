package org.gentar.biology.plan.engine.cre_allele_modification.processors;

import org.gentar.biology.outcome.Outcome;
import org.gentar.biology.plan.Plan;
import org.gentar.biology.plan.PlanQueryHelper;
import org.gentar.biology.plan.attempt.cre_allele_modification.CreAlleleModificationAttempt;
import org.gentar.biology.plan.engine.PlanStateSetter;
import org.gentar.statemachine.AbstractProcessor;
import org.gentar.statemachine.ProcessData;
import org.gentar.statemachine.ProcessEvent;
import org.gentar.statemachine.TransitionEvaluation;
import org.springframework.stereotype.Component;

@Component
public class CreAlleleModificationPlanGenotypeConfirmedProcessor extends AbstractProcessor {

    public CreAlleleModificationPlanGenotypeConfirmedProcessor(PlanStateSetter planStateSetter)
    {
        super(planStateSetter);
    }

    @Override
    public TransitionEvaluation evaluateTransition(ProcessEvent transition, ProcessData data)
    {
        TransitionEvaluation transitionEvaluation = new TransitionEvaluation();
        transitionEvaluation.setTransition(transition);

        boolean genotypeConfirmedColoniesExist = identifyGenotypeConfirmedColonies((Plan) data);

        transitionEvaluation.setExecutable(genotypeConfirmedColoniesExist);

        if (!genotypeConfirmedColoniesExist)
        {
            transitionEvaluation.setNote("A genotyped confirmed colony needs to be associated with the plan.");
        }
        return transitionEvaluation;
    }

    private boolean identifyGenotypeConfirmedColonies(Plan plan)
    {
        return PlanQueryHelper.areAnyColoniesByPlanGenotypeConfirmed(plan);
    }

}
