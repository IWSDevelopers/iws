package net.iaeste.iws.persistence.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 24.07.12
 * Time: 00:35
 */
@Table(name = "study_fields", schema = "iws", catalog = "")
@Entity
public class StudyFieldEntity {
    @Getter
    @Setter
    @Column(name = "id")
    @Id
    private int id;

    @Getter @Setter
    @Column(name = "modified")
    @Basic
    private Timestamp modified;

    @Getter @Setter
    @Column(name = "created")
    @Basic
    private Timestamp created;

}
