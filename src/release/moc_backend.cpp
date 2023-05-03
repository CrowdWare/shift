/****************************************************************************
** Meta object code from reading C++ file 'backend.h'
**
** Created by: The Qt Meta Object Compiler version 67 (Qt 5.15.2)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include <memory>
#include "../backend.h"
#include <QtCore/qbytearray.h>
#include <QtCore/qmetatype.h>
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'backend.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 67
#error "This file was generated using the moc from 5.15.2. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
QT_WARNING_PUSH
QT_WARNING_DISABLE_DEPRECATED
struct qt_meta_stringdata_BackEnd_t {
    QByteArrayData data[39];
    char stringdata0[431];
};
#define QT_MOC_LITERAL(idx, ofs, len) \
    Q_STATIC_BYTE_ARRAY_DATA_HEADER_INITIALIZER_WITH_OFFSET(len, \
    qptrdiff(offsetof(qt_meta_stringdata_BackEnd_t, stringdata0) + ofs \
        - idx * sizeof(QByteArrayData)) \
    )
static const qt_meta_stringdata_BackEnd_t qt_meta_stringdata_BackEnd = {
    {
QT_MOC_LITERAL(0, 0, 7), // "BackEnd"
QT_MOC_LITERAL(1, 8, 16), // "lastErrorChanged"
QT_MOC_LITERAL(2, 25, 0), // ""
QT_MOC_LITERAL(3, 26, 15), // "scoopingChanged"
QT_MOC_LITERAL(4, 42, 14), // "messageChanged"
QT_MOC_LITERAL(5, 57, 11), // "uuidChanged"
QT_MOC_LITERAL(6, 69, 14), // "balanceChanged"
QT_MOC_LITERAL(7, 84, 20), // "registerErrorChanged"
QT_MOC_LITERAL(8, 105, 13), // "resultChanged"
QT_MOC_LITERAL(9, 119, 14), // "onNetworkReply"
QT_MOC_LITERAL(10, 134, 14), // "QNetworkReply*"
QT_MOC_LITERAL(11, 149, 5), // "reply"
QT_MOC_LITERAL(12, 155, 15), // "onMatelistReply"
QT_MOC_LITERAL(13, 171, 15), // "onRegisterReply"
QT_MOC_LITERAL(14, 187, 18), // "onSetScoopingReply"
QT_MOC_LITERAL(15, 206, 10), // "onGetReply"
QT_MOC_LITERAL(16, 217, 5), // "start"
QT_MOC_LITERAL(17, 223, 13), // "createAccount"
QT_MOC_LITERAL(18, 237, 4), // "name"
QT_MOC_LITERAL(19, 242, 5), // "ruuid"
QT_MOC_LITERAL(20, 248, 7), // "country"
QT_MOC_LITERAL(21, 256, 8), // "language"
QT_MOC_LITERAL(22, 265, 7), // "HttpGet"
QT_MOC_LITERAL(23, 273, 3), // "url"
QT_MOC_LITERAL(24, 277, 9), // "lastError"
QT_MOC_LITERAL(25, 287, 7), // "balance"
QT_MOC_LITERAL(26, 295, 8), // "scooping"
QT_MOC_LITERAL(27, 304, 7), // "message"
QT_MOC_LITERAL(28, 312, 4), // "uuid"
QT_MOC_LITERAL(29, 317, 12), // "bookingModel"
QT_MOC_LITERAL(30, 330, 13), // "BookingModel*"
QT_MOC_LITERAL(31, 344, 9), // "mateModel"
QT_MOC_LITERAL(32, 354, 10), // "MateModel*"
QT_MOC_LITERAL(33, 365, 9), // "menuModel"
QT_MOC_LITERAL(34, 375, 10), // "MenuModel*"
QT_MOC_LITERAL(35, 386, 13), // "registerError"
QT_MOC_LITERAL(36, 400, 7), // "version"
QT_MOC_LITERAL(37, 408, 15), // "writepermission"
QT_MOC_LITERAL(38, 424, 6) // "result"

    },
    "BackEnd\0lastErrorChanged\0\0scoopingChanged\0"
    "messageChanged\0uuidChanged\0balanceChanged\0"
    "registerErrorChanged\0resultChanged\0"
    "onNetworkReply\0QNetworkReply*\0reply\0"
    "onMatelistReply\0onRegisterReply\0"
    "onSetScoopingReply\0onGetReply\0start\0"
    "createAccount\0name\0ruuid\0country\0"
    "language\0HttpGet\0url\0lastError\0balance\0"
    "scooping\0message\0uuid\0bookingModel\0"
    "BookingModel*\0mateModel\0MateModel*\0"
    "menuModel\0MenuModel*\0registerError\0"
    "version\0writepermission\0result"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_BackEnd[] = {

 // content:
       8,       // revision
       0,       // classname
       0,    0, // classinfo
      15,   14, // methods
      12,  124, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       7,       // signalCount

 // signals: name, argc, parameters, tag, flags
       1,    0,   89,    2, 0x06 /* Public */,
       3,    0,   90,    2, 0x06 /* Public */,
       4,    0,   91,    2, 0x06 /* Public */,
       5,    0,   92,    2, 0x06 /* Public */,
       6,    0,   93,    2, 0x06 /* Public */,
       7,    0,   94,    2, 0x06 /* Public */,
       8,    0,   95,    2, 0x06 /* Public */,

 // slots: name, argc, parameters, tag, flags
       9,    1,   96,    2, 0x0a /* Public */,
      12,    1,   99,    2, 0x0a /* Public */,
      13,    1,  102,    2, 0x0a /* Public */,
      14,    1,  105,    2, 0x0a /* Public */,
      15,    1,  108,    2, 0x0a /* Public */,

 // methods: name, argc, parameters, tag, flags
      16,    0,  111,    2, 0x02 /* Public */,
      17,    4,  112,    2, 0x02 /* Public */,
      22,    1,  121,    2, 0x02 /* Public */,

 // signals: parameters
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,

 // slots: parameters
    QMetaType::Void, 0x80000000 | 10,   11,
    QMetaType::Void, 0x80000000 | 10,   11,
    QMetaType::Void, 0x80000000 | 10,   11,
    QMetaType::Void, 0x80000000 | 10,   11,
    QMetaType::Void, 0x80000000 | 10,   11,

 // methods: parameters
    QMetaType::Void,
    QMetaType::Void, QMetaType::QString, QMetaType::QString, QMetaType::QString, QMetaType::QString,   18,   19,   20,   21,
    QMetaType::Void, QMetaType::QString,   23,

 // properties: name, type, flags
      24, QMetaType::QString, 0x00495103,
      25, QMetaType::Int, 0x00495001,
      26, QMetaType::LongLong, 0x00495001,
      27, QMetaType::QString, 0x00495001,
      28, QMetaType::QString, 0x00495001,
      29, 0x80000000 | 30, 0x00095409,
      31, 0x80000000 | 32, 0x00095409,
      33, 0x80000000 | 34, 0x00095409,
      35, QMetaType::QString, 0x00495001,
      36, QMetaType::QString, 0x00095401,
      37, QMetaType::Bool, 0x00095401,
      38, QMetaType::QString, 0x00495001,

 // properties: notify_signal_id
       0,
       4,
       1,
       2,
       3,
       0,
       0,
       0,
       5,
       0,
       0,
       6,

       0        // eod
};

void BackEnd::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        auto *_t = static_cast<BackEnd *>(_o);
        Q_UNUSED(_t)
        switch (_id) {
        case 0: _t->lastErrorChanged(); break;
        case 1: _t->scoopingChanged(); break;
        case 2: _t->messageChanged(); break;
        case 3: _t->uuidChanged(); break;
        case 4: _t->balanceChanged(); break;
        case 5: _t->registerErrorChanged(); break;
        case 6: _t->resultChanged(); break;
        case 7: _t->onNetworkReply((*reinterpret_cast< QNetworkReply*(*)>(_a[1]))); break;
        case 8: _t->onMatelistReply((*reinterpret_cast< QNetworkReply*(*)>(_a[1]))); break;
        case 9: _t->onRegisterReply((*reinterpret_cast< QNetworkReply*(*)>(_a[1]))); break;
        case 10: _t->onSetScoopingReply((*reinterpret_cast< QNetworkReply*(*)>(_a[1]))); break;
        case 11: _t->onGetReply((*reinterpret_cast< QNetworkReply*(*)>(_a[1]))); break;
        case 12: _t->start(); break;
        case 13: _t->createAccount((*reinterpret_cast< QString(*)>(_a[1])),(*reinterpret_cast< QString(*)>(_a[2])),(*reinterpret_cast< QString(*)>(_a[3])),(*reinterpret_cast< QString(*)>(_a[4]))); break;
        case 14: _t->HttpGet((*reinterpret_cast< QString(*)>(_a[1]))); break;
        default: ;
        }
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        switch (_id) {
        default: *reinterpret_cast<int*>(_a[0]) = -1; break;
        case 7:
            switch (*reinterpret_cast<int*>(_a[1])) {
            default: *reinterpret_cast<int*>(_a[0]) = -1; break;
            case 0:
                *reinterpret_cast<int*>(_a[0]) = qRegisterMetaType< QNetworkReply* >(); break;
            }
            break;
        case 8:
            switch (*reinterpret_cast<int*>(_a[1])) {
            default: *reinterpret_cast<int*>(_a[0]) = -1; break;
            case 0:
                *reinterpret_cast<int*>(_a[0]) = qRegisterMetaType< QNetworkReply* >(); break;
            }
            break;
        case 9:
            switch (*reinterpret_cast<int*>(_a[1])) {
            default: *reinterpret_cast<int*>(_a[0]) = -1; break;
            case 0:
                *reinterpret_cast<int*>(_a[0]) = qRegisterMetaType< QNetworkReply* >(); break;
            }
            break;
        case 10:
            switch (*reinterpret_cast<int*>(_a[1])) {
            default: *reinterpret_cast<int*>(_a[0]) = -1; break;
            case 0:
                *reinterpret_cast<int*>(_a[0]) = qRegisterMetaType< QNetworkReply* >(); break;
            }
            break;
        case 11:
            switch (*reinterpret_cast<int*>(_a[1])) {
            default: *reinterpret_cast<int*>(_a[0]) = -1; break;
            case 0:
                *reinterpret_cast<int*>(_a[0]) = qRegisterMetaType< QNetworkReply* >(); break;
            }
            break;
        }
    } else if (_c == QMetaObject::IndexOfMethod) {
        int *result = reinterpret_cast<int *>(_a[0]);
        {
            using _t = void (BackEnd::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&BackEnd::lastErrorChanged)) {
                *result = 0;
                return;
            }
        }
        {
            using _t = void (BackEnd::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&BackEnd::scoopingChanged)) {
                *result = 1;
                return;
            }
        }
        {
            using _t = void (BackEnd::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&BackEnd::messageChanged)) {
                *result = 2;
                return;
            }
        }
        {
            using _t = void (BackEnd::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&BackEnd::uuidChanged)) {
                *result = 3;
                return;
            }
        }
        {
            using _t = void (BackEnd::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&BackEnd::balanceChanged)) {
                *result = 4;
                return;
            }
        }
        {
            using _t = void (BackEnd::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&BackEnd::registerErrorChanged)) {
                *result = 5;
                return;
            }
        }
        {
            using _t = void (BackEnd::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&BackEnd::resultChanged)) {
                *result = 6;
                return;
            }
        }
    } else if (_c == QMetaObject::RegisterPropertyMetaType) {
        switch (_id) {
        default: *reinterpret_cast<int*>(_a[0]) = -1; break;
        case 5:
            *reinterpret_cast<int*>(_a[0]) = qRegisterMetaType< BookingModel* >(); break;
        case 6:
            *reinterpret_cast<int*>(_a[0]) = qRegisterMetaType< MateModel* >(); break;
        case 7:
            *reinterpret_cast<int*>(_a[0]) = qRegisterMetaType< MenuModel* >(); break;
        }
    }

#ifndef QT_NO_PROPERTIES
    else if (_c == QMetaObject::ReadProperty) {
        auto *_t = static_cast<BackEnd *>(_o);
        Q_UNUSED(_t)
        void *_v = _a[0];
        switch (_id) {
        case 0: *reinterpret_cast< QString*>(_v) = _t->lastError(); break;
        case 1: *reinterpret_cast< int*>(_v) = _t->getBalance(); break;
        case 2: *reinterpret_cast< qint64*>(_v) = _t->getScooping(); break;
        case 3: *reinterpret_cast< QString*>(_v) = _t->getMessage(); break;
        case 4: *reinterpret_cast< QString*>(_v) = _t->getUuid(); break;
        case 5: *reinterpret_cast< BookingModel**>(_v) = _t->getBookingModel(); break;
        case 6: *reinterpret_cast< MateModel**>(_v) = _t->getMateModel(); break;
        case 7: *reinterpret_cast< MenuModel**>(_v) = _t->getMenuModel(); break;
        case 8: *reinterpret_cast< QString*>(_v) = _t->getRegisterError(); break;
        case 9: *reinterpret_cast< QString*>(_v) = _t->getVersion(); break;
        case 10: *reinterpret_cast< bool*>(_v) = _t->getWritepermission(); break;
        case 11: *reinterpret_cast< QString*>(_v) = _t->getResult(); break;
        default: break;
        }
    } else if (_c == QMetaObject::WriteProperty) {
        auto *_t = static_cast<BackEnd *>(_o);
        Q_UNUSED(_t)
        void *_v = _a[0];
        switch (_id) {
        case 0: _t->setLastError(*reinterpret_cast< QString*>(_v)); break;
        default: break;
        }
    } else if (_c == QMetaObject::ResetProperty) {
    }
#endif // QT_NO_PROPERTIES
}

QT_INIT_METAOBJECT const QMetaObject BackEnd::staticMetaObject = { {
    QMetaObject::SuperData::link<QObject::staticMetaObject>(),
    qt_meta_stringdata_BackEnd.data,
    qt_meta_data_BackEnd,
    qt_static_metacall,
    nullptr,
    nullptr
} };


const QMetaObject *BackEnd::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *BackEnd::qt_metacast(const char *_clname)
{
    if (!_clname) return nullptr;
    if (!strcmp(_clname, qt_meta_stringdata_BackEnd.stringdata0))
        return static_cast<void*>(this);
    return QObject::qt_metacast(_clname);
}

int BackEnd::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QObject::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 15)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 15;
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        if (_id < 15)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 15;
    }
#ifndef QT_NO_PROPERTIES
    else if (_c == QMetaObject::ReadProperty || _c == QMetaObject::WriteProperty
            || _c == QMetaObject::ResetProperty || _c == QMetaObject::RegisterPropertyMetaType) {
        qt_static_metacall(this, _c, _id, _a);
        _id -= 12;
    } else if (_c == QMetaObject::QueryPropertyDesignable) {
        _id -= 12;
    } else if (_c == QMetaObject::QueryPropertyScriptable) {
        _id -= 12;
    } else if (_c == QMetaObject::QueryPropertyStored) {
        _id -= 12;
    } else if (_c == QMetaObject::QueryPropertyEditable) {
        _id -= 12;
    } else if (_c == QMetaObject::QueryPropertyUser) {
        _id -= 12;
    }
#endif // QT_NO_PROPERTIES
    return _id;
}

// SIGNAL 0
void BackEnd::lastErrorChanged()
{
    QMetaObject::activate(this, &staticMetaObject, 0, nullptr);
}

// SIGNAL 1
void BackEnd::scoopingChanged()
{
    QMetaObject::activate(this, &staticMetaObject, 1, nullptr);
}

// SIGNAL 2
void BackEnd::messageChanged()
{
    QMetaObject::activate(this, &staticMetaObject, 2, nullptr);
}

// SIGNAL 3
void BackEnd::uuidChanged()
{
    QMetaObject::activate(this, &staticMetaObject, 3, nullptr);
}

// SIGNAL 4
void BackEnd::balanceChanged()
{
    QMetaObject::activate(this, &staticMetaObject, 4, nullptr);
}

// SIGNAL 5
void BackEnd::registerErrorChanged()
{
    QMetaObject::activate(this, &staticMetaObject, 5, nullptr);
}

// SIGNAL 6
void BackEnd::resultChanged()
{
    QMetaObject::activate(this, &staticMetaObject, 6, nullptr);
}
QT_WARNING_POP
QT_END_MOC_NAMESPACE
