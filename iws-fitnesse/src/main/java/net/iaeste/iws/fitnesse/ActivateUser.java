package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.fitnesse.callers.AdministrationCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * Created with IntelliJ IDEA.
 * User: martin
 * Date: 4/21/13
 * Time: 8:51 PM
 * To change this template use File | Settings | File Templates.
 */
public final class ActivateUser extends AbstractFixture<Fallible> {

    private final Administration administration = new AdministrationCaller();
    private String activationString = null;

    public void setActivationString(String activationString)
    {
        this.activationString = activationString;
    }

    public void activateUser()
    {
        execute();
    }
    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(administration.activateUser(activationString));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        activationString = null;
    }
}
