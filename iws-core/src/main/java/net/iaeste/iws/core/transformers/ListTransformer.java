/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.transformers.ListTransformer
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

import java.util.ArrayList;
import java.util.List;

/**
 * Tranformer for the List of values, handles various transformations
 * of the list
 *
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class ListTransformer {
    public final static String delimiter = "|";
    private static String delimiterRegExp = "\\|";

    private ListTransformer() {
    }

    /**
     * Concatenates a list of enum values into the one string
     *
     * @param list   List of values to be concatenated
     * @return concatenated String
     */
    public static <T extends Enum> String concatEnumList(final List<T> list) {
        StringBuilder sb = new StringBuilder();
        if(list.size() > 0) {
            sb.append(list.get(0).name());
            for(int i=1;i<list.size();i++) {
                sb.append(delimiter);
                sb.append(list.get(i).name());
            }
        }
        return sb.toString();
    }

    /**
     * Concatenates a list of enum values into the one string
     *
     * @param enumType   The Class object of the enum type from which to return a constant
     * @param value      String which is splited into the list of enum values
     * @return           List of enum values
     */
    public static <T extends Enum<T>> List<T> explodeEnumList(Class<T> enumType, String value) {
        String[] array = value.split(delimiterRegExp);
        List<T> result = new ArrayList<>();
        for(String s : array) {
            try {
                T v = T.valueOf(enumType, s);
                result.add(v);
            }
            catch (IllegalArgumentException e) { }
        }

        return result;
    }
}
