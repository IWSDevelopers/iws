/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.StorageTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Storage;
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
import org.junit.Ignore;
import org.junit.Test;

import java.nio.charset.Charset;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class StorageTest extends AbstractTest {

    private final Storage storage = new StorageClient();

    //--  Root EId: "afec3bc0-296b-4bf2-8a9e-c2d7b74e93a0"
    //--    - Board Minutes PUBLIC                 EId: "6adc3bac-c85c-4dfa-b9d8-3d9b848314af"
    //--       - AC PUBLIC                         EId: "c9bde21a-011e-42e4-8985-b1da95c0fbdf"
    //--          - 2014 PUBLIC                    EId: "af0a0c34-7037-4153-a707-65a1ed0b380b"
    //--       - Finances PROTECTED                EId: "b54a005d-d9cd-4456-9263-7ab3833ab303"
    //--          - 2014 PUBLIC                    EId: "4126e3c4-2e79-4ee1-a73f-a3ab5f9e52a5"
    //--          - 2015 PROTECTED                 EId: "9dd06a36-4e02-4a7d-9a46-da78648eaaae"
    //--    - SID 2014 PUBLIC                      EId: "d8d6d7eb-754a-4e3b-8d4d-7657339096d1"
    //--       - Administration PUBLIC             EId: "01749318-9f20-4369-bd05-555c18e643ad"
    //--       - IWUG PUBLIC                       EId: "888f6c83-fcae-4e4a-9f8c-4fb02c2ec8e2"
    //--    - SID 2015 PROTECTED                   EId: "60ba5c47-3dce-41a1-94f9-e0dafe2f717a"
    //--       - Administration PUBLIC             EId: "a4fa7715-3a10-470f-8538-e8ee13666819"
    //--       - IWUG PUBLIC                       EId: "f50bed53-7295-4b91-8746-78136df0a189"
    //--    - NC Tunisia Travel Documents PUBLIC   EId: "94a6d74f-c510-4302-83fd-cd79ea8a4c9a"
    //--       - 2015 PUBLIC                       EId: "b0ed8121-f359-4bc5-8ce8-bc5c4a7de536"
    //--          - EU PUBLIC                      EId: "ed5e0487-0605-428d-b40c-78e88df85d8d"
    //--          - Other PUBLIC                   EId: "0328281b-cf80-4c7a-8ad7-04721eaa45aa"
    //--       - 2014 PROTECTED                    EId: "df7a45f5-2aed-4529-a527-488ad538e03a"
    //--          - EU PUBLIC                      EId: "30a460be-641e-4cb9-a336-5eab00c19c3a"
    //--          - Other PUBLIC                   EId: "bb7953a8-8b7a-43a2-84fe-dca74291abd3"
    //--

    @Override
    public void setup() {
        token = login("finland@iaeste.fi", "finland");
    }

    @Override
    public void tearDown() {
        logout(token);
    }

    @Test
    public void testReadingFolders() {
        final FetchFolderRequest request = new FetchFolderRequest();
        final FetchFolderResponse response = storage.fetchFolder(token, request);
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
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
