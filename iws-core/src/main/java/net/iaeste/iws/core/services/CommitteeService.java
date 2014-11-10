/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.CommitteeService
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.services;

import static net.iaeste.iws.common.utils.StringUtils.toLower;
import static net.iaeste.iws.core.transformers.CommonTransformer.transform;
import static net.iaeste.iws.core.util.LogUtil.formatLogMessage;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.dtos.UserGroup;
import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.FetchCommitteeRequest;
import net.iaeste.iws.api.requests.FetchInternationalGroupRequest;
import net.iaeste.iws.api.requests.FetchSurveyOfCountryRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.requests.SurveyOfCountryRequest;
import net.iaeste.iws.api.responses.FetchCommitteeResponse;
import net.iaeste.iws.api.responses.FetchInternationalGroupResponse;
import net.iaeste.iws.api.responses.FetchSurveyOfCountryRespose;
import net.iaeste.iws.common.exceptions.IllegalActionException;
import net.iaeste.iws.core.transformers.AdministrationTransformer;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.CommitteeDao;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import net.iaeste.iws.persistence.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class CommitteeService {

    private static final Logger log = LoggerFactory.getLogger(CommitteeService.class);

    private final CommitteeDao dao;

    public CommitteeService(final CommitteeDao dao) {
        this.dao = dao;
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
     */
    public void processCommittee(final Authentication authentication, final CommitteeRequest request) {
        switch (request.getAction()) {
            case CREATE:
                createCommittee(authentication, request);
                break;
            case CHANGE_NS:
                changeNsForCommittee(authentication, request);
                break;
            case MERGE:
                mergeCommittees(authentication, request);
                break;
            case UPGRADE:
                upgradeCommittee(authentication, request);
                break;
            case ACTIVATE:
                activateCommittee(authentication, request);
                break;
            case SUSPEND:
                suspendCommittee(authentication, request);
                break;
            case DELETE:
                deleteCommittee(authentication, request);
                break;
        }
    }

    private void createCommittee(final Authentication authentication, final CommitteeRequest request) {
        final CountryEntity country = dao.findCountry(request.getCountryId());
        if (country != null) {
            if ((country.getMembership() != Membership.ASSOCIATE_MEMBER) && (country.getMembership() != Membership.FULL_MEMBER)) {
                if (country.getMembership() == Membership.COOPERATING_INSTITUTION) {
                    final String groupname = prepareCommitteeName(country, request.getInstitutionName());
                    final GroupEntity group = dao.findGroupByName(groupname);
                    if (group == null) {

                    } else {
                        throw new IllegalActionException("A Committee with the name " + groupname + "already exist.");
                    }
                } else {
                    final GroupEntity members = new GroupEntity();
                    final GroupEntity staff = new GroupEntity();
                    country.setMembership(Membership.COOPERATING_INSTITUTION);
                    final UserEntity user = new UserEntity();
                    final UserGroupEntity nsMember = new UserGroupEntity();
                    final UserGroupEntity nsStaff = new UserGroupEntity();
                }
            } else {
                throw new IllegalActionException("Cannot create a new Cooperating Institution for a Member Country.");
            }
        } else {
            throw new IllegalActionException("Cannot create a Committee for a not existing Country.");
        }
    }

    private String prepareCommitteeName(final CountryEntity country, final String institutionName) {
        String committeeName = country.getCountryName() + ", ";

        for (final String part : institutionName.split(" ")) {
            committeeName += part.substring(0, 1).toUpperCase(IWSConstants.DEFAULT_LOCALE);
        }

        return committeeName;
    }

    private UserEntity createNationalSecretary(final Authentication authentication, final CommitteeRequest request) {
        final UserEntity existing = dao.findUserByUsername(request.getUsername());
        if (existing == null) {
            final UserEntity user = new UserEntity();

            return user;
        } else {
            throw new IllegalActionException("Cannot create National Secretary for the new Committee, as the username is already in the system.");
        }
    }

    private void changeNsForCommittee(final Authentication authentication, final CommitteeRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    private void mergeCommittees(final Authentication authentication, final CommitteeRequest request) {
        throw new NotImplementedException("Method pending information from Board before implementation can be done.");
    }

    /**
     * Upgrades a Committee to the next membership level.
     *
     * @param authentication User authentication Object
     * @param request        Request Object
     */
    private void upgradeCommittee(final Authentication authentication, final CommitteeRequest request) {
        final GroupEntity staff = dao.findGroupByExternalId(request.getNationalCommittee().getGroupId());

        if (staff != null) {
            if (staff.getStatus() == GroupStatus.ACTIVE) {
                final CountryEntity country = staff.getCountry();

                if (country.getMembership() == Membership.COOPERATING_INSTITUTION) {
                    List<GroupEntity> staffs = dao.findAllCommitteesForCountry(country);

                    if (staffs.size() == 1) {
                        country.setMembership(Membership.ASSOCIATE_MEMBER);
                        dao.persist(authentication, country);
                        log.info("Country {} has been upgraded from {} to {}.", country.getCountryName(), Membership.COOPERATING_INSTITUTION, Membership.ASSOCIATE_MEMBER);
                    } else {
                        throw new IllegalActionException("Attempting to updgrade a Cooperating Institution to Associate Membership, while other Cooperating Institutions still exist for the Country.");
                    }
                } else if (country.getMembership() == Membership.ASSOCIATE_MEMBER) {
                    country.setMembership(Membership.FULL_MEMBER);
                    dao.persist(authentication, country);
                    log.info("Country {} has been upgraded from {} to {}.", country.getCountryName(), Membership.ASSOCIATE_MEMBER, Membership.FULL_MEMBER);
                } else {
                    throw new IllegalActionException("Attempting to updgrade a Committee, which is already a Full Member.");
                }
            } else {
                throw new IllegalActionException("Attempting to upgrade a non-Active Committee, is not allowed.");
            }
        } else {
            throw new IllegalActionException("Attempting to upgrade non-existing Committee.");
        }
    }

    /**
     * Activates a previously suspended Committee again. This includes all the
     * Members of the Committee.
     *
     * @param authentication User authentication Object
     * @param request        Request Object
     */
    private void activateCommittee(final Authentication authentication, final CommitteeRequest request) {
        final GroupEntity staff = dao.findGroupByExternalId(request.getNationalCommittee().getGroupId());

        if (staff != null) {
            if (staff.getStatus() == GroupStatus.SUSPENDED) {
                final GroupEntity member = dao.findMemberGroupForStaff(staff);

                // Change the Status for the Groups to Active
                changeStatusForGroup(authentication, member, GroupStatus.ACTIVE);
                changeStatusForGroup(authentication, staff, GroupStatus.ACTIVE);

                // All Members will be activated now, so their accounts can be
                // used again
                changeStatusForMembers(authentication, member, UserStatus.ACTIVE);
            } else {
                throw new IllegalActionException("Cannot activate an already active Committee.");
            }
        } else {
            throw new IllegalActionException("Attempting to activate non-existing Committee.");
        }
    }

    /**
     * Suspends a currently active Committee. This includes deleting all
     * accounts with status new and suspend the remaining.
     *
     * @param authentication User authentication Object
     * @param request        Request Object
     */
    private void suspendCommittee(final Authentication authentication, final CommitteeRequest request) {
        final GroupEntity staff = dao.findGroupByExternalId(request.getNationalCommittee().getGroupId());

        if (staff != null) {
            if (staff.getStatus() == GroupStatus.ACTIVE) {
                final GroupEntity member = dao.findMemberGroupForStaff(staff);

                // Change the Status for the Groups to Active
                changeStatusForGroup(authentication, member, GroupStatus.SUSPENDED);
                changeStatusForGroup(authentication, staff, GroupStatus.SUSPENDED);

                // All Members will be suspended now, so their accounts cannot
                // be used anymore
                changeStatusForMembers(authentication, member, UserStatus.SUSPENDED);
                deleteNewMembers(authentication, member);
            } else {
                throw new IllegalActionException("Cannot suspend an already suspended Committee.");
            }
        } else {
            throw new IllegalActionException("Attempting to suspend non-existing Committee.");
        }
    }

    private void deleteCommittee(final Authentication authentication, final CommitteeRequest request) {
        final GroupEntity staff = dao.findGroupByExternalId(request.getNationalCommittee().getGroupId());

        if (staff != null) {
            if (staff.getStatus() == GroupStatus.SUSPENDED) {
                final CountryEntity country = staff.getCountry();
                final GroupEntity member = dao.findMemberGroupForStaff(staff);

                // all Members of the Committee will have status Suspended, so
                // we start by deleting them
                deleteSuspendedMembers(authentication, member);

                // Now, we will attempt to delete all Subgroups, doing so will
                // only set the status flag, as the only data currently present
                // in the system is Offers and derived data, which we cannot
                // delete
                deleteGroupStructure(authentication, member);

                // Finally, set status of the Country to former member
                country.setMembership(Membership.FORMER_MEMBER);
                dao.persist(authentication, country);
            } else {
                throw new IllegalActionException("Cannot delete an active Committee.");
            }
        } else {
            throw new IllegalActionException("Attempting to delete non-existing Committee.");
        }
    }

    private void changeStatusForGroup(final Authentication authentication, final GroupEntity group, final GroupStatus status) {
        group.setStatus(status);
        dao.persist(authentication, group);
    }

    private void changeStatusForMembers(final Authentication authentication, final GroupEntity group, final UserStatus status) {
        final List<UserGroupEntity> users = dao.findGroupMembers(group, EnumSet.of(UserStatus.ACTIVE, UserStatus.SUSPENDED));
        for (final UserGroupEntity entity : users) {
            final UserEntity user = entity.getUser();
            user.setStatus(status);
            dao.persist(authentication, user);
        }
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

    private void deleteNewMembers(final Authentication authentication, final GroupEntity group) {
        final List<UserGroupEntity> users = dao.findGroupMembers(group, EnumSet.of(UserStatus.NEW));
        for (final UserGroupEntity entity : users) {
            final UserEntity user = entity.getUser();

            // Delete the Users Private Group
            final GroupEntity privateGroup = dao.findPrivateGroup(user);
            dao.delete(privateGroup);

            // Now delete the new User completely
            deletePerson(user);
            dao.delete(user);

            log.info(formatLogMessage(authentication, "Deleted the new user %s %s <%s> completely.", user.getFirstname(), user.getLastname(), user.getUsername()));
        }
    }

    private void deletePerson(final UserEntity user) {
        if (user.getPerson() != null) {
            if (user.getPerson().getAddress() != null) {
                dao.delete(user.getPerson().getAddress());
            }
            dao.delete(user.getPerson());
        }
    }

    private void deleteSuspendedMembers(final Authentication authentication, final GroupEntity group) {
        final List<UserGroupEntity> UserGroupEntity = dao.findGroupMembers(group, EnumSet.of(UserStatus.SUSPENDED));

        for (final UserGroupEntity entity : UserGroupEntity) {
            final UserEntity user = entity.getUser();
            try {
                deletePrivateData(authentication, user);
            } catch (IWSException e) {
                log.warn("Unable to delete the Account for {} {} <{}>, reason: {}", user.getFirstname(), user.getLastname(), user.getUsername(), e.getMessage());
            }
        }
    }

    public void deletePrivateData(final Authentication authentication, final UserEntity user) {
        // First, delete the Sessions, they are linked to the User account, and
        // not the users private Group
        final int deletedSessions = dao.deleteSessions(user);

        // Secondly, delete all data associated with the user, meaning the users
        // private Group
        final GroupEntity group = dao.findPrivateGroup(user);
        dao.delete(group);

        // Delete the private data
        deletePerson(user);

        // Now, remove and System specific data from the Account, and set the
        // Status to deleted, thus preventing the account from being used
        // anymore
        user.setCode(null);
        // We remove the Username from the account as well, since it may
        // otherwise block if the user later on create a new Account. A
        // deleted account should remaing deleted - and we do not wish to
        // drop the Unique Constraint in the database.
        user.setUsername(UUID.randomUUID() + "@iaeste.com");
        user.setPassword(null);
        user.setSalt(null);
        user.setPerson(null);
        user.setStatus(UserStatus.DELETED);
        dao.persist(user);

        log.info(formatLogMessage(authentication, "Deleted all private data for user %s, including %d sessions.", user, deletedSessions));
    }

    // =========================================================================
    // Fetch & Process International Group logic
    // =========================================================================

    /**
     * Retrieves a List of International Groups with their respective Owners or
     * Coordinators. The list will consists of those International Groups,
     * matching the requrested, i.e. Active and/or Suspended.
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
                log.info(formatLogMessage(authentication, "Created new International Group %s with Coordinator %s", group.getGroupName(), user.getFirstname() + ' ' + user.getLastname()));
            } else {
                throw new VerificationException("Attempting to greate a new International Group failed as no Coordinator provided doesn't exist.");
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
                    groupEntity.setStatus(status);
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
                groupEntity.setStatus(status);
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
                UserGroupEntity newCoordinator = dao.findExistingRelation(groupEntity, userEntity);

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
                oldCoordinator.setRole(dao.findRole(IWSConstants.ROLE_MODERATOR));
                oldCoordinator.setTitle("Former " + oldCoordinator.getTitle());
                dao.persist(authentication, oldCoordinator);
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
        userGroup.setOnPublicList(true);
        userGroup.setOnPrivateList(true);
        userGroup.setWriteToPrivateList(true);
        userGroup.setRole(dao.findRole(IWSConstants.ROLE_OWNER));
        userGroup.setTitle(group.getGroupName() + " Coordinator");

        dao.persist(authentication, userGroup);
    }

    // =========================================================================
    // Fetch & Process Survey of Country logic
    // =========================================================================

    public FetchSurveyOfCountryRespose fetchSurveyOfCountry(final Authentication authentication, final FetchSurveyOfCountryRequest request) {
        throw new NotImplementedException("Method pending implementation, see Trac #924.");
    }

    public void processSurveyOfCountry(final Authentication authentication, final SurveyOfCountryRequest request) {
        throw new NotImplementedException("Method pending implementation, see Trac #924.");
    }
}
