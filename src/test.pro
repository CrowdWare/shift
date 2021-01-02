QT += widgets testlib

CONFIG += c++11

SOURCES += \
    test.cpp \
    database.cpp

HEADERS += \
    database.h \ 

# install
target.path = $$[QT_INSTALL_EXAMPLES]/qtestlib/tutorial1
INSTALLS += target