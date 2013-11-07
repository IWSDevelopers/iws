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
import net.iaeste.iws.core.transformers.ViewTransformer;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.StudentDao;
import net.iaeste.iws.persistence.ViewsDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.exchange.ApplicationEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import net.iaeste.iws.persistence.entities.exchange.StudentEntity;
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
public final class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    private final StudentDao studentDao;
    private final AccessDao accessDao;
    private final ExchangeDao exchangeDao;
    private final ViewsDao viewsDao;

    public StudentService(final AccessDao accessDao, final ExchangeDao exchangeDao, final StudentDao studentDao, final ViewsDao viewsDao) {
        this.accessDao = accessDao;
        this.exchangeDao = exchangeDao;
        this.studentDao = studentDao;
        this.viewsDao = viewsDao;
    }

    public void processStudent(final Authentication authentication, final StudentRequest request) {
        throw new NotImplementedException("Pending Implementation.");
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

    private ApplicationEntity processStudentApplication(final  Authentication authentication, final StudentApplication application) {
        final GroupEntity memberGroup = accessDao.findMemberGroup(authentication.getUser());
        final String externalId = application.getApplicationId();
        ApplicationEntity applicationEntity = studentDao.findApplicationByExternalId(externalId);
        final OfferEntity sharedOffer = verifyOfferIsSharedToGroup(authentication.getGroup(), application.getOffer().getOfferId());
        final StudentEntity student = studentDao.findStudentByExternal(memberGroup.getId(), application.getStudent().getUser().getUserId());

        if (sharedOffer == null || !sharedOffer.getStatus().equals(OfferState.SHARED)) {
            throw new VerificationException("The offer with id '" + externalId + "' is not shared to the group '" + authentication.getGroup().getGroupName() + "'.");
        }

        //TODO StudentEntity has no externalId and StudentEntity from application has no ID so no change to Student will be reflectec in DB

        if (applicationEntity == null) {
            applicationEntity = transform(application);
            applicationEntity.setOffer(sharedOffer);
            studentDao.persist(authentication, student, applicationEntity.getStudent());
            applicationEntity.setStudent(student);
            studentDao.persist(authentication, applicationEntity);
        } else {
            final ApplicationEntity updated = transform(application);
            studentDao.persist(authentication, student, updated.getStudent());
            studentDao.persist(authentication, applicationEntity, updated);
        }

        return applicationEntity;
    }

    public FetchStudentApplicationsResponse fetchStudentApplications(final Authentication authentication, final FetchStudentApplicationsRequest request) {
        final List<OfferGroupEntity> offerGroups = exchangeDao.findInfoForSharedOffer(request.getOfferId());
        OfferEntity offer = null;
        for (final OfferGroupEntity offerGroup : offerGroups) {
            if (offerGroup.getGroup().equals(authentication.getGroup())) {
                offer = offerGroup.getOffer();
            }
        }

        if (offer == null) {
            throw new VerificationException("The offer with id '" + request.getOfferId() + "' was not found.");
        }

        final List<ApplicationEntity> found = studentDao.findApplicationsForOffer(offer.getId());

        final List<StudentApplication> applications = new ArrayList<>(found.size());
        for (final ApplicationEntity entity : found) {
            applications.add(transform(entity));
        }

        return new FetchStudentApplicationsResponse(applications);
    }

    public StudentApplicationResponse processApplicationStatus(final Authentication authentication, final StudentApplicationRequest request) {
        throw new NotImplementedException("Pending Implementation.");
    }

    private OfferEntity verifyOfferIsSharedToGroup(final GroupEntity group, final String offerExternalId) {
        OfferEntity result = null;
        final List<OfferGroupEntity> offerGroups = exchangeDao.findInfoForSharedOffer(offerExternalId);

        for (final OfferGroupEntity offerGroup : offerGroups) {
            if (offerGroup.getGroup().equals(group)) {
                result = offerGroup.getOffer();
            }
        }

        return result;
    }
}
