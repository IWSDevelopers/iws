/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.transformers.ViewTransformer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.transformers;

import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.persistence.views.EmployerView;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class ViewTransformer {

    private ViewTransformer() {
    }

    /**
     * The result of Views are always a full Object. However, since all fields
     * are listed in the same Object, it is not possible to use the Entity
     * transformations for this one.
     *
     * @param view Employer View to transform
     * @return Employer DTO Object Tree
     */
    public static Employer transform(final EmployerView view) {
        final Employer employer = new Employer();

        // First, read out the the common Employer fields
        employer.setEmployerId(view.getEmployer().getExternalId());
        employer.setName(view.getEmployer().getName());
        employer.setDepartment(view.getEmployer().getDepartment());
        employer.setBusiness(view.getEmployer().getBusiness());
        employer.setEmployeesCount(view.getEmployer().getNumberOfEmployees());
        employer.setWebsite(view.getEmployer().getWebsite());
        employer.setWorkingPlace(view.getEmployer().getWorkingPlace());
        employer.setCanteen(view.getEmployer().getCanteen());
        employer.setNearestAirport(view.getEmployer().getNearestAirport());
        employer.setNearestPublicTransport(view.getEmployer().getNearestPublicTransport());
        employer.setWeeklyHours(view.getEmployer().getWeeklyHours());
        employer.setDailyHours(view.getEmployer().getDailyHours());

        // Second, read out the Group of the Employer
        final Group group = new Group();
        group.setGroupId(view.getGroup().getExternalId());
        group.setGroupName(view.getGroup().getGroupName());
        group.setGroupType(view.getGroup().getGroupType());
        employer.setGroup(group);

        // Third, read out the Address of the Employer
        final Address address = new Address();
        address.setStreet1(view.getAddress().getStreet1());
        address.setStreet2(view.getAddress().getStreet2());
        address.setZip(view.getAddress().getZip());
        address.setCity(view.getAddress().getCity());
        address.setState(view.getAddress().getState());
        employer.setAddress(address);

        // Finally, return our newly found Employer
        return employer;
    }
}
