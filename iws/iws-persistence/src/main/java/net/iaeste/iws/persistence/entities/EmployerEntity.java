package net.iaeste.iws.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @Getter @Setter
    @Column(name = "id")
    @Id
    private int id;

    @Getter @Setter
    @Column(name = "name")
    @Basic
    private String name;

    @Getter @Setter
    @Column(name = "address_id")
    @Basic
    private int addressId;

    @Getter @Setter
    @Column(name = "workplace")
    @Basic
    private String workplace;

    @Getter @Setter
    @Column(name = "website")
    @Basic
    private String website;

    @Getter @Setter
    @Column(name = "business")
    @Basic
    private String business;

    @Getter @Setter
    @Column(name = "responsible_person")
    @Basic
    private String responsiblePerson;

    @Getter @Setter
    @Column(name = "airport")
    @Basic
    private String airport;

    @Getter @Setter
    @Column(name = "transport")
    @Basic
    private String transport;

    @Getter @Setter
    @Column(name = "employees")
    @Basic
    private String employees;

    @Getter @Setter
    @Column(name = "modified")
    @Basic
    private Timestamp modified;

    @Getter @Setter
    @Column(name = "created")
    @Basic
    private Timestamp created;

    @Getter @Setter
    @ManyToOne
    @javax.persistence.JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private AddressEntity addresses;

    @Getter @Setter
    @OneToMany(mappedBy = "employer")
    private Collection<OfferEntity> offers;

}
