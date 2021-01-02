QT += widgets testlib sql

CONFIG += c++11

SOURCES += \
    test.cpp \
    database.cpp \
    simplecrypt.cpp

HEADERS += \
    database.h \ 
    simplecrypt.h 

# install
target.path = $$[QT_INSTALL_EXAMPLES]/qtestlib/tutorial1
INSTALLS += target