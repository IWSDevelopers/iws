/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.StudentService
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

import static net.iaeste.iws.core.transformers.ExchangeTransformer.transform;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.File;
import net.iaeste.iws.api.dtos.exchange.Student;
import net.iaeste.iws.api.dtos.exchange.StudentApplication;
import net.iaeste.iws.api.enums.exchange.ApplicationStatus;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.requests.student.FetchStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentsRequest;
import net.iaeste.iws.api.requests.student.ProcessStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.StudentApplicationRequest;
import net.iaeste.iws.api.requests.student.StudentRequest;
import net.iaeste.iws.api.responses.student.FetchStudentApplicationsResponse;
import net.iaeste.iws.api.responses.student.FetchStudentsResponse;
import net.iaeste.iws.api.responses.student.StudentApplicationResponse;
import net.iaeste.iws.api.responses.student.StudentResponse;
import net.iaeste.iws.api.util.DateTime;
import net.iaeste.iws.core.transformers.StorageTransformer;
import net.iaeste.iws.core.transformers.ViewTransformer;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.StudentDao;
import net.iaeste.iws.persistence.ViewsDao;
import net.iaeste.iws.persistence.entities.AttachmentEntity;
import net.iaeste.iws.persistence.entities.FileEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.exchange.ApplicationEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import net.iaeste.iws.persistence.entities.exchange.StudentEntity;
import net.iaeste.iws.persistence.views.ApplicationView;
import net.iaeste.iws.persistence.views.StudentView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class StudentService extends CommonService<StudentDao> {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    private final AccessDao accessDao;
    private final ExchangeDao exchangeDao;
    private final ViewsDao viewsDao;

    public StudentService(final AccessDao accessDao, final ExchangeDao exchangeDao, final StudentDao studentDao, final ViewsDao viewsDao) {
        super(studentDao);
        this.accessDao = accessDao;
        this.exchangeDao = exchangeDao;
        this.viewsDao = viewsDao;
    }

    public StudentResponse processStudent(final Authentication authentication, final StudentRequest request) {
        final StudentEntity studentEntity = processStudent(authentication, request.getStudent());
        return new StudentResponse(transform(studentEntity));
    }

    public StudentEntity processStudent(final Authentication authentication, final Student student) {
        final GroupEntity memberGroup = accessDao.findMemberGroup(authentication.getUser());
        final UserEntity user = accessDao.findUserByExternalId(student.getStudentId());
        final StudentEntity newEntity = transform(student);
        newEntity.setUser(user);
        final StudentEntity existingEntity = dao.findStudentByExternal(memberGroup.getId(), student.getStudentId());
        if (existingEntity != null) {
            dao.persist(authentication, existingEntity, newEntity);
        } else {
            throw new VerificationException("The student with id '" + student.getStudentId() + "' was not found.");
        }

        return existingEntity;
    }

    public FetchStudentsResponse fetchStudents(final Authentication authentication, final FetchStudentsRequest request) {
        final List<StudentView> found = viewsDao.findStudentsForMemberGroup(authentication.getGroup().getParentId(), request.getPagingInformation());

        final List<Student> result = new ArrayList<>(found.size());
        for (final StudentView view : found) {
            result.add(ViewTransformer.transform(view));
        }

        return new FetchStudentsResponse(result);
    }

    public StudentApplicationResponse processStudentApplication(final Authentication authentication, final ProcessStudentApplicationsRequest request) {
        final ApplicationEntity entity = processStudentApplication(authentication, request.getStudentApplication());
        final List<AttachmentEntity> attachments = processAttachments(authentication, entity, request.getStudentApplication().getAttachments());

        final StudentApplication application = transform(entity, attachments, false);
        return new StudentApplicationResponse(application);
    }

    private ApplicationEntity processStudentApplication(final Authentication authentication, final StudentApplication application) {
        final GroupEntity nationalGroup = accessDao.findNationalGroup(authentication.getUser());
        final String externalId = application.getApplicationId();
        ApplicationEntity applicationEntity = dao.findApplicationByExternalId(externalId);

        if ((applicationEntity == null) || (applicationEntity.getOfferGroup().getGroup().getId().equals(nationalGroup.getId()))) {
            //application owner
            return processStudentApplicationByApplicationOwner(authentication, application, applicationEntity);
        } else {
            final OfferGroupEntity sharedOfferGroup = applicationEntity.getOfferGroup();
            final OfferEntity offer = sharedOfferGroup.getOffer();
            if (offer.getEmployer().getGroup().getId().equals(nationalGroup.getId())) {
                //offer owner
                return processStudentApplicationByOfferOwner(authentication, application, applicationEntity);
            }
        }

        throw new IWSException(IWSErrors.PROCESSING_FAILURE, "Cannot process student application");
    }

    private ApplicationEntity processStudentApplicationByApplicationOwner(final Authentication authentication, final StudentApplication application, ApplicationEntity applicationEntity) {
        final GroupEntity nationalGroup = accessDao.findNationalGroup(authentication.getUser());
        final OfferGroupEntity sharedOfferGroup;
        if (applicationEntity == null) {
            sharedOfferGroup = verifyOfferIsSharedToGroup(authentication.getGroup(), application.getOffer().getOfferId());
        } else {
            sharedOfferGroup = applicationEntity.getOfferGroup();
        }

        if ((sharedOfferGroup == null) || (!sharedOfferGroup.getGroup().getId().equals(nationalGroup.getId()))) {
            final String offerId = application.getOffer() != null ? application.getOffer().getOfferId() : "null";
            throw new VerificationException("The offer with id '" + offerId + "' is not shared to the group '" + authentication.getGroup().getGroupName() + "'.");
        }

        final GroupEntity memberGroup = accessDao.findMemberGroup(authentication.getUser());
        final StudentEntity student = dao.findStudentByExternal(memberGroup.getId(), application.getStudent().getUser().getUserId());

        if (EnumSet.of(OfferState.CLOSED, OfferState.COMPLETED, OfferState.NEW).contains(sharedOfferGroup.getOffer().getStatus())) {
            throw new VerificationException("It is not possible to create/update application for the offer with status '" + sharedOfferGroup.getOffer().getStatus() + "'.");
        }

        if (applicationEntity == null) {
            applicationEntity = transform(application);
            applicationEntity.setOfferGroup(sharedOfferGroup);
            processAddress(authentication, applicationEntity.getHomeAddress());
            processAddress(authentication, applicationEntity.getAddressDuringTerms());
            //dao.persist(authentication, student, applicationEntity.getStudent());
            applicationEntity.setStudent(student);
            dao.persist(authentication, applicationEntity);

            //TODO complete status list from which we should change the status
            if (OfferState.SHARED.equals(sharedOfferGroup.getStatus())) {
                sharedOfferGroup.setStatus(OfferState.APPLICATIONS);
                dao.persist(sharedOfferGroup);
            }
        } else {
            final ApplicationEntity updated = transform(application);
            //TODO - allow update application status?
            if (!applicationEntity.getStatus().equals(updated.getStatus())) {
                //we are updating application and its status changed -> check if it's allowed
                verifyOfferAcceptNewApplicationStatus(sharedOfferGroup.getOffer().getStatus(), applicationEntity.getStatus());
                verifyApplicationStatusTransition(applicationEntity.getStatus(), updated.getStatus());
            }

            //using OfferGroup from found entity since this field can't be updated
            updated.setOfferGroup(applicationEntity.getOfferGroup());
            processAddress(authentication, applicationEntity.getHomeAddress(), application.getHomeAddress());
            processAddress(authentication, applicationEntity.getAddressDuringTerms(), application.getAddressDuringTerms());
            //dao.persist(authentication, student, updated.getStudent());
            dao.persist(authentication, applicationEntity, updated);
        }

        return applicationEntity;
    }

    private ApplicationEntity processStudentApplicationByOfferOwner(final Authentication authentication, final StudentApplication application, final ApplicationEntity applicationEntity) {
        final GroupEntity nationalGroup = accessDao.findNationalGroup(authentication.getUser());
        final OfferGroupEntity sharedOfferGroup = applicationEntity.getOfferGroup();
        final OfferEntity offer = sharedOfferGroup.getOffer();

        if (!offer.getEmployer().getGroup().getId().equals(nationalGroup.getId())) {
            throw new VerificationException("The group with '" + authentication.getGroup().getGroupName() + "' does not own the offer with id '" + offer.getExternalId() + "'.");
        }

        //Offer owner can change only acceptance
        final StudentApplication updatedApplication = transform(applicationEntity);
        updatedApplication.setAcceptance(application.getAcceptance());

        final ApplicationEntity newEntity = transform(updatedApplication);
        dao.persist(authentication, applicationEntity, newEntity);
        return applicationEntity;
    }

    private List<AttachmentEntity> processAttachments(final Authentication authentication, final ApplicationEntity applicationEntity, final List<File> files) {
        final List<AttachmentEntity> attachments = new ArrayList<>(files.size());

        for (final File file : files) {
            final FileEntity fileEntity = processFile(authentication, file);
            final AttachmentEntity attachmentEntity = processAttachment(authentication, applicationEntity, fileEntity);
            attachments.add(attachmentEntity);
        }

        return attachments;
    }

    private AttachmentEntity processAttachment(final Authentication authentication, final ApplicationEntity applicationEntity, final FileEntity fileEntity) {
        AttachmentEntity attachmentEntity = dao.findAttachment("student_applications", applicationEntity.getId(), fileEntity.getId());

        if (attachmentEntity == null) {
            attachmentEntity = new AttachmentEntity();
            attachmentEntity.setTable("student_applications");
            attachmentEntity.setRecord(applicationEntity.getId());
            attachmentEntity.setFile(fileEntity);

            dao.persist(authentication, attachmentEntity);
        }

        return attachmentEntity;
    }

    public FetchStudentApplicationsResponse fetchStudentApplications(final Authentication authentication, final FetchStudentApplicationsRequest request) {
        final String offerExternalId = request.getOfferId();
        final OfferEntity ownedOffer = exchangeDao.findOfferByExternalId(authentication, offerExternalId);

        final List<ApplicationView> found;
        if (ownedOffer != null && ownedOffer.getEmployer().getGroup().equals(authentication.getGroup())) {
            found = dao.findForeignApplicationsForOffer(offerExternalId, authentication.getGroup().getId());
        } else {
            found = dao.findDomesticApplicationsForOffer(offerExternalId, authentication.getGroup().getId());
        }

        final List<StudentApplication> applications = new ArrayList<>(found.size());
        for (final ApplicationView entity : found) {
            final StudentApplication application = ViewTransformer.transform(entity);
            final List<File> attachments = findAndTransformAttachments(entity);
            application.setAttachments(attachments);

            applications.add(application);
        }

        return new FetchStudentApplicationsResponse(applications);
    }

    private List<File> findAndTransformAttachments(final ApplicationView view) {
        final List<AttachmentEntity> attachments = dao.findAttachments("student_applications", view.getId());
        final List<File> files = new ArrayList<>(attachments.size());

        for (final AttachmentEntity entity : attachments) {
            final File file = StorageTransformer.transform(entity.getFile());
            files.add(file);
        }

        return files;
    }

    public StudentApplicationResponse processApplicationStatus(final Authentication authentication, final StudentApplicationRequest request) {
        final StudentApplicationResponse response;
        final ApplicationEntity found = dao.findApplicationByExternalId(request.getApplicationId());

        if (found == null) {
            throw new VerificationException("The application with id '" + request.getApplicationId() + "' was not found.");
        }

        final GroupEntity nationalGroup = accessDao.findNationalGroup(authentication.getUser());
        final OfferEntity offer = found.getOfferGroup().getOffer();

        if (found.getOfferGroup().getGroup().getId().equals(nationalGroup.getId())) {
            //application owner
             processApplicationStatusByApplicationOwner(authentication, request, found);
        } else if (offer.getEmployer().getGroup().getId().equals(nationalGroup.getId())) {
            //offer owner
            processApplicationStatusByOfferOwner(authentication, request, found);
        } else {
            throw new IWSException(IWSErrors.PROCESSING_FAILURE, "Cannot process student application status.");
        }

        return new StudentApplicationResponse(transform(found));
    }

    private void processApplicationStatusByOfferOwner(final Authentication authentication, final StudentApplicationRequest request, final ApplicationEntity applicationEntity) {
        final OfferGroupEntity sharedOfferGroup = applicationEntity.getOfferGroup();

        final StudentApplication studentApplication = transform(applicationEntity);

        verifyOfferAcceptNewApplicationStatus(sharedOfferGroup.getStatus(), request.getStatus());
        verifyApplicationStatusTransition(studentApplication.getStatus(), request.getStatus());

        switch (request.getStatus()) {
            case REJECTED:
                rejectApplication(authentication, request, applicationEntity);
                break;
            case FORWARDED_TO_EMPLOYER:
                forwardToEmployer(authentication, studentApplication, applicationEntity);
                break;
            default:
                throw new NotImplementedException("Action '" + request.getStatus() + "' pending implementation.");
        }
    }

    private void processApplicationStatusByApplicationOwner(final Authentication authentication, final StudentApplicationRequest request, final ApplicationEntity applicationEntity) {
        final OfferGroupEntity sharedOfferGroup = applicationEntity.getOfferGroup();

        final StudentApplication studentApplication = transform(applicationEntity);

        verifyOfferAcceptNewApplicationStatus(sharedOfferGroup.getStatus(), request.getStatus());
        verifyApplicationStatusTransition(studentApplication.getStatus(), request.getStatus());
        //TODO - see #526
        //TODO - when application status affects also offer status, change it accordingly
        switch (request.getStatus()) {
            case NOMINATED:
                nominateApplication(authentication, studentApplication, applicationEntity);
                break;
            default:
                throw new NotImplementedException("Action '" + request.getStatus() + "' pending implementation.");
        }
    }

    private void forwardToEmployer(final Authentication authentication, final StudentApplication application, final ApplicationEntity applicationEntity) {
        application.setStatus(ApplicationStatus.FORWARDED_TO_EMPLOYER);
        ApplicationEntity updated = transform(application);
        updated.setOfferGroup(applicationEntity.getOfferGroup());
        dao.persist(authentication, applicationEntity, updated);

        //update status for OfferGroup
        updateOfferGroupStatus(applicationEntity.getOfferGroup(), OfferState.AT_EMPLOYER);
        //update status for Offer
        updateOfferStatus(applicationEntity.getOfferGroup().getOffer(), OfferState.AT_EMPLOYER);
    }

    private void nominateApplication(final Authentication authentication, final StudentApplication application, final ApplicationEntity storedApplication) {
        application.setNominatedAt(new DateTime());
        application.setStatus(ApplicationStatus.NOMINATED);
        ApplicationEntity updated = transform(application);
        //using OfferGroup from found entity since this field can't be updated
        updated.setOfferGroup(storedApplication.getOfferGroup());
        dao.persist(authentication, storedApplication, updated);

        //update status for OfferGroup
        updateOfferGroupStatus(storedApplication.getOfferGroup(), OfferState.NOMINATIONS);
        //update status for Offer
        updateOfferStatus(storedApplication.getOfferGroup().getOffer(), OfferState.NOMINATIONS);
    }

    private void rejectApplication(final Authentication authentication, final StudentApplicationRequest request, final ApplicationEntity storedApplication) {
        final StudentApplication application = transform(storedApplication);

        application.setStatus(request.getStatus());
        application.setRejectByEmployerReason(request.getRejectByEmployerReason());
        application.setRejectDescription(request.getRejectDescription());
        application.setRejectInternalComment(request.getRejectInternalComment());
        ApplicationEntity updated = transform(application);
        //using OfferGroup from stored entity since this field can't be updated
        updated.setOfferGroup(storedApplication.getOfferGroup());

        dao.persist(authentication, storedApplication, updated);

        //update status for OfferGroup
        updateOfferGroupStatus(storedApplication.getOfferGroup(), OfferState.APPLICATION_REJECTED);
        //update status for Offer
        if (dao.otherNominatedApplications(storedApplication.getId())) {
            updateOfferStatus(storedApplication.getOfferGroup().getOffer(), OfferState.SHARED);
        }
    }

    private void updateOfferGroupStatus(final OfferGroupEntity offerGroup, final OfferState state) {
        offerGroup.setStatus(state);
        dao.persist(offerGroup);
    }

    private void updateOfferStatus(final OfferEntity offer, final OfferState state) {
        offer.setStatus(state);
        dao.persist(offer);
    }

    private void verifyOfferAcceptNewApplicationStatus(final OfferState offerState, final ApplicationStatus applicationStatus) {
        switch (offerState) {
            case COMPLETED:
                switch (applicationStatus) {
                    case REJECTED:
                    case CANCELED:
                        break;
                    default:
                        throw new VerificationException("Offer with status '" + offerState + "' does not accept new application status '" + applicationStatus + "'");
                }
                break;
            case CLOSED:
                throw new VerificationException("Offer with status '" + offerState + "' does not accept new application status '" + applicationStatus + "'");
        }
    }

    private void verifyApplicationStatusTransition(final ApplicationStatus oldStatus, final ApplicationStatus newStatus) {
        switch (oldStatus) {
            case APPLIED:
                switch (newStatus) {
                    case NOMINATED:
                        break;
                    default:
                        throw new VerificationException("Unsupported transition from '" + oldStatus + "' to " + newStatus);
                }
                break;
            case FORWARDED_TO_EMPLOYER:
                switch (newStatus) {
                    case ACCEPTED:
                    case CANCELED:
                    case REJECTED:
                        break;
                    default:
                        throw new VerificationException("Unsupported transition from '" + oldStatus + "' to " + newStatus);
                }
                break;
            case REJECTED:
                switch (newStatus) {
                    case NOMINATED:
                        break;
                    default:
                        throw new VerificationException("Unsupported transition from '" + oldStatus + "' to " + newStatus);
                }
                break;
            case CANCELED:
                switch (newStatus) {
                    case NOMINATED:
                        break;
                    default:
                        throw new VerificationException("Unsupported transition from '" + oldStatus + "' to " + newStatus);
                }
                break;
            case NOMINATED:
                switch (newStatus) {
                    case CANCELED:
                    case FORWARDED_TO_EMPLOYER:
                    case REJECTED:
                        break;
                    default:
                        throw new VerificationException("Unsupported transition from '" + oldStatus + "' to " + newStatus);
                }
                break;
        }
    }

    private OfferGroupEntity verifyOfferIsSharedToGroup(final GroupEntity group, final String offerExternalId) {
        OfferGroupEntity result = null;
        final List<OfferGroupEntity> offerGroups = exchangeDao.findInfoForSharedOffer(offerExternalId);

        for (final OfferGroupEntity offerGroup : offerGroups) {
            if (offerGroup.getGroup().equals(group)) {
                result = offerGroup;
            }
        }

        return result;
    }
}
