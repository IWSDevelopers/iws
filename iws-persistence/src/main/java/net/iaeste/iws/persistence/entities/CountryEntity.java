package net.iaeste.iws.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Collection;


/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@Table(name = "countries", schema = "iws", catalog = "")
//@Entity
public class CountryEntity {

    @Column(name = "id")
    @Id
    private int id;

    @Column(name = "country_code")
    @javax.persistence.Basic
    private String countryCode;

    @Column(name = "country_name")
    @javax.persistence.Basic
    private String countryName;


    @Column(name = "country_fullname")
    @javax.persistence.Basic
    private String countryFullname;


    @Column(name = "country_native")
    @javax.persistence.Basic
    private String countryNative;


    @Column(name = "nationality")
    @javax.persistence.Basic
    private String nationality;


    @Column(name = "citizens")
    @javax.persistence.Basic
    private String citizens;


    @Column(name = "phone_code")
    @javax.persistence.Basic
    private String phoneCode;


    @Column(name = "currency")
    @javax.persistence.Basic
    private String currency;


    @Column(name = "languages")
    @javax.persistence.Basic
    private String anguages;


    @Column(name = "membership")
    @javax.persistence.Basic
    private int membership;


    @Column(name = "membership_since")
    @javax.persistence.Basic
    private int membershipSince;


    @Column(name = "modified")
    @javax.persistence.Basic
    private Timestamp modified;


    @Column(name = "created")
    @javax.persistence.Basic
    private Timestamp created;

    @javax.persistence.OneToMany(mappedBy = "country")
    private Collection<AddressEntity> addresses;

    @javax.persistence.OneToMany(mappedBy = "country")
    private Collection<OfferEntity> offers;

}
