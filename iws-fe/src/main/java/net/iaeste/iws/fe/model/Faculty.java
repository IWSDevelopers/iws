/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.model.Faculty
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.fe.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that serves as a dummy database to test the functionality
 * of the autocomplete component for faculties
 *
 * @author Marko Cilimkovic / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class Faculty {
    private String name;

    public static List<Faculty> getDummyFaculties() {
        List<Faculty> list = new ArrayList<>();
        for (int i=0; i< 10; i++) {
            Faculty e = new Faculty();
            e.setName("Faculty" + i);

            list.add(e);
        }

        return list;
    }

    public boolean nameMatches(String str) {
        return this.name.toLowerCase().startsWith(str.toLowerCase());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
