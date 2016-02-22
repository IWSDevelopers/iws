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
package net.iaeste.iws.persistence.views;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.exchange.OfferState;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

/**
 * Internal Class, used as Embedded Id, it contains the combined Primary
 * Key for the View. This is required, since Views normally doesn't have a
 * single column, which can act as the primary key by itself. Hence, for
 * the UserPermission View, the combined UserId, GroupId & PermissionId
 * makes up the Primary Key.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Embeddable
public final class ForeignOfferStatisticsId implements Serializable {

    /** {@see IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, insertable = false, updatable = false)
    private OfferState status = null;

    @Column(name = "group_id", nullable = false, insertable = false, updatable = false)
    private Long groupId = null;

    /**
     * Default Empty JPA Constructor.
     */
    public ForeignOfferStatisticsId() {
        status = null;
        groupId = null;
    }

    public void setStatus(final OfferState status) {
        this.status = status;
    }

    public OfferState getStatus() {
        return status;
    }

    public void setGroupId(final Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
    }

    // =========================================================================
    // Methods required by JPA
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ForeignOfferStatisticsId)) {
            return false;
        }

        final ForeignOfferStatisticsId that = (ForeignOfferStatisticsId) obj;

        if ((groupId != null) ? !groupId.equals(that.groupId) : (that.groupId != null)) {
            return false;
        }

        return status == that.status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = (status != null) ? status.hashCode() : 0;

        result = (IWSConstants.HASHCODE_MULTIPLIER * result) + ((groupId != null) ? groupId.hashCode() : 0);

        return result;
    }
}
