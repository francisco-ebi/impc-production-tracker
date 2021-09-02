/******************************************************************************
 Copyright 2019 EMBL - European Bioinformatics Institute

 Licensed under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License. You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 either express or implied. See the License for the specific
 language governing permissions and limitations under the
 License.
 */
package org.gentar.biology.plan.attempt.crispr;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@RequiredArgsConstructor
public class CrisprAttemptDTO
{
    @JsonIgnore
    private Long crisprAttemptId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("imitsMiAttemptId")
    private Long imitsMiAttempt;

    private LocalDate miDate;

    @JsonProperty("attemptExternalRef")
    private String miExternalRef;

    private Boolean experimental;

    private String comment;

    private String mutagenesisExternalRef;

    @JsonProperty("embryoTransferDay")
    private String embryoTransferDay;

    @JsonProperty("totalTransferred")
    private Integer totalTransferred;

    @JsonProperty("nucleases")
    private List<NucleaseDTO> nucleaseDTOS;

    @JsonProperty("guides")
    private List<GuideDTO> guideDTOS;

    @JsonProperty("mutagenesisDonors")
    private List<MutagenesisDonorDTO> mutagenesisDonorDTOS;

    @JsonProperty("reagents")
    private List<CrisprAttemptReagentDTO> crisprAttemptReagentDTOS;

    @JsonProperty("genotypePrimers")
    private List<GenotypePrimerDTO> genotypePrimerDTOS;

    @JsonProperty("totalEmbryosInjected")
    private Integer totalEmbryosInjected;

    @JsonProperty("totalEmbryosSurvived")
    private Integer totalEmbryosSurvived;

    @JsonProperty("embryo2Cell")
    private String embryo2Cell;

    @JsonProperty("assay")
    private AssayDTO assay;

    @JsonProperty("strainInjectedName")
    private String strainName;
}
