#!/bin/sh
rm -rf .idea
./gradlew clean
rm -rf .gradle
rm -rf build
rm -rf */build
rm -rf sameples/samplle-ios-app/iosApp.xcworkspace
rm -rf sameples/samplle-ios-app/Pods
rm -rf sameples/samplle-ios-app/iosApp.xcodeproj/project.xcworkspace
rm -rf sameples/samplle-ios-app/iosApp.xcodeproj/xcuserdata
