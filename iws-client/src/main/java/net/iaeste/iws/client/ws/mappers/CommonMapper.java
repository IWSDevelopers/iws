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
package net.iaeste.iws.client.ws.mappers;

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
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DateTime;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

/**
 * Common Mapper for all the WebService Mapping.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
abstract class CommonMapper {

    protected static IWSError map(final net.iaeste.iws.ws.IWSError ws) {
        return new IWSError(ws.getError(), ws.getDescription());
    }

    public static AuthenticationToken map(net.iaeste.iws.ws.AuthenticationToken ws) {
        final AuthenticationToken api = new AuthenticationToken();

        if (ws != null) {
            api.setToken(ws.getToken());
            api.setGroupId(ws.getGroupId());
        }

        return api;
    }

    public static net.iaeste.iws.ws.AuthenticationToken map(AuthenticationToken api) {
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

    private static net.iaeste.iws.ws.Currency map(final Currency api) {
        return api != null ? net.iaeste.iws.ws.Currency.valueOf(api.name()) : null;
    }

    private static net.iaeste.iws.ws.Membership map(final Membership api) {
        return api != null ? net.iaeste.iws.ws.Membership.valueOf(api.name()) : null;
    }

    protected static Address map(final net.iaeste.iws.ws.Address ws) {
        final Address api = new Address();

        api.setStreet1(ws.getStreet1());
        api.setStreet2(ws.getStreet2());
        api.setPostalCode(ws.getPostalCode());
        api.setCity(ws.getCity());
        api.setState(ws.getState());
        api.setPobox(ws.getPobox());
        api.setCountry(map(ws.getCountry()));

        return api;
    }

    protected static net.iaeste.iws.ws.Address map(final Address api) {
        final net.iaeste.iws.ws.Address ws = new net.iaeste.iws.ws.Address();

        ws.setStreet1(api.getStreet1());
        ws.setStreet2(api.getStreet2());
        ws.setPostalCode(api.getPostalCode());
        ws.setCity(api.getCity());
        ws.setState(api.getState());
        ws.setPobox(api.getPobox());
        ws.setCountry(map(api.getCountry()));

        return ws;
    }

    protected static UserGroup map(final net.iaeste.iws.ws.UserGroup ws) {
        final UserGroup api = new UserGroup();

        api.setUserGroupId(ws.getUserGroupId());
        api.setUser(map(ws.getUser()));
        api.setGroup(map(ws.getGroup()));
        api.setRole(map(ws.getRole()));
        api.setTitle(ws.getTitle());
        api.setOnPrivateList(ws.isOnPrivateList());
        api.setOnPublicList(ws.isOnPublicList());
        // TODO Correct UserGroup, as the flag is not set. Assuming it is because the getter is called "may..."
        //api.setWriteToPrivateList(api.isWriteToPrivateList());
        api.setMemberSince(map(ws.getMemberSince()));

        return api;
    }

    protected static User map(final net.iaeste.iws.ws.User ws) {
        final User api = new User();

        api.setUserId(ws.getUserId());
        api.setUsername(ws.getUsername());
        api.setAlias(ws.getAlias());
        api.setFirstname(ws.getFirstname());
        api.setLastname(ws.getLastname());
        api.setPerson(map(ws.getPerson()));
        api.setStatus(map(ws.getStatus()));
        api.setPrivacy(map(ws.getPrivacy()));
        api.setNotifications(map(ws.getNotifications()));

        return api;
    }

    protected static Person map(final net.iaeste.iws.ws.Person ws) {
        final Person api = new Person();

        api.setNationality(map(ws.getNationality()));
        api.setAddress(map(ws.getAddress()));
        api.setAlternateEmail(ws.getAlternateEmail());
        api.setMobile(ws.getMobile());
        api.setPhone(ws.getPhone());
        api.setFax(ws.getFax());
        api.setBirthday(map(ws.getBirthday()));
        api.setGender(Gender.valueOf(ws.getGender().name()));

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

    protected static Set<Permission> mapPermissionList(List<net.iaeste.iws.ws.Permission> ws) {
        final Set<Permission> api = EnumSet.noneOf(Permission.class);

        for (final net.iaeste.iws.ws.Permission permission : ws) {
            api.add(map(permission));
        }

        return api;
    }

    protected static net.iaeste.iws.ws.Date map(final Date api) {
        net.iaeste.iws.ws.Date ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.Date();
            final java.util.Date theDate = api.toDate();
            if (theDate != null) {
                GregorianCalendar calendar = new GregorianCalendar();
                // Throws a NullPointerException without the null check
                calendar.setTime(theDate);

                try {
                    final XMLGregorianCalendar converted = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
                    ws.setMidnight(converted);
                } catch (DatatypeConfigurationException e) {
                    throw new IWSException(IWSErrors.ERROR, e.getMessage(), e);
                }
            }
        }

        return ws;
    }

    protected static Date map(final net.iaeste.iws.ws.Date ws) {
        Date api = null;

        if (ws != null) {
            api = new Date(map(ws.getMidnight()));
        }

        return api;
    }

    protected static DateTime map(final net.iaeste.iws.ws.DateTime ws) {
        DateTime api = null;

        if (ws != null) {
            api = new DateTime(map(ws.getTimestamp()));
        }

        return api;
    }

    // =========================================================================
    // Internal Convertion of Enums
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

    // =========================================================================
    // Internal Convertion of some WebService specific things
    // =========================================================================

    protected static java.util.Date map(final XMLGregorianCalendar calendar) {
        java.util.Date converted = null;

        if (calendar != null) {
            converted = calendar.toGregorianCalendar().getTime();
        }

        return converted;
    }

    protected static XMLGregorianCalendar map(java.util.Date date) {
        XMLGregorianCalendar converted = null;

        if (date != null) {
            GregorianCalendar calendar = new GregorianCalendar();
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
