cmake_minimum_required(VERSION 3.10)

project(shift)

find_package (Python3 REQUIRED COMPONENTS Interpreter)


execute_process(
    COMMAND ${Python3_EXECUTABLE} ${CMAKE_CURRENT_SOURCE_DIR}/scripts/generate_code.py
    WORKING_DIRECTORY ${CMAKE_CURRENT_BINARY_DIR}
    RESULT_VARIABLE GIT_SUBMOD_RESULT
)


# Set the path to the Qt installation directory
set(QT_DIR "D:/Qt")
set(Qt5_DIR "D:/Qt/5.15.2/android/lib/cmake/Qt5")
set(CMAKE_LIBRARY_PATH ${CMAKE_LIBRARY_PATH} "D:/Qt/5.15.2/android/lib")


#find_package(Qt5 COMPONENTS Core QGui REQUIRED)

# Set the Qt version and modules to use
set(QT_VERSION "5.15.2")
#set(QT_MODULES "Core Widgets")

# Set the Android NDK path and version
set(ANDROID_NDK_PATH "D:/Android/android-sdk/ndk")
set(ANDROID_NDK_VERSION "19.2.5345600")

# Set the target Android API level and architecture
set(ANDROID_API_LEVEL "28")
set(ANDROID_ABI "arm64-v8a")

# Include the Qt CMake modules for Android cross-compilation
set(CMAKE_MODULE_PATH "${QT_DIR}/${QT_VERSION}/android_cmake")
include(FeatureSummary)

# Set the Qt CMake configuration options for Android
set(ANDROID_PACKAGE_SOURCE_DIR "${CMAKE_CURRENT_SOURCE_DIR}/android")
set(ANDROID_EXTRA_LIBS "${QT_DIR}/${QT_VERSION}/android_${ANDROID_ABI}/lib/libQt5Core.so")
set(ANDROID_EXTRA_PLUGINS "${QT_DIR}/${QT_VERSION}/android_${ANDROID_ABI}/qml")
set(ANDROID_APK_DIR "${CMAKE_CURRENT_BINARY_DIR}/android-build")
set(ANDROID_NATIVE_LIB_DEPENDENCIES "Qt5Core")


# Set the source files for the project
set(SOURCES 
    src/shift.cpp
    src/simplecrypt.cpp
    src/shareutils.cpp
    src/plugin.cpp 
    src/menumodel.cpp 
    src/menu.cpp 
    src/matemodel.cpp 
    src/mate.cpp 
    src/bookingmodel.cpp 
    src/booking.cpp 
    src/backend.cpp 
)

set(TEST_SOURCES 
    src/test.cpp
    src/simplecrypt.cpp
    src/backend.cpp 
)

# Create the executable for the project
add_executable(shift ${SOURCES})
add_executable(test ${TEST_SOURCES})

# Link the executable with the Qt modules
#target_link_libraries(shift Qt5::Core Qt5::Widgets)

# Install the executable and required files
install(TARGETS test DESTINATION bin)

target_include_directories(shift PRIVATE
    src
    D:/Qt/5.15.2/android/include
    D:/Qt/5.15.2/android/include/QtCore
    D:/Qt/5.15.2/android/include/QtGui
    D:/Qt/5.15.2/android/include/QtQml
    D:/Qt/5.15.2/android/include/QtQuick    
    D:/Qt/5.15.2/android/include/QtQuickControls2
    D:/Qt/5.15.2/android/mkspecs/common/uikit
)

#add_custom_command(TARGET shift POST_BUILD
#    COMMAND /path/to/qt5/installation/bin/androiddeployqt
#        --input ${CMAKE_CURRENT_BINARY_DIR}/android-libMyApp.so-deployment-settings.json
#        --output ${CMAKE_CURRENT_BINARY_DIR}
#        --android-platform android-30
#        --jdk /path/to/jdk
#        --gradle
#    COMMENT "Deploying MyApp to Android device or emulator"
#)