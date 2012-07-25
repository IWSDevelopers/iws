package net.iaeste.iws.persistence.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "addresses", schema = "iws", catalog = "")
@Entity
public class AddressEntity {
    @Column(name = "id")
    @Id
    private int id;


    @Column(name = "street1")
    @Basic
    private String street1;


    @Column(name = "street2")
    @Basic
    private String street2;


    @Column(name = "zip")
    @Basic
    private String zip;


    @Column(name = "city")
    @Basic
    private String city;


    @Column(name = "country_id")
    @Basic
    private int countryId;


    @Column(name = "modified")
    @Basic
    private Timestamp modified;


    @Column(name = "created")
    @Basic
    private Timestamp created;


    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private CountryEntity country;


    @OneToMany(mappedBy = "addressesByAddressId")
    private Collection<EmployerEntity> employersesById;

}
