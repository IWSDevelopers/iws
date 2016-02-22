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
import net.iaeste.iws.api.dtos.Folder;
import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.util.AbstractVerification;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "folderRequest", propOrder = { "action", "parentId", "folder" })
public final class FolderRequest extends AbstractVerification implements Actionable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** Default allowed Actions for the Folder Request. */
    private static final Set<Action> allowed = EnumSet.of(Action.PROCESS, Action.MOVE, Action.DELETE);

    /** Action to perform against the given Folder. */
    @XmlElement(required = true, nillable = false)
    private Action action = Action.PROCESS;

    /**
     * The Id of the Parent Folder, if nothing given - the Group's root folder
     * is used. This value is used when creating new Folders and when moving
     * Folders.
     */
    @XmlElement(required = true, nillable = true)
    private String parentId = null;

    /** The Folder Object to process. */
    @XmlElement(required = true, nillable = true)
    private Folder folder = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FolderRequest() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Default Constructor.
     *
     * @param folder Meta data for the folder
     */
    public FolderRequest(final Folder folder) {
        setFolder(folder);
    }

    // =========================================================================
    // Implementation of the Actionable Interface
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Action> allowedActions() {
        return allowed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAction(final Action action) throws IllegalArgumentException {
        ensureNotNullAndContains("action", action, allowed);
        this.action = action;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action getAction() {
        return action;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the File to be processed. The method will thrown an
     * {@code IllegalArgumentException} if the Object is not verifiable.
     *
     * @param folder Folder Object to be processed
     * @throws IllegalArgumentException if the File Object is not verifiable
     */
    public void setFolder(final Folder folder) throws IllegalArgumentException {
        ensureNotNullAndVerifiable("folder", folder);
        this.folder = new Folder(folder);
    }

    public Folder getFolder() {
        return new Folder(folder);
    }

    public void setParentId(final String parentId) throws IllegalArgumentException {
        ensureValidId("parentId", parentId);
        this.parentId = parentId;
    }

    public String getParentId() {
        return parentId;
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

        isNotNull(validation, "folder", folder);
        isNotNull(validation, "action", action);

        return validation;
    }
}
