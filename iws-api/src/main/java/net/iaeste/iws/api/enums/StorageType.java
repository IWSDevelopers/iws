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
package net.iaeste.iws.api.enums;

import javax.xml.bind.annotation.XmlType;

/**
 * Files stored in the system can be either owned by someone or shared to
 * someone. In order for the reading of files to work properly, we need to
 * provide the correct context. Hence, this enumerated type.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 * @deprecated pending removal, as it has become obsolete
 */
@XmlType(name = "storageType")
@Deprecated
public enum StorageType implements Descriptable<StorageType> {

    /**
     * If reading a file from a public folder, then this Storage Type must be
     * used.
     */
    FOLDER("Folder"),

    /**
     * Attached to an Offer, meaning that the rule that applies is that the
     * provided File Id must be belong to an attachment, which the retrieving
     * person has access to.
     */
    ATTACHED_TO_APPLICATION("Student Application Attachment");

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;

    StorageType(final String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }
}
