/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.EmployerEntity
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

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Collection;


/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 24.07.12
 * Time: 00:35
 */
@Table(name = "employers", schema = "iws", catalog = "")
@Entity
public class EmployerEntity {

    @Column(name = "id")
    @Id
    private int id;

    @Column(name = "name")
    @Basic
    private String name;

    @Column(name = "address_id")
    @Basic
    private int addressId;

    @Column(name = "workplace")
    @Basic
    private String workplace;

    @Column(name = "website")
    @Basic
    private String website;

    @Column(name = "business")
    @Basic
    private String business;

    @Column(name = "responsible_person")
    @Basic
    private String responsiblePerson;

    @Column(name = "airport")
    @Basic
    private String airport;

    @Column(name = "employees")
    @Basic
    private String employees;

    @Column(name = "transport")
    @Basic
    private String transport;

    @Column(name = "modified")
    @Basic
    private Timestamp modified;

    @Column(name = "created")
    @Basic
    private Timestamp created;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private AddressEntity addresses;

    @OneToMany(mappedBy = "employer")
    private Collection<OfferEntity> offers;

}
