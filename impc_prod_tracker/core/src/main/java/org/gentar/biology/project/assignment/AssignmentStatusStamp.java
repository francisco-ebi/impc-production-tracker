package org.gentar.biology.project.assignment;

import lombok.*;
import org.gentar.BaseEntity;
import org.gentar.biology.project.Project;
import org.gentar.biology.status.StatusStamp;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
@Data
@Entity
public class AssignmentStatusStamp extends BaseEntity implements Serializable, StatusStamp
{
    @Id
    @SequenceGenerator(name = "assignmentStatusStampSeq", sequenceName = "ASSIGNMENT_STATUS_STAMP_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assignmentStatusStampSeq")
    private Long id;

    @NotNull
    @ManyToOne(targetEntity = Project.class)
    private Project project;

    @NotNull
    @ManyToOne(targetEntity = AssignmentStatus.class)
    private AssignmentStatus assignmentStatus;

    @NotNull
    private LocalDateTime date;

    @Override
    public String getStatusName()
    {
        return assignmentStatus.getName();
    }
}
