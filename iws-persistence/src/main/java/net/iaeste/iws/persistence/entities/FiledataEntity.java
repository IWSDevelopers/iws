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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Entity which holds the file data. This entity is referencing the file,
 * rather than the other way around, as we this way will by default never read
 * out any data, which may be resource demanding, instead we use this Entity to
 * retrieve data once we have verified that the User is allowed.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@Entity
@Table(name = "filedata")
public final class FiledataEntity implements IWSEntity {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @ManyToOne(targetEntity = FileEntity.class)
    @JoinColumn(name = "file_id", referencedColumnName = "id", nullable = false, updatable = false)
    private FileEntity file = null;

    @Column(name = "file_data", updatable = false)
    private byte[] fileData = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
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

    public void setFile(final FileEntity file) {
        this.file = file;
    }

    public FileEntity getFile() {
        return file;
    }

    public void setFileData(final byte[] fileData) {
        this.fileData = fileData;
    }

    public byte[] getFileData() {
        return fileData;
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
}
