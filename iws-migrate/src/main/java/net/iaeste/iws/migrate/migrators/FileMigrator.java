/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.migrators.FileMigrator
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.migrators;

import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.migrate.daos.IWSDao;
import net.iaeste.iws.migrate.entities.IW3FileEntity;
import net.iaeste.iws.persistence.entities.FileEntity;
import net.iaeste.iws.persistence.entities.FolderEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public class FileMigrator implements Migrator<IW3FileEntity> {

    private static final Logger log = LoggerFactory.getLogger(FileMigrator.class);

    @Autowired
    private IWSDao iwsDao;

    public FileMigrator() {
    }

    public FileMigrator(final IWSDao iwsDao) {
        this.iwsDao = iwsDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MigrationResult migrate() {
        throw new IllegalArgumentException("This Migrator is not allowed here.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = "transactionManagerIWS", propagation = Propagation.REQUIRES_NEW)
    public MigrationResult migrate(final List<IW3FileEntity> oldEntities) {
        int skipped = 0;
        int folders = 0;
        int files = 0;

        for (final IW3FileEntity oldFile : oldEntities) {
            if (skipThis(oldFile)) {
                skipped++;
            } else {
                if ("d".equals(oldFile.getFiletype())) {
                    if (migrateFolder(oldFile)) {
                        folders++;
                    } else {
                        skipped++;
                    }
                } else {
                    if (migrateFile(oldFile)) {
                        files++;
                    } else {
                        skipped++;
                    }
                }
            }
        }

        log.info("Completed migrating block with {} files and {} folders.", files, folders);
        return new MigrationResult(folders + files, skipped);
    }

    // =========================================================================
    // Internal Countries Migration Methods
    // =========================================================================

    private boolean skipThis(final IW3FileEntity entity) {
        boolean result = false;

        if (entity.getFileid() <= 2) {
            // Default Folders (root & library) is skipped
            result = true;
        } else if (entity.getGroupId() == 117) {
            result = true;
        }

        return result;
    }

    private boolean migrateFolder(final IW3FileEntity oldEntity) {
        final GroupEntity groupEntity = findGroup(oldEntity);
        final FolderEntity folderEntity = findFolder(oldEntity);
        final boolean persisted;

        if (groupEntity == null) {
            log.info("Skipping folder (missing Group): [" + oldEntity.getFileid() + "] " + oldEntity.getFilename());
            persisted = false;
        } else {
            final FolderEntity entity = new FolderEntity();
            entity.setExternalId(UUID.randomUUID().toString());
            entity.setParentId(folderEntity != null ? folderEntity.getId() : null);
            entity.setGroup(findGroup(oldEntity));
            entity.setFoldername(oldEntity.getFilename().trim());
            entity.setOldIW3FileId(oldEntity.getFileid());
            entity.setModified(readDate(oldEntity.getModified(), new Date()));
            entity.setCreated(readDate(entity.getCreated(), entity.getModified()));

            iwsDao.persist(entity);
            persisted = true;
        }

        return persisted;
    }

    private boolean migrateFile(final IW3FileEntity oldEntity) {
        final GroupEntity groupEntity = findGroup(oldEntity);
        final UserEntity userEntity = findUser(oldEntity);
        final boolean persisted;

        if (userEntity == null) {
            log.info("Skipping file (missing User): [" + oldEntity.getFileid() + "] " + oldEntity.getFilename());
            persisted = false;
        } else if (groupEntity == null) {
            log.info("Skipping file (missing Group): [" + oldEntity.getFileid() + "] " + oldEntity.getFilename());
            persisted = false;
        } else {
            final FileEntity entity = new FileEntity();
            entity.setExternalId(UUID.randomUUID().toString());
            // For now, we're assuming that all files are Protected. Later, we
            // can try to change that when we know more about them.
            entity.setPrivacy(Privacy.PROTECTED);
            entity.setGroup(findGroup(oldEntity));
            entity.setUser(findUser(oldEntity));
            entity.setFolder(findFolder(oldEntity));
            entity.setFilename(oldEntity.getFilename().trim());
            entity.setStoredFilename("files/" + oldEntity.getUserId() + '/' + oldEntity.getSystemname());
            entity.setFilesize(oldEntity.getFilesize());
            entity.setMimetype(oldEntity.getMimetype().getMimetype());
            entity.setDescription(oldEntity.getDescription().trim());
            entity.setKeywords(oldEntity.getKeywords().trim());
            entity.setChecksum(convertChecksum(oldEntity.getChecksum()));
            entity.setOldIW3FileId(oldEntity.getFileid());
            entity.setModified(readDate(oldEntity.getModified(), new Date()));
            entity.setCreated(readDate(oldEntity.getCreated(), oldEntity.getModified()));

            iwsDao.persist(entity);
            persisted = true;
        }

        return persisted;
    }

    private static Date readDate(final Date primary, final Date secondary) {
        final Date date;

        if (primary != null) {
            date = primary;
        } else if (secondary != null) {
            date = secondary;
        } else {
            date = new Date();
        }

        return date;
    }

    private GroupEntity findGroup(final IW3FileEntity oldEntity) {
        final Integer iw3GroupId = oldEntity.getGroupId();
        final GroupEntity entity = iwsDao.findGroupByIW3Id(iw3GroupId);

        return entity;
    }

    private UserEntity findUser(final IW3FileEntity oldEntity) {
        final Integer iw3UserId = oldEntity.getUserId();
        UserEntity entity = iwsDao.findUserByIW3Id(iw3UserId);

        if (entity == null) {
            // Most documents which is also having a Group associated, should
            // also be migrated, so we will simply set it to the GroupOwner
            final GroupEntity group = findGroup(oldEntity);
            if (group != null) {
                entity = iwsDao.findGroupOwner(group);
            }
        }

        return entity;
    }

    private FolderEntity findFolder(final IW3FileEntity oldEntity) {
        // In IW3, the FolderId 1 is the same as a non-public root folder. In
        // IWS, it is simply set to null. The Library in IW3 has the FolderId 2,
        // which is mapped over in IWS, so it will work fine :-)
        final Integer iw3FolderId = oldEntity.getFolderid();
        final FolderEntity entity;
        if (iw3FolderId == 1) {
            entity = null;
        } else {
            entity = iwsDao.findFolderByIW3Id(Long.valueOf(iw3FolderId));
        }

        return entity;
    }

    private Long convertChecksum(final String checksum) {
        return null;
    }
}
