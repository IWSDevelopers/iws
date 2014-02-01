/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.entities.IW3ProfilesEntity
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.entities;

import net.iaeste.iws.api.constants.IWSConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@NamedQueries({
        @NamedQuery(name = "profiles.countAll",
                query = "select count(p.profileid) from IW3ProfilesEntity p"),
        @NamedQuery(name = "profiles.findAll",
                query = "select p from IW3ProfilesEntity p " +
                        "order by p.profileid asc"),
        @NamedQuery(name = "profiles.findById",
                query = "select p from IW3ProfilesEntity p " +
                        "where p.profileid = :id " +
                        "order by p.profileid asc")
})
@Entity
@Table(name = "profiles")
public class IW3ProfilesEntity implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @Column(name = "profileid", nullable = false, length = 10)
    private Integer profileid = null;

    @OneToOne(targetEntity = IW3UsersEntity.class)
    @JoinColumn(name = "profileid", referencedColumnName = "userid", nullable = false, updatable = false)
    private IW3UsersEntity user = null;

    @Column(name = "picture", length = 250)
    private String picture = null;

    @Column(name = "locale", length = 2)
    private String locale = null;

    @Column(name = "theme", length = 25)
    private String theme = null;

    @Column(name = "fontsize", length = 10)
    private Integer fontsize = null;

    @Column(name = "username", length = 75)
    private String username = null;

    @Column(name = "password", length = 32)
    private String password = null;

    @Column(name = "checksum", length = 32)
    private String checksum = null;

    @Column(name = "discsize", length = 10)
    private Integer discsize = null;

    @Column(name = "discused", length = 10)
    private Integer discused = null;

    @Column(name = "logins", length = 10)
    private Integer logins = null;

    @Temporal(TemporalType.TIME)
    @Column(name = "onlinetime")
    private Date onlinetime = null;

    @Column(name = "mailalias", length = 100)
    private byte[] mailalias = null;

    @Column(name = "privateaddress", length = 1)
    private Boolean privateaddress = null;

    @Column(name = "privatephones", length = 1)
    private Boolean privatephones = null;

    @Column(name = "privatework", length = 1)
    private Boolean privatework = null;

    @Column(name = "status", length = 25)
    private String status = null;

    @Column(name = "listsize", length = 10)
    private Integer listsize = null;

    @Column(name = "notificationfrequency", length = 1)
    private String notificationfrequency = null;

    @Column(name = "csvdelimiter", length = 1)
    private String csvdelimiter = null;

    @Column(name = "papertype", length = 10)
    private String papertype = null;

    @Column(name = "dateformat", length = 2147483647)
    private String dateformat = null;

    @Column(name = "timeformat", length = 2147483647)
    private String timeformat = null;

    @Column(name = "timezone", length = 2147483647)
    private String timezone = null;

    @Column(name = "termsofusage", length = 10)
    private Integer termsofusage = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created = null;

    // =========================================================================
    // IW3 Entity Setters & Getters
    // =========================================================================

    public void setProfileid(final Integer profileid) {
        this.profileid = profileid;
    }

    public Integer getProfileid() {
        return profileid;
    }

    public void setUser(final IW3UsersEntity user) {
        this.user = user;
    }

    public IW3UsersEntity getUser() {
        return user;
    }

    public void setPicture(final String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setLocale(final String locale) {
        this.locale = locale;
    }

    public String getLocale() {
        return locale;
    }

    public void setTheme(final String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

    public void setFontsize(final Integer fontsize) {
        this.fontsize = fontsize;
    }

    public Integer getFontsize() {
        return fontsize;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setChecksum(final String checksum) {
        this.checksum = checksum;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setDiscsize(final Integer discsize) {
        this.discsize = discsize;
    }

    public Integer getDiscsize() {
        return discsize;
    }

    public void setDiscused(final Integer discused) {
        this.discused = discused;
    }

    public Integer getDiscused() {
        return discused;
    }

    public void setLogins(final Integer logins) {
        this.logins = logins;
    }

    public Integer getLogins() {
        return logins;
    }

    public void setOnlinetime(final Date onlinetime) {
        this.onlinetime = onlinetime;
    }

    public Date getOnlinetime() {
        return onlinetime;
    }

    public void setMailalias(final byte[] mailalias) {
        this.mailalias = mailalias;
    }

    public byte[] getMailalias() {
        return mailalias;
    }

    public void setPrivateaddress(final Boolean privateaddress) {
        this.privateaddress = privateaddress;
    }

    public Boolean getPrivateaddress() {
        return privateaddress;
    }

    public void setPrivatephones(final Boolean privatephones) {
        this.privatephones = privatephones;
    }

    public Boolean getPrivatephones() {
        return privatephones;
    }

    public void setPrivatework(final Boolean privatework) {
        this.privatework = privatework;
    }

    public Boolean getPrivatework() {
        return privatework;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setListsize(final Integer listsize) {
        this.listsize = listsize;
    }

    public Integer getListsize() {
        return listsize;
    }

    public void setNotificationfrequency(final String notificationfrequency) {
        this.notificationfrequency = notificationfrequency;
    }

    public String getNotificationfrequency() {
        return notificationfrequency;
    }

    public void setCsvdelimiter(final String csvdelimiter) {
        this.csvdelimiter = csvdelimiter;
    }

    public String getCsvdelimiter() {
        return csvdelimiter;
    }

    public void setPapertype(final String papertype) {
        this.papertype = papertype;
    }

    public String getPapertype() {
        return papertype;
    }

    public void setDateformat(final String dateformat) {
        this.dateformat = dateformat;
    }

    public String getDateformat() {
        return dateformat;
    }

    public void setTimeformat(final String timeformat) {
        this.timeformat = timeformat;
    }

    public String getTimeformat() {
        return timeformat;
    }

    public void setTimezone(final String timezone) {
        this.timezone = timezone;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTermsofusage(final Integer termsofusage) {
        this.termsofusage = termsofusage;
    }

    public Integer getTermsofusage() {
        return termsofusage;
    }

    public void setModified(final Date modified) {
        this.modified = modified;
    }

    public Date getModified() {
        return modified;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }
}
