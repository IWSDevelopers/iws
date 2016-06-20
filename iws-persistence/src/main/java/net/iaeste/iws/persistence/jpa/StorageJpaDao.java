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
package net.iaeste.iws.persistence.jpa;

import static net.iaeste.iws.common.configuration.InternalConstants.ROOT_FOLDER_EID;

import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.StorageDao;
import net.iaeste.iws.persistence.entities.FileEntity;
import net.iaeste.iws.persistence.entities.FolderEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.IWSEntity;
import net.iaeste.iws.persistence.exceptions.StorageException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
public final class StorageJpaDao extends BasicJpaDao implements StorageDao {

    /**
     * Default Constructor.
     *
     * @param entityManager Entity Manager instance to use
     * @param settings      IWS System Settings
     */
    public StorageJpaDao(final EntityManager entityManager, final Settings settings) {
        super(entityManager, settings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FolderEntity> findFolders(final String eid) {
        final List<FolderEntity> folders;

        if (ROOT_FOLDER_EID.equals(eid)) {
            folders = new ArrayList<>(1);
            folders.add(findRootFolder());
        } else {
            folders = findFoldersRecursively(eid);
        }

        return folders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FolderEntity> readFolders(final Authentication authentication, final FolderEntity folder) {
        final String jql =
                "select f FROM FolderEntity f " +
                "where f.parentId = :pid" +
                "  and (" +
                "    (f.group.groupType.folderType = :type" +
                "      and f.privacy = :privacy)" +
                "    or (f.group.id in (" +
                "      select u2g.group.id" +
                "      from UserGroupEntity u2g" +
                "      where u2g.user.id = :uid)))";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("type", GroupType.FolderType.PUBLIC);
        query.setParameter("privacy", Privacy.PUBLIC);
        query.setParameter("uid", authentication.getUser().getId());
        query.setParameter("pid", folder.getId());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FileEntity> readFiles(final Authentication authentication, final FolderEntity folder) {
        final String jql =
                "select f FROM FileEntity f " +
                "where f.folder.id = :fid" +
                "  and ((f.group.groupType.folderType = '" + GroupType.FolderType.PUBLIC + '\'' +
                "    and f.privacy = '" + Privacy.PUBLIC + "')" +
                "  or (f.group.id in (" +
                "      select u2g.group.id" +
                "      from UserGroupEntity u2g" +
                "      where u2g.user.id = :uid)))";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("uid", authentication.getUser().getId());
        query.setParameter("fid", folder.getId());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FolderEntity findRootFolder() {
        final String jql =
                "select f from FolderEntity f " +
                "where f.externalId = :eid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("eid", ROOT_FOLDER_EID);

        return getSingleResultWithException(query, "Could not uniquely identify the folder by its Id.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FolderEntity findParentFolder(final String parentId, final GroupEntity group) {
        final String jql =
                "select f from FolderEntity f " +
                "where f.id = :fid" +
                "  and f.group.id = :gid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("fid", parentId);
        query.setParameter("gid", group.getId());

        return getSingleResultWithException(query, "Could not uniquely identify the folder by its Id.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FolderEntity findRootFolder(final Group group) {
        final String jql =
                "select f from FolderEntity f " +
                "where f.group.parentId is null" +
                "  and f.group.externalId = :egid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("egid", group.getGroupId());

        return getSingleResultWithException(query, "Multiple Root folders exists for Group " + group.getGroupName() + ", only one is allowed.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FolderEntity findSimilarFolder(final String foldername, final Long parentId) {
        final String jql =
                "select f from FolderEntity f " +
                "where lower(f.foldername) = lower(:name)" +
                "  and f.parentId = :pid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("name", foldername.trim());
        query.setParameter("pid", parentId);

        return getSingleResultWithException(query, "Multiple folders exists with name '" + foldername + "', only one is allowed.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FolderEntity> readSubFolders(final FolderEntity parentFolder) {
        // Let's fetch all Folders for a given parent, and then we'll iterate
        // over them to see if the user is allowed to see the folder or not
        final String jql =
                "select f from FolderEntity f " +
                "where f.parentId = :pid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("pid", parentFolder.getId());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FileEntity> findFiles(final FolderEntity parentFolder) {
        // First step, read in all files that is present in the folder. Since
        // no data is read out at this point (and yes, we should move to a file
        // view) - we're simply not concerned about the amount. Rather one
        // simple query with more than required data than an extremely complex
        // and error prone query.
        final String jql =
                "select f from FileEntity f " +
                "where f.folder.id = :fid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("fid", parentFolder.getId());
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileEntity readFile(final String externalFileId) {
        final String jql =
                "select f from FileEntity f " +
                "where f.externalId = :fid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("fid", externalFileId);

        return getSingleResultWithException(query, "Could not uniquely identify the file by its Id.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FolderEntity> findFoldersRecursively(final String externalId) {
        // Following JPA Query will fetch the Folders for the Id's, which we
        // can find via the Native Query call. We're doing it this way, with two
        // selects to minimize the complexity of the logic here.
        final List<Long> ids = findFolderIds(externalId);
        final List<FolderEntity> folders;

        if (ids.isEmpty()) {
            folders = new ArrayList<>(0);
        } else {
            final String jql =
                    "select f " +
                    "from FolderEntity f " +
                    "where f.id in :ids " +
                    "order by f.id desc";
            final Query query = entityManager.createQuery(jql);
            query.setParameter("ids", ids);

            folders = query.getResultList();
        }

        return folders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> findFolderIds(final String externalId) {
        // Native Query to retrieve the Id's for the Tree starting with the
        // deepest (requested Id) and going up to the root. Query utilizes
        // the SQL:1999 Common Table Expression to achieve a Recursive lookup.
        final String nativeSQL =
                "with recursive tree (id, parent_id) as (\n" +
                "    select\n" +
                "      f1.id,\n" +
                "      f1.parent_id\n" +
                "    from folders f1\n" +
                "    where f1.external_id = :externalId\n" +
                "  union\n" +
                "    select\n" +
                "      f2.id,\n" +
                "      f2.parent_id\n" +
                "    from folders f2\n" +
                "    inner join tree t on f2.id = t.parent_id)\n" +
                "select id from tree\n" +
                "order by id desc";
        final Query nativeQuery = entityManager.createNativeQuery(nativeSQL);
        nativeQuery.setParameter("externalId", externalId);

        // The Native Query is returning a list of Integers, but the JPA Query
        // requires a list of Longs. So we have to convert the list.
        return toLong(nativeQuery.getResultList());
    }

    private static <T extends IWSEntity> T getSingleResultWithException(final Query query, final String name) {
        final List<T> found = query.getResultList();
        T entity = null;

        if (found.size() == 1) {
            entity = found.get(0);
        } else if (found.size() > 1){
            throw new StorageException("Could not uniquely identify the " + name + " by its Id.");
        }

        return entity;
    }

    private static List<Long> toLong(final List<Integer> integers) {
        final List<Long> longs = new ArrayList<>(integers.size());

        for (final Integer id : integers) {
            longs.add(id.longValue());
        }

        return longs;
    }
}
