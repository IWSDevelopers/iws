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
package net.iaeste.iws.persistence;

import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.persistence.entities.FileEntity;
import net.iaeste.iws.persistence.entities.FiledataEntity;
import net.iaeste.iws.persistence.entities.FolderEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;

import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
public interface StorageDao extends BasicDao {

    List<FolderEntity> findFolders(final String eid);

    /**
     * Reads out a list of Sub-folders for a given Folder. All folders belonging
     * to the current User can be read, as can all folders which belong Groups
     * with Public folders.
     *
     * @param authentication User Authentication information
     * @param folder  Parent Folder to find sub folders for
     * @return Folder List
     */
    List<FolderEntity> readFolders(Authentication authentication, FolderEntity folder);

    /**
     * Reads out the list of Files for a given Folder. The method is making the
     * assumption that the folder is one that the user is allowed to read. I.e.
     * that it is either a public folder (via GroupType) or a folder belonging
     * to a Group, which the user is a member of.
     *
     * @param authentication User Authentication Information
     * @param folder         Folder to read the content of
     * @return List of Files to be shown
     */
    List<FileEntity> readFiles(Authentication authentication, FolderEntity folder);

    FolderEntity findRootFolder();

    FolderEntity findParentFolder(String parentId, GroupEntity group);

    FolderEntity findRootFolder(Group group);

    FolderEntity findSimilarFolder(String foldername, Long parentId);

    List<FolderEntity> readSubFolders(FolderEntity parentFolder);

    List<FileEntity> findFiles(FolderEntity parentFolder);

    FiledataEntity readFile(final String externalFileId);

    /**
     * The IWS Folder Structure is based on the Adjacency Tree Model [1]. This
     * is a fairly simple construction, where each node is having a reference
     * to a parent.<br />
     *   Problem here, is to find all the nodes from a branch and down to the
     * root, so we can verify the permissions. To do so, we need to recursively
     * lookup the Tree using the given Id. Doing a recursive lookup by iterating
     * over the elements is not a good idea, it is very performance costly, so
     * instead a solution with a minimal amount of Database Activity is
     * required.<br />
     *   SQL:1999 is having a nice solution ready called Common Table Expression
     * or CTE [2]. This is supported by the majority of DBMS, but is not mapped
     * into JPA! So, rather than using JPA to have an elegant solution, we must
     * instead use a native Query. Only problem with using a Native Query is
     * that we cannot utilize the JPA based prepared statement, instead we have
     * to revert to injecting the Id directly into the Query :-(<br />
     *   Luckily we're saved by the fact that all input validation is in place
     * and the given Id must be valid - this way we can safely run the Query,
     * but with the hope that next generation JPA will have support for Common
     * Table Expressions. It should be noted that some JPA tricks exist [3], but
     * they do not seem to work with our data model.<br />
     *   Note; that although PostgreSQL have had support for CTE for quite a
     * while, HyperSQL is a different story [4].
     *   For more information, please see the following list of Links:
     * <ul>
     *   <li>[1] <a href="http://kawoolutions.com/SQL_Database_Design/15._Advanced_Data_Structures/15.1_Trees">SQL Tree Structures</a></li>
     *   <li>[2] <a href="https://en.wikipedia.org/wiki/Hierarchical_and_recursive_queries_in_SQL#Common_table_expression">CTE Queries</a></li>
     *   <li>[3] <a href="http://www.tikalk.com/java/load-tree-jpa-and-hibernate/">Potential JPA solution</a></li>
     *   <li>[4] <a href="http://sourceforge.net/p/hsqldb/discussion/73674/thread/33731466/">CTE in HyperSQL</a></li>
     * </ul>
     *
     * @param externalId External Id of the Folder to find the structure for
     * @return Sorted list of Folders - first matches given Id and last is the root
     */
    List<FolderEntity> findFoldersRecursively(String externalId);

    /**
     * This method will use a Native SQL Query, based on the SQL:1999 Common
     * Table Expression, CTE, functionality, which is implemented in most
     * Databases, but is not mapped over to JPA.
     *
     * @param externalId External Id of the Folder to find the structure for
     * @return Sorted list of Folders - first matches given Id and last is the root
     */
    List<Long> findFolderIds(String externalId);
}
