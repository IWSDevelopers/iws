/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.ws.AccessMapper
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client.ws;

import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Authorization;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.Password;
import net.iaeste.iws.api.dtos.Person;
import net.iaeste.iws.api.dtos.Role;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.dtos.UserGroup;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Gender;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.enums.NotificationFrequency;
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DateTime;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class AccessMapper {

    public static net.iaeste.iws.ws.AuthenticationRequest map(final AuthenticationRequest api) {
        final net.iaeste.iws.ws.AuthenticationRequest ws = new net.iaeste.iws.ws.AuthenticationRequest();

        ws.setUsername(api.getUsername());
        ws.setPassword(api.getPassword());

        return ws;
    }

    public static AuthenticationResponse map(final net.iaeste.iws.ws.AuthenticationResponse ws) {
        final AuthenticationResponse api = new AuthenticationResponse(map(ws.getError()), ws.getMessage());

        api.setToken(map(ws.getToken()));

        return api;
    }

    public static FetchPermissionResponse map(final net.iaeste.iws.ws.FetchPermissionResponse ws) {
        final FetchPermissionResponse api = new FetchPermissionResponse(map(ws.getError()), ws.getMessage());

        api.setUserId(ws.getUserId());
        api.setAuthorizations(map(ws.getAuthorizations()));

        return api;
    }

    public static List<Authorization> map(final List<net.iaeste.iws.ws.Authorization> ws) {
        List<Authorization> api = null;

        if (ws != null) {
            api = new ArrayList<>(ws.size());
            for (final net.iaeste.iws.ws.Authorization wsAuth : ws) {
                Authorization auth = new Authorization();
                auth.setUserGroup(map(wsAuth.getUserGroup()));
                api.add(auth);
            }
        }

        return api;
    }

    public static UserGroup map(final net.iaeste.iws.ws.UserGroup ws) {
        final UserGroup api = new UserGroup();

        api.setUserGroupId(ws.getUserGroupId());
        api.setUser(map(ws.getUser()));
        //api.setGroup(map(ws.getGroup()));
        api.setRole(map(ws.getRole()));
        api.setTitle(ws.getTitle());
        api.setOnPrivateList(ws.isOnPrivateList());
        api.setOnPublicList(ws.isOnPublicList());
        //api.setWriteToPrivateList(ws.isWriteToPrivateList());
        api.setMemberSince(map(ws.getMemberSince()));

        return api;
    }

    public static Role map(final net.iaeste.iws.ws.Role ws) {
        final Role api = new Role();

        api.setRoleId(ws.getRoleId());
        api.setRoleName(ws.getRoleName());
        //api.setPermissions();

        return api;
    }

    public static User map(final net.iaeste.iws.ws.User ws) {
        final User api = new User();

        api.setUserId(ws.getUserId());
        api.setUsername(ws.getUsername());
        api.setAlias(ws.getAlias());
        api.setFirstname(ws.getFirstname());
        api.setLastname(ws.getLastname());
        api.setPerson(map(ws.getPerson()));
        api.setStatus(UserStatus.valueOf(ws.getStatus().name()));
        api.setPrivacy(Privacy.valueOf(ws.getPrivacy().name()));
        api.setNotifications(NotificationFrequency.valueOf(ws.getNotifications().name()));

        return api;
    }

    public static Person map(final net.iaeste.iws.ws.Person ws) {
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

    public static Country map(final net.iaeste.iws.ws.Country ws) {
        final Country api = new Country();

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

        return api;
    }

    public static Address map(final net.iaeste.iws.ws.Address ws) {
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

    public static net.iaeste.iws.ws.Date map(final Date api) {
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

    public static Date map(final net.iaeste.iws.ws.Date ws) {
        Date api = null;

        if (ws != null) {
            api = new Date(map(ws.getMidnight()));
        }

        return api;
    }

    public static DateTime map(final net.iaeste.iws.ws.DateTime ws) {
        DateTime api = null;

        if (ws != null) {
            api = new DateTime(map(ws.getTimestamp()));
        }

        return api;
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

    public static IWSError map(final net.iaeste.iws.ws.IWSError ws) {
        return new IWSError(ws.getError(), ws.getDescription());
    }

    public static net.iaeste.iws.ws.Password map(final Password api) {
        final net.iaeste.iws.ws.Password ws = new net.iaeste.iws.ws.Password();

        ws.setNewPassword(api.getNewPassword());
        ws.setOldPassword(api.getOldPassword());

        return ws;
    }

    // =========================================================================
    // Internal Convertion of some WebService specific things
    // =========================================================================

    public static java.util.Date map(final XMLGregorianCalendar calendar) {
        java.util.Date converted = null;

        if (calendar != null) {
            converted = calendar.toGregorianCalendar().getTime();
        }

        return converted;
    }

    private static XMLGregorianCalendar map(java.util.Date date) {
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
