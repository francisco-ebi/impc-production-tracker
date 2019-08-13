/*******************************************************************************
 * Copyright 2019 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 *******************************************************************************/
package uk.ac.ebi.impc_prod_tracker.web.mapping.plan.phenotype_plan.phenotype_production;

import org.springframework.stereotype.Component;
import uk.ac.ebi.impc_prod_tracker.data.biology.attempt.phenotyping_attempt.PhenotypingAttempt;
import uk.ac.ebi.impc_prod_tracker.data.biology.attempt.phenotyping_attempt.tissue_distribution_centre.TissueDistributionCentre;
import uk.ac.ebi.impc_prod_tracker.data.biology.plan.Plan;
import uk.ac.ebi.impc_prod_tracker.service.plan.PhenotypingProductionService;
import uk.ac.ebi.impc_prod_tracker.web.dto.plan.phenotype_plan.phenotyping_production.PhenotypingProductionDTO;
import uk.ac.ebi.impc_prod_tracker.web.dto.plan.phenotype_plan.phenotyping_production.TissueDistributionCentreDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PhenotypingProductionDTOBuilder
{
    private PhenotypingProductionService phenotypingProductionService;

    public PhenotypingProductionDTOBuilder(
        PhenotypingProductionService phenotypingProductionService)
    {

        this.phenotypingProductionService = phenotypingProductionService;
    }

    public PhenotypingProductionDTO buildFromPlan(final Plan plan)
    {
        PhenotypingProductionDTO phenotypingProductionDTO = new PhenotypingProductionDTO();
        Optional<PhenotypingAttempt> phenotypingProductionOpt =
            phenotypingProductionService.getPhenotypingProductionByPlan(plan);

        if (phenotypingProductionOpt.isPresent())
        {
            PhenotypingAttempt phenotypingProduction = phenotypingProductionOpt.get();
//            phenotypingProductionDTO.setColonyName(phenotypingProduction.getPhenotypingColonyName());
//            if (phenotypingProduction.getPhenotypingColonyStrain() != null)
//            {
//                phenotypingProductionDTO.setColonyStrainName(
//                    phenotypingProduction.getPhenotypingColonyStrain().getName());
//            }
            phenotypingProductionDTO.setExperimentStartedOn(
                phenotypingProduction.getPhenotypingExperimentsStarted());
            phenotypingProductionDTO.setStarted(phenotypingProduction.getPhenotypingStarted());
            phenotypingProductionDTO.setComplete(phenotypingProduction.getPhenotypingStarted());
            phenotypingProductionDTO.setDoNotCountTowardsCompleteness(
                phenotypingProduction.getDoNotCountTowardsCompleteness());

            List<TissueDistributionCentre> tissueDistributionCentreList =
                phenotypingProductionService.getTissueDistributionCentresByPhenotypingProduction(
                    phenotypingProduction);

            List<TissueDistributionCentreDTO> tissueDistributionCentreDTOS =
                tissueDistributionCentreList.stream().map(
                    p -> {
                        TissueDistributionCentreDTO tissueDistributionCentreDTO =
                            new TissueDistributionCentreDTO();
                        tissueDistributionCentreDTO.setStartDate(p.getStartDate());
                        tissueDistributionCentreDTO.setEndDate(p.getEndDate());
                        if (p.getDistributionCentre() != null)
                        {
                            tissueDistributionCentreDTO.setCentreName(
                                p.getDistributionCentre().getName());
                        }
                        if (p.getMaterialType() != null)
                        {
                            tissueDistributionCentreDTO.setMaterialTypeName(
                                p.getMaterialType().getName());
                        }

                        return tissueDistributionCentreDTO;

                    }).collect(Collectors.toList());
            phenotypingProductionDTO.setTissueDistributionCentreDTOs(tissueDistributionCentreDTOS);

        }
        return phenotypingProductionDTO;
    }
}
