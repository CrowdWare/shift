mkdir build-test
cd build-test
/Users/art/qt/5.12.3/clang_64/bin/qmake ../test.pro
make
cd ..



D:/Qt/5.15.2/msvc2019_64/bin/qmake.exe E:\SourceCode\Shift\src\test.pro -spec win32-msvc "CONFIG+=qtquickcompiler" && D:/Qt/Tools/QtCreator/bin/jom/jom.exe qmake_all