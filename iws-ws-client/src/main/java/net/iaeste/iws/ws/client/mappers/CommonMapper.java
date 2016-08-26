/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.ws.client.mappers;

import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.File;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.Person;
import net.iaeste.iws.api.dtos.Role;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.dtos.UserGroup;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.enums.exchange.FieldOfStudy;
import net.iaeste.iws.api.enums.exchange.StudyLevel;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.Response;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DatePeriod;
import net.iaeste.iws.api.util.DateTime;
import net.iaeste.iws.api.util.Page;
import net.iaeste.iws.ws.IwsError;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Collection;
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
class CommonMapper extends EnumMapper {

    /** Protected Constructor, this is a Utility Class. */
    protected CommonMapper() {
    }

    /**
     * Mapping of the WebService IWSError Response Object to the API SOAP
     * Object.
     *
     * @param ws WebService IWSError Response Object
     * @return API IWSError Response Object
     */
    public static IWSError map(final IwsError ws) {
        IWSError api = null;

        if (ws != null) {
            api = new IWSError(ws.getError(), ws.getDescription());
        }

        return api;
    }

    /**
     * Mapping of the WebService AuthenticationToken Response Object to the API
     * SOAP Object.
     *
     * @param ws WebService AuthenticationToken Response Object
     * @return API AuthenticationToken Response Object
     */
    public static AuthenticationToken map(final net.iaeste.iws.ws.AuthenticationToken ws) {
        final AuthenticationToken api = new AuthenticationToken();

        if (ws != null) {
            api.setToken(ws.getToken());
            api.setGroupId(ws.getGroupId());
        }

        return api;
    }

    /**
     * Mapping of the API AuthenticationToken Request Object to the WebService
     * SOAP Object.
     *
     * @param api API AuthenticationToken Request Object
     * @return WS AuthenticationToken Request Object
     */
    public static net.iaeste.iws.ws.AuthenticationToken map(final AuthenticationToken api) {
        net.iaeste.iws.ws.AuthenticationToken ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.AuthenticationToken();

            ws.setToken(api.getToken());
            ws.setGroupId(api.getGroupId());
        }

        return ws;
    }

    /**
     * Mapping of the WebService FallibleResponse Response Object to the API
     * SOAP Object.
     *
     * @param ws WebService FallibleResponse Response Object
     * @return API FallibleResponse Response Object
     */
    public static Response map(final net.iaeste.iws.ws.Response ws) {
        return (ws != null) ? new Response(map(ws.getError()), ws.getMessage()) : null;
    }

    /**
     * Mapping of the API CreateUserRequest Request Object to the WebService
     * SOAP Object.
     *
     * @param api API CreateUserRequest Request Object
     * @return WS CreateUserRequest Request Object
     */
    public static net.iaeste.iws.ws.CreateUserRequest map(final CreateUserRequest api) {
        net.iaeste.iws.ws.CreateUserRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.CreateUserRequest();

            ws.setUsername(api.getUsername());
            ws.setPassword(api.getPassword());
            ws.setFirstname(api.getFirstname());
            ws.setLastname(api.getLastname());
            ws.setStudentAccount(api.isStudent());
        }

        return ws;
    }

    /**
     * Mapping of the WebService CreateUserResponse Response Object to the API
     * SOAP Object.
     *
     * @param ws WebService CreateUserResponse Response Object
     * @return API CreateUserResponse Response Object
     */
    public static CreateUserResponse map(final net.iaeste.iws.ws.CreateUserResponse ws) {
        CreateUserResponse api = null;

        if (ws != null) {
            api = new CreateUserResponse(map(ws.getError()), ws.getMessage());

            api.setUser(map(ws.getUser()));
        }

        return api;
    }

    /**
     * Mapping of the WebService Country Response Object to the API SOAP Object.
     *
     * @param ws WebService Country Response Object
     * @return API Country Response Object
     */
    public static Country map(final net.iaeste.iws.ws.Country ws) {
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
            api.setCurrency(map(ws.getCurrency()));
            api.setLanguages(ws.getLanguages());
            api.setMembership(map(ws.getMembership()));
            api.setMemberSince(mapStringToInteger(ws.getMemberSince()));
        }

        return api;
    }

    /**
     * Mapping of the API Country Request Object to the WebService SOAP Object.
     *
     * @param api API Country Request Object
     * @return WS Country Request Object
     */
    public static net.iaeste.iws.ws.Country map(final Country api) {
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
            ws.setMemberSince(mapIntegerToString(api.getMemberSince()));
        }

        return ws;
    }

    /**
     * Mapping of the WebService Address Response Object to the API SOAP Object.
     *
     * @param ws WebService Address Response Object
     * @return API Address Response Object
     */
    public static Address map(final net.iaeste.iws.ws.Address ws) {
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

    /**
     * Mapping of the API Address Request Object to the WebService SOAP Object.
     *
     * @param api API Address Request Object
     * @return WS Address Request Object
     */
    public static net.iaeste.iws.ws.Address map(final Address api) {
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

    /**
     * Mapping of the WebService UserGroup Response Object to the API SOAP
     * Object.
     *
     * @param ws WebService UserGroup Response Object
     * @return API UserGroup Response Object
     */
    public static UserGroup map(final net.iaeste.iws.ws.UserGroup ws) {
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

    /**
     * Mapping of the API UserGroup Request Object to the WebService SOAP
     * Object.
     *
     * @param api API UserGroup Request Object
     * @return WS UserGroup Request Object
     */
    public static net.iaeste.iws.ws.UserGroup map(final UserGroup api) {
        net.iaeste.iws.ws.UserGroup ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.UserGroup();

            ws.setUserGroupId(api.getUserGroupId());
            ws.setUser(map(api.getUser()));
            ws.setGroup(map(api.getGroup()));
            ws.setRole(map(api.getRole()));
            ws.setTitle(api.getTitle());
            ws.setOnPrivateList(api.isOnPrivateList());
            ws.setOnPublicList(api.isOnPublicList());
            ws.setWriteToPrivateList(api.mayWriteToPrivateList());
            ws.setMemberSince(map(api.getMemberSince()));
        }

        return ws;
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
            api.setType(map(ws.getType()));
            api.setPrivacy(map(ws.getPrivacy()));
            api.setNotifications(map(ws.getNotifications()));
        }

        return api;
    }

    protected static net.iaeste.iws.ws.User map(final User api) {
        net.iaeste.iws.ws.User ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.User();

            ws.setUserId(api.getUserId());
            ws.setUsername(api.getUsername());
            ws.setAlias(api.getAlias());
            ws.setFirstname(api.getFirstname());
            ws.setLastname(api.getLastname());
            ws.setPerson(map(api.getPerson()));
            ws.setStatus(map(api.getStatus()));
            ws.setType(map(api.getType()));
            ws.setPrivacy(map(api.getPrivacy()));
            ws.setNotifications(map(api.getNotifications()));
        }

        return ws;
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
            api.setGender(map(ws.getGender()));
            api.setUnderstoodPrivacySettings(ws.isUnderstoodPrivacySettings());
            api.setAcceptNewsletters(ws.isAcceptNewsletters());
        }

        return api;
    }

    protected static net.iaeste.iws.ws.Person map(final Person api) {
        net.iaeste.iws.ws.Person ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.Person();

            ws.setNationality(map(api.getNationality()));
            ws.setAddress(map(api.getAddress()));
            ws.setAlternateEmail(api.getAlternateEmail());
            ws.setMobile(api.getMobile());
            ws.setPhone(api.getPhone());
            ws.setFax(api.getFax());
            ws.setBirthday(map(api.getBirthday()));
            ws.setGender(map(api.getGender()));
            ws.setUnderstoodPrivacySettings(api.getUnderstoodPrivacySettings());
            ws.setAcceptNewsletters(api.getAcceptNewsletters());
        }

        return ws;
    }

    protected static Group map(final net.iaeste.iws.ws.Group ws) {
        Group api = null;

        if (ws != null) {
            api = new Group();

            api.setGroupId(ws.getGroupId());
            api.setParentId(ws.getParentId());
            api.setGroupName(ws.getGroupName());
            api.setFullName(ws.getFullName());
            api.setListName(ws.getListName());
            api.setPrivateList(ws.isPrivateList());
            api.setPrivateListReplyTo(map(ws.getPrivateListReplyTo()));
            api.setPublicList(ws.isPublicList());
            api.setPublicListReplyTo(map(ws.getPublicListReplyTo()));
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
            ws.setParentId(api.getParentId());
            ws.setGroupName(api.getGroupName());
            ws.setFullName(api.getFullName());
            ws.setListName(api.getListName());
            ws.setPrivateList(api.hasPrivateList());
            ws.setPrivateListReplyTo(map(api.getPrivateListReplyTo()));
            ws.setPublicList(api.hasPublicList());
            ws.setPublicListReplyTo(map(api.getPublicListReplyTo()));
            ws.setGroupType(map(api.getGroupType()));
            ws.setDescription(api.getDescription());
            ws.setMonitoringLevel(map(api.getMonitoringLevel()));
            ws.setCountry(map(api.getCountry()));
        }

        return ws;
    }

    protected static Role map(final net.iaeste.iws.ws.Role ws) {
        Role api = null;

        if (ws != null) {
            api = new Role();

            api.setRoleId(ws.getRoleId());
            api.setRoleName(ws.getRoleName());
            api.setPermissions(mapPermissionList(ws.getPermissions()));
        }

        return api;
    }

    protected static net.iaeste.iws.ws.Role map(final Role api) {
        net.iaeste.iws.ws.Role ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.Role();

            ws.setRoleId(api.getRoleId());
            ws.setRoleName(api.getRoleName());
            ws.getPermissions().addAll(mapAPIPermissionList(api.getPermissions()));
        }

        return ws;
    }

    private static Set<Permission> mapPermissionList(final List<net.iaeste.iws.ws.Permission> ws) {
        final Set<Permission> api = EnumSet.noneOf(Permission.class);

        for (final net.iaeste.iws.ws.Permission permission : ws) {
            api.add(map(permission));
        }

        return api;
    }

    private static Collection<net.iaeste.iws.ws.Permission> mapAPIPermissionList(final Collection<Permission> api) {
        final Set<net.iaeste.iws.ws.Permission> ws = EnumSet.noneOf(net.iaeste.iws.ws.Permission.class);

        if (api != null) {
            for (final Permission permission : api) {
                ws.add(map(permission));
            }
        }

        return ws;
    }

    protected static net.iaeste.iws.ws.Page map(final Page api) {
        net.iaeste.iws.ws.Page ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.Page();

            ws.setPageNumber(api.pageNumber());
            ws.setPageSize(api.pageSize());
            ws.setSortOrder(map(api.sortOrder()));
            ws.setSortBy(map(api.sortBy()));
        }

        return ws;
    }

    protected static File map(final net.iaeste.iws.ws.File ws) {
        File api = null;

        if (ws != null) {
            api = new File();

            api.setFileId(ws.getFileId());
            api.setPrivacy(map(ws.getPrivacy()));
            api.setGroup(map(ws.getGroup()));
            api.setUser(map(ws.getUser()));
            api.setFolderId(ws.getFolderId());
            api.setFilename(ws.getFilename());
            api.setFiledata(ws.getFiledata());
            api.setFilesize(mapStringToInteger(ws.getFilesize()));
            api.setMimetype(ws.getMimetype());
            api.setDescription(ws.getDescription());
            api.setKeywords(ws.getKeywords());
            api.setChecksum(ws.getChecksum());
            api.setModified(map(ws.getModified()));
            api.setCreated(map(ws.getCreated()));
        }

        return api;
    }

    protected static net.iaeste.iws.ws.File map(final File api) {
        net.iaeste.iws.ws.File ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.File();

            ws.setFileId(api.getFileId());
            ws.setPrivacy(map(api.getPrivacy()));
            ws.setGroup(map(api.getGroup()));
            ws.setUser(map(api.getUser()));
            ws.setFolderId(api.getFolderId());
            ws.setFilename(api.getFilename());
            ws.setFiledata(api.getFiledata());
            ws.setFilesize(mapIntegerToString(api.getFilesize()));
            ws.setMimetype(api.getMimetype());
            ws.setDescription(api.getDescription());
            ws.setKeywords(api.getKeywords());
            ws.setChecksum(api.getChecksum());
            ws.setModified(map(api.getModified()));
            ws.setCreated(map(api.getCreated()));
        }

        return ws;
    }

    // =========================================================================
    // Conversion of Collections
    // =========================================================================

    static List<Group> mapWSGroupCollection(final Collection<net.iaeste.iws.ws.Group> ws) {
        final List<Group> api;

        if (ws != null) {
            api = new ArrayList<>(ws.size());
            for (final net.iaeste.iws.ws.Group group : ws) {
                api.add(map(group));
            }
        } else {
            api = new ArrayList<>(0);
        }

        return api;
    }

    static List<net.iaeste.iws.ws.Group> mapAPIGroupCollection(final Collection<Group> api) {
        final List<net.iaeste.iws.ws.Group> ws;

        if (api != null) {
            ws = new ArrayList<>(api.size());

            for (final Group group : api) {
                ws.add(map(group));
            }
        } else {
            ws = new ArrayList<>(0);
        }

        return ws;
    }

    static List<UserGroup> mapWSUserGroupCollection(final Collection<net.iaeste.iws.ws.UserGroup> ws) {
        final List<UserGroup> api;

        if (ws != null) {
            api = new ArrayList<>(ws.size());

            for (final net.iaeste.iws.ws.UserGroup userGroup : ws) {
                api.add(map(userGroup));
            }
        } else {
            api = new ArrayList<>(0);
        }

        return api;
    }

    static List<File> mapWSFileCollection(final List<net.iaeste.iws.ws.File> ws) {
        final List<File> api;

        if (ws != null) {
            api = new ArrayList<>(ws.size());

            for (final net.iaeste.iws.ws.File file : ws) {
                api.add(map(file));
            }
        } else {
            api = new ArrayList<>(0);
        }

        return api;
    }

    static List<net.iaeste.iws.ws.File> mapAPIFileCollection(final List<File> api) {
        final List<net.iaeste.iws.ws.File> ws;

        if (api != null) {
            ws = new ArrayList<>(api.size());

            for (final File file : api) {
                ws.add(map(file));
            }
        } else {
            ws = new ArrayList<>(0);
        }

        return ws;
    }

    static ArrayList<String> mapStringCollection(final Collection<String> source) {
        // It may seem stupid to map a Collection into a different Collection,
        // but we need this, to ensure that we do not get any pesky NPE's!
        return (source != null) ? new ArrayList<>(source) : new ArrayList<String>(0);
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

        if ((ws != null) && (ws.getFromDate() != null) && (ws.getToDate() != null)) {
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
    // Shared Exchange related Conversions
    // =========================================================================

    static Set<FieldOfStudy> mapFieldOfStudyCollection(final Collection<net.iaeste.iws.ws.FieldOfStudy> source) {
        final Set<FieldOfStudy> set = EnumSet.noneOf(FieldOfStudy.class);

        if (source != null) {
            for (final net.iaeste.iws.ws.FieldOfStudy fieldOfStudy : source) {
                set.add(map(fieldOfStudy));
            }
        }

        return set;
    }

    static Collection<net.iaeste.iws.ws.FieldOfStudy> mapAPIFieldOfStudyCollection(final Collection<FieldOfStudy> source) {
        final Set<net.iaeste.iws.ws.FieldOfStudy> set = EnumSet.noneOf(net.iaeste.iws.ws.FieldOfStudy.class);

        if (source != null) {
            for (final FieldOfStudy fieldOfStudy : source) {
                set.add(map(fieldOfStudy));
            }
        }

        return set;
    }

    static Set<StudyLevel> mapStudyLevelCollectionToSet(final Collection<net.iaeste.iws.ws.StudyLevel> source) {
        final Set<StudyLevel> set = EnumSet.noneOf(StudyLevel.class);

        if (source != null) {
            for (final net.iaeste.iws.ws.StudyLevel studyLevel : source) {
                set.add(map(studyLevel));
            }
        }

        return set;
    }

    static Collection<net.iaeste.iws.ws.StudyLevel> mapStudyLevelCollection(final Collection<StudyLevel> source) {
        final Set<net.iaeste.iws.ws.StudyLevel> set = EnumSet.noneOf(net.iaeste.iws.ws.StudyLevel.class);

        if (source != null) {
            for (final StudyLevel studyLevel : source) {
                set.add(map(studyLevel));
            }
        }

        return set;
    }

    private static FieldOfStudy map(final net.iaeste.iws.ws.FieldOfStudy ws) {
        return (ws != null) ? FieldOfStudy.valueOf(ws.value()) : null;
    }

    private static net.iaeste.iws.ws.FieldOfStudy map(final FieldOfStudy api) {
        return (api != null) ? net.iaeste.iws.ws.FieldOfStudy.valueOf(api.name()) : null;
    }

    // =========================================================================
    // Internal Conversion of some WebService specific things
    // =========================================================================

    private static String mapIntegerToString(final Integer value) {
        return String.valueOf(value);
    }

    private static Integer mapStringToInteger(final String str) {
        Integer value;

        try {
            value = Integer.valueOf(str);
        } catch (NumberFormatException ignore) {
            value = null;
        }

        return value;
    }

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
