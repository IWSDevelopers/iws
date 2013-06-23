package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Committees;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.fitnesse.callers.CommitteeCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class ManageComittee extends AbstractFixture<Fallible> {

    private final Committees committees = new CommitteeCaller();
    private CommitteeRequest request = new CommitteeRequest();

    public void manageComittee() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(committees.manageCommittee(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
