/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.ProcessEmployer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.requests.exchange.ProcessEmployerRequest;
import net.iaeste.iws.api.responses.exchange.EmployerResponse;
import net.iaeste.iws.fitnesse.callers.ExchangeCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ProcessEmployer extends AbstractFixture<EmployerResponse> {

    private final Exchange exchange = new ExchangeCaller();
    private ProcessEmployerRequest request = null;
    private final Employer employer = new Employer();
    private final Address address = new Address();

    public void setName(final String name) {
        employer.setName(name);
    }

    public void setBusiness(final String business) {
        employer.setBusiness(business);
    }

    public void setDepartment(final String department) {
        employer.setDepartment(department);
    }

    public void setStreet1(final String street1) {
        address.setStreet1(street1);
    }

    public void setStreet2(final String street2) {
        address.setStreet2(street2);
    }

    public void setPostalCode(final String postalCode)  {
        address.setPostalCode(postalCode);
    }

    public void setCity(final String city)  {
        address.setCity(city);
    }

    public void setState(final String state)  {
        address.setState(state);
    }

    public void setPobox(final String pobox)  {
        address.setPobox(pobox);
    }

    public void setCountry(final Country country) {
        address.setCountry(country);
    }

    public void setEmployeesCount(final String employeesCount) {
        employer.setEmployeesCount(employeesCount);
    }

    public void setWebsite(final String website) {
        employer.setWebsite(website);
    }

    public void setWorkingPlace(final String workingPlace) {
        employer.setWorkingPlace(workingPlace);
    }

    public void setCanteen(final Boolean canteen) {
        employer.setCanteen(canteen);
    }

    public void setNearestAirport(final String nearestAirport) {
        employer.setNearestAirport(nearestAirport);
    }

    public void setNearestPublicTransport(final String nearestPublicTransport) {
        employer.setNearestPublicTransport(nearestPublicTransport);
    }

    public String printEmployer() {
        return getResponse().getEmployer().toString();
    }

    public String getEmployerId() {
        return getResponse().getEmployer().getEmployerId();
    }

    public void processEmployer() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        employer.setAddress(address);
        request = new ProcessEmployerRequest(employer);
        setResponse(exchange.processEmployer(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
