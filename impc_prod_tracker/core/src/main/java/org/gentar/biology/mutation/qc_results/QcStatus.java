package org.gentar.biology.mutation.qc_results;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gentar.BaseEntity;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@Data
@Entity
public class QcStatus extends BaseEntity
{
    @Id
    @SequenceGenerator(name = "qcStatusSeq", sequenceName = "QC_STATUS_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qcStatusSeq")
    private Long id;

    @NotNull
    private String name;

}
