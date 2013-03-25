package net.iaeste.iws.fitnesse;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.constants.IWSErrors;
import org.junit.After;
import org.junit.Test;

/**
 * @author  Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class FetchOffersTest {
    private FetchOffers cut;

    @Test
    public void testInvalidCall() {
        cut = new FetchOffers();
        cut.reset();

        // First we login
        cut.setUsernameAndPassword("austria", "incorrect");
        assertThat(cut.login(), is(false));

        cut.execute();

        assertThat(cut.isRequestOk(), is(false));
        assertThat(cut.errorCode(), is(IWSErrors.VERIFICATION_ERROR.getError()));
        assertThat(cut.errorMessage(), startsWith("Invalid Authentication Token provided."));
    }

    @Test
    public void testFetchCall() {
        cut = new FetchOffers();
        cut.reset();

        // First we login
        cut.setUsernameAndPassword("austria", "austria");
        assertThat(cut.login(), is(true));

        // Set fetch request type
        cut.setRequestType("ALL");

        // Try to fetch offers
        cut.execute();
        assertThat(cut.isRequestOk(), is(true));
        assertThat(cut.errorCode(), is(IWSErrors.SUCCESS.getError()));
        assertThat(cut.numberOfFetchedOffers(), is(4));
    }

    @After
    public void logout() {
        cut.deprecateSession();
        cut = null;
    }
}
