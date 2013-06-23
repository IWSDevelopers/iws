package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Committees;
import net.iaeste.iws.api.requests.RegionalGroupRequest;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.fitnesse.callers.CommitteeCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class CreateRegionalGroup extends AbstractFixture<Fallible> {

    private final Committees committees = new CommitteeCaller();
    private RegionalGroupRequest request = new RegionalGroupRequest();

    public void createRegionalGroup() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(committees.createRegionalGroup(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
