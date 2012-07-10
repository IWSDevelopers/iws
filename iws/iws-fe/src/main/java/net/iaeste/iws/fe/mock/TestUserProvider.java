/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.mock.TestUserProvider
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fe.mock;

import net.iaeste.iws.api.data.Authorization;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Permission;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.*;

/**
 * Provides the Application with dummy users
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@Named
@ApplicationScoped
public class TestUserProvider {

    /* Map<Username,Password> */
    private Map<String, String> users = new HashMap<>();

    private Map<String, List<Authorization>> permissions = new HashMap<>();

    public TestUserProvider() {
        initUsers();
        initPermissions();
        // TODO add a properties file for the test data  and provide better test data
    }

    private void initUsers() {
        users.put("guest@iaeste.org", "guest");
        users.put("member@iaeste.org", "member");
        users.put("nase@iaeste.org", "nase");
        users.put("gs@iaeste.org", "gs");
    }

    private void initPermissions() {
        permissions.put("guest@iaeste.org", Arrays.asList(new Authorization(Permission.AUTHENTICATE, GroupType.MEMBERS)));
        permissions.put("member@iaeste.org", Arrays.asList(new Authorization(Permission.AUTHENTICATE, GroupType.MEMBERS)));
        permissions.put("nase@iaeste.org", Arrays.asList(new Authorization(Permission.AUTHENTICATE, GroupType.MEMBERS)));
        permissions.put("gs@iaeste.org", Arrays.asList(new Authorization(Permission.AUTHENTICATE, GroupType.MEMBERS)));
    }

    public Set<String> getUserNames() {
        return users.keySet();
    }

    public List<String> getUserNameList() {
        return new ArrayList<>(users.keySet());
    }

    public String getPassword(String username) {
        return users.get(username);
    }

    public List<Authorization> getUserAuthorizations(String username) {
        return permissions.get(username);
    }
}
