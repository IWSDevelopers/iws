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
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.util.Verifications;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchFileRequest", propOrder = { "fileId", "groupId" })
public final class FetchFileRequest extends Verifications {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;
    private static final String FIELD_FILEID = "fileId";
    private static final String FIELD_GROUPID = "groupId";

    @XmlElement(required = true)                   private String fileId = null;
    @XmlElement(required = true, nillable = true)  private String groupId = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * <p>Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.</p>
     */
    public FetchFileRequest() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * <p>Default Constructor.</p>
     *
     * @param fileId the Id of the file to read
     * @throws IllegalArgumentException if the fileId is invalid
     */
    public FetchFileRequest(final String fileId) {
        setFileId(fileId);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>The FileId is a mandatory field, and must be a proper Id, otherwise
     * the method will throw an {@code IllegalArgumentException}.</p>
     *
     * @param fileId Id of the file to fetch
     * @throws IllegalArgumentException if null or an invalid Id
     */
    public void setFileId(final String fileId) {
        ensureNotNullAndValidId(FIELD_FILEID, fileId);
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }

    /**
     * <p>Files which is attached to an IWS Object, must have the GroupId set,
     * so the IWS can ascertain if the User is permitted to read out the File.
     * The IWS will assume that the File is shared via a Folder if no GroupId
     * is present.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the given
     * GroupId is invalid.</p>
     *
     * @param groupId GroupId
     * @throws IllegalArgumentException if the given GroupId is invalid
     */
    public void setGroupId(final String groupId) {
        ensureValidId(FIELD_GROUPID, groupId);
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(1);

        isNotNull(validation, FIELD_FILEID, fileId);

        return validation;
    }
}
