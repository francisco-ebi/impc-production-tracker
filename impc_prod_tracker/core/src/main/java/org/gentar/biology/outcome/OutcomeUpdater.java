package org.gentar.biology.outcome;

import org.gentar.audit.history.History;
import org.gentar.audit.history.HistoryService;
import org.gentar.biology.colony.Colony;
import org.gentar.biology.colony.Colony_;
import org.gentar.biology.plan.PlanStatusManager;
import org.gentar.biology.status.Status_;
import org.gentar.statemachine.StateTransitionsManager;
import org.springframework.stereotype.Component;

@Component
public class OutcomeUpdater
{
    private HistoryService<Outcome> historyService;
    private StateTransitionsManager stateTransitionsManager;
    private OutcomeRepository outcomeRepository;
    private PlanStatusManager planStatusManager;

    public OutcomeUpdater(
        HistoryService historyService,
        StateTransitionsManager stateTransitionsManager,
        OutcomeRepository outcomeRepository, PlanStatusManager planStatusManager)
    {
        this.historyService = historyService;
        this.stateTransitionsManager = stateTransitionsManager;
        this.outcomeRepository = outcomeRepository;
        this.planStatusManager = planStatusManager;
    }

    History update(Outcome originalOutcome, Outcome newOutcome)
    {
        validatePermission(newOutcome);
        validateData(newOutcome);
        changeStatusIfNeeded(newOutcome);
        History history = detectTrackOfChanges(originalOutcome, newOutcome);
        saveChanges(newOutcome);
        saveTrackOfChanges(history);
        planStatusManager.setSummaryStatus(originalOutcome.getPlan());
        return history;
    }

    private void validatePermission(Outcome newOutcome)
    {
        // Add validations if needed
    }

    private void validateData(Outcome newOutcome)
    {
        // Add validations if needed
    }

    private void changeStatusIfNeeded(Outcome outcome)
    {
        Colony colony = outcome.getColony();
        if (colony != null && colony.getEvent() != null)
        {
            stateTransitionsManager.processEvent(colony);
        }
    }

    private void saveChanges(Outcome outcome)
    {
        outcomeRepository.save(outcome);
    }

    private void saveTrackOfChanges(History history)
    {
        historyService.saveTrackOfChanges(history);
    }

    private History detectTrackOfChanges(Outcome originalOutcome, Outcome newOutcome)
    {

        History history =
            historyService.detectTrackOfChanges(
                originalOutcome, newOutcome, originalOutcome.getId());
        history = historyService.filterDetailsInNestedEntity(history, Colony_.STATUS, Status_.NAME);
        return history;
    }
}
