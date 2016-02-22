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

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Embeddable
public final class EmbeddedFile {

    @Column(name = "file_id", insertable = false, updatable = false)
    private Long id = null;

    @Column(name = "file_external_id", insertable = false, updatable = false)
    private String externalId = null;

    @Embedded
    @Column(name = "file_group_id", insertable = false, updatable = false)
    private EmbeddedGroup group = null;

    @Column(name = "file_name", insertable = false, updatable = false)
    private String fileName = null;

    @Column(name = "file_stored_name", insertable = false, updatable = false)
    private String storedName = null;

    @Column(name = "file_size", insertable = false, updatable = false)
    private Integer size = null;

    @Column(name = "file_mimetype", insertable = false, updatable = false)
    private String mimeType = null;

    @Column(name = "file_description", insertable = false, updatable = false)
    private String description = null;

    @Column(name = "file_keywords", insertable = false, updatable = false)
    private String keywords = null;

    @Column(name = "file_checksum", insertable = false, updatable = false)
    private Long checksum = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "file_modified", insertable = false, updatable = false)
    private Date modified = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "file_created", insertable = false, updatable = false)
    private Date created = null;

    // =========================================================================
    // View Setters & Getters
    // =========================================================================

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setStoredName(final String storedName) {
        this.storedName = storedName;
    }

    public String getStoredName() {
        return storedName;
    }

    public void setSize(final Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }

    public void setMimeType(final String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setKeywords(final String keywords) {
        this.keywords = keywords;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setChecksum(final Long checksum) {
        this.checksum = checksum;
    }

    public Long getChecksum() {
        return checksum;
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
