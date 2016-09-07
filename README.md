# [IWS](http://iwsdevelopers.github.io/iws/) [![Build Status](https://travis-ci.org/IWSDevelopers/iws.png)](https://travis-ci.org/IWSDevelopers/iws) [![Coverity Status](https://scan.coverity.com/projects/8602/badge.svg)](https://scan.coverity.com/projects/iws) [![SonarQube Badge](https://sonarqube.com/api/badges/gate?key=net.iaeste.iws:iws)](https://sonarqube.com/dashboard/index/net.iaeste.iws:iws) [![Codacy Badge](https://api.codacy.com/project/badge/grade/f333d50ceac6407286b1fb610b390dc1)](https://www.codacy.com/app/IWSDevelopers/iws) [![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

### The IntraWeb Services Project

The IWS was initiated in 2011 and development began in 2012. It was designed
to replace the existing backend system for the [IAESTE](http://www.iaeste.org/) [IntraWeb](https://www.iaeste.net/), and additionally
add a new public API, to properly facilitate automated interaction for the many
Committees around the world.

IWS is designed as a pure JEE6 Application later upgraded to JEE7, written in
Java7. It has been tested to run under [WildFly](http://wildfly.org/), [JBoss](http://www.redhat.com/en/technologies/jboss-middleware) & [Glassfish](https://glassfish.java.net/). To build,
please check out the sources and run the Maven build command. Note that
only Configuration files for [WildFly](http://wildfly.org/) has been created, and can be found in the
iws-accessories folder.

* Supported Application Servers
  * [WildFly](http://wildfly.org/)
  * [JBoss](http://www.redhat.com/en/technologies/jboss-middleware)
  * [Glassfish](https://glassfish.java.net/)
* Supported Databases
  * [PostgreSQL](http://www.postgresql.org/)
  * [HyperSQL](http://hsqldb.org/)

## IWS moving away from IAESTE
The IAESTE Board have made a decision to move to a new platform during 2016,
meaning that the IWS will no longer be needed for their future infrastructure.
This also means that the IAESTE specific aspects of the IWS is obsolete and
can be removed.

Rather than dismissing the project, the IWS will continue to evolve, but
without IAESTE. This means that during the Autumn 2016, the IWS code will
be split into an IAESTE release 1.2, and an IWS 2.0, where the IWS 2.0 will
have all IAESTE specific parts removed so a focus can be shifted towards
the following goals:

* Pure Java 8 & JavaEE 7
* Highly configurable User/Group model with customizable Roles & permissions
* Strong cryptographical backend with support for exchange of data securely
* Two disctinct Types:
  * Sharing of Rules based Objects
  * Sharing of Folders & Files
* SOAP & REST based API's

## License
Copyright (C) 2012-2016 IWSDevelopers

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
