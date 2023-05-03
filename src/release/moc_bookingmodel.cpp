/****************************************************************************
** Meta object code from reading C++ file 'bookingmodel.h'
**
** Created by: The Qt Meta Object Compiler version 67 (Qt 5.15.2)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include <memory>
#include "../bookingmodel.h"
#include <QtCore/qbytearray.h>
#include <QtCore/qmetatype.h>
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'bookingmodel.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 67
#error "This file was generated using the moc from 5.15.2. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
QT_WARNING_PUSH
QT_WARNING_DISABLE_DEPRECATED
struct qt_meta_stringdata_BookingModel_t {
    QByteArrayData data[11];
    char stringdata0[74];
};
#define QT_MOC_LITERAL(idx, ofs, len) \
    Q_STATIC_BYTE_ARRAY_DATA_HEADER_INITIALIZER_WITH_OFFSET(len, \
    qptrdiff(offsetof(qt_meta_stringdata_BookingModel_t, stringdata0) + ofs \
        - idx * sizeof(QByteArrayData)) \
    )
static const qt_meta_stringdata_BookingModel_t qt_meta_stringdata_BookingModel = {
    {
QT_MOC_LITERAL(0, 0, 12), // "BookingModel"
QT_MOC_LITERAL(1, 13, 6), // "insert"
QT_MOC_LITERAL(2, 20, 0), // ""
QT_MOC_LITERAL(3, 21, 5), // "index"
QT_MOC_LITERAL(4, 27, 8), // "Booking*"
QT_MOC_LITERAL(5, 36, 7), // "booking"
QT_MOC_LITERAL(6, 44, 6), // "append"
QT_MOC_LITERAL(7, 51, 5), // "clear"
QT_MOC_LITERAL(8, 57, 5), // "count"
QT_MOC_LITERAL(9, 63, 6), // "remove"
QT_MOC_LITERAL(10, 70, 3) // "get"

    },
    "BookingModel\0insert\0\0index\0Booking*\0"
    "booking\0append\0clear\0count\0remove\0get"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_BookingModel[] = {

 // content:
       8,       // revision
       0,       // classname
       0,    0, // classinfo
       6,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       0,       // signalCount

 // methods: name, argc, parameters, tag, flags
       1,    2,   44,    2, 0x02 /* Public */,
       6,    1,   49,    2, 0x02 /* Public */,
       7,    0,   52,    2, 0x02 /* Public */,
       8,    0,   53,    2, 0x02 /* Public */,
       9,    1,   54,    2, 0x02 /* Public */,
      10,    1,   57,    2, 0x02 /* Public */,

 // methods: parameters
    QMetaType::Void, QMetaType::Int, 0x80000000 | 4,    3,    5,
    QMetaType::Void, 0x80000000 | 4,    5,
    QMetaType::Void,
    QMetaType::Int,
    QMetaType::Void, QMetaType::Int,    3,
    0x80000000 | 4, QMetaType::Int,    3,

       0        // eod
};

void BookingModel::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        auto *_t = static_cast<BookingModel *>(_o);
        Q_UNUSED(_t)
        switch (_id) {
        case 0: _t->insert((*reinterpret_cast< int(*)>(_a[1])),(*reinterpret_cast< Booking*(*)>(_a[2]))); break;
        case 1: _t->append((*reinterpret_cast< Booking*(*)>(_a[1]))); break;
        case 2: _t->clear(); break;
        case 3: { int _r = _t->count();
            if (_a[0]) *reinterpret_cast< int*>(_a[0]) = std::move(_r); }  break;
        case 4: _t->remove((*reinterpret_cast< int(*)>(_a[1]))); break;
        case 5: { Booking* _r = _t->get((*reinterpret_cast< int(*)>(_a[1])));
            if (_a[0]) *reinterpret_cast< Booking**>(_a[0]) = std::move(_r); }  break;
        default: ;
        }
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        switch (_id) {
        default: *reinterpret_cast<int*>(_a[0]) = -1; break;
        case 0:
            switch (*reinterpret_cast<int*>(_a[1])) {
            default: *reinterpret_cast<int*>(_a[0]) = -1; break;
            case 1:
                *reinterpret_cast<int*>(_a[0]) = qRegisterMetaType< Booking* >(); break;
            }
            break;
        case 1:
            switch (*reinterpret_cast<int*>(_a[1])) {
            default: *reinterpret_cast<int*>(_a[0]) = -1; break;
            case 0:
                *reinterpret_cast<int*>(_a[0]) = qRegisterMetaType< Booking* >(); break;
            }
            break;
        }
    }
}

QT_INIT_METAOBJECT const QMetaObject BookingModel::staticMetaObject = { {
    QMetaObject::SuperData::link<QAbstractListModel::staticMetaObject>(),
    qt_meta_stringdata_BookingModel.data,
    qt_meta_data_BookingModel,
    qt_static_metacall,
    nullptr,
    nullptr
} };


const QMetaObject *BookingModel::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *BookingModel::qt_metacast(const char *_clname)
{
    if (!_clname) return nullptr;
    if (!strcmp(_clname, qt_meta_stringdata_BookingModel.stringdata0))
        return static_cast<void*>(this);
    return QAbstractListModel::qt_metacast(_clname);
}

int BookingModel::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QAbstractListModel::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 6)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 6;
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        if (_id < 6)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 6;
    }
    return _id;
}
QT_WARNING_POP
QT_END_MOC_NAMESPACE
