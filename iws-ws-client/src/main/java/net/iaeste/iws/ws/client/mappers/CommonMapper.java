/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.ws.mappers.CommonMapper
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ws.client.mappers;

import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.Person;
import net.iaeste.iws.api.dtos.Role;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.dtos.UserGroup;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Gender;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.enums.MonitoringLevel;
import net.iaeste.iws.api.enums.NotificationFrequency;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.api.enums.SortingField;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DatePeriod;
import net.iaeste.iws.api.util.DateTime;
import net.iaeste.iws.api.util.Page;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Common Mapper for all the WebService Mapping. Made package private, as it is
 * only used by the API Mappers.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public class CommonMapper {

    protected static IWSError map(final net.iaeste.iws.ws.IWSError ws) {
        return new IWSError(ws.getError(), ws.getDescription());
    }

    public static AuthenticationToken map(final net.iaeste.iws.ws.AuthenticationToken ws) {
        final AuthenticationToken api = new AuthenticationToken();

        if (ws != null) {
            api.setToken(ws.getToken());
            api.setGroupId(ws.getGroupId());
        }

        return api;
    }

    public static net.iaeste.iws.ws.AuthenticationToken map(final AuthenticationToken api) {
        final net.iaeste.iws.ws.AuthenticationToken ws = new net.iaeste.iws.ws.AuthenticationToken();

        ws.setToken(api.getToken());
        ws.setGroupId(api.getGroupId());

        return ws;
    }

    public static FallibleResponse map(final net.iaeste.iws.ws.FallibleResponse ws) {
        return new FallibleResponse(map(ws.getError()), ws.getMessage());
    }

    protected static Country map(final net.iaeste.iws.ws.Country ws) {
        Country api = null;

        // The CountryName may be null if it was a null Object returned, not
        // sure why the CountryCode is set in that case.
        if ((ws != null) && (ws.getCountryName() != null)) {
            api = new Country();

            api.setCountryCode(ws.getCountryCode());
            api.setCountryName(ws.getCountryName());
            api.setCountryNameFull(ws.getCountryNameFull());
            api.setCountryNameNative(ws.getCountryNameNative());
            api.setNationality(ws.getNationality());
            api.setCitizens(ws.getCitizens());
            api.setPhonecode(ws.getPhonecode());
            api.setCurrency(Currency.valueOf(ws.getCurrency().name()));
            api.setLanguages(ws.getLanguages());
            api.setMembership(Membership.valueOf(ws.getMembership().name()));
            api.setMemberSince(ws.getMemberSince());
        }

        return api;
    }

    protected static net.iaeste.iws.ws.Country map(final Country api) {
        net.iaeste.iws.ws.Country ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.Country();

            ws.setCountryCode(api.getCountryCode());
            ws.setCountryName(api.getCountryName());
            ws.setCountryNameFull(api.getCountryNameFull());
            ws.setCountryNameNative(api.getCountryNameNative());
            ws.setNationality(api.getNationality());
            ws.setCitizens(api.getCitizens());
            ws.setPhonecode(api.getPhonecode());
            ws.setCurrency(map(api.getCurrency()));
            ws.setLanguages(api.getLanguages());
            ws.setMembership(map(api.getMembership()));
            ws.setMemberSince(api.getMemberSince());
        }

        return ws;
    }

    protected static Address map(final net.iaeste.iws.ws.Address ws) {
        Address api = null;

        if (ws != null) {
            api = new Address();

            api.setStreet1(ws.getStreet1());
            api.setStreet2(ws.getStreet2());
            api.setPostalCode(ws.getPostalCode());
            api.setCity(ws.getCity());
            api.setState(ws.getState());
            api.setPobox(ws.getPobox());
            api.setCountry(map(ws.getCountry()));
        }

        return api;
    }

    protected static net.iaeste.iws.ws.Address map(final Address api) {
        net.iaeste.iws.ws.Address ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.Address();

            ws.setStreet1(api.getStreet1());
            ws.setStreet2(api.getStreet2());
            ws.setPostalCode(api.getPostalCode());
            ws.setCity(api.getCity());
            ws.setState(api.getState());
            ws.setPobox(api.getPobox());
            ws.setCountry(map(api.getCountry()));
        }

        return ws;
    }

    protected static UserGroup map(final net.iaeste.iws.ws.UserGroup ws) {
        UserGroup api = null;

        if (ws != null) {
            api = new UserGroup();

            api.setUserGroupId(ws.getUserGroupId());
            api.setUser(map(ws.getUser()));
            api.setGroup(map(ws.getGroup()));
            api.setRole(map(ws.getRole()));
            api.setTitle(ws.getTitle());
            api.setOnPrivateList(ws.isOnPrivateList());
            api.setOnPublicList(ws.isOnPublicList());
            api.setWriteToPrivateList(ws.isWriteToPrivateList());
            api.setMemberSince(map(ws.getMemberSince()));
        }

        return api;
    }

    protected static User map(final net.iaeste.iws.ws.User ws) {
        User api = null;

        if (ws != null) {
            api = new User();

            api.setUserId(ws.getUserId());
            api.setUsername(ws.getUsername());
            api.setAlias(ws.getAlias());
            api.setFirstname(ws.getFirstname());
            api.setLastname(ws.getLastname());
            api.setPerson(map(ws.getPerson()));
            api.setStatus(map(ws.getStatus()));
            api.setPrivacy(map(ws.getPrivacy()));
            api.setNotifications(map(ws.getNotifications()));
        }

        return api;
    }

    protected static Person map(final net.iaeste.iws.ws.Person ws) {
        Person api = null;

        if (ws != null) {
            api = new Person();

            api.setNationality(map(ws.getNationality()));
            api.setAddress(map(ws.getAddress()));
            api.setAlternateEmail(ws.getAlternateEmail());
            api.setMobile(ws.getMobile());
            api.setPhone(ws.getPhone());
            api.setFax(ws.getFax());
            api.setBirthday(map(ws.getBirthday()));
            api.setGender(Gender.valueOf(ws.getGender().name()));
        }

        return api;
    }

    protected static Group map(final net.iaeste.iws.ws.Group ws) {
        Group api = null;

        if (ws != null) {
            api = new Group();

            api.setGroupId(ws.getGroupId());
            api.setGroupName(ws.getGroupName());
            api.setFullName(ws.getFullName());
            api.setListName(ws.getListName());
            api.setPrivateList(ws.isPrivateList());
            api.setPublicList(ws.isPublicList());
            api.setGroupType(map(ws.getGroupType()));
            api.setDescription(ws.getDescription());
            api.setMonitoringLevel(map(ws.getMonitoringLevel()));
            api.setCountry(map(ws.getCountry()));
        }

        return api;
    }

    protected static net.iaeste.iws.ws.Group map(final Group api) {
        net.iaeste.iws.ws.Group ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.Group();

            ws.setGroupId(api.getGroupId());
            ws.setGroupName(api.getGroupName());
            ws.setFullName(api.getFullName());
            ws.setListName(api.getListName());
            ws.setPrivateList(api.getPrivateList());
            ws.setPublicList(api.getPublicList());
            ws.setGroupType(map(api.getGroupType()));
            ws.setDescription(api.getDescription());
            ws.setMonitoringLevel(map(api.getMonitoringLevel()));
            ws.setCountry(map(api.getCountry()));
        }

        return ws;
    }

    protected static Role map(final net.iaeste.iws.ws.Role ws) {
        final Role api = new Role();

        api.setRoleId(ws.getRoleId());
        api.setRoleName(ws.getRoleName());
        api.setPermissions(mapPermissionList(ws.getPermissions()));

        return api;
    }

    protected static Set<Permission> mapPermissionList(final List<net.iaeste.iws.ws.Permission> ws) {
        final Set<Permission> api = EnumSet.noneOf(Permission.class);

        for (final net.iaeste.iws.ws.Permission permission : ws) {
            api.add(map(permission));
        }

        return api;
    }

    //protected static Page map(final net.iaeste.iws.ws.Page ws) {
    //    Page api = null;
    //
    //    if (ws != null) {
    //        api = new Page();
    //
    //        api.setPageNumber(ws.getPageNumber());
    //        api.setPageSize(ws.getPageSize());
    //        api.setSortAscending(ws.isSortAscending());
    //        api.setSortBy(map(ws.getSortBy()));
    //    }
    //
    //    return api;
    //}

    protected static net.iaeste.iws.ws.Page map(final Page api) {
        net.iaeste.iws.ws.Page ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.Page();

            ws.setPageNumber(api.pageNumber());
            ws.setPageSize(api.pageSize());
            ws.setSortAscending(api.sortAscending());
            ws.setSortBy(map(api.sortBy()));
        }

        return ws;
    }

    // =========================================================================
    // Convertion of Collections
    // =========================================================================

    protected static Collection<Group> mapWSGroupCollection(final Collection<net.iaeste.iws.ws.Group> ws) {
        final Collection<Group> collection;

        if (ws != null) {
            collection = new HashSet<>(ws.size());
            for (final net.iaeste.iws.ws.Group group : ws) {
                collection.add(map(group));
            }
        } else {
            collection = new HashSet<>(0);
        }

        return collection;
    }

    protected static Collection<net.iaeste.iws.ws.Group> mapAPIGroupCollection(final Collection<Group> ws) {
        final Collection<net.iaeste.iws.ws.Group> collection;

        if (ws != null) {
            collection = new HashSet<>(ws.size());
            for (final Group group : ws) {
                collection.add(map(group));
            }
        } else {
            collection = new HashSet<>(0);
        }

        return collection;
    }

    protected static Collection<String> mapStringCollection(final Collection<String> source) {
        final Collection<String> collection;

        if (source != null) {
            collection = new HashSet<>(source);
        } else {
            collection = new HashSet<>(0);
        }

        return collection;
    }

    protected static Set<String> mapStringCollectionToSet(final Collection<String> source) {
        final Set<String> set;

        if (source != null) {
            set = new HashSet<>(source);
        } else {
            set = new HashSet<>(0);
        }

        return set;
    }

    protected static List<String> mapStringCollectionToList(final Collection<String> source) {
        final List<String> list;

        if (source != null) {
            list = new ArrayList<>(source);
        } else {
            list = new ArrayList<>(0);
        }

        return list;
    }

    // =========================================================================
    // Convertion of Enums
    // =========================================================================

    private static Privacy map(final net.iaeste.iws.ws.Privacy ws) {
        return ws != null ? Privacy.valueOf(ws.value()) : null;
    }

    private static UserStatus map(final net.iaeste.iws.ws.UserStatus ws) {
        return ws != null ? UserStatus.valueOf(ws.value()) : null;
    }

    private static GroupType map(final net.iaeste.iws.ws.GroupType ws) {
        return ws != null ? GroupType.valueOf(ws.value()) : null;
    }

    private static net.iaeste.iws.ws.GroupType map(final GroupType api) {
        return api != null ? net.iaeste.iws.ws.GroupType.valueOf(api.name()) : null;
    }

    protected static Currency map(final net.iaeste.iws.ws.Currency ws) {
        return ws != null ? Currency.valueOf(ws.value()) : null;
    }

    protected static net.iaeste.iws.ws.Currency map(final Currency api) {
        return api != null ? net.iaeste.iws.ws.Currency.valueOf(api.name()) : null;
    }

    private static net.iaeste.iws.ws.Membership map(final Membership api) {
        return api != null ? net.iaeste.iws.ws.Membership.valueOf(api.name()) : null;
    }

    private static MonitoringLevel map(final net.iaeste.iws.ws.MonitoringLevel ws) {
        return MonitoringLevel.valueOf(ws.value());
    }

    private static net.iaeste.iws.ws.MonitoringLevel map(final MonitoringLevel api) {
        return api != null ? net.iaeste.iws.ws.MonitoringLevel.valueOf(api.name()) : null;
    }

    private static NotificationFrequency map(final net.iaeste.iws.ws.NotificationFrequency ws) {
        return ws != null ? NotificationFrequency.valueOf(ws.value()) : null;
    }

    protected static Permission map(final net.iaeste.iws.ws.Permission ws) {
        return ws != null ? Permission.valueOf(ws.value()) : null;
    }

    //private static SortingField map(final net.iaeste.iws.ws.SortingField ws) {
    //    return ws != null ? SortingField.valueOf(ws.value()) : null;
    //}

    private static net.iaeste.iws.ws.SortingField map(final SortingField api) {
        return api != null ? net.iaeste.iws.ws.SortingField.valueOf(api.name()) : null;
    }

    // =========================================================================
    // Date & Time Conversion
    // =========================================================================

    protected static Date map(final net.iaeste.iws.ws.Date ws) {
        Date api = null;

        if (ws != null) {
            api = new Date(map(ws.getMidnight()));
        }

        return api;
    }

    protected static net.iaeste.iws.ws.Date map(final Date api) {
        net.iaeste.iws.ws.Date ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.Date();

            ws.setMidnight(map(api.toDate()));
        }

        return ws;
    }

    protected static DateTime map(final net.iaeste.iws.ws.DateTime ws) {
        DateTime api = null;

        if (ws != null) {
            api = new DateTime(map(ws.getTimestamp()));
        }

        return api;
    }

    protected static net.iaeste.iws.ws.DateTime map(final DateTime api) {
        net.iaeste.iws.ws.DateTime ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.DateTime();

            ws.setTimestamp(map(api.toDate()));
        }

        return ws;
    }

    protected static DatePeriod map(final net.iaeste.iws.ws.DatePeriod ws) {
        DatePeriod api = null;

        if (ws != null) {
            api = new DatePeriod(map(ws.getFromDate()), map(ws.getToDate()));
        }

        return api;
    }

    protected static net.iaeste.iws.ws.DatePeriod map(final DatePeriod api) {
        net.iaeste.iws.ws.DatePeriod ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.DatePeriod();

            ws.setFromDate(map(api.getFromDate()));
            ws.setToDate(map(api.getToDate()));
        }

        return ws;
    }

    // =========================================================================
    // Internal Convertion of some WebService specific things
    // =========================================================================

    private static java.util.Date map(final XMLGregorianCalendar calendar) {
        java.util.Date converted = null;

        if (calendar != null) {
            converted = calendar.toGregorianCalendar().getTime();
        }

        return converted;
    }

    private static XMLGregorianCalendar map(final java.util.Date date) {
        XMLGregorianCalendar converted = null;

        if (date != null) {
            final GregorianCalendar calendar = new GregorianCalendar();
            // Throws a NullPointerException without the null check
            calendar.setTime(date);

            try {
                converted = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            } catch (DatatypeConfigurationException e) {
                throw new IWSException(IWSErrors.ERROR, e.getMessage(), e);
            }
        }

        return converted;
    }
}
