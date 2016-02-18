/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.GroupType
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.enums;

import javax.xml.bind.annotation.XmlType;

/**
 * All Groups in the IW has to be assigned an overall type, which determines its
 * basic functionality (permissions). Please note, that certain GroupTypes, are
 * designed so any given user may only be member of 1 (one), others are open,
 * so users can be part of many. The restricted groups are: Administration,
 * Members and National - In fact, a user can only be member of National Group.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "groupType")
public enum GroupType implements Descriptable<GroupType> {

    /**
     * <p>The Administration Group is present to ensure that certain users may
     * perform special tasks, that is otherwise not allowed. For example, to
     * avoid data corruption, it is not allowed for users to change their first
     * and last names. However, there are cases where you wish to change them,
     * such as marriage where the family name is changed.</p>
     *
     * <p>This GroupType will have both a public Mailing list and a public file
     * folder for others to view the content of, if the files have been marked
     * public, otherwise only the Group may access the files.</p>
     */
    ADMINISTRATION("Administration", WhoMayJoin.ALL, true, true, FolderType.PUBLIC),

    /**
     * <p>All user accounts have a private group assigned, with this type. It is
     * there to ensure that all data is only linked with Groups, and allow a
     * simpler logic when handling data.</p>
     *
     * <p>The Private group is also there to ensure that if a user is removed
     * from the system, then the private data can be easily removed as
     * well. Among the private data is also users files. A user may store files
     * for private purposes.</p>
     */
    PRIVATE("Private", WhoMayJoin.NONE, false, true, FolderType.PRIVATE),

    /**
     * <p>All members are assigned to this type, which gives the rights to the
     * basic functionality in the system.</p>
     *
     * <p>Each member country have a designated Members group, where all their
     * members are added. However, as some members may not be a member of a
     * specific country, to avoid conflicts between their work and their
     * national organization - another group exists called "Global", for all
     * other members. Mostly this consists of the General Secretary, Ombudsman,
     * IDT members, etc.</p>
     *
     * <p>The GroupType will have only a private mailinglist and also only a
     * private folder for files.</p>
     *
     * <p><i>Note;</i> users can only be member of 1 Members Group!</p>
     */
    MEMBER("Members", WhoMayJoin.NONE, true, false, FolderType.PRIVATE),

    /**
     * <p>International Groups, are Groups which share members across Country
     * Borders. There's two types of International Groups, the first is the
     * default added, which includes those from the list below:</p>
     * <ul>
     *   <li><b>GS</b>
     *   <p>General Secretary, and assigned assistants.</p>
     *   </li>
     *   <li><b>Board</b>
     *   <p>Members of the IAESTE A.s.b.l. Board.</p>
     *   </li>
     *   <li><b>SID</b>
     *   <p>Members who participate in the annual Seminar on IAESTE
     *   Development.</p>
     *   </li>
     *   <li><b>IDT</b>
     *   <p>Members of the IAESTE Internet Development Team.</p>
     *   </li>
     *   <li><b>Jump</b>
     *   <p>Participants in the annual Jump event, a training forum for members
     *   who wishes to participate in International IAESTE work.</p>
     *   </li>
     *   <li><b>Ombudsman</b>
     *   <p>The IAESTE Ombudsman.</p>
     *   </li>
     * </ul>
     *   The second type of International Groups, was the "Regional" Groups,
     * from IW3. These Groups were not truly international, as their purpose
     * were handled by a smaller collection of countries, examples thereof:
     * <ul>
     *   <li><b>CEC</b>
     *   <p>Central European Countries</p>
     *   </li>
     *   <li><b>Nordic</b>
     *   <p>Members from the Nordic, i.e. Scandinavian and Baltic Countries.</p>
     *   </li>
     * </ul>
     * <p>Regardless of the purpose, any group which purpose is not bound to a
     * Single country, is an International Group.</p>
     *
     * <p>International Groups will have both a public and a private mailinglist
     * available. The Group will also have a public folder with information that
     * can be shared to others, if the containing files have been marked public,
     * otherwise only the group members may view them.</p>
     */
    INTERNATIONAL("International", WhoMayJoin.ALL, true, true, FolderType.PUBLIC),

    /**
     * <p>All Countries have both a Members group, where all the people who are
     * a part of the Organization in that country are listed. However, for the
     * staff, certain other functionality is required. The National Group will
     * make up for that.</p>
     *
     * <p>The type of functionality will consists of access to certain sections
     * of the IntraWeb, and only some of the members of the Staff group will
     * be allowed to join the NC's Mailinglist.</p>
     *
     * <p>The National Committees will also have a public folder for files, for
     * sharing of important information. All files in the folder marked public
     * will be accessible for others, files marked protected will only be
     * accessible by the group members.</p>
     *
     * <p>Note; users can only be member of 1 National Group!</p>
     */
    NATIONAL("Staff", WhoMayJoin.MEMBERS, false, true, FolderType.PUBLIC),

    /**
     * <p>Local Groups are for Local Committees around the Country. Local Groups
     * will have a National Group as parent Group.</p>
     *
     * <p>Although Local Committees is allowed to have public mailing lists,
     * they will not have a public folder for sharing files, this is reserved to
     * one of the top level groups, Administration, International &amp;
     * National. However, the Local Committees may have private sharing of
     * files.</p>
     */
    LOCAL("Local Committee", WhoMayJoin.MEMBERS, true, true, FolderType.PRIVATE),

    /**
     * <p>For Groups, where you need only to have a common mailinglist as well
     * as some other means of sharing information, the WorkGroup's will serve
     * this purpose well.</p>
     *
     * <p>Although WorkGroups is allowed to have public mailing lists, they
     * will not have a public folder for sharing files, this is reserved to one
     * of the top level groups, Administration, International &amp; National.
     * However, WorkGroups may have private sharing of files.</p>
     *
     * <p>WorkGroup's can be assigned as a sub-group to any of the other
     * groups.</p>
     */
    WORKGROUP("WorkGroup", WhoMayJoin.INHERITED, true, true, FolderType.PRIVATE),

    /**
     * <p>The Student Group is for Offer Applicants, meaning that if a person
     * wishes to apply for an Open Offer for a given Country, the person must be
     * a member of the Country Student Group.</p>
     *
     * <p>Students who have been accepted for an Offer cannot be removed from the
     * Student Group, only those accounts that are currently unassigned can be
     * removed.</p>
     *
     * <p>When creating "new" Student Accounts, the user is automatically
     * assigned to the Country's Student Group, and additionally to the Members
     * group (with role Student). Normal members who wishes to apply for Offers,
     * must also be added to the Student Group.</p>
     *
     * <p>Student Groups may also not have file sharing, neither public not
     * private.</p>
     */
    STUDENT("Students", WhoMayJoin.MEMBERS, false, false, FolderType.NONE);

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    /**
     * This enum contains the rules regarding who may join a Group, since it
     * depends on the specific type.
     */
    public enum WhoMayJoin {

        /** Only users belonging to the Member Group may join. */
        MEMBERS,

        /** Everybody may join this type of Group. */
        ALL,

        /** The group is closed for adding members. */
        NONE,

        /** The rule, regarding who may join is the same as the parents. */
        INHERITED
    }

    public enum FolderType {
        PRIVATE,
        PUBLIC,
        NONE
    }

    private final String description;
    private final WhoMayJoin whoMayJoin;
    private final Boolean mayHavePrivateMailinglist;
    private final Boolean mayHavePublicMailinglist;
    private final FolderType folderType;

    /**
     * <p>Constructor for this enumerated type. GroupTypes is there to handle
     * meta information for other Groups, and is used extensively within the
     * IWS.</p>
     *
     * <p>The enumerated type have a few settings, which is important for
     * displaying and creating/altering Groups. The provided description is used
     * for displaying information about the Group. The two Boolean flags, is
     * there to say if this Group may have a private or public mailing list. The
     * presence of one such list requires that there's some members present
     * who's on either.</p>
     *
     * @param description               Display name for this GroupType
     * @param whoMayJoin                The members who may join
     * @param mayHavePrivateMailinglist May this GroupType have private lists
     * @param mayHavePublicMailinglist  May this GroupType have public lists
     * @param folderType                What type of folder the GroupType allows
     */
    GroupType(final String description, final WhoMayJoin whoMayJoin, final Boolean mayHavePrivateMailinglist, final Boolean mayHavePublicMailinglist, final FolderType folderType) {
        this.description = description;
        this.whoMayJoin = whoMayJoin;
        this.mayHavePrivateMailinglist = mayHavePrivateMailinglist;
        this.mayHavePublicMailinglist = mayHavePublicMailinglist;
        this.folderType = folderType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }

    public WhoMayJoin getWhoMayJoin() {
        return whoMayJoin;
    }

    public Boolean getMayHavePrivateMailinglist() {
        return mayHavePrivateMailinglist;
    }

    public Boolean getMayHavePublicMailinglist() {
        return mayHavePublicMailinglist;
    }

    public FolderType getFolderType() {
        return folderType;
    }
}
