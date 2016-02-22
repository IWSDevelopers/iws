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
package net.iaeste.iws.client;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Storage;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.File;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.requests.FetchFileRequest;
import net.iaeste.iws.api.requests.FetchFolderRequest;
import net.iaeste.iws.api.requests.FileRequest;
import net.iaeste.iws.api.responses.FetchFileResponse;
import net.iaeste.iws.api.responses.FetchFolderResponse;
import net.iaeste.iws.api.responses.FileResponse;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.charset.Charset;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Ignore
public final class StorageTest extends AbstractTest {

    private final Storage storage = new StorageClient();

    // Below is a list of our test folders, with the first word describing if
    // the folder is expected to be public, protected or mixed, meaning that
    // the end folder is public, but there's a protected folder in the path.
    //   The rest of the name is the name of the Folder Names with Path, where
    // each path of the name represent a folder.
    private static final String ROOT = null;
    private static final String PUBLIC_BOARD = "6adc3bac-c85c-4dfa-b9d8-3d9b848314af";
    private static final String PUBLIC_BOARD_AC = "c9bde21a-011e-42e4-8985-b1da95c0fbdf";
    private static final String PUBLIC_BOARD_AC_2014 = "af0a0c34-7037-4153-a707-65a1ed0b380b";
    private static final String PROTECTED_BOARD_FINANCES = "b54a005d-d9cd-4456-9263-7ab3833ab303";
    private static final String MIXED_BOARD_FINANCES_2014 = "4126e3c4-2e79-4ee1-a73f-a3ab5f9e52a5";
    private static final String PROTECTED_BOARD_FINANCES_2015 = "9dd06a36-4e02-4a7d-9a46-da78648eaaae";
    private static final String PUBLIC_SID_2014 = "d8d6d7eb-754a-4e3b-8d4d-7657339096d1";
    private static final String PUBLIC_SID_2014_ADMINISTRATION = "01749318-9f20-4369-bd05-555c18e643ad";
    private static final String PUBLIC_SID_2014_IWUG = "888f6c83-fcae-4e4a-9f8c-4fb02c2ec8e2";
    private static final String PROTECTED_SID_2015 = "60ba5c47-3dce-41a1-94f9-e0dafe2f717a";
    private static final String MIXED_SID_2015_ADMINISTRATION = "a4fa7715-3a10-470f-8538-e8ee13666819";
    private static final String MIXED_SID_2015_IWUG = "f50bed53-7295-4b91-8746-78136df0a189";
    private static final String PUBLIC_TUNISIA = "94a6d74f-c510-4302-83fd-cd79ea8a4c9a";
    private static final String PUBLIC_TUNISIA_2015 = "b0ed8121-f359-4bc5-8ce8-bc5c4a7de536";
    private static final String PUBLIC_TUNISIA_2015_EU = "ed5e0487-0605-428d-b40c-78e88df85d8d";
    private static final String PUBLIC_TUNISIA_2015_OTHER = "0328281b-cf80-4c7a-8ad7-04721eaa45aa";
    private static final String PROTECTED_TUNISIA_2014 = "df7a45f5-2aed-4529-a527-488ad538e03a";
    private static final String MIXED_TUNISIA_2014_EU = "30a460be-641e-4cb9-a336-5eab00c19c3a";
    private static final String MIXED_TUNISIA_2014_OTHER = "bb7953a8-8b7a-43a2-84fe-dca74291abd3";

    /**
     * {@inheritDoc}
     */
    @Override
    public void setup() {
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
        final FetchFolderRequest request = new FetchFolderRequest(ROOT);
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
        final FetchFolderRequest request = new FetchFolderRequest(ROOT);
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

    /**
     * <p>The Board have a published folder, which contain both minutes and
     * finance data. The finance data is protected, so reading the published
     * Board folder as a non-Board member should only give a single folder
     * back.</p>
     */
    @Test
    public void testReadingBoardFolder() {
        token = login("finland@iaeste.fi", "finland");
        final FetchFolderRequest request = new FetchFolderRequest(PUBLIC_BOARD);
        final FetchFolderResponse response = storage.fetchFolder(token, request);

        // Standard check, we assume that everything is ok.
        assertThat(response.isOk(), is(true));
        assertThat(response.getError(), is(IWSErrors.SUCCESS));
        assertThat(response.getMessage(), is(IWSConstants.SUCCESS));

        // Now, let's check that we have a folder with content
        assertThat(response.getFolder(), is(not(nullValue())));
        assertThat(response.getFolder().getFolders().size(), is(1));
        assertThat(response.getFolder().getFiles().size(), is(0));
    }

    @Test
    public void testReadingBoardFolderAsBoardMember() {
        token = login("australia@iaeste.au", "australia");
        final FetchFolderRequest request = new FetchFolderRequest(PUBLIC_BOARD);
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

    @Test
    public void testReadingIWUGFolder() {
        // The SID IWUG Folder have Id: 888f6c83-fcae-4e4a-9f8c-4fb02c2ec8e2
        // Contain zero sub folders and 1 file
        final FetchFolderRequest request = new FetchFolderRequest("888f6c83-fcae-4e4a-9f8c-4fb02c2ec8e2");
        final FetchFolderResponse response = storage.fetchFolder(token, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getFolder().getFoldername(), is("IWUG"));
        assertThat(response.getFolder().getFolders().size(), is(0));
        assertThat(response.getFolder().getFiles().size(), is(1));
        assertThat(response.getFolder().getFiles().get(0).getFilename(), is("bla17.txt"));
    }

    @Test
    public void testReadingRootFolderAsBoard() {
        final AuthenticationToken tunisia = login("tunisia@iaeste.tn", "tunisia");
        final FetchFolderRequest request = new FetchFolderRequest(ROOT);
        final FetchFolderResponse response = storage.fetchFolder(tunisia, request);

        // Standard check, we assume that everything is ok.
        assertThat(response.isOk(), is(true));
        assertThat(response.getError(), is(IWSErrors.SUCCESS));
        assertThat(response.getMessage(), is(IWSConstants.SUCCESS));

        // Now, let's check that we have a folder with content
        assertThat(response.getFolder(), is(not(nullValue())));
        assertThat(response.getFolder().getFolders().size(), is(4));
        assertThat(response.getFolder().getFiles().size(), is(0));

        logout(tunisia);
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
        final FileRequest createRequest = new FileRequest(file);
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
        final FileRequest updateRequest = new FileRequest(file);
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
        final FileRequest deleteRequest = new FileRequest(response.getFile());
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
