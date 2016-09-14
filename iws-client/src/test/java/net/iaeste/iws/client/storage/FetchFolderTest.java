/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.client.storage;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isOneOf;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Storage;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.File;
import net.iaeste.iws.api.dtos.Folder;
import net.iaeste.iws.api.requests.FetchFolderRequest;
import net.iaeste.iws.api.responses.FetchFolderResponse;
import net.iaeste.iws.client.AbstractTest;
import net.iaeste.iws.client.StorageClient;
import net.iaeste.iws.client.utils.Credentials;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>Note, this test is scheduled for an overhaul, so it is parameterized.
 * Since it is then easier to check each of the different folders using
 * different accounts, so all aspects of the fetching can be achieved. Test
 * should also be split up into a Fetching Test and a Processing Test.</p>
 *
 * <p>A better division between Files and Folders is also required.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@RunWith(Parameterized.class)
public final class FetchFolderTest extends AbstractTest {

    private static final Logger LOG = LoggerFactory.getLogger(FetchFolderTest.class);

    private static final Credentials BOARD = new Credentials("australia@iaeste.au", "australia");
    private static final Credentials TUNISIA = new Credentials("tunisia@iaeste.tn", "tunisia");
    private static final Credentials OTHER = new Credentials("finland@iaeste.fi", "finland");

    private static final String ROOT = null;
    private static final String PUBLIC_BOARD = "6adc3bac-c85c-4dfa-b9d8-3d9b848314af";
    private static final String PUBLIC_BOARD_AC = "c9bde21a-011e-42e4-8985-b1da95c0fbdf";
    private static final String PUBLIC_BOARD_AC_2014 = "af0a0c34-7037-4153-a707-65a1ed0b380b";
    private static final String PROTECTED_BOARD_FINANCES = "b54a005d-d9cd-4456-9263-7ab3833ab303";
    private static final String MIXED_BOARD_FINANCES_2014 = "4126e3c4-2e79-4ee1-a73f-a3ab5f9e52a5";
    private static final String PROTECTED_BOARD_FINANCES_2015 = "9dd06a36-4e02-4a7d-9a46-da78648eaaae";

    private static final String PUBLIC_TUNISIA = "94a6d74f-c510-4302-83fd-cd79ea8a4c9a";
    private static final String PROTECTED_TUNISIA_2014 = "df7a45f5-2aed-4529-a527-488ad538e03a";
    private static final String MIXED_TUNISIA_2014_EU = "30a460be-641e-4cb9-a336-5eab00c19c3a";
    private static final String MIXED_TUNISIA_2014_OTHER = "bb7953a8-8b7a-43a2-84fe-dca74291abd3";
    private static final String PUBLIC_TUNISIA_2015 = "b0ed8121-f359-4bc5-8ce8-bc5c4a7de536";
    private static final String PUBLIC_TUNISIA_2015_EU = "ed5e0487-0605-428d-b40c-78e88df85d8d";
    private static final String PUBLIC_TUNISIA_2015_OTHER = "0328281b-cf80-4c7a-8ad7-04721eaa45aa";

    private static final String UNAUTHORIZED = "Unauthorized attempt at accessing the Folder ";
    private static final String[] EMPTY = {};

    private final Storage storage = new StorageClient();
    private final Credentials credentials;
    private final String folderId;
    private final String iwsMessage;
    private final String[] folders;
    private final String[] files;

    /**
     * <p>The test is running with the following parameters:</p>
     * <ul>
     *   <li>The name of the test, to be listed when running</li>
     *   <li>The credentials for the user invoking IWS</li>
     *   <li>The FolderId to read</li>
     *   <li>The expected IWS Result message (OK if successful)</li>
     *   <li>If successful, a list of existing sub-folder names</li>
     *   <li>If successful, a list of existing files</li>
     * </ul>
     *
     * @return Parameters for the testing
     */
    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { "Board reading /", BOARD, ROOT, IWSConstants.SUCCESS, new String[]{ "Minutes", "2014", "Travel Documents" }, EMPTY },
                { "Board reading /Minutes/", BOARD, PUBLIC_BOARD, IWSConstants.SUCCESS, new String[]{ "AC", "Finances" }, new String[]{ "bla01.txt", "bla02.txt" } },
                { "Board reading /Minutes/AC", BOARD, PUBLIC_BOARD_AC, IWSConstants.SUCCESS, new String[]{ "2014" }, new String[]{ "bla03.txt", "bla04.txt" } },
                { "Board reading /Minutes/AC/2014", BOARD, PUBLIC_BOARD_AC_2014, IWSConstants.SUCCESS, EMPTY, new String[]{ "bla05.txt", "bla06.txt" } },
                { "Board reading /Minutes/Finances", BOARD, PROTECTED_BOARD_FINANCES, IWSConstants.SUCCESS, new String[]{ "2014", "2015" }, new String[]{ "bla07.txt", "bla08.txt" } },
                { "Board reading /Minutes/Finances/2014", BOARD, MIXED_BOARD_FINANCES_2014, IWSConstants.SUCCESS, EMPTY, new String[]{ "bla09.txt", "bla10.txt" } },
                { "Board reading /Minutes/Finances/2015", BOARD, PROTECTED_BOARD_FINANCES_2015, IWSConstants.SUCCESS, EMPTY, new String[]{ "bla11.txt", "bla12.txt" } },
                { "Board reading /Tunisia/", BOARD, PUBLIC_TUNISIA, IWSConstants.SUCCESS, new String[]{ "2015" }, new String[]{ "bla25.txt" } },
                { "Board reading /Tunisia/2014/", BOARD, PROTECTED_TUNISIA_2014, UNAUTHORIZED + PROTECTED_TUNISIA_2014 + '.', EMPTY, EMPTY },
                { "Board reading /Tunisia/2014/EU/", BOARD, MIXED_TUNISIA_2014_EU, UNAUTHORIZED + MIXED_TUNISIA_2014_EU + '.', EMPTY, EMPTY },
                { "Board reading /Tunisia/2014/Other/", BOARD, MIXED_TUNISIA_2014_OTHER, UNAUTHORIZED + MIXED_TUNISIA_2014_OTHER + '.', EMPTY, EMPTY },
                { "Board reading /Tunisia/2015/", BOARD, PUBLIC_TUNISIA_2015, IWSConstants.SUCCESS, new String[]{ "EU", "Other" }, new String[]{ "bla27.txt" } },
                { "Board reading /Tunisia/2015/EU/", BOARD, PUBLIC_TUNISIA_2015_EU, IWSConstants.SUCCESS, EMPTY, new String[]{ "bla29.txt" } },
                { "Board reading /Tunisia/2015/Other/", BOARD, PUBLIC_TUNISIA_2015_OTHER, IWSConstants.SUCCESS, EMPTY, new String[]{ "bla31.txt" } },

                { "Tunisia reading /", TUNISIA, ROOT, IWSConstants.SUCCESS, new String[]{ "Minutes", "2014", "2015", "Travel Documents" }, EMPTY },
                { "Tunisia reading /Minutes/", TUNISIA, PUBLIC_BOARD, IWSConstants.SUCCESS, new String[]{ "AC" }, new String[]{ "bla01.txt" } },
                { "Tunisia reading /Minutes/AC", TUNISIA, PUBLIC_BOARD_AC, IWSConstants.SUCCESS, new String[]{ "2014" }, new String[]{ "bla03.txt" } },
                { "Tunisia reading /Minutes/AC/2014", TUNISIA, PUBLIC_BOARD_AC_2014, IWSConstants.SUCCESS, EMPTY, new String[]{ "bla05.txt" } },
                { "Tunisia reading /Minutes/Finances", TUNISIA, PROTECTED_BOARD_FINANCES, UNAUTHORIZED + PROTECTED_BOARD_FINANCES + '.', EMPTY, EMPTY },
                { "Tunisia reading /Minutes/Finances/2014", TUNISIA, MIXED_BOARD_FINANCES_2014, UNAUTHORIZED + MIXED_BOARD_FINANCES_2014 + '.', EMPTY, EMPTY },
                { "Tunisia reading /Minutes/Finances/2015", TUNISIA, PROTECTED_BOARD_FINANCES_2015, UNAUTHORIZED + PROTECTED_BOARD_FINANCES_2015 + '.', EMPTY, EMPTY },
                { "Tunisia reading /Tunisia/", TUNISIA, PUBLIC_TUNISIA, IWSConstants.SUCCESS, new String[]{ "2014", "2015" }, new String[]{ "bla25.txt", "bla26.txt" } },
                { "Tunisia reading /Tunisia/2014/", TUNISIA, PROTECTED_TUNISIA_2014, IWSConstants.SUCCESS, new String[]{ "EU", "Other" }, new String[]{ "bla33.txt", "bla34.txt" } },
                { "Tunisia reading /Tunisia/2014/EU/", TUNISIA, MIXED_TUNISIA_2014_EU, IWSConstants.SUCCESS, EMPTY, new String[]{ "bla35.txt", "bla36.txt" } },
                { "Tunisia reading /Tunisia/2014/Other/", TUNISIA, MIXED_TUNISIA_2014_OTHER, IWSConstants.SUCCESS, EMPTY, new String[]{ "bla37.txt", "bla38.txt" } },
                { "Tunisia reading /Tunisia/2015/", TUNISIA, PUBLIC_TUNISIA_2015, IWSConstants.SUCCESS, new String[]{ "EU", "Other" }, new String[]{ "bla27.txt", "bla28.txt" } },
                { "Tunisia reading /Tunisia/2015/EU/", TUNISIA, PUBLIC_TUNISIA_2015_EU, IWSConstants.SUCCESS, EMPTY, new String[]{ "bla29.txt", "bla30.txt" } },
                { "Tunisia reading /Tunisia/2015/Other/", TUNISIA, PUBLIC_TUNISIA_2015_OTHER, IWSConstants.SUCCESS, EMPTY, new String[]{ "bla31.txt", "bla32.txt" } },

                { "Other reading /", OTHER, ROOT, IWSConstants.SUCCESS, new String[]{ "Minutes", "2014", "Travel Documents" }, EMPTY },
                { "Other reading /Minutes/", OTHER, PUBLIC_BOARD, IWSConstants.SUCCESS, new String[]{ "AC" }, new String[]{ "bla01.txt" } },
                { "Other reading /Minutes/AC", OTHER, PUBLIC_BOARD_AC, IWSConstants.SUCCESS, new String[]{ "2014" }, new String[]{ "bla03.txt" } },
                { "Other reading /Minutes/AC/2014", OTHER, PUBLIC_BOARD_AC_2014, IWSConstants.SUCCESS, EMPTY, new String[]{ "bla05.txt" } },
                { "Other reading /Minutes/Finances", OTHER, PROTECTED_BOARD_FINANCES, UNAUTHORIZED + PROTECTED_BOARD_FINANCES + '.', EMPTY, EMPTY },
                { "Other reading /Minutes/Finances/2014", OTHER, MIXED_BOARD_FINANCES_2014, UNAUTHORIZED + MIXED_BOARD_FINANCES_2014 + '.', EMPTY, EMPTY },
                { "Other reading /Minutes/Finances/2015", OTHER, PROTECTED_BOARD_FINANCES_2015, UNAUTHORIZED + PROTECTED_BOARD_FINANCES_2015 + '.', EMPTY, EMPTY },
                { "Other reading /Tunisia/", OTHER, PUBLIC_TUNISIA, IWSConstants.SUCCESS, new String[]{ "2015" }, new String[]{ "bla25.txt" } },
                { "Other reading /Tunisia/2014/", OTHER, PROTECTED_TUNISIA_2014, UNAUTHORIZED + PROTECTED_TUNISIA_2014 + '.', EMPTY, EMPTY },
                { "Other reading /Tunisia/2014/EU/", OTHER, MIXED_TUNISIA_2014_EU, UNAUTHORIZED + MIXED_TUNISIA_2014_EU + '.', EMPTY, EMPTY },
                { "Other reading /Tunisia/2014/Other/", OTHER, MIXED_TUNISIA_2014_OTHER, UNAUTHORIZED + MIXED_TUNISIA_2014_OTHER + '.', EMPTY, EMPTY },
                { "Other reading /Tunisia/2015/", OTHER, PUBLIC_TUNISIA_2015, IWSConstants.SUCCESS, new String[]{ "EU", "Other" }, new String[]{ "bla27.txt" } },
                { "Other reading /Tunisia/2015/EU/", OTHER, PUBLIC_TUNISIA_2015_EU, IWSConstants.SUCCESS, EMPTY, new String[]{ "bla29.txt" } },
                { "Other reading /Tunisia/2015/Other/", OTHER, PUBLIC_TUNISIA_2015_OTHER, IWSConstants.SUCCESS, EMPTY, new String[]{ "bla31.txt" } },
        });
    }

    public FetchFolderTest(final String name, final Credentials credentials, final String folderId, final String iwsMessage, final String[] folders, final String[] files){
        LOG.debug("Initializing {} test.", name);
        this.credentials = credentials;
        this.folderId = folderId;
        this.iwsMessage = iwsMessage;
        this.folders = Arrays.copyOf(folders, folders.length);
        this.files = Arrays.copyOf(files, files.length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUp() {
        token = login(credentials.getUsername(), credentials.getPassword());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tearDown() {
        logout(token);
    }

    @Test
    public void test() {
        final FetchFolderRequest request = new FetchFolderRequest();
        request.setFolderId(folderId);
        final FetchFolderResponse response = storage.fetchFolder(token, request);

        // First check, the response error information. Regardless of what is
        // going on, we always get a response.
        assertThat(response.getMessage(), is(iwsMessage));
        final Folder folder = response.getFolder();

        // If the response was successful, i.e. the user was allowed to read a
        // particular folder, then we can verify the findings.
        if (Objects.equals(IWSConstants.SUCCESS, iwsMessage)) {
            assertThat(folder, is(not(nullValue())));

            if (folders.length > 0) {
                // A more detailed check is not possible, since other tests
                // will be creating Files & Folders, which makes this test
                // more complicated, if we were to cover all aspects. So in
                // the name of laziness, this is not done.
                assertThat(folder.getFolders().size() >= folders.length, is(true));
            }

            if (files.length > 0) {
                assertThat(folder.getFiles().size(), is(files.length));
                for (final File file : folder.getFiles()) {
                    assertThat(file.getFilename(), isOneOf(files));
                }
            }
        } else {
            assertThat(folder.getFolderId(), is(nullValue()));
            assertThat(folder.getFoldername(), is(nullValue()));
            assertThat(folder.getGroup(), is(nullValue()));
            assertThat(folder.getFolders().isEmpty(), is(true));
            assertThat(folder.getFiles().isEmpty(), is(true));
        }
    }
}
