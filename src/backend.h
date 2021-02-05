/****************************************************************************
# Copyright (C) 2020 Olaf Japp
#
# This file is part of SHIFT.
#
#  SHIFT is free software: you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation, either version 3 of the License, or
#  (at your option) any later version.
#
#  SHIFT is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#
#  You should have received a copy of the GNU General Public License
#  along with SHIFT.  If not, see <http://www.gnu.org/licenses/>.
#
****************************************************************************/

#ifndef BACKEND_H
#define BACKEND_H

#include <QObject>
#include <QString>
#include <QSettings>
#include <QDate>
#include <qqml.h>
#include <QNetworkReply>
#include "simplecrypt.h"

#define BAD_FILE_FORMAT -1
#define UNSUPPORTED_VERSION -2
#define FILE_COULD_NOT_OPEN -3
#define CRYPTO_ERROR -4
#define CHAIN_NOT_LOADED_BEFORE_SAVE -5
#define CHAIN_LOADED 0
#define CHAIN_SAVED 0

class Booking : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QString description READ description WRITE setDescription NOTIFY descriptionChanged)
    Q_PROPERTY(quint64 amount READ amount WRITE setAmount NOTIFY amountChanged)
    Q_PROPERTY(QDate date READ date WRITE setDate NOTIFY dateChanged)
    
public:
    explicit Booking(QString description, quint64 amount, QDate date, QObject *parent = nullptr);

    QString description();
    quint64 amount();
    QDate date();
    void setDescription(const QString &description);
    void setAmount(quint64 amount);
    void setDate(QDate date);

signals:
    void descriptionChanged();
    void amountChanged();
    void dateChanged();

private:
    QString m_description;
    quint64 m_amount;
    QDate m_date;
};


class BackEnd : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QString lastError READ lastError WRITE setLastError NOTIFY lastErrorChanged)
    Q_PROPERTY(int balance READ getBalance)
    Q_PROPERTY(qint64 scooping READ getScooping NOTIFY scoopingChanged)
    Q_PROPERTY(QString message READ getMessage NOTIFY messageChanged)
 
public:
    explicit BackEnd(QObject *parent = nullptr);

    Q_INVOKABLE void start(); 

    QString lastError();
    void setLastError(const QString &lastError);
    int getBalance();
    qint64 getScooping();
    QString getMessage();
    int saveChain();
    int loadChain();
    QList<QObject *> getBookings();
    void loadMessage();

#ifndef TEST
private:
#endif
    int mintedBalance(qint64 time);

#ifdef TEST
public:
    void setScooping_test(qint64 time);
    quint64 getBalance_test();
    qint64 getScooping_test();
    void addBooking_test(Booking *booking);
    void resetBookings();
#endif

signals:
    void lastErrorChanged();
    void scoopingChanged();
    void messageChanged();

public slots:
    void onNetworkReply(QNetworkReply* reply);

private:
    QString m_lastError;
    QSettings *m_settings;
    SimpleCrypt m_crypto;
    quint64 m_balance;
    qint64 m_scooping;
    QList<QObject *> m_bookings;
    QString m_message;
};
#endif // BACKEND_H