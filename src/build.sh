export ANDROID_NDK_ROOT=/Users/art/Library/Android/sdk/ndk/19.2.5345600
export ANDROID_NDK_PLATFORM=android-28
export ANDROID_SDK_ROOT=/Users/art/Library/Android/sdk
mkdir build-project
cd build-project
/Users/art/qt/5.12.3/android_armv7/bin/qmake ../shift.pro
make
make install INSTALL_ROOT=/Users/art/SourceCode/Shift/src/build-project/
/Users/art/qt/5.12.3/android_armv7/bin/androiddeployqt --input /Users/art/SourceCode/Shift/src/build-project/android-libshift.so-deployment-settings.json --output /Users/art/SourceCode/Shift/src/build-project --android-platform android-28 --jdk /home/art/Software/jdk1.8.0_231/bin/java --gradle
cd ..

