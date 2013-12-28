/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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

import net.iaeste.iws.api.dtos.exchange.Student;
import net.iaeste.iws.api.dtos.exchange.StudentApplication;
import net.iaeste.iws.api.enums.exchange.ApplicationStatus;
import net.iaeste.iws.api.enums.exchange.OfferState;
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
import net.iaeste.iws.core.transformers.ViewTransformer;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.StudentDao;
import net.iaeste.iws.persistence.ViewsDao;
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
        return new StudentApplicationResponse(transform(entity));
    }

    private ApplicationEntity processStudentApplication(final Authentication authentication, final StudentApplication application) {
        final GroupEntity memberGroup = accessDao.findMemberGroup(authentication.getUser());
        final String externalId = application.getApplicationId();
        ApplicationEntity applicationEntity = dao.findApplicationByExternalId(externalId);
        final OfferGroupEntity sharedOfferGroup = verifyOfferIsSharedToGroup(authentication.getGroup(), application.getOffer().getOfferId());
        final StudentEntity student = dao.findStudentByExternal(memberGroup.getId(), application.getStudent().getUser().getUserId());

        if ((sharedOfferGroup == null) || (sharedOfferGroup.getGroup() == null) || (sharedOfferGroup.getOffer().getStatus() != OfferState.SHARED)) {
            throw new VerificationException("The offer with id '" + externalId + "' is not shared to the group '" + authentication.getGroup().getGroupName() + "'.");
        }

        //TODO StudentEntity has no externalId and StudentEntity from application has no ID so no change to Student will be reflectec in DB

        if (applicationEntity == null) {
            applicationEntity = transform(application);
            applicationEntity.setOfferGroup(sharedOfferGroup);
            processAddress(authentication, applicationEntity.getHomeAddress());
            processAddress(authentication, applicationEntity.getAddressDuringTerms());
            dao.persist(authentication, student, applicationEntity.getStudent());
            applicationEntity.setStudent(student);
            dao.persist(authentication, applicationEntity);
        } else {
            final ApplicationEntity updated = transform(application);
            //using OfferGroup from found entity since this field can't be updated
            updated.setOfferGroup(applicationEntity.getOfferGroup());
            processAddress(authentication, applicationEntity.getHomeAddress(), application.getHomeAddress());
            processAddress(authentication, applicationEntity.getAddressDuringTerms(), application.getAddressDuringTerms());
            dao.persist(authentication, student, updated.getStudent());
            dao.persist(authentication, applicationEntity, updated);
        }

        return applicationEntity;
    }

    public FetchStudentApplicationsResponse fetchStudentApplications(final Authentication authentication, final FetchStudentApplicationsRequest request) {
        final String offerExternalId = request.getOfferId();
        final OfferEntity ownedOffer = exchangeDao.findOfferByExternalId(authentication, offerExternalId);

        List<ApplicationView> found;
        if (ownedOffer != null && ownedOffer.getEmployer().getGroup().equals(authentication.getGroup())) {
            found = dao.findForeignApplicationsForOffer(offerExternalId, authentication.getGroup().getId());
        } else {
            found = dao.findDomesticApplicationsForOffer(offerExternalId, authentication.getGroup().getId());
        }

        final List<StudentApplication> applications = new ArrayList<>(found.size());
        for (final ApplicationView entity : found) {
            applications.add(ViewTransformer.transform(entity));
        }

        return new FetchStudentApplicationsResponse(applications);
    }

    public StudentApplicationResponse processApplicationStatus(final Authentication authentication, final StudentApplicationRequest request) {
        //TODO - verify that only owner of the application or offer can change the status
        final ApplicationEntity found = dao.findApplicationByExternalId(request.getApplicationId());

        if (found == null) {
            throw new VerificationException("The application with id '" + request.getApplicationId() + "' was not found.");
        }

        final OfferGroupEntity foundOfferGroup = found.getOfferGroup();
        if (foundOfferGroup == null ||
            (!authentication.getGroup().equals(foundOfferGroup.getGroup())
             && !authentication.getGroup().equals(foundOfferGroup.getOffer().getEmployer().getGroup()))
           ) {
            throw new VerificationException("Only groups related to the application can change its status");
        }

        final StudentApplication studentApplication = transform(found);

        //TODO - see #526
        //TODO - when application status affects also offer status, change it accordingly
        if (studentApplication.getStatus() == ApplicationStatus.APPLIED) {
            switch (request.getStatus()) {
                case NOMINATED:
                    studentApplication.setNominatedAt(new DateTime());
                    studentApplication.setStatus(request.getStatus());
                    ApplicationEntity updated = transform(studentApplication);
                    //using OfferGroup from found entity since this field can't be updated
                    updated.setOfferGroup(found.getOfferGroup());
                    dao.persist(authentication, found, updated);
                    return new StudentApplicationResponse(studentApplication);
                default:
                    throw new VerificationException("Unsupported transition from '" + studentApplication.getStatus() + "' to " + request.getStatus());
            }
        } else {
            throw new NotImplementedException("Pending Implementation.");
        }
    }

    private void verifyApplicationStatusTransition(final ApplicationStatus oldStatus, final ApplicationStatus newStatus) {
        //TODO - move switch from processApplicationStatus, check correct order and throw exception if violated
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
