#!/usr/bin/env bash
/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/bin/java -Dfile.encoding=UTF-8 -Dapp.home=/Users/tuzi/AndroidStudioProjects/AutoTest/AutoTest_CMS_Web/target/webapp -Dapp.name=AutoTest_CMS_Web -Dapp.port=3000 -jar /Users/tuzi/AndroidStudioProjects/AutoTest/AutoTest_CMS_Web/target/resin-pro-3.1.12/lib/resin.jar -resin-home /Users/tuzi/AndroidStudioProjects/AutoTest/AutoTest_CMS_Web/target/resin-pro-3.1.12 -conf /Users/tuzi/AndroidStudioProjects/AutoTest/AutoTest_CMS_Web/target/resin-mac.xml -server AutoTest_CMS_Web  stop
