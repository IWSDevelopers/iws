/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.DeleteGroup
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.Person;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.fitnesse.callers.AdministrationCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection RefusedBequest
 */
public final class DeleteGroup extends AbstractFixture<Fallible> {

    private final Administration administration = new AdministrationCaller();
    private GroupRequest request = new GroupRequest();

    private final User user = new User();
    private final Group group = new Group();

    public void setUserId(final String userId) {
        user.setUserId(userId);
    }

    @Override
    public void setUsername(final String username) {
        user.setUsername(username);
    }

    public void setAlias(final String alias) {
        user.setAlias(alias);
    }

    public void setFirstname(final String firstname) {
        user.setFirstname(firstname);
    }

    public void setLastname(final String lastname) {
        user.setLastname(lastname);
    }

    public void setStatus(final String status) {
        user.setStatus(UserStatus.valueOf(status));
    }

    public void setPrivacy(final String privacy) {
        user.setPrivacy(Privacy.valueOf(privacy));
    }

    public void setAlternateEmail(final String alternateEmail) {
        final Person person = new Person();
        person.setAlternateEmail(alternateEmail);
        user.setPerson(person);
    }

    public void setGroupId(final String groupId) {
        group.setGroupId(groupId);
    }

    public void setGroupName(final String groupName) {
        group.setGroupName(groupName);
    }

    public void setGroupType(final GroupType groupType) {
        group.setGroupType(groupType);
    }

    public void setDescription(final String description) {
        group.setDescription(description);
    }

//    public void setCountryId(final String countryId) {
//        group.setCountryId(countryId);
//    }

    public void deleteGroup() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        request.setUser(user);
        request.setGroup(group);
        setResponse(administration.deleteSubGroup(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
