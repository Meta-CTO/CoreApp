.PHONY: pod-install pull-mains cleanup

# 1. pull_mains.sh
pull-mains:
	@echo "Pulling main branches..."
	git pull origin develop
	git pull origin staging
	git pull origin release

# 2. cleanup.sh
cleanup:
	@echo "Cleaning project..."
	rm -rf .idea
	./gradlew clean
	rm -rf .gradle
	rm -rf build
	rm -rf */build
	rm -rf sameples/samplle-ios-app/iosApp.xcworkspace
	rm -rf sameples/samplle-ios-app/Pods
	rm -rf sameples/samplle-ios-app/iosApp.xcodeproj/project.xcworkspace
	rm -rf sameples/samplle-ios-app/iosApp.xcodeproj/xcuserdata