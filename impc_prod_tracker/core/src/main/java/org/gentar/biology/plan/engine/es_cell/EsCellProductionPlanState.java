package org.gentar.biology.plan.engine.es_cell;

import org.gentar.statemachine.EnumStateHelper;
import org.gentar.statemachine.ProcessState;

import java.util.Arrays;

public enum EsCellProductionPlanState implements ProcessState
{
    PlanCreated("Plan Created"),
    PlanAbandoned("Plan Abandoned"),
    AttemptInProgress("Attempt In Progress"),
    ChimerasFounderObtained("Chimeras/Founder Obtained"),
    AttemptAborted("Attempt Aborted");

    private String internalName;

    EsCellProductionPlanState(String internalName)
    {
        this.internalName = internalName;
    }

    public String getInternalName()
    {
        return internalName;
    }

    public static ProcessState getStateByInternalName(String internalName)
    {
        return EnumStateHelper.getStateByInternalName(
                Arrays.asList(EsCellProductionPlanState.values()), internalName);
    }

    @Override
    public String getName()
    {
        return this.name();
    }
}
