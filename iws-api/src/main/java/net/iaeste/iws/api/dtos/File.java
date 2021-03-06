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
package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.api.util.Verifications;
import net.iaeste.iws.api.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "file", propOrder = { "fileId", "privacy", "group", "user", "folderId", "filename", "filedata", "filesize", "mimetype", "description", "keywords", "checksum", "modified", "created" })
public final class File extends Verifications {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true) private String fileId = null;
    @XmlElement(required = true)                  private Privacy privacy = Privacy.PROTECTED;
    @XmlElement(required = true, nillable = true) private Group group = null;
    @XmlElement(required = true, nillable = true) private User user = null;
    @XmlElement(required = true, nillable = true) private String folderId = null;
    @XmlElement(required = true)                  private String filename = null;
    @XmlElement(required = true, nillable = true) private byte[] filedata = null;
    @XmlElement(required = true, nillable = true) private Integer filesize = null;
    @XmlElement(required = true, nillable = true) private String mimetype = null;
    @XmlElement(required = true, nillable = true) private String description = null;
    @XmlElement(required = true, nillable = true) private String keywords = null;
    @XmlElement(required = true, nillable = true) private Long checksum = null;
    @XmlElement(required = true, nillable = true) private Date modified = new Date();
    @XmlElement(required = true, nillable = true) private Date created = new Date();

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public File() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Copy Constructor.
     *
     * @param file File Object to copy
     */
    public File(final File file) {
        if (file != null) {
            fileId = file.fileId;
            privacy = file.privacy;
            group = new Group(file.group);
            user = new User(file.user);
            folderId = file.folderId;
            filename = file.filename;
            filedata = file.filedata;
            filesize = file.filesize;
            mimetype = file.mimetype;
            description = file.description;
            keywords = file.keywords;
            checksum = file.checksum;
            modified = file.modified;
            created = file.created;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the File Id for this File Object. The Id is generated by the IWS
     * upon saving the File.</p>
     *
     * <p>Note, that if the value is invalid, then the method will thrown an
     * {@code IllegalArgumentException}.</p>
     *
     * @param fileId File Id
     * @throws IllegalArgumentException if the File Id is invalid
     */
    public void setFileId(final String fileId) {
        ensureValidId("fileId", fileId);
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }

    /**
     * <p>Sets the files privacy level. Files marked Public can be read by
     * anyone but only processed by the owning Group, files marked Protected can
     * only be viewed or processed by by the owning Group, and files marked
     * Private can be viewed by the Group, but only processed by the owning
     * User.</p>
     *
     * <p>Note, that if the privacy information must be set, if set to null, the
     * method will throw an {@code IllegalArgumentException}.</p>
     *
     * @param privacy The File's privacy setting
     */
    public void setPrivacy(final Privacy privacy) {
        ensureNotNull("privacy", privacy);
        this.privacy = privacy;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    /**
     * <p>The owning group for this file. All file management is handled via the
     * Group, and this this is a mandatory field.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the Group
     * is either null or invalid.</p>
     *
     * @param group Group
     * @throws IllegalArgumentException if null or not verifiable
     */
    public void setGroup(final Group group) {
        ensureNotNullAndVerifiable("group", group);
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    /**
     * Sets the User who uploaded the file. The field is mandatory, which means
     * that the method will thrown an {@code IllegalArgumentException} if the
     * value is either null or not verifiable.
     *
     * @param user User who uploaded the File
     * @throws IllegalArgumentException if null or not verifiable
     */
    public void setUser(final User user) {
        ensureNotNullAndVerifiable("user", user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    /**
     * <p>Sets the FolderId for the File, i.e. the location where the file can
     * be viewed from.</p>
     *
     * <p>If set, then the folder must be a valid folder, otherwise the method
     * will throw an {@code IllegalArgumentException}.</p>
     *
     * @param folderId FolderId for the File
     */
    public void setFolderId(final String folderId) {
        ensureValidId("folderId", folderId);
        this.folderId = folderId;
    }

    public String getFolderId() {
        return folderId;
    }

    /**
     * The fileName is mandatory in the IWS, and if not set or too long, the
     * method will throw an {@code IllegalArgumentException}.
     *
     * @param filename Name of the File
     * @throws IllegalArgumentException if null, empty or too long
     */
    public void setFilename(final String filename) {
        ensureNotNullOrEmptyOrTooLong("filename", filename, 100);
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    /**
     * Sets the data for the File Object. The data is read as a raw byte array,
     * and stored as such internally. However, the byte array may not exceed
     * 25MB, if it exceeds this length, then the method will thrown an
     * {@code IllegalArgumentException}.
     *
     * @param filedata Raw file data
     * @throws IllegalArgumentException if the byte array is too long
     */
    public void setFiledata(final byte[] filedata) {
        ensureNotTooLong("filedata", filedata, 26214400);
        this.filedata = Arrays.copyOf(filedata, filedata.length);
    }

    public byte[] getFiledata() {
        return Arrays.copyOf(filedata, filedata.length);
    }

    /**
     * The filesize is set by the IWS internally, and simply matches the size of
     * the file data upon creation.
     *
     * @param filesize Size of the File
     */
    public void setFilesize(final Integer filesize) {
        this.filesize = filesize;
    }

    public Integer getFilesize() {
        return filesize;
    }

    /**
     * <p>The file MIME Type, which is used for Clients to determine what
     * Application should be used to handle the file.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the
     * MIME Type is too long.</p>
     *
     * @param mimetype File MIME Type
     * @throws IllegalArgumentException if the MIME Type value exceeds 50 characters
     */
    public void setMimetype(final String mimetype) {
        ensureNotTooLong("mimetype", mimetype, 50);
        this.mimetype = mimetype;
    }

    public String getMimetype() {
        return mimetype;
    }

    /**
     * <p>Sets the description of the File, the Description can be anything as long
     * as the length does not exceed the maximum allowed length of 250
     * characters.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the length
     * of the description exceeds 250 characters.</p>
     *
     * @param description File Description
     * @throws IllegalArgumentException if the length exceeds 250 characters
     */
    public void setDescription(final String description) {
        ensureNotTooLong("description", description, 250);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * <p>Sets the Keywords for the File, meaning a list of words used for
     * indexing the file. The Keywords are not used by the IWS, but is purely
     * for client side usage, and is thus an optional value.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the
     * length of the Keywords exceeds 250 characters.</p>
     *
     * @param keywords File Keywords
     * @throws IllegalArgumentException if the length exceeds 250 characters.
     */
    public void setKeywords(final String keywords) {
        ensureNotTooLong("keywords", keywords, 250);
        this.keywords = keywords;
    }

    public String getKeywords() {
        return keywords;
    }

    /**
     * The File Checksum, used for validation of the file, to ensure that the
     * data is not corrupted. The value is set by the IWS upon saving the File.
     *
     * @param checksum File Checksum
     */
    public void setChecksum(final Long checksum) {
        this.checksum = checksum;
    }

    public Long getChecksum() {
        return checksum;
    }

    /**
     * Sets the last time the file was modified. Note, that this value is
     * controlled by the IWS, so any changes to it will be ignored.
     *
     * @param modified Date of last modification
     */
    public void setModified(final Date modified) {
        this.modified = modified;
    }

    public Date getModified() {
        return modified;
    }

    /**
     * Sets the time the file was created. Note, that this value is controlled
     * by the IWS, so any changes to it will be ignored.
     *
     * @param created Date of creation
     */
    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "filename", filename);
        isNotNull(validation, "privacy", privacy);

        return validation;
    }
}
