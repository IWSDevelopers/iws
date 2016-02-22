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

import net.iaeste.iws.api.enums.exchange.OfferState;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Embeddable
public final class EmbeddedOfferGroup {

    @Column(name = "shared_external_id", insertable = false, updatable = false)
    private String externalId = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "shared_status", insertable = false, updatable = false)
    private OfferState status = null;

    @Column(name = "shared_comment", insertable = false, updatable = false)
    private String sharedComment = null;

    @Column(name = "shared_hidden", insertable = false, updatable = false)
    private Boolean hidden = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "shared_modified", insertable = false, updatable = false)
    private Date modified = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "shared_created", insertable = false, updatable = false)
    private Date created = null;

    // =========================================================================
    // View Setters & Getters
    // =========================================================================

    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setStatus(final OfferState status) {
        this.status = status;
    }

    public OfferState getStatus() {
        return status;
    }

    public void setSharedComment(final String exchangeYear) {
        this.sharedComment = exchangeYear;
    }

    public String getSharedComment() {
        return sharedComment;
    }

    public void setHidden(final Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean getHidden() {
        return hidden;
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
