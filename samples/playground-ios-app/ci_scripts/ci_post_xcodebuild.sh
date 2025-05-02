#!/bin/sh

brew install firebase-cli
firebase appdistribution:distribute "$CI_AD_HOC_SIGNED_APP_PATH/My Atlas - DEV.ipa" \
        --app "1:413144840663:ios:16bdffb6a23c75f05ff392" \
        --token "$FIREBASE_TOKEN" \
        --groups "metacto-testers"

#!/bin/sh

set -e
if [[ -n $CI_ARCHIVE_PATH ]]; then
    echo "Found valid archive path, trying to upload dSYMs for config $GSERVICE_PLIST_NAME from $CI_ARCHIVE_PATH"
    ../Pods/FirebaseCrashlytics/upload-symbols -gsp ../iosApp/Configuration/Dev/GoogleService-Info.plist -p ios $CI_ARCHIVE_PATH/dSYMs
fi
