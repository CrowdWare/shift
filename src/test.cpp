#include <QtTest/QtTest>
#include "backend.h"

class TestBackend: public QObject
{
    Q_OBJECT
private slots:
    void balance();
    void minted();
    void chain();
    void initDatabase();
};

void TestBackend::balance()
{
    BackEnd backend;

    backend.loadChain();
    backend.resetBookings_test();
    backend.addBooking_test(new Booking("test", 10, QDate(1900,1,1)));
    backend.addBooking_test(new Booking("test", 10, QDate(1900,1,2)));
    backend.setScooping_test(0);
    int balance = backend.getBalance();
    QCOMPARE(balance, 20000);
}

void TestBackend::minted()
{
    BackEnd backend;

    qint64 time = QDateTime::currentSecsSinceEpoch();
    backend.loadChain();
    backend.resetBookings_test();
    backend.addBooking_test(new Booking("test", 10, QDate(1900,1,1)));
    backend.addBooking_test(new Booking("test", 10, QDate(1900,1,2)));
    backend.setScooping_test(time);
    // 4 hours * 60 minutes * 60 seconds = 2 THX added
    int minted = backend.mintedBalance(time + 4 * 60 * 60);
    QCOMPARE(minted, 22000);

    // 21 hours should be treated as 20 hours = 10 THX added
    int minted2 = backend.mintedBalance(time + 21 * 60 * 60);
    QCOMPARE(minted2, 30000);
}

void TestBackend::chain()
{
    BackEnd backend;

    backend.loadChain();
    backend.resetBookings_test();
    backend.addBooking_test(new Booking("test", 10, QDate(1900,1,1)));
    backend.addBooking_test(new Booking("test", 10, QDate(1900,1,2)));
    backend.setScooping_test(1234567890);
    
    int rc = backend.saveChain();
    QCOMPARE(rc, 0);
    if(rc != 0)
        return;
    int rc2 = backend.loadChain();
    QCOMPARE(rc2, 0);
    QCOMPARE(backend.getBalance_test(), (quint64)20);
    QCOMPARE(backend.getScooping_test(), (qint64)1234567890);
}

void TestBackend::initDatabase()
{
    BackEnd backend;

    backend.initDatabase();
    QCOMPARE(backend.getBalance_test(), (quint64)1);
}

QTEST_MAIN(TestBackend)
#include "test.moc"