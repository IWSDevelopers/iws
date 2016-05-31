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
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Storage;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.File;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.requests.FetchFileRequest;
import net.iaeste.iws.api.requests.FetchFolderRequest;
import net.iaeste.iws.api.requests.FileRequest;
import net.iaeste.iws.api.responses.FetchFileResponse;
import net.iaeste.iws.api.responses.FetchFolderResponse;
import net.iaeste.iws.api.responses.FileResponse;
import net.iaeste.iws.client.AbstractTest;
import net.iaeste.iws.client.StorageClient;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.charset.Charset;

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
public final class StorageTest extends AbstractTest {

    private final Storage storage = new StorageClient();

    // Below is a list of our test folders, with the first word describing if
    // the folder is expected to be public, protected or mixed, meaning that
    // the end folder is public, but there's a protected folder in the path.
    //   The rest of the name is the name of the Folder Names with Path, where
    // each path of the name represent a folder.
    private static final String ROOT = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUp() {
        // Unused, no need to setup anything here
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tearDown() {
        logout(token);
    }

    // =========================================================================
    // The actual Storage tests
    // =========================================================================

    /**
     * <p>First simple test, try to read the Root folder, i.e. no folder given.
     * As an arbitrary member, which should only reveal all the public folders
     * shared there.</p>
     *
     * <p>IAESTE Tunisia have both a published and protected folder, and as a
     * non-Tunisian member, we should only be able to read the published
     * folder.</p>
     */
    @Test
    public void testReadingRootFolder() {
        token = login("finland@iaeste.fi", "finland");
        final FetchFolderRequest request = new FetchFolderRequest();
        request.setFolderId(ROOT);
        final FetchFolderResponse response = storage.fetchFolder(token, request);

        // Standard check, we assume that everything is ok.
        assertThat(response.isOk(), is(true));
        assertThat(response.getError(), is(IWSErrors.SUCCESS));
        assertThat(response.getMessage(), is(IWSConstants.SUCCESS));

        // Now, let's check that we have a folder with content
        assertThat(response.getFolder(), is(not(nullValue())));
        assertThat(response.getFolder().getFolders().size(), is(3));
        assertThat(response.getFolder().getFiles().size(), is(0));
    }

    /**
     * <p>This is the second test to read the root folder, this time as a
     * member of IAESTE Tunisia, who have a protected folder. So we expect
     * that the list of Folders will also contain this.</p>
     */
    @Test
    public void testReadingRootFolderAsSharingNS() {
        token = login("tunisia@iaeste.tn", "tunisia");
        final FetchFolderRequest request = new FetchFolderRequest();
        request.setFolderId(ROOT);
        final FetchFolderResponse response = storage.fetchFolder(token, request);

        // Standard check, we assume that everything is ok.
        assertThat(response.isOk(), is(true));
        assertThat(response.getError(), is(IWSErrors.SUCCESS));
        assertThat(response.getMessage(), is(IWSConstants.SUCCESS));

        // Now, let's check that we have a folder with content
        assertThat(response.getFolder(), is(not(nullValue())));
        assertThat(response.getFolder().getFolders().size(), is(4));
        assertThat(response.getFolder().getFiles().size(), is(0));
    }

    @Test
    @Ignore
    public void testStoreFetchUpdateDeleteFile() {
        // First generate a primitive file to store.
        final byte[] testData1 = { (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5,
                                   (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 0 };
        final byte[] testData2 = "My Test data".getBytes(Charset.defaultCharset());
        final File file = new File();
        file.setFilename("testFile");
        file.setFiledata(testData1);
        file.setKeywords("Test, File, Finland");
        file.setDescription("Finnish TestFile");
        file.setMimetype("text/plain");

        // Create new file
        final FileRequest createRequest = new FileRequest();
        createRequest.setFile(file);
        final Group nationalGroup = findNationalGroup(token);
        token.setGroupId(nationalGroup.getGroupId());
        final FileResponse response = storage.processFile(token, createRequest);
        assertThat(response.isOk(), is(true));

        // Fetch the File
        final FetchFileRequest fetchRequest = new FetchFileRequest(response.getFile().getFileId());
        fetchRequest.setReadFileData(true);
        final FetchFileResponse fetchResponseForGroup = storage.fetchFile(token, fetchRequest);
        token.setGroupId(null);
        final FetchFileResponse fetchResponseForUser = storage.fetchFile(token, fetchRequest);
        assertThat(fetchResponseForGroup.isOk(), is(true));
        assertThat(fetchResponseForGroup.getFile().getFiledata(), is(testData1));
        assertThat(fetchResponseForGroup.getFile().getChecksum(), is(response.getFile().getChecksum()));
        assertThat(fetchResponseForUser.isOk(), is(true));
        assertThat(fetchResponseForUser.getFile().getFiledata(), is(testData1));
        assertThat(fetchResponseForUser.getFile().getChecksum(), is(response.getFile().getChecksum()));

        // Update the file
        final FileRequest updateRequest = new FileRequest();
        updateRequest.setFile(file);
        file.setFileId(fetchResponseForGroup.getFile().getFileId());
        file.setFiledata(testData2);
        updateRequest.setFile(file);
        token.setGroupId(nationalGroup.getGroupId());
        final FileResponse updateResponse = storage.processFile(token, updateRequest);
        assertThat(updateResponse.isOk(), is(true));

        // Fetch Updated file
        final FetchFileResponse fetchUpdatedFile = storage.fetchFile(token, fetchRequest);
        assertThat(fetchUpdatedFile.isOk(), is(true));
        assertThat(fetchUpdatedFile.getFile().getFiledata(), is(testData2));

        // Delete the File
        final FileRequest deleteRequest = new FileRequest();
        deleteRequest.setFile(response.getFile());
        deleteRequest.setAction(Action.DELETE);
        token.setGroupId(nationalGroup.getGroupId());
        final FileResponse deleteResponse = storage.processFile(token, deleteRequest);
        assertThat(deleteResponse.isOk(), is(true));

        // Finally, verify that the file was deleted
        token.setGroupId(null);
        final FetchFileResponse findDeletedFileResponse = storage.fetchFile(token, fetchRequest);
        assertThat(findDeletedFileResponse.isOk(), is(false));
        assertThat(findDeletedFileResponse.getError(), is(IWSErrors.AUTHENTICATION_ERROR));
        assertThat(findDeletedFileResponse.getMessage(), is("No File was found."));
    }
}
