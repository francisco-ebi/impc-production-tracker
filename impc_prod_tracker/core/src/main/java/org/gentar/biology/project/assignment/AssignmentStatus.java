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
package org.gentar.biology.project.assignment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gentar.BaseEntity;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
@AllArgsConstructor
@Data
@Entity
public class AssignmentStatus extends BaseEntity
{
    @Id
    @SequenceGenerator(name = "assignmentStatusSeq", sequenceName = "ASSIGNMENT_STATUS_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assignmentStatusSeq")
    private Long id;

    private String name;

    private String description;

    @NotNull
    @Column(columnDefinition = "bigint default 0")
    private Integer ordering;
}
