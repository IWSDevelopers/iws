/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.transformers.ViewTransformer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.transformers;

import static net.iaeste.iws.core.transformers.EmbeddedConverter.convert;

import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.dtos.exchange.Student;
import net.iaeste.iws.persistence.views.EmployerView;
import net.iaeste.iws.persistence.views.OfferView;
import net.iaeste.iws.persistence.views.StudentView;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class ViewTransformer {

    /**
     * Private Constructor, this is a utility class.
     */
    private ViewTransformer() {
    }

    /**
     * Transforms the {@link EmployerView} to an {@link Employer} DTO Object. As
     * the DTO contains several sub-objects, which again may contain certain
     * other objects, the transformer is building the entire Object Structure.
     *
     * @param view EmployerView to transform
     * @return Employer DTO Object to display externally
     */
    public static Employer transform(final EmployerView view) {
        final Country country = convert(view.getCountry());

        final Group group = convert(view.getGroup());
        group.setCountry(country);

        final Address address = convert(view.getAddress());
        address.setCountry(country);

        final Employer employer = convert(view.getEmployer());
        employer.setAddress(address);
        employer.setGroup(group);

        return employer;
    }

    /**
     * Transforms the {@link OfferView} to an {@link Offer} DTO Object. As the
     * DTO contains several sub-objects, which again may contain certain other
     * objects, the transformer is building the entire Object Structure.
     *
     * @param view OfferView to transform
     * @return Offer DTO Object to display externally
     */
    public static Offer transform(final OfferView view) {
        final Country country = convert(view.getCountry());

        final Group group = convert(view.getGroup());
        group.setCountry(country);

        final Address address = convert(view.getAddress());
        address.setCountry(country);

        final Employer employer = convert(view.getEmployer());
        employer.setAddress(address);
        employer.setGroup(group);

        final Offer offer = convert(view.getOffer());
        offer.setNsFirstname(view.getNsFirstname());
        offer.setNsLastname(view.getNsLastname());
        offer.setEmployer(employer);

        return offer;
    }

    /**
     * Transforms the {@link StudentView} to an {@link Student} DTO Object. As
     * the DTO contains several sub-objects, which again may contain certain
     * other objects, the transformer is building the entire Object Structure.
     *
     * @param view StudentView to transform
     * @return Student DTO Object to display externally
     */
    public static Student transform(final StudentView view) {
        final Student student = convert(view.getStudent());

        final User user = convert(view.getUser());
        student.setUser(user);

        return student;
    }
}
