package org.gentar.biology.project.specs;

import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.SetJoin;
import org.gentar.biology.colony.Colony;
import org.gentar.biology.colony.Colony_;
import org.gentar.biology.gene.Gene;
import org.gentar.biology.gene.Gene_;
import org.gentar.biology.intention.project_intention.ProjectIntention;
import org.gentar.biology.intention.project_intention.ProjectIntention_;
import org.gentar.biology.intention.project_intention_gene.ProjectIntentionGene;
import org.gentar.biology.intention.project_intention_gene.ProjectIntentionGene_;
import org.gentar.biology.mutation.molecular_type.MolecularMutationType;
import org.gentar.biology.mutation.molecular_type.MolecularMutationType_;
import org.gentar.biology.outcome.Outcome;
import org.gentar.biology.outcome.Outcome_;
import org.gentar.biology.plan.Plan;
import org.gentar.biology.plan.Plan_;
import org.gentar.biology.plan.attempt.phenotyping.PhenotypingAttempt;
import org.gentar.biology.plan.attempt.phenotyping.PhenotypingAttempt_;
import org.gentar.biology.project.Project;
import org.gentar.biology.project.Project_;
import org.gentar.biology.project.assignment.AssignmentStatus;
import org.gentar.biology.project.assignment.AssignmentStatus_;
import org.gentar.biology.project.consortium.ProjectConsortium;
import org.gentar.biology.project.consortium.ProjectConsortium_;
import org.gentar.biology.project.privacy.Privacy;
import org.gentar.biology.project.privacy.Privacy_;
import org.gentar.biology.status.Status;
import org.gentar.biology.status.Status_;
import org.gentar.organization.consortium.Consortium;
import org.gentar.organization.consortium.Consortium_;
import org.gentar.organization.work_group.WorkGroup;
import org.gentar.organization.work_group.WorkGroup_;
import org.gentar.organization.work_unit.WorkUnit;
import org.gentar.organization.work_unit.WorkUnit_;

public class ProjectPaths
{
    public static Path<String> getTpnPath(Root<Project> root)
    {
        return root.get(Project_.tpn);
    }

    public static Path<String> getPrivacyNamePath(Root<Project> root)
    {
        Path<Privacy> privacy = root.get(Project_.privacy);
        return privacy.get(Privacy_.name);
    }

    public static Path<String> getAssignmentNamePath(Root<Project> root)
    {
        Path<AssignmentStatus> status = root.get(Project_.assignmentStatus);
        return status.get(AssignmentStatus_.name);
    }


    public static Path<String> getSummaryStatusNamePath(Root<Project> root)
    {
        Path<Status> status = root.get(Project_.summaryStatus);
        return status.get(Status_.name);
    }

    public static Path<String> getConsortiaNamePath(Root<Project> root)
    {
        Path<ProjectConsortium> projectProjectConsortiumSetJoin =
            root.join(Project_.projectConsortia);
        Path<Consortium> consortiumPath =
            projectProjectConsortiumSetJoin.get(ProjectConsortium_.consortium);
        return consortiumPath.get(Consortium_.name);
    }

    public static Path<String> getWorkUnitNamePath(Root<Project> root)
    {
        SetJoin<Project, Plan> plansJoin = root.join(Project_.plans);
        Path<WorkUnit> workUnitPath = plansJoin.get(Plan_.workUnit);
        return workUnitPath.get(WorkUnit_.name);
    }

    public static Path<String> getWorkGroupNamePath(Root<Project> root)
    {
        SetJoin<Project, Plan> plansJoin = root.join(Project_.plans);
        Path<WorkGroup> workGroupPath = plansJoin.get(Plan_.workGroup);
        return workGroupPath.get(WorkGroup_.name);
    }

    public static Path<String> getMolecularMutationTypeNamePath(Root<Project> root)
    {
        ListJoin<Project, ProjectIntention> projectProjectIntentionSetJoin =
            root.join(Project_.projectIntentions);

        Path<MolecularMutationType> molecularMutationTypePath =
            projectProjectIntentionSetJoin.join(ProjectIntention_.molecularMutationType);

        return molecularMutationTypePath.get(MolecularMutationType_.name);
    }

    public static Path<String> getMarkerSymbolPath(Root<Project> root)
    {
        ListJoin<Project, ProjectIntention> projectProjectIntentionSetJoin =
            root.join(Project_.projectIntentions);

        Path<ProjectIntentionGene> projectIntentionProjectGeneSetJoin =
            projectProjectIntentionSetJoin.get(ProjectIntention_.projectIntentionGene);
        Path<Gene> genePath = projectIntentionProjectGeneSetJoin.get(ProjectIntentionGene_.gene);
        return genePath.get(Gene_.symbol);
    }

    public static Path<String> getAccIdPath(Root<Project> root)
    {
        ListJoin<Project, ProjectIntention> projectProjectIntentionSetJoin =
            root.join(Project_.projectIntentions);
        Path<ProjectIntentionGene> projectGenePath =
            projectProjectIntentionSetJoin.join(ProjectIntention_.projectIntentionGene);
        Path<Gene> genePath = projectGenePath.get(ProjectIntentionGene_.gene);
        return genePath.get(Gene_.accId);
    }

    public static Path<Long> getImitsMiPlanPath(Root<Project> root)
    {
        return root.get(Project_.imitsMiPlan);
    }

    public static Path<String> getColonyNamePath(Root<Project> root)
    {
        SetJoin<Project, Plan> planSetJoin = root.join(Project_.plans);
        SetJoin<Plan, Outcome> outcomeSetJoin = planSetJoin.join(Plan_.outcomes);
        Path<Colony> colonyPath = outcomeSetJoin.get(Outcome_.colony);
        return colonyPath.get(Colony_.name);
    }

    public static Path<String> getPhenotypingExternalRefName(Root<Project> root)
    {
        SetJoin<Project, Plan> planSetJoin = root.join(Project_.plans);
        Path<PhenotypingAttempt> phenotypingAttemptPath = planSetJoin.get(Plan_.phenotypingAttempt);
        return phenotypingAttemptPath.get(PhenotypingAttempt_.phenotypingExternalRef);
    }
}
