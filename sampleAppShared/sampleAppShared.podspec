Pod::Spec.new do |spec|
    spec.name                     = 'sampleAppShared'
    spec.version                  = '1.0.0'
    spec.homepage                 = 'https://www.metacto.com/'
    spec.source                   = { :http=> ''}
    spec.authors                  = ''
    spec.license                  = ''
    spec.summary                  = 'Sample app shared module'
    spec.vendored_frameworks      = 'build/cocoapods/framework/sampleAppShared.framework'
    spec.libraries                = 'c++'
    spec.ios.deployment_target = '14.1'
    spec.dependency 'AWSS3'
    spec.dependency 'Amplitude'
    spec.dependency 'AppsFlyerFramework'
    spec.dependency 'CleverTap-iOS-SDK'
    spec.dependency 'FirebaseAuth'
    spec.dependency 'FirebaseCrashlytics'
    spec.dependency 'FirebaseDynamicLinks'
    spec.dependency 'FirebaseRemoteConfig'
    spec.dependency 'GoogleSignIn', '7.0.0'
                
    if !Dir.exist?('build/cocoapods/framework/sampleAppShared.framework') || Dir.empty?('build/cocoapods/framework/sampleAppShared.framework')
        raise "

        Kotlin framework 'sampleAppShared' doesn't exist yet, so a proper Xcode project can't be generated.
        'pod install' should be executed after running ':generateDummyFramework' Gradle task:

            ./gradlew :sampleAppShared:generateDummyFramework

        Alternatively, proper pod installation is performed during Gradle sync in the IDE (if Podfile location is set)"
    end
                
    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':sampleAppShared',
        'PRODUCT_MODULE_NAME' => 'sampleAppShared',
    }
                
    spec.script_phases = [
        {
            :name => 'Build sampleAppShared',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                if [ "YES" = "$OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED" ]; then
                  echo "Skipping Gradle build task invocation due to OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED environment variable set to \"YES\""
                  exit 0
                fi
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/../gradlew" -p "$REPO_ROOT" $KOTLIN_PROJECT_PATH:syncFramework \
                    -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME \
                    -Pkotlin.native.cocoapods.archs="$ARCHS" \
                    -Pkotlin.native.cocoapods.configuration="$CONFIGURATION"
            SCRIPT
        }
    ]
                
end