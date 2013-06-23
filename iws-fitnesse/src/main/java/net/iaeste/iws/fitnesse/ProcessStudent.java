package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Student;
import net.iaeste.iws.api.requests.student.StudentRequest;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.fitnesse.callers.StudentCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class ProcessStudent extends AbstractFixture<Fallible> {

    private final Student student = new StudentCaller();
    private StudentRequest request = new StudentRequest();

    public void processStudent() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(student.processStudent(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
