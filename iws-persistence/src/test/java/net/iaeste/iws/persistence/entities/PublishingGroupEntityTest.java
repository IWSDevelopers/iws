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
package net.iaeste.iws.persistence.entities;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.entities.exchange.PublishingGroupEntity;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.ExchangeJpaDao;
import net.iaeste.iws.persistence.setup.SpringConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Contains tests for PublishingGroupEntityTest and fetching from ExchangeDao
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { SpringConfig.class })
public class PublishingGroupEntityTest {

    private static final String SHARING_LIST_EXTERNAL_ID = "adc8dfd4-bc3a-4b27-897b-87d3950db503";
    private static final String SHARING_LIST_NAME = "My Selection";

    @PersistenceContext
    private EntityManager entityManager;

    private ExchangeDao exchangeDao = null;
    private AccessDao accessDao = null;

    private Authentication authentication = null;

    @Before
    public void before() {
        accessDao = new AccessJpaDao(entityManager);
        exchangeDao = new ExchangeJpaDao(entityManager);

        final AuthenticationToken token = new AuthenticationToken();
        final UserEntity user = accessDao.findActiveUserByUsername("austria@iaeste.at");
        final GroupEntity group = accessDao.findNationalGroup(user);
        authentication = new Authentication(token, user, group, UUID.randomUUID().toString());
    }

    @After
    public void cleanUp() {
    }

    @Test
    @Transactional
    public void testClassflow() {
        final PublishingGroupEntity sharingList = new PublishingGroupEntity();
        final List<GroupEntity> groups = new ArrayList<>(2);

        final UserEntity polandUser = accessDao.findActiveUserByUsername("poland@iaeste.pl");
        final GroupEntity polandGroup = accessDao.findNationalGroup(polandUser);
        final UserEntity germanyUser = accessDao.findActiveUserByUsername("germany@iaeste.de");
        final GroupEntity germanyGroup = accessDao.findNationalGroup(germanyUser);
        final UserEntity norwayUser = accessDao.findActiveUserByUsername("norway@iaeste.no");
        final GroupEntity norwayGroup = accessDao.findNationalGroup(norwayUser);

        groups.add(polandGroup);
        groups.add(germanyGroup);
        groups.add(norwayGroup);

        sharingList.setExternalId(SHARING_LIST_EXTERNAL_ID);
        sharingList.setGroup(authentication.getGroup());
        sharingList.setName(SHARING_LIST_NAME);
        sharingList.setList(groups);

        entityManager.persist(sharingList);

        assertThat(sharingList.getId(), is(not(nullValue())));

        PublishingGroupEntity fetchedList = exchangeDao.getSharingListByExternalIdAndOwnerId(SHARING_LIST_EXTERNAL_ID, authentication.getGroup().getId());
        assertThat(sharingList, is(fetchedList));

        groups.remove(norwayGroup);
        entityManager.merge(sharingList);
        fetchedList = exchangeDao.getSharingListByExternalIdAndOwnerId(SHARING_LIST_EXTERNAL_ID, authentication.getGroup().getId());
        assertThat(sharingList, is(fetchedList));
    }

}
