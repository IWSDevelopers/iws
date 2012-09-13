/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-common) - net.iaeste.iws.common.utils.JSON
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.common.utils;

import com.google.gson.Gson;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class JSON {
    private static final Gson GSON = new Gson();

    private JSON() {
    }

    public static <T> String serialize(final T object) {
        return GSON.toJson(object);
    }
    public static <T> T deserialize(final Class<T> clazz, final String json) {
        return GSON.fromJson(json, clazz);
    }
}
