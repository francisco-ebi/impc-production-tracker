package org.gentar.biology.plan.engine.es_cell_allele_modification.processors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.gentar.biology.plan.Plan;
import org.gentar.biology.plan.attempt.es_cell_allele_modification.EsCellAlleleModificationAttempt;
import org.gentar.biology.plan.engine.PlanStateSetter;
import org.gentar.mockdata.MockData;
import org.gentar.statemachine.ProcessEvent;
import org.gentar.statemachine.ProcessState;
import org.gentar.statemachine.Processor;
import org.gentar.statemachine.TransitionEvaluation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class EsCellAlleleModificationPlanCreExcisionStartedProcessorTest {

    private EsCellAlleleModificationPlanCreExcisionStartedProcessor testInstance;

    @Mock
    private PlanStateSetter planStateSetter;

    @BeforeEach
    void setUp()
    {
        testInstance = new EsCellAlleleModificationPlanCreExcisionStartedProcessor(planStateSetter);
    }

    @Test
    void evaluateTransition() {
        TransitionEvaluation transitionEvaluation =
            testInstance.evaluateTransition(processEventMockData(),
                planMockData());

        assertEquals(transitionEvaluation.getNote(),"Please update again to continue Cre Excision Started state.");
    }

    @Test
    void evaluateTransitionDeleterStrainOrTatCreExists() {
        TransitionEvaluation transitionEvaluation =
            testInstance.evaluateTransition(processEventMockData(),
                MockData.planMockData());

        assertEquals(transitionEvaluation.getNote(),"A deleter strain or tat Cre needs to be specified, or specify rederivation as the Next Step.");
    }

    private ProcessEvent processEventMockData() {
        ProcessEvent processEvent = new ProcessEvent() {
            @Override
            public Class<? extends Processor> getNextStepProcessor() {
                return null;
            }

            @Override
            public String getName() {
                return "updateToCreExcisionStarted";
            }

            @Override
            public String getDescription() {
                return "Update to Cre Excision Started.";
            }

            @Override
            public ProcessState getInitialState() {
                return new ProcessState() {
                    @Override
                    public String getName() {
                        return "MouseAlleleModificationRegistered";
                    }

                    @Override
                    public String getInternalName() {
                        return "Mouse Allele Modification Registered";
                    }
                };
            }

            @Override
            public ProcessState getEndState() {
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