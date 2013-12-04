/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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
import net.iaeste.iws.api.requests.FetchFileRequest;
import net.iaeste.iws.api.requests.FileRequest;
import net.iaeste.iws.api.responses.FetchFileResponse;
import net.iaeste.iws.api.responses.FileResponse;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class StorageTest extends AbstractTest {

    private final Storage storage = new StorageClient();

    @Override
    public void setup() {
        token = login("finland@iaeste.fi", "finland");
    }

    @Override
    public void tearDown() {
    }

    @Test
    public void testStoreFindDeleteFile() {
        // First generate a primitive file to store.
        final byte[] testdata = { (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5,
                                  (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 0 };
        final File file = new File();
        file.setFilename("testFile");
        file.setFiledata(testdata);
        file.setKeywords("Test, File, Finland");
        file.setDescription("Finish Testfile");
        file.setMimetype("text/plain");

        final FileRequest request = new FileRequest(file);
        final Group nationalGroup = findNationalGroup(token);
        token.setGroupId(nationalGroup.getGroupId());
        final FileResponse response = storage.processFile(token, request);
        assertThat(response.isOk(), is(true));
        final FetchFileRequest fetchRequest = new FetchFileRequest(response.getFile().getFileId());
        final FetchFileResponse fetchResponseForGroup = storage.fetchFile(token, fetchRequest);
        token.setGroupId(null);
        final FetchFileResponse fetchResponseForUser = storage.fetchFile(token, fetchRequest);
        assertThat(fetchResponseForGroup.isOk(), is(true));
        assertThat(fetchResponseForGroup.getFile().getFiledata(), is(testdata));
        assertThat(fetchResponseForGroup.getFile().getChecksum(), is(response.getFile().getChecksum()));
        assertThat(fetchResponseForUser.isOk(), is(true));
        assertThat(fetchResponseForUser.getFile().getFiledata(), is(testdata));
        assertThat(fetchResponseForUser.getFile().getChecksum(), is(response.getFile().getChecksum()));
        final FileRequest deleteRequest = new FileRequest(response.getFile());
        deleteRequest.setDeleteFile(true);
        token.setGroupId(nationalGroup.getGroupId());
        final FileResponse deleteResponse = storage.processFile(token, deleteRequest);
        assertThat(deleteResponse.isOk(), is(true));
        token.setGroupId(null);
        final FetchFileResponse findDeletedFileResponse = storage.fetchFile(token, fetchRequest);
        assertThat(findDeletedFileResponse.isOk(), is(false));
        assertThat(findDeletedFileResponse.getError(), is(IWSErrors.OBJECT_IDENTIFICATION_ERROR));
        assertThat(findDeletedFileResponse.getMessage(), is("No File was found."));
    }
}
