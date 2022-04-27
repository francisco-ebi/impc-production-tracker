package org.gentar.biology.targ_rep.genbank_file;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gentar.BaseEntity;


/**
 * TargRepGenbankFile.
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@Data
@Entity
public class TargRepGenbankFile extends BaseEntity {
    @Id
    @SequenceGenerator(name = "targRepGenbankFileSeq", sequenceName = "TARG_REP_GENBANK_FILE_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "targRepGenbankFileSeq")
    private Long id;

    private String host;

    private String type;

    private String genbankFilePath;

    private String alleleImagePath;

    private String cassetteImagePath;

    private String simpleImagePath;

    private String vectorImagePath;

}
