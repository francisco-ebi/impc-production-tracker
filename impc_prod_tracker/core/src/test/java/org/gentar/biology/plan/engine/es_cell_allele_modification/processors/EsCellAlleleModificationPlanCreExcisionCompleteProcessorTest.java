package org.gentar.biology.plan.engine.es_cell_allele_modification.processors;

import static org.junit.jupiter.api.Assertions.*;

import org.gentar.biology.plan.Plan;
import org.gentar.biology.plan.attempt.es_cell_allele_modification.EsCellAlleleModificationAttempt;
import org.gentar.biology.plan.engine.PlanStateSetter;
import org.gentar.biology.plan.engine.crispr.processors.AbortFounderObtainedProcessor;
import org.gentar.mockdata.MockData;
import org.gentar.statemachine.ProcessEvent;
import org.gentar.statemachine.ProcessState;
import org.gentar.statemachine.Processor;
import org.gentar.statemachine.TransitionEvaluation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class EsCellAlleleModificationPlanCreExcisionCompleteProcessorTest {

    private EsCellAlleleModificationPlanCreExcisionCompleteProcessor testInstance;

    @Mock
    private PlanStateSetter planStateSetter;

    @BeforeEach
    void setUp() {
        testInstance =
            new EsCellAlleleModificationPlanCreExcisionCompleteProcessor(planStateSetter);
    }

    @Test
    void evaluateTransition() {
        TransitionEvaluation transitionEvaluation =
            testInstance.evaluateTransition(processEventMockData(),
                planMockData());

        assertEquals(transitionEvaluation.getNote(),"Please update again to continue Cre Excision Completed state.");
    }

    @Test
    void evaluateTransitionNotsuccessfulMatingsAndEitherDeleterStrainOrTatCreExists() {
        TransitionEvaluation transitionEvaluation =
            testInstance.evaluateTransition(processEventMockData(),
                MockData.planMockData());

        assertEquals(transitionEvaluation.getNote(),"Successful matings need to be specified to continue Cre Excision Completed state.");
    }


    private ProcessEvent processEventMockData() {
        ProcessEvent processEvent = new ProcessEvent() {
            @Override
            public Class<? extends Processor> getNextStepProcessor() {
                return null;
            }

            @Override
            public String getName() {
                return "updateToCreExcisionComplete";
            }

            @Override
            public String getDescription() {
                return "Update to Cre Excision Complete.";
            }

            @Override
            public ProcessState getInitialState() {
                return new ProcessState() {
                    @Override
                    public String getName() {
                        return "CreExcisionStarted";
                    }

                    @Override
                    public String getInternalName() {
                        return "Cre Excision Started";
                    }
                };
            }

            @Override
            public ProcessState getEndState() {
                return new ProcessState() {
                    @Override
                    public String getName() {
                        return "CreExcisionComplete";
                    }

                    @Override
                    public String getInternalName() {
                        return "Cre Excision Complete";
                    }
                };
            }

            @Override
            public boolean isTriggeredByUser() {
                return false;
            }

            @Override
            public String getTriggerNote() {
                return null;
            }
        };

        return processEvent;
    }


    private Plan planMockData(){
        Plan plan = MockData.planMockData();
        plan.setEsCellAlleleModificationAttempt(esCellAlleleModificationAttemptMockData());
        return plan;
    }

    private EsCellAlleleModificationAttempt esCellAlleleModificationAttemptMockData(){
        EsCellAlleleModificationAttempt esCellAlleleModificationAttempt =  new EsCellAlleleModificationAttempt();
        esCellAlleleModificationAttempt.setTatCre(true);
        return esCellAlleleModificationAttempt;
    }
}