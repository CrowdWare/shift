#include <QtTest/QtTest>
#include "backend.h"

class TestBackend: public QObject
{
    Q_OBJECT
private slots:
    void hash();
    void balance();
    void minted();
    void chain();
};

void TestBackend::hash()
{
    QString text = "Hello";
    QString res = QString(QCryptographicHash::hash(text.toUtf8(), QCryptographicHash::Md5).toHex());
    
    QCOMPARE(res, "8b1a9953c4611296a827abf8c47804d7");
}

void TestBackend::balance()
{
    BackEnd backend;

    backend.loadChain();
    backend.setBalance_test(10);
    backend.setScooping_test(0);
    int balance = backend.getBalance();
    QCOMPARE(balance, 10000);
}

void TestBackend::minted()
{
    BackEnd backend;

    qint64 time = QDateTime::currentSecsSinceEpoch();
    backend.loadChain();
    backend.setBalance_test(10);
    backend.setScooping_test(time);
    // 4 hours * 60 minutes * 60 seconds = 2 THX added
    int minted = backend.mintedBalance(time + 4 * 60 * 60);
    QCOMPARE(minted, 12000);

    // 21 hours should be treated as 20 hours = 10 THX added
    int minted2 = backend.mintedBalance(time + 21 * 60 * 60);
    QCOMPARE(minted2, 20000);
}

void TestBackend::chain()
{
    BackEnd backend;

    backend.loadChain();
    backend.setBalance_test(15);
    backend.setScooping_test(1234567890);
    
    int rc = backend.saveChain();
    QCOMPARE(rc, 0);
    if(rc != 0)
        return;
    int rc2 = backend.loadChain();
    QCOMPARE(rc2, 0);
    QCOMPARE(backend.getBalance_test(), 15);
    QCOMPARE(backend.getScooping_test(), 1234567890);
}


QTEST_MAIN(TestBackend)
#include "test.moc"