TEMPLATE = app
TARGET = shift
QT += quick quickcontrols2 core 
CONFIG += c++11

SOURCES += \
    shift.cpp \
    backend.cpp \ 
    simplecrypt.cpp

HEADERS += \
    backend.h \ 
    simplecrypt.h

RESOURCES += \
    shift.qml \
    gui/Home.qml \
    gui/Friends.qml \
    qtquickcontrols2.conf \
    icons/shift/index.theme \
    icons/shift/20x20/back.png \
    icons/shift/20x20/drawer.png \
    icons/shift/20x20/menu.png \
    icons/shift/20x20@2/back.png \
    icons/shift/20x20@2/drawer.png \
    icons/shift/20x20@2/menu.png \
    icons/shift/20x20@3/back.png \
    icons/shift/20x20@3/drawer.png \
    icons/shift/20x20@3/menu.png \
    icons/shift/20x20@4/back.png \
    icons/shift/20x20@4/drawer.png \
    icons/shift/20x20@4/menu.png \
    images/logo.png \
    images/checked.png

target.path = $$PWD/quickcontrols2/shift
INSTALLS += target

CONFIG += qmltypes
QML_IMPORT_NAME = at.crowdware.backend
QML_IMPORT_MAJOR_VERSION = 1

android {
    DEFINES += ANDROID
    QT += androidextras
    ANDROID_PACKAGE_SOURCE_DIR = $$PWD/android
    OTHER_FILES += \
        android/src/com/lasconic/QShareUtils.java \
        android/src/org/qtproject/example/notification/NotificationClient.java
    
    SOURCES += android/androidshareutils.cpp \
        shareutils.cpp \
        notificationclient.cpp \
    HEADERS += android/androidshareutils.h \
        shareutils.h \
        notificationclient.h \

    DISTFILES += \
        android/AndroidManifest.xml \
        android/build.gradle \
        android/res/values/libs.xml
}

ios {
    OBJECTIVE_SOURCES += ios/iosshareutils.mm
    HEADERS += ios/iosshareutils.h

    Q_ENABLE_BITCODE.name = ENABLE_BITCODE
    Q_ENABLE_BITCODE.value = NO
    QMAKE_MAC_XCODE_SETTINGS += Q_ENABLE_BITCODE
}