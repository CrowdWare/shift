#include <QtTest/QtTest>
#include "database.h"

class TestBackend: public QObject
{
    Q_OBJECT
private slots:
    void hash();
    void encryptDecrypt();
};

void TestBackend::hash()
{
    Database db;
    QString res = db.hash("Hello");
    
    QCOMPARE(res, "8b1a9953c4611296a827abf8c47804d7");
}

void TestBackend::encryptDecrypt()
{
    Database db;
    QString tmp = db.encrypt("Hello");
    QString res = db.decrypt(tmp);
    QCOMPARE(res, "Hello");
}

QTEST_MAIN(TestBackend)
#include "test.moc"