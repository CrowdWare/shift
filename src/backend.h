/****************************************************************************
# Copyright (C) 2021 CrowdWare
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
#define FILE_NOT_EXISTS -6
#define CHAIN_LOADED 0
#define CHAIN_SAVED 0


class Friend : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QString name READ getName)
    Q_PROPERTY(QString uuid READ getUuid)
    Q_PROPERTY(QString scooping READ getScooping)
 
public:
    explicit Friend(QString name, QString uuid, qint64 scooping, QObject *parent = nullptr);

    QString getName();
    QString getUuid();
    qint64 getScooping();
  
private:
    QString m_name;
    QString m_uuid;
    qint64 m_scooping;
};

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
    Q_PROPERTY(QString uuid READ getUuid NOTIFY uuidChanged)

public:
    explicit BackEnd(QObject *parent = nullptr);

    Q_INVOKABLE void start();
    Q_INVOKABLE void createAccount(QString name, QString ruuid);

    void setName(QString name);
    void setRuuid(QString ruuid);
    QString lastError();
    void setLastError(const QString &lastError);
    int getBalance();
    QString getUuid();
    qint64 getScooping();
    QString getMessage();
    int saveChain();
    int loadChain();
    QList<QObject *> getBookings();
    QList<QObject *> getFriends();
    void loadMessage();
    void loadFriendlist();

#ifndef TEST
private:
#endif
    int mintedBalance(qint64 time);
    void registerAccount();

#ifdef TEST
public:
    void setScooping_test(qint64 time);
    quint64 getBalance_test();
    qint64 getScooping_test();
    void addBooking_test(Booking *booking);
    void resetBookings_test();
    void setUuid_test(QString uuid);
    void setRuuid_test(QString ruuid);
    void setName_test(QString name);
    void resetAccount_test();
#endif

signals:
    void lastErrorChanged();
    void scoopingChanged();
    void messageChanged();
    void uuidChanged();

public slots:
    void onNetworkReply(QNetworkReply* reply);
    void onFriendlistReply(QNetworkReply* reply);
    void onRegisterReply(QNetworkReply* reply);

private:
    QString m_lastError;
    SimpleCrypt m_crypto;
    quint64 m_balance;
    qint64 m_scooping;
    QList<QObject *> m_bookings;
    QList<QObject *> m_friends;
    QString m_message;
    QString m_name;
    QString m_uuid;
    QString m_ruuid;
    QString m_key;
};
#endif // BACKEND_H