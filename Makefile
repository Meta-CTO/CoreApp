.PHONY: pull-mains cleanup new-screen

# ======================PULL MAINS======================
pull-mains:
	@echo "Pulling main branches..."
	git pull
	git pull origin staging
	git pull origin release

# ======================CLEANUP======================
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

# ====================== NEW SCREEN======================
new-screen:
	@echo "Creating new screen..."
	@feature=$(word 2, $(MAKECMDGOALS)); \
	screen=$(word 3, $(MAKECMDGOALS)); \
	if [ -z "$$feature" ] || [ -z "$$screen" ]; then \
		echo "Usage: make new-screen <feature> <screen>"; exit 1; \
	fi; \
	echo "Feature: $$feature, Screen: $$screen"; \
	./scripts/new-screen.sh "$$feature" "$$screen"

# ====================== CATCHALL for dynamic targets =======================
%:
	@echo "Shamy is genius ;)"