/*
 * =============================================================================
 *  Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 *  -----------------------------------------------------------------------------
 *  Project: IntraWeb Services (iws-persistence)
 *  -----------------------------------------------------------------------------
 *  This software is provided by the members of the IAESTE Internet Development
 *  Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 *  redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 *  This software is provided "as is"; the IDT or individuals within the IDT
 *  cannot be held legally responsible for any problems the software may cause.
 *  =============================================================================
 */

package net.iaeste.iws.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Teis Lindemark / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@Entity
@Table(name = "studentfiles")
public class StudentFileEntity {
    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private StudentEntity student;

    //TODO: Still working on this one...
}
