/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.monitoring.MonitoringProcessor
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.monitoring;

import net.iaeste.iws.api.dtos.Field;
import net.iaeste.iws.api.enums.MonitoringLevel;
import net.iaeste.iws.persistence.entities.IWSEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

/**
 * This processor is using the Java Reflection framework to read the Annotation
 * information from the Classes that we wish to monitor.<br />
 *   For more information about Monitoring, please see the document residing in
 * the same package as this class.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 * @noinspection ObjectAllocationInLoop
 */
public final class MonitoringProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(MonitoringProcessor.class);
    private static final Integer DEFAULT_ARRAY_SIZE = 10;

    /**
     * Finds the {@code MonitoringLevel} for an Entity. If no
     * {@code MonitoringLevel} is found, then a "None" is returned.
     *
     * @param entity  The Entity to find the {@code MonitoringLevel} for
     * @return Either found {@code MonitoringLevel} or "None"
     */
    public MonitoringLevel findClassMonitoringLevel(final IWSEntity entity) {
        MonitoringLevel level = MonitoringLevel.NONE;

        if (entity != null) {
            final Annotation[] annotations = entity.getClass().getDeclaredAnnotations();
            for (final Annotation annotation : annotations) {
                if (annotation instanceof Monitored) {
                    level = ((Monitored) annotation).level();
                }
            }
        }

        return level;
    }

    /**
     * Finds the name to be used for a given Entity. If no name is found, then
     * a null is returned.
     *
     * @param entity  The Entity to find the name for
     * @return Entity Monitoring name or null
     */
    public String findClassMonitoringName(final IWSEntity entity) {
        String name = null;

        if (entity != null) {
            final Annotation[] annotations = entity.getClass().getDeclaredAnnotations();
            for (final Annotation annotation : annotations) {
                if (annotation instanceof Monitored) {
                    name = ((Monitored) annotation).name();
                }
            }
        }

        return name;
    }

    /**
     * Finds a list the new values for a new Entity, that are monitored. If
     * nothing is to be monitored, then a null is returned.<br />
     *   Based on the value of the first parameter - the method will either add
     * all information, or only "mark" the new values.
     *
     * @param classLevel  The {@code MonitoringLevel} to use
     * @param entity      The Entity, that is being created
     * @return List with all the monitored values or null
     * @see MonitoringLevel
     */
    public ArrayList<Field> findChanges(final MonitoringLevel classLevel, final IWSEntity entity) {
        // It is customary to return an empty list, however - an empty list
        // may indicate that we checked for details, but didn't find any -
        // in our case, we wish to return a true null, so it is clear that
        // nothing was checked
        ArrayList<Field> found = null;

        if ((classLevel == MonitoringLevel.DETAILED) && (entity != null)) {
            found = new ArrayList<>(DEFAULT_ARRAY_SIZE);

            for (final java.lang.reflect.Field field : entity.getClass().getDeclaredFields()) {
                final Annotation annotation = field.getAnnotation(Monitored.class);

                if (annotation != null) {
                    final MonitoringLevel fieldLevel = ((Monitored) annotation).level();
                    if (fieldLevel == MonitoringLevel.MARKED) {
                        found.add(new Field(((Monitored) annotation).name()));
                    } else if (fieldLevel == MonitoringLevel.DETAILED) {
                        final String name = ((Monitored) annotation).name();
                        final String newValue = readObjectValue(field, entity);

                        // For new Objects, there cannot be any old value, so
                        // we set the 'old' value to null
                        found.add(new Field(name, null, newValue));
                    }
                }
            }
        }

        return found;
    }

    /**
     * Finds a list of changes between two Entities. If no changes is found,
     * then a null is returned.<br />
     *   Based on the value of the first parameter - the method will either add
     * all information, or only "mark" fields that have been changed.
     *
     * @param classLevel  The {@code MonitoringLevel} to use
     * @param oldEntity   The Old Entity, that is being updated
     * @param newEntity   The New Entity, containing the new values
     * @return List with all the monitored changes or null
     * @see MonitoringLevel
     */
    public ArrayList<Field> findChanges(final MonitoringLevel classLevel, final IWSEntity oldEntity, final IWSEntity newEntity) {
        // It is customary to return an empty list, however - an empty list
        // may indicate that we checked for details, but didn't find any -
        // in our case, we wish to return a true null, so it is clear that
        // nothing was checked
        ArrayList<Field> found = null;

        if ((classLevel == MonitoringLevel.DETAILED) && isValidIdenticalObjects(oldEntity, newEntity)) {
            found = new ArrayList<>(DEFAULT_ARRAY_SIZE);

            for (final java.lang.reflect.Field field : oldEntity.getClass().getDeclaredFields()) {
                final Annotation annotation = field.getAnnotation(Monitored.class);

                if (annotation != null) {
                    final MonitoringLevel fieldLevel = ((Monitored) annotation).level();
                    final String newValue = readObjectValue(field, newEntity);
                    final String oldValue = readObjectValue(field, oldEntity);

                    if ((newValue != null) && !newValue.equals(oldValue)) {
                        final String name = ((Monitored) annotation).name();

                        if (fieldLevel == MonitoringLevel.MARKED) {
                            found.add(new Field(name));
                        } else if (fieldLevel == MonitoringLevel.DETAILED) {
                            found.add(new Field(name, oldValue, newValue));
                        }
                    }
                }
            }
        }

        return found;
    }

    /**
     * With the help of the Reflection framework, this method reads the value of
     * the given Field from the given IWSEntity. If an error occurred, then the
     * returned value is null, otherwise the {@code IWSEntity.toString()} value is
     * returned.
     *
     * @param field  The Field to read the value for
     * @param obj    The IWSEntity to read the value from
     * @return The String representation of the Value or null
     */
    private static String readObjectValue(final java.lang.reflect.Field field, final IWSEntity obj) {
        // First, we store the Accessibility information for the IWSEntity, since
        // we need to set it to accessible before attempting to read it
        final boolean accessible = field.isAccessible();
        field.setAccessible(true);

        // Read the content, if we receive an Exception, then lets just assume
        // that the value is null
        Object rawObject = null;
        try {
            rawObject = field.get(obj);
        } catch (IllegalAccessException e) {
            LOG.debug(e.getMessage(), e);
        }

        // Restore the accessibility
        field.setAccessible(accessible);

        // Return the String value or null
        return (rawObject != null) ? rawObject.toString() : null;
    }

    /**
     * Returns true of both the given Objects are value (not null), and they are
     * identical. Otherwise it returns false.
     *
     * @param obj1 The First IWSEntity
     * @param obj2 The Second Object
     * @return True if both Objects are identical, otherwise False
     */
    private static Boolean isValidIdenticalObjects(final IWSEntity obj1, final IWSEntity obj2) {
        return (obj1 != null) && (obj2 != null) && obj1.getClass().equals(obj2.getClass());
    }
}
