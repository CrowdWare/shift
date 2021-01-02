#include <QtTest/QtTest>
#include "database.h"

class TestBackend: public QObject
{
    Q_OBJECT
private slots:
    void hash();
};

void TestBackend::hash()
{
    Database db;
    QString res = db.hash("Hello");
    
    QCOMPARE(res, "8b1a9953c4611296a827abf8c47804d7");
}

QTEST_MAIN(TestBackend)
#include "test.moc"