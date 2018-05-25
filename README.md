# :robot: opendaylight-bot :robot:

[![Build Status](https://travis-ci.org/vorburger/opendaylight-bot.svg?branch=master)](https://travis-ci.org/vorburger/opendaylight-bot)

**[LIVE INSTANCE running here](http://opendaylight-bot-opendaylight-bot.6923.rh-us-east-1.openshiftapps.com)** as well as [here](http://prod-opendaylight-bot.e8ca.engint.openshiftapps.com)  (URLs may change).


## Overview

The ODL :robot: Bot is a software delivery automation tool. It originated in the OpenDaylight.org FLOSS SDN community,
but could be used by other open-source communities (and for in-house proprietary code) as well.  Its features are:

* Multi Git repo Gerrit "managed" topics
  * Multi project builds and status tracking

Ideas for future features include:
* kick off Jenkins builds
* email developer lists
* Simultaneously merge changes based on a topic
* Automated upstream downstream repo syncing
* Multi project "version bumping looking ahead" builds
* …


## How to run locally

You need the Maven settings.xml from OpenDaylight to have Maven grab stuff 
from https://nexus.opendaylight.org instead of from https://repo.maven.apache.org/maven2.
This is [described in the OpenDaylight documentation](http://docs.opendaylight.org/en/latest/developer-guide/developing-apps-on-the-opendaylight-controller.html),
but basically just:

    cp -n ~/.m2/settings.xml{,.orig} ; wget -q -O - https://raw.githubusercontent.com/opendaylight/odlparent/master/settings.xml > ~/.m2/settings.xml

then you can:

    ./mvnw package
    java -jar spring/target/org.opendaylight.bot.spring.jar

or 

    ./mvnw package  -Dcheckstyle.skip   // to skip Checkstyle Violations
    java -jar spring/target/org.opendaylight.bot.spring.jar
 



## How to run on OpenShift

If https://github.com/fabric8io-images/s2i/pull/152 is already merged, then:

    oc new-build https://github.com/fabric8io-images/s2i.git --context-dir=java/images/jboss

otherwise:

    oc new-build https://github.com/vorburger/s2i.git#maven-wrapper --context-dir=java/images/jboss

and either way then:

    oc new-app s2i~https://github.com/vorburger/opendaylight-bot


## How to locally test the S2I container image build

    s2i build --copy . fabric8/s2i-java opendaylight-bot --incremental

    docker run --rm -p 8080:8080 opendaylight-bot
