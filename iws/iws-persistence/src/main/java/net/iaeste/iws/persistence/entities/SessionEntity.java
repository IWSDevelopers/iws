/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.SessionEntity
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @noinspection AssignmentToDateFieldFromParameter
 * @since 1.7
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "findAllSessions",
                query = "SELECT s FROM SessionEntity s"),
        @NamedQuery(name = "findActiveSession",
                query = "select s from SessionEntity s where sessionKey = :key")
})
@Table(name = "sessions")
public class SessionEntity {
    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter @Setter
    @Basic
    @Column(nullable = false, name = "session_key")
    private String sessionKey;

    @Getter @Setter
    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;

    @Getter @Setter
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public SessionEntity() {
        id = null;
        sessionKey = null;
        user = null;
        created = new Date();
    }

    public SessionEntity(final Long id, final String sessionKey) {
        this.id = id;
        this.sessionKey = sessionKey;
        user = null;
        created = null;
    }
}
