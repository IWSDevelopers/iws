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
package net.iaeste.iws.api.util.adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * When an Integer value which is null and is allowed to be null is coming in
 * via a SOAP WebService request, then the default handling is throwing a
 * {@code NumberFormatException}, which for a value that is allowed to be null
 * is bad. This Adapter will ensure that any such information is logged and a
 * usable value (null) is returned.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
public class IntegerXmlAdapter extends XmlAdapter<String, Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(IntegerXmlAdapter.class);

    @Override
    public Integer unmarshal(final String v) throws Exception {
        Integer result = null;

        try {
            result = Integer.valueOf(v);
        } catch (NumberFormatException e) {
            LOG.debug(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public String marshal(final Integer v) throws Exception {
        return String.valueOf(v);
    }
}
