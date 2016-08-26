/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.persistence.entities;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.UserStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "userMailinglist.findSubscription",
                query = "select u " +
                        "from UserMailinglistEntity u " +
                        "where u.mailinglist = :mailinglist" +
                        "  and u.userGroup.id = :subscriber"),
        @NamedQuery(name = "userMailinglist.findChangedSubscribers",
                query = "select u " +
                        "from UserMailinglistEntity u " +
                        "where u.member != u.userGroup.user.username"),
        @NamedQuery(name = "userMailinglist.findUnprocessedSubscriptions",
                query = "select u from UserGroupEntity u " +
                        "where u.user.status = 'ACTIVE'" +
                        "  and u.id not in (" +
                        "    select s.userGroup.id" +
                        "    from UserMailinglistEntity s)"),
        @NamedQuery(name = "userMailinglist.findMissingNcsSubscribers",
                query = "select ug " +
                        "from UserGroupEntity ug " +
                        "where ug.group.status = :groupStatus" +
                        "  and ug.user.status = :userStatus" +
                        "  and ug.onPublicList = true" +
                        "  and ug.group.groupType.grouptype in (:types)" +
                        "  and ug not in (" +
                        "    select um.userGroup" +
                        "    from UserMailinglistEntity um" +
                        "    where um.mailinglist.listAddress = :address)"),
        @NamedQuery(name = "userMailinglist.findMissingAnnounceSubscribers",
                query = "select ug " +
                        "from UserGroupEntity ug " +
                        "where ug.group.groupType.grouptype = :type" +
                        "  and ug.user.status = :status" +
                        "  and ug not in (" +
                        "    select um.userGroup" +
                        "    from UserMailinglistEntity um" +
                        "    where um.mailinglist.listAddress = :address)"),
        @NamedQuery(name = "userMailinglist.deleteSubscriber",
                query = "delete from UserMailinglistEntity " +
                        "where mailinglist.id = :mailinglist"),
        @NamedQuery(name = "userMailinglist.updatePrivateSubscriptions",
                query = "update UserMailinglistEntity set" +
                        "   status = :status," +
                        "   modified = current_timestamp " +
                        "where userGroup.id in (" +
                        "    select s.userGroup.id" +
                        "    from UserMailinglistEntity s" +
                        "    where s.mailinglist.listType = 'PRIVATE_LIST'" +
                        "      and s.status != :status" +
                        "      and (s.userGroup.user.status = :status" +
                        "        and s.userGroup.onPrivateList = :onList))"),
        @NamedQuery(name = "userMailinglist.updatePublicSubscriptions",
                query = "update UserMailinglistEntity set" +
                        "   status = :status," +
                        "   modified = current_timestamp " +
                        "where userGroup.id in (" +
                        "    select s.userGroup.id" +
                        "    from UserMailinglistEntity s" +
                        "    where s.mailinglist.listType != 'PRIVATE_LIST'" +
                        "      and s.status != :status" +
                        "      and (s.userGroup.user.status = :status" +
                        "        and s.userGroup.onPublicList = :onList))"),
        @NamedQuery(name = "userMailinglist.updateWritePermission",
                query = "update UserMailinglistEntity set" +
                        "   mayWrite = :mayWrite," +
                        "   modified = current_timestamp " +
                        "where userGroup.id in (" +
                        "    select s.userGroup.id" +
                        "    from UserMailinglistEntity s" +
                        "    where s.mailinglist.listType = 'PRIVATE_LIST'" +
                        "      and s.mayWrite != :mayWrite" +
                        "      and s.userGroup.writeToPrivateList = :mayWrite)"),
        @NamedQuery(name = "userMailinglist.findDeprecatedNcsSubscribers",
                query = "select ug " +
                        "from UserGroupEntity ug " +
                        "where (ug.group.status != :groupStatus" +
                        "  or ug.user.status != :userStatus" +
                        "  or ug.onPublicList = false)" +
                        "  and ug in (" +
                        "    select um.userGroup" +
                        "    from UserMailinglistEntity um" +
                        "    where um.mailinglist.listAddress = :address)"),
        @NamedQuery(name = "userMailinglist.deleteDeprecatedSubscribers",
                query = "delete from UserMailinglistEntity " +
                        "where userGroup.id in (" +
                        "    select s.id" +
                        "    from UserGroupEntity s" +
                        "    where s.user.status = 'DELETED')")
})
@Table(name = "user_to_mailing_list")
public final class UserMailinglistEntity extends AbstractUpdateable<UserMailinglistEntity> {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @ManyToOne(targetEntity = MailinglistEntity.class)
    @JoinColumn(name = "mailing_list_id", referencedColumnName = "id", nullable = false, updatable = false)
    private MailinglistEntity mailinglist = null;

    @ManyToOne(targetEntity = UserGroupEntity.class)
    @JoinColumn(name = "user_to_group_id", referencedColumnName = "id", nullable = false, updatable = false)
    private UserGroupEntity userGroup = null;

    @Column(name = "member", length = 100, nullable = false)
    private String member = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 25, nullable = false)
    private UserStatus status = UserStatus.ACTIVE;

    @Column(name = "may_write", nullable = false)
    private Boolean mayWrite = true;

    /**
     * Last time the Entity was modified.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified", nullable = false)
    private Date modified = new Date();

    /**
     * Timestamp when the Entity was created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, updatable = false)
    private Date created = new Date();

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return id;
    }

    public void setMailinglist(final MailinglistEntity mailinglist) {
        this.mailinglist = mailinglist;
    }

    public MailinglistEntity getMailinglist() {
        return mailinglist;
    }

    public void setUserGroup(final UserGroupEntity userGroup) {
        this.userGroup = userGroup;
    }

    public UserGroupEntity getUserGroup() {
        return userGroup;
    }

    public void setMember(final String member) {
        this.member = member;
    }

    public String getMember() {
        return member;
    }

    public void setStatus(final UserStatus status) {
        this.status = status;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setMayWrite(final Boolean mayWrite) {
        this.mayWrite = mayWrite;
    }

    public Boolean getMayWrite() {
        return mayWrite;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModified(final Date modified) {
        this.modified = modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getModified() {
        return modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCreated(final Date created) {
        this.created = created;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getCreated() {
        return created;
    }

    // =========================================================================
    // Entity Standard Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean diff(final UserMailinglistEntity obj) {
        boolean differs = false;

        if (!Objects.equals(member, obj.member)) {
            // We have different target e-mail addresses
            differs = true;
        }

        if (!Objects.equals(status, obj.status)) {
            // We have different status, i.e. user has gone from new to
            // activate, suspended. Deleted should not be checked here,
            // since it should instead result in the record being
            // completely deleted.
            differs = true;
        }

        if (!Objects.equals(mayWrite, obj.mayWrite)) {
            // If the list is a private mailing list, then it may be that the
            // User is not allowed to write to it.
            differs = true;
        }

        return differs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final UserMailinglistEntity obj) {
        if (canMerge(obj)) {
            // The member or the target e-mail address for a user, i.e. the
            // username for the User
            member = obj.member;

            // We will update the status with the status of the User. This way,
            // if a User is suspended or activated, the list will reflect this
            status = obj.status;

            // If it is a private List, then this field may also be set, for
            // public lists, it is ignored
            mayWrite = obj.mayWrite;
        }
    }
}
