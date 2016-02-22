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
package net.iaeste.iws.ejb.cdi;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * CDI excepts that all classes are uniquely identifiable, using type checks. If
 * multiple beans exists of the same type, then it is not able to correctly find
 * and inject this. By introducing a new Annotation for our Beans, it is
 * possible to better control what Bean is expected. CDI is used for both the
 * Bean definition, and for the Bean injection, to better control it.<br />
 *   The annotation takes a type as value, which is then used to uniquely
 * identify the Bean. For more information about this approach, please see the
 * Blog from <a href="http://antoniogoncalves.org/2011/04/07/injection-with-cdi-part-i/">Antonia goncalves</a>.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@Qualifier
@Retention(RUNTIME)
@Target({ TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR })
public @interface IWSBean {

    Type value() default Type.PRIMARY;

    /**
     * We have different types of Qualifiers for our Beans, so we can deploy them
     * properly. The different types are controlled via this Enum.
     */
    enum Type {

        /** Standard Beans for IWS. */
        PRIMARY,

        /** For secondary or external Resources / Beans. */
        SECONDARY,
    }
}
