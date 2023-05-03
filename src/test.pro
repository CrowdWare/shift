QT += widgets testlib sql quick quickcontrols2

CONFIG += c++11
QMAKE_WINSDK_VERSION = 10.0.2

SOURCES += \
    test.cpp \
    backend.cpp \ 
    simplecrypt.cpp

HEADERS += \
    backend.h \ 
    simplecrypt.h 
                
INCLUDEPATH += 
    "D:\Microsoft Visual Studio\2022\Community\VC\Tools\MSVC\14.33.31629\include" 
    "D:\Microsoft Visual Studio\2022\Community\VC\Tools\MSVC\14.33.31629\ATLMFC\include" 
    "D:\Microsoft Visual Studio\2022\Community\VC\Auxiliary\VS\include"
    "C:\Program Files (x86)\Windows Kits\10\include\10.0.22621.0\ucrt"
    "C:\Program Files (x86)\Windows Kits\10\\include\10.0.22621.0\\um"
    "C:\Program Files (x86)\Windows Kits\10\\include\10.0.22621.0\\shared"
    "C:\Program Files (x86)\Windows Kits\10\\include\10.0.22621.0\\winrt"
    "C:\Program Files (x86)\Windows Kits\10\\include\10.0.22621.0\\cppwinrt"
    "C:\Program Files (x86)\Windows Kits\NETFXSDK\4.8\include\um"
    "D:\Microsoft Visual Studio\2022\Community\VC\Tools\MSVC\14.33.31629\include"
    "D:\Microsoft Visual Studio\2022\Community\VC\Tools\MSVC\14.33.31629\ATLMFC\include"
    "D:\Microsoft Visual Studio\2022\Community\VC\Auxiliary\VS\include"
    "C:\Program Files (x86)\Windows Kits\10\include\10.0.22621.0\ucrt"
    "C:\Program Files (x86)\Windows Kits\10\\include\10.0.22621.0\\um"
    "C:\Program Files (x86)\Windows Kits\10\\include\10.0.22621.0\\shared"
    "C:\Program Files (x86)\Windows Kits\10\\include\10.0.22621.0\\winrt"
    "C:\Program Files (x86)\Windows Kits\10\\include\10.0.22621.0\\cppwinrt"
    "C:\Program Files (x86)\Windows Kits\NETFXSDK\4.8\include\um"

# install
target.path = $$[QT_INSTALL_EXAMPLES]/qtestlib/tutorial1
INSTALLS += target

DEFINES += TEST
