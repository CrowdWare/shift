#include <QtTest/QtTest>
#include "backend.h"

class TestBackend: public QObject
{
    Q_OBJECT
private slots:
    void hash();
    void scooping();
    void balance();
    void minted();
};

void TestBackend::hash()
{
    QString text = "Hello";
    QString res = QString(QCryptographicHash::hash(text.toUtf8(), QCryptographicHash::Md5).toHex());
    
    QCOMPARE(res, "8b1a9953c4611296a827abf8c47804d7");
}

void TestBackend::scooping()
{
    BackEnd backend;

    qint64 time = QDateTime::currentSecsSinceEpoch();
    backend.startScooping(time);
    int last = backend.lastScooping();
    QCOMPARE(last, time);
}

void TestBackend::balance()
{
    BackEnd backend;

    backend.setBalance(10);
    int balance = backend.balance();
    QCOMPARE(balance, 10);
}

void TestBackend::minted()
{
    BackEnd backend;

    qint64 time = QDateTime::currentSecsSinceEpoch();
    backend.startScooping(time);
    backend.setBalance(10);
    // 4 hours * 60 minutes * 60 seconds = 2 THX added
    int minted = backend.mintedBalance(time + 4 * 60 * 60);
    QCOMPARE(minted, 12);

    // 21 hours should be treated as 20 hours = 10 THX added
    int minted2 = backend.mintedBalance(time + 21 * 60 * 60);
    QCOMPARE(minted2, 20);

}


QTEST_MAIN(TestBackend)
#include "test.moc"