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
#include <QAbstractListModel>
#include <QColor>
#include "simplecrypt.h"
#include "bookingmodel.h"
#include "matemodel.h"
#include "menumodel.h" 

#define BAD_FILE_FORMAT -1
#define UNSUPPORTED_VERSION -2
#define FILE_COULD_NOT_OPEN -3
#define CRYPTO_ERROR -4
#define CHAIN_NOT_LOADED_BEFORE_SAVE -5
#define FILE_NOT_EXISTS -6
#define CHAIN_LOADED 0
#define CHAIN_SAVED 0


class BackEnd : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QString lastError READ lastError WRITE setLastError NOTIFY lastErrorChanged)
    Q_PROPERTY(int balance READ getBalance NOTIFY balanceChanged)
    Q_PROPERTY(qint64 scooping READ getScooping NOTIFY scoopingChanged)
    Q_PROPERTY(QString message READ getMessage NOTIFY messageChanged)
    Q_PROPERTY(QString uuid READ getUuid NOTIFY uuidChanged)
    Q_PROPERTY(BookingModel *bookingModel READ getBookingModel CONSTANT)
    Q_PROPERTY(MateModel *mateModel READ getMateModel CONSTANT)
    Q_PROPERTY(MenuModel *menuModel READ getMenuModel CONSTANT)
    Q_PROPERTY(QString registerError READ getRegisterError NOTIFY registerErrorChanged)
    Q_PROPERTY(QString version READ getVersion CONSTANT)
    Q_PROPERTY(bool writepermission READ getWritepermission CONSTANT)
    
public:
    explicit BackEnd(QObject *parent = nullptr);

    Q_INVOKABLE void start();
    Q_INVOKABLE void createAccount(QString name, QString ruuid, QString country, QString language);

    void setName(QString name);
    void setRuuid(QString ruuid);
    QString lastError();
    void setLastError(const QString &lastError);
    int getBalance();
    QString getUuid();
    qint64 getScooping();
    QString getMessage();
    QString getRegisterError();
    QString getVersion();
    bool getWritepermission();
    bool checkPermission();
    int saveChain();
    int loadChain();
    void loadMenu();
    void loadPlugins();
    void loadMessage();
    void loadMatelist();
    BookingModel *getBookingModel();
    MateModel *getMateModel();
    MenuModel *getMenuModel();

#ifndef TEST
private:
#endif
    int mintedBalance(qint64 time);
    void registerAccount();
    void setScooping();

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
    void setCountry_test(QString country);
    void setLanguage_test(QString language);
    void resetAccount_test();
    QString getCheck() {return m_check;};
#endif

signals:
    void lastErrorChanged();
    void scoopingChanged();
    void messageChanged();
    void uuidChanged();
    void balanceChanged();
    void registerErrorChanged();

public slots:
    void onNetworkReply(QNetworkReply* reply);
    void onMatelistReply(QNetworkReply* reply);
    void onRegisterReply(QNetworkReply* reply);
    void onSetScoopingReply(QNetworkReply* reply);

private:
    QString m_lastError;
    SimpleCrypt m_crypto;
    quint64 m_balance;
    qint64 m_scooping;
    QString m_message;
    QString m_name;
    QString m_uuid;
    QString m_ruuid;
    QString m_key;
    QString m_country;
    QString m_language;
    QString m_registerError;
    BookingModel m_bookingModel;
    MateModel m_mateModel;
    MenuModel m_menuModel;
    QString m_check;
    int m_mates;
    bool m_writepermission;
};
#endif // BACKEND_H