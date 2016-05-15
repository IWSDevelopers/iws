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
import net.iaeste.iws.api.dtos.File;
import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.enums.StorageType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fileRequest", propOrder = { "file", "type" })
public final class FileRequest extends Actions {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** <p>The File Object to process.</p> */
    @XmlElement(required = true)
    private File file = null;

    /**
     * <p>he IWS supports different ways of working with Files. By default, all
     * files are stored as private files, where the type is explicitly set to
     * "Owner". To read files in different ways, i.e. if the file is an
     * attachment, other internal criteria's is used to determine if the user is
     * allowed, hence - it is important that the type is set accordingly.</p>
     */
    @XmlElement(required = true)
    private StorageType type = StorageType.OWNER;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * <p>Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.</p>
     */
    public FileRequest() {
        super(EnumSet.of(Action.PROCESS, Action.DELETE), Action.PROCESS);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the File to be processed. The method will thrown an
     * {@code IllegalArgumentException} if the Object is not verifiable.</p>
     *
     * @param file File Object to be processed
     * @throws IllegalArgumentException if the File Object is not verifiable
     */
    public void setFile(final File file) {
        ensureVerifiable("file", file);
        this.file = new File(file);
    }

    public File getFile() {
        return new File(file);
    }

    /**
     * <p>Sets the type of Storage that is requested. Most commonly, files are
     * retrieved by their owners (default), but a file can also be attached to
     * other Objects, which means that the system needs to be informed, if the
     * fetching should be for an attached offer.</p>
     *
     * <p>The field is mandatory, since it is used to determine which mechanism
     * is needed for the lookup, hence it must be defined. If set to null, the
     * method will throw an {@code IllegalArgumentException}.</p>
     *
     * @param type Storage Type
     * @throws IllegalArgumentException if the Storage Type is null
     */
    public void setType(final StorageType type) {
        ensureNotNull("type", type);
        this.type = type;
    }

    public StorageType getType() {
        return type;
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

        isNotNull(validation, "action", action);
        isNotNull(validation, "file", file);
        isNotNull(validation, "type", type);

        return validation;
    }
}
