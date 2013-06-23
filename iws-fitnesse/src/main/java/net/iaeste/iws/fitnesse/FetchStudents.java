package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Student;
import net.iaeste.iws.api.requests.student.FetchStudentsRequest;
import net.iaeste.iws.api.responses.student.FetchStudentResponse;
import net.iaeste.iws.fitnesse.callers.StudentCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class FetchStudents extends AbstractFixture<FetchStudentResponse> {

    private final Student student = new StudentCaller();
    private FetchStudentsRequest request = new FetchStudentsRequest();

    public void fetchStudents() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(student.fetchStudents(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
