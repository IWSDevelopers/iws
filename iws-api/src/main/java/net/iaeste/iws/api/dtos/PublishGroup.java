/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - AuthenticationToken
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.requests.AbstractVerification;
import java.util.List;
import java.util.Map;

/**
 * All requests (with the exception of the initial Authorization request) is
 * made with an Object if this type as the first parameter. The Token contains
 * enough information to positively identify the user, who initiated a given
 * Request.
 *
 * @author  Sondre Naustdal / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection SuppressionAnnotation, CastToConcreteClass
 */
public final class PublishGroup extends AbstractVerification {
    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;
    
    private Long studentId = null;
    private String studentName = null;
    private Group group = null;
    
    private List<Group> groupList;
    
    /** Empty Constructor, required for some communication frameworks. */
    public PublishGroup(){
    }
    
    public PublishGroup(final List<Group> groups){
        this.groupList = groups;
    }
    public PublishGroup(final PublishGroup publishGroup){
        if(publishGroup != null){
            groupList = publishGroup.groupList;
        }
    }
    

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
