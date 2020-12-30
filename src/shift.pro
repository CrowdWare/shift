TEMPLATE = app
TARGET = shift
QT += quick quickcontrols2

SOURCES += \
    shift.cpp

RESOURCES += \
    shift.qml \
    gui/Home.qml \
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
    images/logo.png

target.path = $$PWD/quickcontrols2/shift
INSTALLS += target


android {
    ANDROID_PACKAGE_SOURCE_DIR = $$PWD/android

    DISTFILES += \
        android/AndroidManifest.xml \
        android/build.gradle \
        android/res/values/libs.xml
}