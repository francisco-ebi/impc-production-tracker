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
package uk.ac.ebi.impc_prod_tracker.data.organization.work_group;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.ac.ebi.impc_prod_tracker.data.BaseEntity;
import uk.ac.ebi.impc_prod_tracker.data.organization.consortium.Consortium;
import uk.ac.ebi.impc_prod_tracker.data.organization.funder.Funder;
import uk.ac.ebi.impc_prod_tracker.data.organization.work_unit.WorkUnit;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import java.util.Set;

@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@Getter
@Setter
@Entity
public class WorkGroup extends BaseEntity
{
    @Id
    @SequenceGenerator(name = "workGroupSeq", sequenceName = "WORK_GROUP_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workGroupSeq")
    private Long id;

    private String name;

    private String description;

    @ManyToMany(mappedBy = "workGroups")
    private Set<Consortium> consortia;

    @ManyToMany
    @JoinTable(
        name = "work_group_unit",
        joinColumns = @JoinColumn(name = "work_group_id"),
        inverseJoinColumns = @JoinColumn(name = "work_unit_id"))
    private Set<WorkUnit> workUnits;

    @ManyToMany(mappedBy = "workGroups")
    private Set<Funder> funders;
}