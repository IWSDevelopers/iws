/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.Group
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.MailReply;
import net.iaeste.iws.api.enums.MonitoringLevel;
import net.iaeste.iws.api.util.AbstractVerification;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "group", propOrder = { "groupId", "parentId", "groupName", "fullName", "listName", "privateList", "privateListReplyTo", "publicList", "publicListReplyTo", "groupType", "description", "monitoringLevel", "country" })
public final class Group extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;
    private static final Pattern PATTERN_STAFF = Pattern.compile("Staff", Pattern.LITERAL);

    @XmlElement(required = true, nillable = true)  private String groupId = null;
    @XmlElement(required = true, nillable = true)  private String parentId = null;
    @XmlElement(required = true, nillable = false) private String groupName = null;
    @XmlElement(required = true, nillable = true)  private String fullName = null;
    @XmlElement(required = true, nillable = true)  private String listName = null;
    @XmlElement(required = true, nillable = false) private Boolean privateList = true;
    @XmlElement(required = true, nillable = false) private MailReply privateListReplyTo = MailReply.REPLY_TO_LIST;
    @XmlElement(required = true, nillable = false) private Boolean publicList = true;
    @XmlElement(required = true, nillable = false) private MailReply publicListReplyTo = MailReply.REPLY_TO_SENDER;
    @XmlElement(required = true, nillable = false) private GroupType groupType = null;
    @XmlElement(required = true, nillable = true)  private String description = null;
    @XmlElement(required = true, nillable = true)  private MonitoringLevel monitoringLevel = MonitoringLevel.NONE;
    @XmlElement(required = true, nillable = true)  private Country country = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Group() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Copy Constructor.
     *
     * @param group Group Object to copy
     */
    public Group(final Group group) {
        if (group != null) {
            groupId = group.groupId;
            parentId = group.parentId;
            groupName = group.groupName;
            fullName = group.fullName;
            listName = group.listName;
            privateList = group.privateList;
            publicList = group.publicList;
            groupType = group.groupType;
            monitoringLevel = group.monitoringLevel;
            description = group.description;
            if (group.country != null) {
                country = new Country(group.country);
            }
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the Group Id, which is the internally generated key for this
     * Object. Note, that the presence of the value will determine if the IWS
     * should process this record as if it exist or not. If the Id is set, but
     * no record exists, then the system will reply with an error. Likewise, if
     * no Id is provided, but the record exists, the system will reply with an
     * error.</p>
     *
     * <p>The value must be a valid Id, otherwise the method will throw an
     * {@code IllegalArgumentException}.</p>
     *
     * @param groupId Group Id
     * @throws IllegalArgumentException if the Id is set but invalid
     * @see AbstractVerification#UUID_FORMAT
     */
    public void setGroupId(final String groupId) throws IllegalArgumentException {
        ensureValidId("groupId", groupId);
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    /**
     * Sets the Id of the Parent Group, which is helpful to build Group tree's.
     * The value cannot be changed and will be ignored if altered by a Client.
     *
     * @param parentId The Id of the Parent Group
     */
    public void setParentId(final String parentId) {
        this.parentId = parentId;
    }

    public String getParentId() {
        return parentId;
    }

    /**
     * <p>Sets the name of the Group. The name may neither be null nor empty or
     * too long, the maximum allowed length is 50 characters. Further, the name
     * of the Group must be unique in the context, i.e. among other groups with
     * the same parent.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the given
     * value is invalid, i.e. null, empty or too long.</p>
     *
     * @param groupName The name of the Group
     * @throws IllegalArgumentException if the given value is invalid
     */
    public void setGroupName(final String groupName) throws IllegalArgumentException {
        ensureNotNullOrEmptyOrTooLong("name", groupName, 50);
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    /**
     * This method is setting the FullName of the Group. The fullName is
     * depending on the Group hierarchy. Note, this value is controlled
     * internally, so any changes will be ignored.
     *
     * @param fullName Group FullName
     */
    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    /**
     * This method is setting the Group mailinglist name, meaning the e-mail
     * address which is associated with the Group. Note, this value is
     * controlled internally, so any changes will be ignored.
     *
     * @param listName Group Mailinglist name
     */
    public void setListName(final String listName) {
        this.listName = listName;
    }

    /**
     * Retrieves the name of the mailing list based on the Mailing list flags.
     * Note, that it is possible for a Group to have both a public and a private
     * list.
     *
     * @return Public list if set, otherwise private list if set or null if not
     */
    public String getListName() {
        return listName;
    }

    /**
     * <p>Sets the Private Mailinglist flag. Meaning, that if the underlying
     * GroupType allows a private mailing list, then the Group can be updated to
     * also have one.</p>
     *
     * <p>Note, the value must be set to either true or false. Changing the flag
     * for a group where it is not allowed, will be ignored. Attempting to set
     * the value to null will result in an IllegalArgument Exception.</p>
     *
     * @param privateList True or False, depending on the GroupType
     * @throws IllegalArgumentException if set to null
     */
    public void setPrivateList(final Boolean privateList) throws IllegalArgumentException {
        ensureNotNull("privateList", privateList);
        this.privateList = privateList;
    }

    public boolean hasPrivateList() {
        return (groupType != null) && groupType.getMayHavePrivateMailinglist() && privateList;
    }

    public String getPrivateList() {
        return (hasPrivateList() && (listName != null)) ? (listName + '@' + IWSConstants.PRIVATE_EMAIL_ADDRESS) : null;
    }

    /**
     * <p>Sets the reply to flag for private mailing lists for this Group. The
     * Flag will control how the mailing list is created.</p>
     *
     * <p>Note, the method will throw an {@code IllegalArgumentException} if the
     * value is set to null.</p>
     *
     * @param privateListReplyTo Who is receiving mail when replying
     * @throws IllegalArgumentException if set to null
     */
    public void setPrivateListReplyTo(final MailReply privateListReplyTo) throws IllegalArgumentException {
        ensureNotNull("privateListReplyTo", privateListReplyTo);
        this.privateListReplyTo = privateListReplyTo;
    }

    public MailReply getPrivateListReplyTo() {
        return privateListReplyTo;
    }

    /**
     * <p>Sets the Public Mailinglist flag. Meaning, that if the underlying
     * GroupType allows a private mailing list, then the Group can be updated to
     * also have one.</p>
     *
     * <p>Note, the value must be set to either true or false. Changing the flag
     * for a group where it is not allowed, will be ignored. Attempting to set
     * the value to null will result in an IllegalArgument Exception.</p>
     *
     * @param publicList True or False, depending on the GroupType
     * @throws IllegalArgumentException if set to null
     */
    public void setPublicList(final Boolean publicList) {
        ensureNotNull("publicList", publicList);
        this.publicList = publicList;
    }

    public boolean hasPublicList() {
        return (groupType != null) && groupType.getMayHavePublicMailinglist() && publicList;
    }

    public String getPublicList() {
        return (hasPublicList() && (listName != null)) ? (listName + '@' + IWSConstants.PUBLIC_EMAIL_ADDRESS) : null;
    }

    /**
     * <p>Sets the reply to flag for public mailing lists for this Group. The
     * Flag will control how the mailing list is created.</p>
     *
     * <p>Note, the method will throw an {@code IllegalArgumentException} if the
     * value is set to null.</p>
     *
     * @param publicListReplyTo Who is receiving mail when replying
     * @throws IllegalArgumentException if set to null
     */
    public void setPublicListReplyTo(final MailReply publicListReplyTo) throws IllegalArgumentException {
        ensureNotNull("publicListReplyTo", publicListReplyTo);
        this.publicListReplyTo = publicListReplyTo;
    }

    public MailReply getPublicListReplyTo() {
        return publicListReplyTo;
    }

    /**
     * Retrieve committee name of this group, if it is of type {@link GroupType#NATIONAL}
     * or {@link GroupType#MEMBER} Otherwise return the name itself.
     *
     * @return committee name of this group
     */
    public String getCommitteeName() {
        final String committeeName;

        if (groupType == GroupType.NATIONAL) {
            committeeName = PATTERN_STAFF.matcher(groupName).replaceAll("").trim();
        } else if (groupType == GroupType.MEMBER) {
            committeeName = groupName;
        } else if (groupType == GroupType.LOCAL) {
            committeeName = fullName.replace(groupName, "").trim();
        } else {
            committeeName = "";
        }

        return committeeName;
    }

    /**
     * <p>Sets the type of Group. The type is used to determine its internal set
     * of permissions. Hence, the type may not be null.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the given
     * value is invalid, i.e. null.</p>
     *
     * @param groupType The Type of Group
     * @throws IllegalArgumentException if the given value is invalid
     */
    public void setGroupType(final GroupType groupType) throws IllegalArgumentException {
        ensureNotNull("groupType", groupType);
        this.groupType = groupType;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    /**
     * <p>Sets the Group Description. This is a way to describe the purpose of
     * the Group for others. The given value may be max 250 characters long.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the given
     * value is invalid, i.e. too long.</p>
     *
     * @param description The Description of the Group
     * @throws IllegalArgumentException if the given value is invalid
     */
    public void setDescription(final String description) {
        ensureNotTooLong("description", description, 250);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * <p>Sets the Group Monitoring Level. This will tell IWS if and how any
     * changes should be monitored.</p>
     *
     * <p>By default, the policy of the IWS is to provide maximum privacy, so no
     * data is monitored. If monitoring is desired, then this value must
     * explicitly be changed.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the given
     * value is invalid, i.e. null.</p>
     *
     * @param monitoringLevel Monitoring Level
     */
    public void setMonitoringLevel(final MonitoringLevel monitoringLevel) {
        ensureNotNull("monitoringLevel", monitoringLevel);
        this.monitoringLevel = monitoringLevel;
    }

    public MonitoringLevel getMonitoringLevel() {
        return monitoringLevel;
    }

    /**
     * Sets the Group Country, if the Group is having a Country relation.
     *
     * @param country The Country for this Group
     */
    public void setCountry(final Country country) {
        this.country = new Country(country);
    }

    public Country getCountry() {
        return new Country(country);
    }

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "groupName", groupName);
        isNotNull(validation, "groupType", groupType);
        isNotNull(validation, "privateList", privateList);
        isNotNull(validation, "privateListReplyTo", privateListReplyTo);
        isNotNull(validation, "publicList", publicList);
        isNotNull(validation, "publicListReplyTo", publicListReplyTo);

        return validation;
    }
}
