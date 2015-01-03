/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.committees.ProcessCommitteeTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client.committees;

import static org.junit.Assert.fail;

import net.iaeste.iws.api.Committees;
import net.iaeste.iws.client.AbstractTest;
import net.iaeste.iws.client.CommitteeClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class ProcessCommitteeTest extends AbstractTest {

    private final Committees committees = new CommitteeClient();

    /**
     * {@inheritDoc}
     */
    @Before
    @Override
    public void setup() {
        token = login("australia@iaeste.au", "australia");
    }

    /**
     * {@inheritDoc}
     */
    @After
    @Override
    public void tearDown() {
        logout(token);
    }

    // =========================================================================
    // Testing Create Committee
    // =========================================================================

    // =========================================================================
    // Testing Update Committee
    // =========================================================================

    // =========================================================================
    // Testing Merge Committee
    // =========================================================================

    // =========================================================================
    // Testing Upgrade Committee
    // =========================================================================

    @Test
    @Ignore("Test case not yet implemented.")
    public void testUpgradeInactiveCommittee() {
        fail("Not yet implemented.");
    }

    @Test
    @Ignore("Test case not yet implemented.")
    public void testUpgradeCooperatingInstitutionToAssociateMember() {
        fail("Not yet implemented.");
    }

    @Test
    @Ignore("Test case not yet implemented.")
    public void testUpgradeCountryWithMultipleCooperatingInstitutions() {
        fail("Not yet implemented.");
    }

    @Test
    @Ignore("Test case not yet implemented.")
    public void testUpgradeAssociateMemberToFullMember() {
        fail("Not yet implemented.");
    }

    @Test
    @Ignore("Test case not yet implemented.")
    public void testUpgradeFullMember() {
        fail("Not yet implemented.");
    }

    // =========================================================================
    // Testing Activat Committee
    // =========================================================================

    // =========================================================================
    // Testing Suspend Committee
    // =========================================================================

    // =========================================================================
    // Testing Delete Committee
    // =========================================================================
}
