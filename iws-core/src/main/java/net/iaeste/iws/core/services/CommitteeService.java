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
package net.iaeste.iws.core.services;

import static net.iaeste.iws.common.utils.LogUtil.formatLogMessage;
import static net.iaeste.iws.common.utils.StringUtils.toLower;
import static net.iaeste.iws.core.transformers.CommonTransformer.transform;

import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.dtos.UserGroup;
import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.MailReply;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.CountrySurveyRequest;
import net.iaeste.iws.api.requests.FetchCommitteeRequest;
import net.iaeste.iws.api.requests.FetchCountrySurveyRequest;
import net.iaeste.iws.api.requests.FetchInternationalGroupRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.responses.CommitteeResponse;
import net.iaeste.iws.api.responses.FetchCommitteeResponse;
import net.iaeste.iws.api.responses.FetchCountrySurveyResponse;
import net.iaeste.iws.api.responses.FetchInternationalGroupResponse;
import net.iaeste.iws.common.configuration.InternalConstants;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.common.exceptions.IllegalActionException;
import net.iaeste.iws.common.exceptions.NotImplementedException;
import net.iaeste.iws.common.exceptions.VerificationException;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.common.utils.PasswordGenerator;
import net.iaeste.iws.common.utils.StringUtils;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.core.transformers.AdministrationTransformer;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.CommitteeDao;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import net.iaeste.iws.persistence.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class CommitteeService extends CommonService<CommitteeDao> {

    private static final Logger LOG = LoggerFactory.getLogger(CommitteeService.class);
    private static final Pattern PATTERN_SEPARATOR = Pattern.compile(", ", Pattern.LITERAL);

    private final Notifications notifications;

    public CommitteeService(final Settings settings, final CommitteeDao dao, final Notifications notifications) {
        super(settings, dao);

        this.notifications = notifications;
    }

    // =========================================================================
    // Fetch & Process Committee logic
    // =========================================================================

    /**
     * This method will fetch a list of Committees, or National Groups, based on
     * the given parameters in the Request Object. The Request has two mutually
     * exclusive parameters, CountryIds and Membership, meaning that only one of
     * them may be set. If neither is set - we're simply assuming that all
     * Committees matching the required Statuses.<br />
     *   Additionally, the result can be limited by the status values, where it
     * is possible to request either Active Committees. Suspended Committees or
     * both.
     *
     * @param request Request Object
     * @return Response Object with the Committees matching the request
     */
    public FetchCommitteeResponse fetchCommittees(final FetchCommitteeRequest request) {
        final List<String> countryIds = request.getCountryIds();
        final Membership membership = request.getMembership();
        final Set<GroupStatus> statuses = request.getStatuses();

        final List<UserGroupEntity> found;
        if (countryIds != null) {
            found = dao.findCommitteesByContryIds(countryIds, statuses);
        } else if (membership != null) {
            found = dao.findCommitteesByMembership(membership, statuses);
        } else {
            found = dao.findGroupsByTypeAndStatuses(GroupType.NATIONAL, request.getStatuses());
        }
        final List<UserGroup> result = AdministrationTransformer.transformMembers(found);

        return new FetchCommitteeResponse(result);
    }

    /**
     * This method will handle all the different Business Cases for processing
     * Committees. Meaning, Create, Update, Merge, Upgrade, Activate, Suspend
     * and Delete said Committees.
     *
     * @param authentication User authentication Object
     * @param request        Request Object
     * @return Response Object with National Secretary and Error Information
     */
    public CommitteeResponse processCommittee(final Authentication authentication, final CommitteeRequest request) {
        final UserGroupEntity nsStaff;

        switch (request.getAction()) {
            case CREATE:
                nsStaff = createCommittee(authentication, request);
                break;
            case CHANGE_NS:
                nsStaff = changeNsForCommittee(authentication, request);
                break;
            case UPGRADE:
                nsStaff = upgradeCommittee(authentication, request);
                break;
            case ACTIVATE:
                nsStaff = activateCommittee(authentication, request);
                break;
            case SUSPEND:
                nsStaff = suspendCommittee(authentication, request);
                break;
            case DELETE:
                nsStaff = deleteCommittee(authentication, request);
                break;
            case UPDATE:
            case MERGE:
            default:
                throw new NotImplementedException("Method not implemented, due to missing specifications.");
        }

        final UserGroup userGroup = AdministrationTransformer.transform(nsStaff);
        return new CommitteeResponse(userGroup);
    }

    /**
     *
     * @param authentication User authentication Object
     * @param request        Request Object
     * @return National Secretary
     */
    private UserGroupEntity createCommittee(final Authentication authentication, final CommitteeRequest request) {
        // If we don't find a Country, an exception is thrown!
        final CountryEntity country = dao.findCountry(request.getCountryCode());
        final UserGroupEntity nsStaff;

        if ((country.getMembership() != Membership.ASSOCIATE_MEMBER) && (country.getMembership() != Membership.FULL_MEMBER)) {
            final String groupName = country.getCountryName() + ", " + request.getInstitutionAbbreviation();
            if (country.getMembership() == Membership.COOPERATING_INSTITUTION) {
                final GroupEntity group = dao.findGroupByNameAndType(groupName, GroupType.NATIONAL);
                if (group == null) {
                    // No Committee exists with the name, which is good, as
                    // we can then create a new Cooperating Institution
                    nsStaff = doCreateCommittee(authentication, request, country, groupName);
                } else {
                    throw new IllegalActionException("A Committee with the name " + groupName + " already exist.");
                }
            } else {
                // Country is currently not a Cooperating Institution, so
                // we'll create the first new one for it
                nsStaff = doCreateCommittee(authentication, request, country, groupName);
                country.setMembership(Membership.COOPERATING_INSTITUTION);
                country.setMemberSince(Calendar.getInstance().get(Calendar.YEAR));
                dao.persist(authentication, country);
            }
        } else {
            throw new IllegalActionException("Cannot create a new Cooperating Institution for a Member Country.");
        }

        return nsStaff;
    }

    private UserGroupEntity doCreateCommittee(final Authentication authentication, final CommitteeRequest request, final CountryEntity country, final String groupname) {
        final RoleEntity owner = dao.findRole(InternalConstants.ROLE_OWNER);
        final UserEntity ns = createNationalSecretary(authentication, owner, request);
        final GroupEntity members = createGroup(authentication, authentication.getGroup(), country, GroupType.MEMBER, groupname, request.getInstitutionName());
        final GroupEntity staff = createGroup(authentication, members, country, GroupType.NATIONAL, groupname, request.getInstitutionName());
        final UserGroupEntity nsMembers = new UserGroupEntity(ns, members, owner);
        final UserGroupEntity nsStaff = new UserGroupEntity(ns, staff, owner);
        dao.persist(authentication, nsMembers);
        dao.persist(authentication, nsStaff);

        return nsStaff;
    }

    private GroupEntity createGroup(final Authentication authentication, final GroupEntity parent, final CountryEntity country, final GroupType type, final String groupName, final String institution) {
        final GroupEntity group = new GroupEntity();
        group.setGroupName(groupName);
        group.setFullName(groupName + ' ' + type.getDescription());
        group.setDescription(country.getCountryName() + ' ' + institution);
        group.setGroupType(dao.findGroupTypeByType(type));
        group.setParentId(parent.getId());
        group.setExternalParentId(parent.getExternalId());
        group.setListName(toLower(PATTERN_SEPARATOR.matcher(groupName).replaceAll(Matcher.quoteReplacement("_"))));
        group.setPublicList(type.getMayHavePublicMailinglist());
        group.setPrivateList(type.getMayHavePrivateMailinglist());
        group.setPrivateReplyTo(MailReply.REPLY_TO_LIST);
        group.setPublicReplyTo(MailReply.REPLY_TO_SENDER);
        group.setCountry(country);
        dao.persist(authentication, group);

        return group;
    }

    /**
     * Created a new National Secretary based on the given information about
     * username, firstname and lastname. The new NS will also be notified of
     * the account.
     *
     * @param authentication User authentication Object
     * @param owner          Owner Role
     * @param request        Request Object
     * @return Newly Persisted User Entity
     */
    private UserEntity createNationalSecretary(final Authentication authentication, final RoleEntity owner, final CommitteeRequest request) {
        final UserEntity existing = dao.findUserByUsername(request.getUsername());
        if (existing == null) {
            final UserEntity user = createAndPersistUserEntity(authentication, request.getUsername(), null, request.getFirstname(), request.getLastname(), false);
            final GroupEntity privateGroup = createAndPersistPrivateGroup(user);
            final UserGroupEntity privateUserGroup = new UserGroupEntity(user, privateGroup, owner);
            dao.persist(privateUserGroup);

            // Now, we can send of Notifications about a new User Account and
            // ensure that the e-mail alias is being properly processed
            notifications.notify(authentication, user, NotificationType.ACTIVATE_NEW_USER);
            notifications.notify(authentication, user, NotificationType.NEW_USER);
            notifications.notify(authentication, user, NotificationType.PROCESS_EMAIL_ALIAS);

            return user;
        } else {
            throw new IllegalActionException("Cannot create National Secretary for the new Committee, as the username is already in the system.");
        }
    }

    /**
     * Changing the National Secretary involves a series of checks...
     *
     * @param authentication User authentication Object
     * @param request        Request Object
     * @return National Secretary
     */
    private UserGroupEntity changeNsForCommittee(final Authentication authentication, final CommitteeRequest request) {
        final GroupEntity staff = dao.findGroupByExternalId(request.getNationalCommittee().getGroupId());
        final UserGroupEntity nsStaff;

        if (staff != null) {
            if (staff.getStatus() == GroupStatus.ACTIVE) {
                final GroupEntity member = dao.findMemberGroupForStaff(staff);
                nsStaff = changeNsForActiveCommittee(authentication, request, staff, member);
            } else {
                throw new IllegalActionException("Attempting to change National Secretary for a suspended Committee, is not allowed.");
            }
        } else {
            throw new IllegalActionException("Attempting to change National Secretary non-existing Committee.");
        }

        return nsStaff;
    }

    private UserGroupEntity changeNsForActiveCommittee(final Authentication authentication, final CommitteeRequest request, final GroupEntity staff, final GroupEntity member) {
        final UserGroupEntity nsStaff;

        if (request.getNationalSecretary() != null) {
            final UserEntity user = dao.findUserByExternalId(request.getNationalSecretary().getUserId());
            if (user != null) {
                nsStaff = makeUserNationalSecretary(authentication, user, member, staff);
            } else {
                throw new IllegalActionException("National Secretary provided is not a valid User.");
            }
        } else if ((request.getFirstname() != null) && (request.getLastname() != null) && (request.getUsername() != null)) {
            final UserEntity existing = dao.findUserByUsername(request.getUsername());
            if (existing == null) {
                nsStaff = makeUserNationalSecretary(authentication, request, member, staff);
            } else {
                throw new IllegalActionException("Cannot create new National Secretary for existing User.");
            }
        } else {
            throw new IllegalActionException("No new National Secretary information provided.");
        }

        return nsStaff;
    }

    private UserGroupEntity makeUserNationalSecretary(final Authentication authentication, final UserEntity user, final GroupEntity member, final GroupEntity staff) {
        final UserGroupEntity nsStaff;

        final UserGroupEntity memberEntity = findGroupMember(user, member);
        if (!Objects.equals(memberEntity.getRole().getId(), InternalConstants.ROLE_OWNER)) {
            // First, we'll downgrade the existing Owner to Moderator
            final RoleEntity moderator = dao.findRole(InternalConstants.ROLE_MODERATOR);
            changeExistingOwnerRole(authentication, moderator, member, staff);

            // Now we can upgrade the role for the new Owner
            nsStaff = dao.findUserGroupRelation(staff, user);
            final RoleEntity owner = dao.findRole(InternalConstants.ROLE_OWNER);
            memberEntity.setRole(owner);
            nsStaff.setRole(owner);
            dao.persist(authentication, memberEntity);
            dao.persist(authentication, nsStaff);
        } else {
            throw new IllegalActionException("Attempting to make existing National Secretary new National Secretary is not allowed.");
        }

        return nsStaff;
    }

    private UserGroupEntity findGroupMember(final UserEntity user, final GroupEntity member) {
        try {
            return dao.findUserGroupRelation(member, user);
        } catch (IdentificationException e) {
            // We're converting the Identification Exception here, since we
            // have a more specific reason. We're deliberately ignoring the
            // caught Exception, since it will result in incorrect errors.
            LOG.debug("Converting Identification Exception to IllegalAction Exception: {}", e.getMessage(), e);
            throw new IllegalActionException("New National Secretary is not a member of the Committee.");
        }
    }

    private UserGroupEntity makeUserNationalSecretary(final Authentication authentication, final CommitteeRequest request, final GroupEntity member, final GroupEntity staff) {
        // First, we'll downgrade the existing Owner to Moderator
        final RoleEntity moderator = dao.findRole(InternalConstants.ROLE_MODERATOR);
        changeExistingOwnerRole(authentication, moderator, member, staff);

        final RoleEntity owner = dao.findRole(InternalConstants.ROLE_OWNER);
        final UserEntity user = new UserEntity();
        // First, the Password. If no password is specified, then we'll generate
        // one. Regardless, the password is set in the UserEntity, for the
        // Notification
        final String password = PasswordGenerator.generatePassword();

        // As we doubt that a user will provide enough entropy to enable us to
        // generate a hash value that cannot be looked up in rainbow tables,
        // we're "salting" it, and additionally storing the the random part of
        // the salt in the Entity as well, the hardcoded part of the Salt is
        // stored in the Hashcode Generator
        final String salt = UUID.randomUUID().toString();

        // Now, set all the information about the user and persist the Account
        user.setUsername(request.getUsername());
        user.setTemporary(password);
        user.setPassword(hashcodeGenerator.generateHash(password, salt));
        user.setSalt(salt);
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setAlias(generateUserAlias(request));
        user.setCode(generateActivationCode(request));
        user.setPerson(createEmptyPerson(authentication));
        dao.persist(authentication, user);

        final GroupEntity privateGroup = createAndPersistPrivateGroup(authentication, user);
        final UserGroupEntity privateUserGroup = new UserGroupEntity(user, privateGroup, owner);
        dao.persist(authentication, privateUserGroup);

        final UserGroupEntity memberGroup = new UserGroupEntity(user, member, owner);
        dao.persist(authentication, memberGroup);
        final UserGroupEntity staffGroup = new UserGroupEntity(user, staff, owner);
        dao.persist(authentication, staffGroup);

        notifications.notify(authentication, user, NotificationType.NEW_USER);
        notifications.notify(authentication, user, NotificationType.PROCESS_EMAIL_ALIAS);
        notifications.notify(authentication, user, NotificationType.ACTIVATE_NEW_USER);

        return staffGroup;
    }

    private void changeExistingOwnerRole(final Authentication authentication, final RoleEntity role, final GroupEntity... groups) {
        if (groups != null) {
            for (final GroupEntity group : groups) {
                final UserGroupEntity currentOwner = dao.findGroupOwner(group);
                currentOwner.setRole(role);
                currentOwner.setTitle("");
                dao.persist(authentication, currentOwner);
            }
        }
    }

    private String generateUserAlias(final CommitteeRequest request) {
        final String name = StringUtils.convertToAsciiMailAlias(request.getFirstname() + '.' + request.getLastname());
        final String address = '@' + settings.getPublicMailAddress();

        final Long serialNumber = dao.findNumberOfAliasesForName(name);
        final String alias;
        if ((serialNumber != null) && (serialNumber > 0)) {
            alias = name + (serialNumber + 1) + address;
        } else {
            alias = name + address;
        }

        return alias;
    }

    private String generateActivationCode(final CommitteeRequest request) {
        final String clear = request.getUsername()
                           + request.getFirstname()
                           + request.getLastname();

        return hashcodeGenerator.generateHash(clear, UUID.randomUUID().toString());
    }

    private GroupEntity createAndPersistPrivateGroup(final Authentication authentication, final UserEntity user) {
        final GroupEntity group = new GroupEntity();

        group.setGroupName(user.getFirstname() + ' ' + user.getLastname());
        group.setGroupType(dao.findGroupTypeByType(GroupType.PRIVATE));
        group.setPublicList(GroupType.PRIVATE.getMayHavePublicMailinglist());
        group.setPrivateList(GroupType.PRIVATE.getMayHavePrivateMailinglist());
        group.setPrivateReplyTo(MailReply.REPLY_TO_LIST);
        group.setPublicReplyTo(MailReply.REPLY_TO_SENDER);
        dao.persist(authentication, group);

        return group;
    }

    /**
     * Upgrades a Committee to the next membership level.
     *
     * @param authentication User authentication Object
     * @param request        Request Object
     * @return National Secretary
     */
    private UserGroupEntity upgradeCommittee(final Authentication authentication, final CommitteeRequest request) {
        final GroupEntity staff = dao.findGroupByExternalId(request.getNationalCommittee().getGroupId());

        if (staff != null) {
            if (staff.getStatus() == GroupStatus.ACTIVE) {
                final CountryEntity country = staff.getCountry();

                if (country.getMembership() == Membership.COOPERATING_INSTITUTION) {
                    updateActiveCooperatingInstitution(authentication, country);
                } else if (country.getMembership() == Membership.ASSOCIATE_MEMBER) {
                    country.setMembership(Membership.FULL_MEMBER);
                    dao.persist(authentication, country);
                    LOG.info(formatLogMessage(authentication, "Country %s has been upgraded from %s to %s.", country.getCountryName(), Membership.ASSOCIATE_MEMBER, Membership.FULL_MEMBER));
                } else {
                    throw new IllegalActionException("Attempting to upgrade a Committee, which is already a Full Member.");
                }
            } else {
                throw new IllegalActionException("Attempting to upgrade a non-Active Committee, is not allowed.");
            }
        } else {
            throw new IllegalActionException("Attempting to upgrade non-existing Committee.");
        }

        return dao.findGroupOwner(staff);
    }

    private void updateActiveCooperatingInstitution(final Authentication authentication, final CountryEntity country) {
        final List<GroupEntity> staffs = dao.findAllCommitteesForCountry(country);

        if (staffs.size() == 1) {
            country.setMembership(Membership.ASSOCIATE_MEMBER);
            dao.persist(authentication, country);
            LOG.info(formatLogMessage(authentication, "Country %s has been upgraded from %s to %s.", country.getCountryName(), Membership.COOPERATING_INSTITUTION, Membership.ASSOCIATE_MEMBER));
        } else {
            throw new IllegalActionException("Attempting to upgrade a Cooperating Institution to Associate Membership, while other Cooperating Institutions still exist for the Country.");
        }
    }

    /**
     * Activates a previously suspended Committee again. This includes all the
     * Members of the Committee.
     *
     * @param authentication User authentication Object
     * @param request        Request Object
     * @return National Secretary
     */
    private UserGroupEntity activateCommittee(final Authentication authentication, final CommitteeRequest request) {
        final GroupEntity staff = dao.findGroupByExternalId(request.getNationalCommittee().getGroupId());
        final UserGroupEntity nsStaff;

        if (staff != null) {
            if (staff.getStatus() == GroupStatus.SUSPENDED) {
                final GroupEntity member = dao.findMemberGroupForStaff(staff);

                // Change the Status for the Groups to Active
                changeStatusForGroup(authentication, member, GroupStatus.ACTIVE);
                changeStatusForGroup(authentication, staff, GroupStatus.ACTIVE);

                // Finally, let's just return the current National Secretary
                nsStaff = dao.findGroupOwner(staff);
            } else {
                throw new IllegalActionException("Cannot activate an already active Committee.");
            }
        } else {
            throw new IllegalActionException("Attempting to activate non-existing Committee.");
        }

        return nsStaff;
    }

    /**
     * Suspends a currently active Committee. This includes deleting all
     * accounts with status new and suspend the remaining.
     *
     * @param authentication User authentication Object
     * @param request        Request Object
     * @return National Secretary
     */
    private UserGroupEntity suspendCommittee(final Authentication authentication, final CommitteeRequest request) {
        final GroupEntity staff = dao.findGroupByExternalId(request.getNationalCommittee().getGroupId());
        final UserGroupEntity nsStaff;

        if (staff != null) {
            if (staff.getStatus() == GroupStatus.ACTIVE) {
                final GroupEntity member = dao.findMemberGroupForStaff(staff);

                // Change the Status for the Groups to Active
                changeStatusForGroup(authentication, member, GroupStatus.SUSPENDED);
                changeStatusForGroup(authentication, staff, GroupStatus.SUSPENDED);

                // Finally, let's just return the current National Secretary
                nsStaff = dao.findGroupOwner(staff);
            } else {
                throw new IllegalActionException("Cannot suspend an already suspended Committee.");
            }
        } else {
            throw new IllegalActionException("Attempting to suspend non-existing Committee.");
        }

        return nsStaff;
    }

    /**
     * Deletes a currently suspended Committee. This includes deleting all
     * member accounts, data and delete subgroups.
     *
     * @param authentication User authentication Object
     * @param request        Request Object
     * @return National Secretary
     */
    private UserGroupEntity deleteCommittee(final Authentication authentication, final CommitteeRequest request) {
        final GroupEntity staff = dao.findGroupByExternalId(request.getNationalCommittee().getGroupId());

        if (staff != null) {
            if (staff.getStatus() == GroupStatus.SUSPENDED) {
                final CountryEntity country = staff.getCountry();
                final GroupEntity member = dao.findMemberGroupForStaff(staff);

                // Now, we will attempt to delete all Subgroups, doing so will
                // only set the status flag, as the only data currently present
                // in the system is Offers and derived data, which we cannot
                // delete, User accounts will automatically ve cleaned up by the
                // StateBean.
                deleteGroupStructure(authentication, member);

                // Finally, set status of the Country to former member, but
                // only if no other Committees exist for the Country.
                final List<GroupEntity> staffs = dao.findAllCommitteesForCountry(country);
                if (staffs.isEmpty()) {
                    country.setMembership(Membership.FORMER_MEMBER);
                    dao.persist(authentication, country);
                }
            } else {
                throw new IllegalActionException("Cannot delete an active Committee.");
            }
        } else {
            throw new IllegalActionException("Attempting to delete non-existing Committee.");
        }

        // When a Committee is deleted, we don't have an NS anymore - so let's
        // just return null here.
        return null;
    }

    private void changeStatusForGroup(final Authentication authentication, final GroupEntity group, final GroupStatus status) {
        group.setStatus(status);
        dao.persist(authentication, group);
    }

    /**
     * Attempts to delete a Group and its subgroups recursively.
     *
     * @param authentication User authentication Object
     * @param group          Group to delete subgroups from
     */
    private void deleteGroupStructure(final Authentication authentication, final GroupEntity group) {
        // First, recursively remove all sub groups. Since the structure of
        // Groups is following a strict hierarchy, this is perfectly safe
        final List<GroupEntity> subgroups = dao.findSubgroups(group);

        for (final GroupEntity entity : subgroups) {
            deleteGroupStructure(authentication, entity);
        }

        // Finally, delete the actual Group. At the moment, no data is
        // associated with Groups except Offers and derived data, which
        // cannot be deleted. So deleting simply means setting the status
        // flag and save the entity
        group.setStatus(GroupStatus.DELETED);
        dao.persist(authentication, group);
    }

    // =========================================================================
    // Fetch & Process International Group logic
    // =========================================================================

    /**
     * Retrieves a List of International Groups with their respective Owners or
     * Coordinators. The list will consists of those International Groups,
     * matching the requested, i.e. Active and/or Suspended.
     *
     * @param request Request Object
     * @return List of International Groups, matching the request
     */
    public FetchInternationalGroupResponse fetchInternationalGroups(final FetchInternationalGroupRequest request) {
        final List<UserGroupEntity> found = dao.findGroupsByTypeAndStatuses(GroupType.INTERNATIONAL, request.getStatuses());
        final List<UserGroup> result = AdministrationTransformer.transformMembers(found);

        return new FetchInternationalGroupResponse(result);
    }

    /**
     * This will handle the actual processing of International Groups. If the
     * given request is not consistent enough to run the request, then various
     * IWS derived Exceptions will be thrown. Otherwise, the requested
     * processing will take place.
     *
     * @param request Request Object
     */
    public void processInternationalGroup(final Authentication authentication, final InternationalGroupRequest request) {
        final Group group = request.getGroup();
        final User user = request.getUser();

        if (group.getGroupId() != null) {
            updateInternationalGroup(authentication, group, user, request.getStatus());
        } else if (user != null) {
            createNewInternationalGroup(authentication, group, user);
        } else {
            throw new VerificationException("Attempting to greate a new International Group failed as no Coordinator was provided.");
        }
    }

    /**
     * Creates a new International Group, provided that the preconditions is in
     * place, i.e. that another Group doesn't already exist with this name and
     * that the Coordinator is an existing user. If either of these checks is
     * failing, then an IWS derived Exception will be thrown.
     *
     * @param authentication User Authentication Object
     * @param group          Group to be created
     * @param user           Group Coordinator to be set
     */
    private void createNewInternationalGroup(final Authentication authentication, final Group group, final User user) {
        // First, we'll check if a group already exist with the same name, if
        // so - then we will throw an Exception.
        final GroupEntity existing = dao.findGroupByName(group.getGroupName());
        if (existing == null) {
            // No problem, group is find - now, we need to find the user that is
            // suppose to be the new Coordinator. If we cannot find the user,
            // then we'll drop out with another Exception.
            final UserEntity userEntity = dao.findUserByExternalId(user.getUserId());
            if (userEntity != null) {
                // All good, now we can create the new International Group :-)
                final GroupEntity groupEntity = doCreateTheInternationalGroup(authentication, group);
                createGroupCoordinator(authentication, groupEntity, userEntity);
                notifications.notify(authentication, groupEntity, NotificationType.NEW_GROUP);
                notifications.notify(authentication, userEntity, NotificationType.NEW_GROUP_OWNER);
                LOG.info(formatLogMessage(authentication, "Created new International Group %s with Coordinator %s", group.getGroupName(), user.getFirstname() + ' ' + user.getLastname()));
            } else {
                throw new VerificationException("Attempting to create a new International Group failed as no Coordinator provided doesn't exist.");
            }
        } else {
            throw new PersistenceException("Group cannot be created, another Group with the same name exists.");
        }
    }

    /**
     * Creates a new GroupEntity, based on the given information and returns it
     * after it has been persisted.
     *
     * @param authentication User Authentication information
     * @param group          Group to create
     * @return New Group Entity
     */
    private GroupEntity doCreateTheInternationalGroup(final Authentication authentication, final Group group) {
        final GroupEntity groupEntity = new GroupEntity();

        // From the given Group Object, we're only using the name and
        // description, the rest is omitted or set to the default
        groupEntity.setGroupName(group.getGroupName().trim());
        groupEntity.setListName(toLower(group.getGroupName()));
        groupEntity.setFullName(group.getFullName().trim());
        groupEntity.setDescription(group.getDescription());

        // The GroupType must be International
        groupEntity.setGroupType(dao.findGroupTypeByType(GroupType.INTERNATIONAL));
        // The Parent is set to the Group from the Authentication Object
        groupEntity.setParentId(authentication.getGroup().getId());
        groupEntity.setExternalParentId(authentication.getGroup().getExternalId());
        groupEntity.setPrivateReplyTo(MailReply.REPLY_TO_LIST);
        groupEntity.setPublicReplyTo(MailReply.REPLY_TO_SENDER);

        // Save the new Group in the database
        dao.persist(authentication, groupEntity);
        return groupEntity;
    }

    /**
     * Updates an International Group, meaning that it will either Delete,
     * Suspend or Activate and/or reassign Coordinator.
     *
     * @param authentication User Authentication Information
     * @param group          Group to update
     * @param user           Coordinator to assign
     * @param status         New Group Status
     */
    private void updateInternationalGroup(final Authentication authentication, final Group group, final User user, final GroupStatus status) {
        final GroupEntity groupEntity = dao.findGroupByExternalId(group.getGroupId());
        if (groupEntity != null ) {
            if (status == GroupStatus.SUSPENDED) {
                // No need to suspend an already suspended Group
                if (groupEntity.getStatus() == GroupStatus.ACTIVE) {
                    // Suspension means just that it will be suspended, and thus
                    // no longer be usable by anyone.
                    groupEntity.setStatus(GroupStatus.SUSPENDED);
                    dao.persist(authentication, groupEntity);
                }
            } else if (status == GroupStatus.DELETED) {
                // International Groups doesn't have any data, meaning they are
                // purely a forum, and thus we simply update the status and
                // save this. However, to ensure that anoth group can later on
                // be created with the same information, we have change the
                // full-name to something which won't cause problems. Even as
                // it may mean that we loose information!
                groupEntity.setFullName(UUID.randomUUID().toString());
                groupEntity.setStatus(GroupStatus.DELETED);
                dao.persist(authentication, groupEntity);
            } else {
                // Defaulting to just updating the Group and set the status to
                // Active, it is irrelevant if the status was either Active or
                // Suspended before. And finally, check if we have to reassign
                // the Coordinator.
                groupEntity.setStatus(GroupStatus.ACTIVE);
                dao.persist(authentication, groupEntity, transform(group));
                reassignCoordinatorIfRequested(authentication, groupEntity, user);
            }
        } else {
            throw new IdentificationException("Attempting to update International Group which doesn't exist.");
        }
    }

    private void reassignCoordinatorIfRequested(final Authentication authentication, final GroupEntity groupEntity, final User user) {
        if (user != null) {
            final UserEntity userEntity = dao.findUserByExternalId(user.getUserId());
            if (userEntity != null) {
                // Okay, we need to reassign the Coordinator role, meaning that
                // we will downgrade the existing Owner. However, we also have
                // to check if the new Owner is already a member of the Group,
                // if so, we'll alter the relation otherwise we have to create
                // a new
                final UserGroupEntity oldCoordinator = dao.findGroupOwner(groupEntity);
                final UserGroupEntity newCoordinator = dao.findExistingRelation(groupEntity, userEntity);

                if (newCoordinator == null) {
                    createGroupCoordinator(authentication, groupEntity, userEntity);
                } else {
                    // New Coordinator is already a member of the Group, so we
                    // just have to set the correct Role and save the merged
                    // result, which our Persist method will ensure is done
                    newCoordinator.setRole(oldCoordinator.getRole());
                    dao.persist(authentication, newCoordinator, oldCoordinator);
                }

                // Now we can downgrade the former Coordinator.
                oldCoordinator.setRole(dao.findRole(InternalConstants.ROLE_MODERATOR));
                oldCoordinator.setTitle("Former " + oldCoordinator.getTitle());
                dao.persist(authentication, oldCoordinator);
                notifications.notify(authentication, groupEntity, NotificationType.CHANGE_IN_GROUP_MEMBERS);
                notifications.notify(authentication, userEntity, NotificationType.NEW_GROUP_OWNER);
            }
        }
    }

    /**
     * Creates a new Group Coordinator for the given International Group.
     *
     * @param authentication User Authentication Information
     * @param group          International Group to create a Coordinator for
     * @param user           The new Coordinator
     */
    private void createGroupCoordinator(final Authentication authentication, final GroupEntity group, final UserEntity user) {
        final UserGroupEntity userGroup = new UserGroupEntity();
        userGroup.setGroup(group);
        userGroup.setUser(user);
        userGroup.setOnPublicList(Boolean.TRUE);
        userGroup.setOnPrivateList(Boolean.TRUE);
        userGroup.setWriteToPrivateList(Boolean.TRUE);
        userGroup.setRole(dao.findRole(InternalConstants.ROLE_OWNER));
        userGroup.setTitle(group.getGroupName() + " Coordinator");

        dao.persist(authentication, userGroup);
    }

    // =========================================================================
    // Fetch & Process Survey of Country logic
    // =========================================================================

    /**
     * Fetch Survey of Countries.
     *
     * @param authentication User Authentication Information
     * @param request        Request Object
     * @return Response Object with the survey data.
     */
    public FetchCountrySurveyResponse fetchCountrySurvey(final Authentication authentication, final FetchCountrySurveyRequest request) {
        LOG.debug("fetchCountrySurvey Request from '{}' with Request '{}'.", authentication, request);
        throw new NotImplementedException("Method pending implementation, see Trac #924.");
    }

    public void processCountrySurvey(final Authentication authentication, final CountrySurveyRequest request) {
        LOG.debug("processCountrySurvey Request from '{}' with Request '{}'.", authentication, request);
        throw new NotImplementedException("Method pending implementation, see Trac #924.");
    }
}
