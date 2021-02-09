mkdir build-desktop
cd build-desktop
/Users/art/qt/5.12.3/clang_64/bin/qmake ../shift.pro
make
make install INSTALL_ROOT=/Users/art/SourceCode/Shift/src/build-desktop/
cd ..