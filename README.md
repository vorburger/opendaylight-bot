# opendaylight-bot

[![Build Status](https://travis-ci.org/vorburger/opendaylight-bot.svg?branch=master)](https://travis-ci.org/vorburger/opendaylight-bot)

## Overview

The ODL Bot is a software delivery automation tool. It originated in the OpenDaylight.org FLOSS SDN community,
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

    ./mvnw package
    java -jar spring/target/org.opendaylight.bot.spring.jar

## How to run on OpenShift

    oc new-build https://github.com/vorburger/s2i.git#maven-wrapper --context-dir=java/images/jboss
    oc new-build https://github.com/fabric8io-images/s2i.git --context-dir=java/images/jboss

    oc new-app s2i~https://github.com/vorburger/opendaylight-bot

## How to locally test the S2I container image build

    s2i build --copy . fabric8/s2i-java opendaylight-bot --incremental

    docker run --rm -p 8080:8080 opendaylight-bot
