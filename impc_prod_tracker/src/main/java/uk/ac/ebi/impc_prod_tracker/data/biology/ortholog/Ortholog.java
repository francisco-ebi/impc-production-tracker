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
package uk.ac.ebi.impc_prod_tracker.data.biology.ortholog;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.ac.ebi.impc_prod_tracker.data.biology.human_gene.HumanGene;
import uk.ac.ebi.impc_prod_tracker.data.biology.mouse_gene.MouseGene;
import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@Data
@Entity
@IdClass(Ortholog.class)
public class Ortholog implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn
    private MouseGene mouseGene;

    @Id
    @ManyToOne
    @JoinColumn
    private HumanGene humanGene;

    private String support;

    private Long supportCount;
}