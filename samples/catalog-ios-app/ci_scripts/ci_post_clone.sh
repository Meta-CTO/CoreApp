#!/bin/sh
brew install cocoapods
brew install openjdk@17
brew install gradle
echo 'export PATH="/usr/local/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
echo 'export CPPFLAGS="-I/usr/local/opt/openjdk@17/include"' >> ~/.zshrc
cd .. && cd .. && ./gradlew podinstall
