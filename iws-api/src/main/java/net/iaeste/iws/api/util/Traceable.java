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
package net.iaeste.iws.api.util;

/**
 * For logging purposes, it is needed to provide a mechanism to see who were
 * doing what, without logging private information about the user. For this,
 * the traceId is needed, and this Interface is used by the logging methods
 * to fetch the Id.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public interface Traceable {

    /**
     * Returns a unique Session based TraceId for a user. The traceId will
     * together with other information be able to see what a User has been
     * doing, and thus help with resolving production problems. All IWS log
     * requests is made with the TraceId, if possible.
     *
     * @return The Users Session based TraceId
     */
    String getTraceId();
}
