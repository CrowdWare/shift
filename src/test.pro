QT += widgets testlib sql quick quickcontrols2

CONFIG += c++11

SOURCES += \
    test.cpp \
    backend.cpp \ 
    simplecrypt.cpp

HEADERS += \
    backend.h \ 
    simplecrypt.h 

# install
target.path = $$[QT_INSTALL_EXAMPLES]/qtestlib/tutorial1
INSTALLS += target

DEFINES += TEST