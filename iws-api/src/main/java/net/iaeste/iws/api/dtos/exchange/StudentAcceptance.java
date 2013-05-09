package net.iaeste.iws.api.dtos.exchange;

import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Date;

import java.util.Map;

/**
 * Contains acceptance information (N5a) for an application.
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class StudentAcceptance extends AbstractVerification  {

    // TODO implement

    private String applicationId;

    private Date firstWorkingDay;
    private String workingPlace; // is often different than the company address on the offer
    private String contactPerson;
    private String contactPersonEmail;
    private String additionalInformation; // allow up to 5000 characters


    @Override
    public Map<String, String> validate() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}