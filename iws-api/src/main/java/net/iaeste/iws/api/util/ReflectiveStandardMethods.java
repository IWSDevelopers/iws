/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.util.ReflectiveStandardMethods
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.util;

import net.iaeste.iws.api.constants.IWSConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

/**
 * <p>Boilerplate code should be avoided if possible, it clutters the classes,
 * reduces readability and is often a nuisance to test, as it often leads to
 * really bad tests, which simply exists to prove that something which is
 * generated should work.</p>
 *
 * <p>Using Reflection is coming with a price, so using a library to handle it,
 * can be tricky to get right, as there is no control over the library, so a
 * change can result in different expectations. Or it can be that it cannot be
 * controlled to the degree required. For example, things like sensitive data
 * should never be part of the generated toString value.</p>
 *
 * <p>This Class will try to handle these problems, by providing a
 * self-implemented, and yet fairly simple Reflective Solution. Although
 * Reflection is having a performance overhead over generated code, it also
 * has the beauty, that it will work even if a developer forgot to update the
 * standard methods.</p>
 *
 * <p>It should be noted, that libraries such as Apache Commons does provide
 * loads if nice functionality, but dragging around with it, is rather heavy,
 * and often only a fraction of the functionality is used. This doesn't mean
 * that such libraries should be avoided, but a balance must be struck, so
 * simple logic is handled directly, rather than via Libraries.</p>
 *
 * <p>The logic implemented in this class focuses on the following standard
 * methods:</p>
 * <ul>
 *   <li>#equals(obj)</li>
 *   <li>#hashCode()</li>
 *   <li>#toString()</li>
 * </ul>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class ReflectiveStandardMethods {

    private static final Logger LOG = LoggerFactory.getLogger(ReflectiveStandardMethods.class);

    /**
     * Private Constructor, this is a Utility Class.
     */
    private ReflectiveStandardMethods() {
    }

    public static boolean reflectiveEquals(final Object left, final Object right) {
        boolean result = true;

        if ((left != null) && (right != null)) {
            // First part, let's compare the pointer references, if it is the
            // same pointer, then we don't have to do have to do anymore.
            if (left != right) {
                // Not the same Object, okay, let's compare the classes, to see
                // if it is the same. Meaning that both Objects must be of the
                // same Class.
                if (Objects.equals(left.getClass(), right.getClass())) {
                    // Same Classes, now, we can start comparing fields.
                    result = equalizeFields(left, right);
                } else {
                    // Not the same Class, then they cannot be equal.
                    result = false;
                }
            }
        } else {
            result = false;
        }

        return result;
    }

    public static int reflectiveHashCode(final Object obj) {
        int result = IWSConstants.HASHCODE_INITIAL_VALUE;

        for (final Field field : readFields(obj)) {
            if (reflectField(field, StandardMethods.For.HASH)) {
                result = (IWSConstants.HASHCODE_MULTIPLIER * result) + Objects.hashCode(readValue(obj, field));
            }
        }

        return result;
    }

    public static String reflectiveToString(final Object obj) {
        final StringBuilder builder = new StringBuilder(256);

        // First part, we're adding the name of the Class.
        builder.append(obj.getClass().getSimpleName());
        // Then we'll add a start brace, to indicate where the content comes
        builder.append('{');

        // The fields are comma separated, so we're adding a comma between
        // each, but not for the first.
        boolean isFirst = true;
        for (final Field field : readFields(obj)) {
            if (reflectField(field, StandardMethods.For.TOSTRING)) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    builder.append(", ");
                }
                builder.append(field.getName());
                builder.append('=');
                builder.append(readValue(obj, field));
            }
        }

        // And finally, we're adding the close brace to tell that we're done
        builder.append('}');

        return builder.toString();
    }

    // =========================================================================
    // Internal Reflective Methods, to help build the standard method results
    // =========================================================================

    /**
     * <p>Some of the information for a Class is present in the Super Class,
     * such as the IWS Error information. To make sure that all information is
     * present and accounted for, the entire set of Fields must be added. Note,
     * that this method will only look at the direct super class, not any other
     * parent classes.</p>
     *
     * @param obj The Object to read the fields from
     * @return Array with all the Fields from the Object, including super
     */
    private static Field[] readFields(final Object obj) {
        // The fields are declared as an array, so we will also return an array,
        // first the array from the Object, and those from the Parent
        final Field[] objectFields = obj.getClass().getDeclaredFields();
        final Field[] superFields = obj.getClass().getSuperclass().getDeclaredFields();

        // The result is also an array, so we'll declare one which can hold the
        // Fields from both Objects
        final Field[] merged = new Field[objectFields.length + superFields.length];

        // Merging two Arrays is done simplest and quickest with the System
        // Array Copier, which does have a boring C-like syntax, but will get
        // the job done faster than if we try to do it our self
        System.arraycopy(objectFields, 0, merged, 0, objectFields.length);
        System.arraycopy(superFields, 0, merged, objectFields.length, superFields.length);

        // And return the merged set of Fields
        return merged;
    }

    /**
     * Compares two Objects of the same type, to see if they are equal or not.
     *
     * @param left  First Object or &quot;this&quot;
     * @param right Second Object to compare against
     * @return True if they are the same, otherwise false
     */
    private static boolean equalizeFields(final Object left, final Object right) {
        boolean result = true;

        // Same Classes, so we can iterate over the fields and
        // verify that they have the same value, if not - then
        // we'll just end here.
        for (final Field field : readFields(left)) {
            final Object obj1 = readValue(left, field);
            final Object obj2 = readValue(right, field);
            if (reflectField(field, StandardMethods.For.HASH) && !Objects.equals(obj1, obj2)) {
                result = false;
                break;
            }
        }

        return result;
    }

    /**
     * <p>Some fields like Password or similar sensitive fields, should never be
     * part of the standard methods, as the standard methods often is used for
     * more generic things like logging information or direct comparison. If
     * sensitive information is stored in an Object, or other information which
     * may be irrelevant, then the Standard Method Annotation can control it, so
     * it is possible to state that it should not be present or only for a
     * specific part.</p>
     *
     * <p>The method will check if the field should be used or not, and return
     * true if it should be used and false if not. The SerialVersion UID is by
     * default excluded, as it is a required field for Serializable Classes.
     * Same applies to synthetic fields, like field injected via instrumentation
     * when running with Agents.</p>
     *
     * @param field Field to check if it should be used or not
     * @param use   The type of request to use it for, i.e. Hash or ToString
     * @return True if it should be used, false otherwise
     */
    private static boolean reflectField(final Field field, final StandardMethods.For use) {
        // By default, we're assuming that the field should be used. Meaning
        // that unless it is explicitly annotated and the annotation states it
        // shouldn't be used, we'll use it. Only exception to this rule is the
        // standard serialization Id & Synthetic fields.
        final boolean useField;

        if (isMember(field) && !"serialVersionUID".equals(field.getName())) {
            useField = reflectOverFieldAnnotations(field, use);
        } else {
            // The field is neither a standard Class member or part of the Class
            // Business Logic, so it should be skipped. Note, that the Serial
            // Version UID is also skipped here - it is required to ensure that
            // Java knows if a Class can be de-serialize or not, so it is not a
            // part of the Class Business aspects.
            useField = false;
        }

        return useField;
    }

    /**
     * <p>We are only interested in fields, which are actual members of a Class
     * or super Class. Certain fields, like Constants or transient values, like
     * Loggers, should be skipped.</p>
     *
     * <p>Fields injected via Agents using Instrumentation to monitor or gather
     * information from a Class, should also be skipped. For example, JaCoCo is
     * using Instrumentation to measure the Code Coverage of a Class.</p>
     *
     * @param field The field to check if it should be used or nog
     * @return True if the field is part of the Business Logic, otherwise false
     */
    private static boolean isMember(final Field field) {
        boolean member = true;

        if (field.isSynthetic()) {
            LOG.trace("Skipping the Field {}, it is Synthetic.", field.getName());
            member = false;
        } else {
            final int modifiers = field.getModifiers();

            if (Modifier.isFinal(modifiers) && Modifier.isStatic(modifiers)) {
                LOG.trace("Skipping the Field {}, it is a Constant.", field.getName());
                member = false;
            } else if (Modifier.isTransient(modifiers)) {
                LOG.trace("Skipping the Field {}, it is Transient.", field.getName());
                member = false;
            }
        }

        return member;
    }

    /**
     * Reflects over the Field Annotations, to see if there is a reason why not
     * to use the field. If so, then a false is returned as the field should be
     * skipped, otherwise a true is returned, if the field should be used.
     *
     * @param field Field to reflect over
     * @param use   The Standard Method Annotation to use
     * @return True if we should use the field, otherwise false
     */
    private static boolean reflectOverFieldAnnotations(final Field field, final StandardMethods.For use) {
        boolean useField = true;

        for (final Annotation annotation : field.getDeclaredAnnotations()) {
            if (annotation instanceof StandardMethods) {
                final StandardMethods.For forField = ((StandardMethods) annotation).value();
                if ((forField == StandardMethods.For.NONE) || ((forField != use) && (forField != StandardMethods.For.ALL))) {
                    useField = false;
                }
            }
        }

        return useField;
    }

    /**
     * <p>Extracts the information from the specific field in the Object given,
     * and returns it.</p>
     *
     * @param object The Object to read an Object from
     * @param field  The Field to read from the Object
     * @return The value from the Field in the Object
     */
    private static Object readValue(final Object object, final Field field) {
        Object obj = null;

        try {
            // First, store the accessibility, and for it accessible, so we
            // can read the content
            final boolean accessible = field.isAccessible();
            field.setAccessible(true);

            // Read and process the content, using the Arrays object
            obj = field.get(object);
            if (obj instanceof String) {
                // Strings are a little special, we would like to make sure
                // that it is clear where they start and end, so we're
                // encapsulating them with single quotes.
                obj = "'" + obj + '\'';
            }

            // Restore the accessibility
            field.setAccessible(accessible);
        } catch (IllegalAccessException e) {
            // We don't wish to see any exceptions when performing this
            // action, so we'll log it and simply return null as the result
            LOG.debug(e.getMessage(), e);
        }

        return obj;
    }
}
