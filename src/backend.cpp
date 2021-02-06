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

#include "backend.h"
#include <QDebug>
#include <QDateTime>
#include <QFile>
#include <QCryptographicHash>
#include <QStandardPaths>
#include <QDataStream>
#include <QBuffer>
#include <QNetworkAccessManager>
#include <QNetworkRequest>
#include <QUuid>
#include <QJsonObject>
#include <QJsonDocument>
#include <QJsonParseError>
#include <QJsonDocument>
#include <QMap>
#include <QJsonArray>


Friend::Friend(QString name, QString uuid, qint64 scooping, QObject *parent) :
    QObject(parent)
{ 
    m_name = name;
    m_uuid = uuid;
    m_scooping = scooping;
}

QString Friend::getName()
{
    return m_name;
}

QString Friend::getUuid()
{
    return m_uuid;
}

qint64 Friend::getScooping()
{
    return m_scooping;
}

Booking::Booking(QString description, quint64 amount, QDate date, QObject *parent) :
    QObject(parent)
{ 
    m_description = description;
    m_amount = amount;
    m_date = date;
}

QString Booking::description()
{
    return m_description;
}

quint64 Booking::amount()
{
    return m_amount;
}

QDate Booking::date()
{
    return m_date;
}

void Booking::setDescription(const QString &description)
{
    m_description = description;
    emit descriptionChanged();
}

void Booking::setAmount(quint64 amount)
{
    m_amount = amount;
    emit amountChanged();
}

void Booking:: setDate(QDate date)
{
    m_date = date;
    emit dateChanged();
}

BackEnd::BackEnd(QObject *parent) :
    QObject(parent)
{   
    m_lastError = "No Error";
    QString path = QStandardPaths::writableLocation(QStandardPaths::DataLocation);
    m_message = "Welcome, please enter your name";
    m_name = "";
    // todo, change and hide key
    m_key = "1234";
    // todo, change and hide key
    m_crypto.setKey(1313);
    m_crypto.setCompressionMode(SimpleCrypt::CompressionAlways);
    m_crypto.setIntegrityProtectionMode(SimpleCrypt::ProtectionHash);
}

void BackEnd::setRuuid(QString ruuid)
{
    m_ruuid = ruuid;
}

void BackEnd::setName(QString name)
{
    m_name = name;
}

void BackEnd::createAccount(QString name, QString ruuid)
{
    m_uuid = QUuid::createUuid().toByteArray().toBase64();
    m_name = name;
    m_balance = 1;
    m_scooping = 0;
    m_bookings.append(new Booking("Initial booking", 1, QDate::currentDate()));
    registerAccount();
}

void BackEnd::registerAccount()
{
    QNetworkRequest request;
    request.setUrl(QUrl("http://artanidosatcrowdwareat.pythonanywhere.com/register"));
    request.setRawHeader("User-Agent", "Shift 1.0");
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");
    QJsonObject obj;
    obj["key"] = m_key;
    obj["name"] = m_name;
    obj["uuid"] = m_uuid;
    obj["ruuid"] = m_ruuid;
    QJsonDocument doc(obj);
    QByteArray data = doc.toJson();
    QNetworkAccessManager* networkManager = new QNetworkAccessManager(this);
    QObject::connect(networkManager, SIGNAL(finished(QNetworkReply*)), this, SLOT(onRegisterReply(QNetworkReply*)));
    networkManager->post(request, data);
}

void BackEnd::onRegisterReply(QNetworkReply* reply)
{
    if(reply->error() == QNetworkReply::NoError)
    {
    	int httpstatuscode = reply->attribute(QNetworkRequest::HttpStatusCodeAttribute).toUInt();
    	switch(httpstatuscode)
    	{
    		case 200:
    		    if (reply->isReadable()) 
    		    {
                    QJsonDocument json = QJsonDocument::fromJson(reply->readAll().data());
                    QJsonObject json_obj = json.object();
                    if (json_obj["isError"].toBool())
                    {
                        setLastError(json_obj["message"].toString());
                        reply->deleteLater();
                        return;
                    }
                    // account is now registered
                    saveChain();
                    emit uuidChanged();
                    m_message = "Welcome, " + m_name;
                    emit messageChanged();
    		    }
                else
                {
                    setLastError("Reply not readable");
                }
    		    break;
    		default:
                setLastError("Response error from webserver: " + QString::number(httpstatuscode));
    			break;
    	}
    }
    else
    {
        setLastError("Reply error from webserver");
    }
     
    reply->deleteLater();
}

void BackEnd::loadMessage()
{
    QNetworkRequest request;
    request.setUrl(QUrl("http://artanidosatcrowdwareat.pythonanywhere.com/message?key=" + m_key + "&name=" + m_name));
    request.setRawHeader("User-Agent", "Shift 1.0");
    QNetworkAccessManager* networkManager = new QNetworkAccessManager(this);
    QObject::connect(networkManager, SIGNAL(finished(QNetworkReply*)), this, SLOT(onNetworkReply(QNetworkReply*)));
    networkManager->get(request);
}

void BackEnd::onNetworkReply(QNetworkReply* reply)
{
    if(reply->error() == QNetworkReply::NoError)
    {
    	int httpstatuscode = reply->attribute(QNetworkRequest::HttpStatusCodeAttribute).toUInt();
    	switch(httpstatuscode)
    	{
    		case 200:
    		    if (reply->isReadable()) 
    		    {
    			    m_message = QString::fromUtf8(reply->readAll().data());
                    emit messageChanged();
    		    }
                else
                {
                    setLastError("Reply not readable");
                }
    		    break;
    		default:
                setLastError("Response error from webserver: " + QString::number(httpstatuscode));
    			break;
    	}
    }
    else
    {
        setLastError("Reply error from webserver");
    }
     
    reply->deleteLater();
}

void BackEnd::loadFriendlist()
{
    QNetworkRequest request;
    request.setUrl(QUrl("http://artanidosatcrowdwareat.pythonanywhere.com/friendlist?key=" + m_key + "&uuid=" + m_uuid));
    request.setRawHeader("User-Agent", "Shift 1.0");
    QNetworkAccessManager* networkManager = new QNetworkAccessManager(this);
    QObject::connect(networkManager, SIGNAL(finished(QNetworkReply*)), this, SLOT(onFriendlistReply(QNetworkReply*)));
    networkManager->get(request);
}

void BackEnd::onFriendlistReply(QNetworkReply* reply)
{
    if(reply->error() == QNetworkReply::NoError)
    {
    	int httpstatuscode = reply->attribute(QNetworkRequest::HttpStatusCodeAttribute).toUInt();
    	switch(httpstatuscode)
    	{
    		case 200:
    		    if (reply->isReadable()) 
    		    {
    			    QJsonDocument json = QJsonDocument::fromJson(reply->readAll().data());
                    QJsonObject json_obj = json.object();
                    if (json_obj["isError"].toBool())
                    {
                        setLastError(json_obj["message"].toString());
                        reply->deleteLater();
                        return;
                    }
                    QJsonArray data = json_obj.value("data").toArray();
                    foreach (const QJsonValue & value, data)
                    {
                        QJsonObject obj = value.toObject();
                        m_friends.append(new Friend(obj["name"].toString(), obj["uuid"].toString(), obj["scooping"].toString().toLongLong()));
    		        }
                }    
                else
                {
                    setLastError("Reply not readable");
                }
    		    break;
    		default:
                setLastError("Response error from webserver: " + QString::number(httpstatuscode));
    			break;
    	}
    }
    else
    {
        setLastError("Reply error from webserver");
    }
     
    reply->deleteLater();
}

QString BackEnd::lastError()
{
    return m_lastError;
} 

QString BackEnd::getMessage()
{
    return m_message;
}

void BackEnd::setLastError(const QString &lastError)
{
    if (lastError == m_lastError)
        return;

    m_lastError = lastError;
    emit lastErrorChanged();
}

int BackEnd::getBalance()
{
    qint64 time = QDateTime::currentSecsSinceEpoch();
    return mintedBalance(time);
}

int BackEnd::mintedBalance(qint64 time)
{
    qreal hours = 0.0;
    if(m_scooping > 0) // still scooping
    {
        int seconds = (time - m_scooping);
        hours = seconds / 60.0 / 60.0;
        if(hours > 20.0)
        {
            hours = 0;
            m_balance = m_balance + 10; // 10 THX per day added (20 hours / 2)
            // stop scooping after 20 hours
            m_scooping = 0;
            m_bookings.insert(0, new Booking("Liquid scooped", 10, QDate::currentDate()));
            saveChain();
            emit scoopingChanged();
        }
    }
    return m_balance * 1000 + (hours * 500.0);
}

qint64 BackEnd::getScooping()
{
    return m_scooping;
}

QString BackEnd::getUuid()
{
    return m_uuid;
}

void BackEnd::start()
{
    m_scooping = QDateTime::currentSecsSinceEpoch();
    saveChain();
}

QList<QObject *> BackEnd::getBookings()
{
    return m_bookings;
}

QList<QObject *> BackEnd::getFriends()
{
    return m_friends;
}

int BackEnd::saveChain()
{
    QBuffer buffer;
    buffer.open(QIODevice::WriteOnly);
    QDataStream out(&buffer);

    QString path = QStandardPaths::writableLocation(QStandardPaths::DataLocation).append("/shift.db");
    QFile file(path);
    if(!file.open(QIODevice::WriteOnly))
    {
        if (file.error() != QFile::NoError) 
        {
            setLastError(file.errorString() + ":" + path);
            return FILE_COULD_NOT_OPEN;
        }
    }
    out << (quint16)0x3113; // magic number
    out << (quint16)100;    // version
    out << m_scooping;
    out << m_uuid;
    out << m_ruuid;
    out << m_name;

    out << m_bookings.count();
    for(int i = 0; i < m_bookings.count(); i++)
    {
        Booking *booking = qobject_cast<Booking *>(m_bookings.at(i));
        out << booking->amount();
        out << booking->date();
        out << booking->description();
    }

    QByteArray cypherText = m_crypto.encryptToByteArray(buffer.data());
    if (m_crypto.lastError() == SimpleCrypt::ErrorNoError) 
    {
        file.write(cypherText);
    }
    else
    {
        buffer.close();
        file.close();
        return CRYPTO_ERROR;
    }
    
    buffer.close();
    file.close();
    return CHAIN_SAVED;
}

int BackEnd::loadChain()
{
    quint16 magic;
    quint16 version;
    int count;

    QString path = QStandardPaths::writableLocation(QStandardPaths::DataLocation);
    QFile file(path.append("/shift.db"));
    if(!file.exists())
        return FILE_NOT_EXISTS;
    if(!file.open(QIODevice::ReadOnly))
    {
        if (file.error() != QFile::NoError) 
        {
            setLastError(file.errorString());
            return FILE_COULD_NOT_OPEN;
        }
    }
    QByteArray cypherText = file.readAll();
    file.close();

    QByteArray plaintext = m_crypto.decryptToByteArray(cypherText);
    if(m_crypto.lastError() == SimpleCrypt::ErrorNoError) 
    {
        QBuffer buffer(&plaintext);
        buffer.open(QIODevice::ReadOnly);
        QDataStream in(&buffer);
        in >> magic;
        if (magic != 0x3113)
        {
            qDebug() << "Magic number is:" << magic;
            file.close();
            return BAD_FILE_FORMAT;
        }
        // check the version
        in >> version;
        if (version < 100)
        {
            file.close();
            return UNSUPPORTED_VERSION;
        }
        in >> m_scooping;
        in >> m_uuid;
        in >> m_ruuid;
        in >> m_name;
        in >> count;
        m_bookings.clear();
        m_balance = 0;
        for(int i = 0; i < count; i++)
        {
            quint64 amount;
            QDate date;
            QString description;
            in >> amount;
            in >> date;
            in >> description;
            m_bookings.append(new Booking(description, amount, date));
            m_balance += amount;
        }
        buffer.close();
    }
    else
        return CRYPTO_ERROR;

    return CHAIN_LOADED;
}

// used for unit tests only
#ifdef TEST
void BackEnd::setScooping_test(qint64 time)
{
    m_scooping = time;
}

quint64 BackEnd::getBalance_test()
{
    return m_balance;
}

qint64 BackEnd::getScooping_test()
{
    return m_scooping;
}

void BackEnd::addBooking_test(Booking *booking)
{
    m_balance += booking->amount();
    m_bookings.insert(0, booking);
}

void BackEnd::resetBookings_test()
{
    m_bookings.clear();
    m_balance = 0;
}

void BackEnd::setUuid_test(QString uuid)
{
    m_uuid = uuid;
}

void BackEnd::setRuuid_test(QString ruuid)
{
    m_ruuid = ruuid;
}

void BackEnd::setName_test(QString name)
{
    m_name = name;
}

void BackEnd::resetAccount_test()
{
    QString path = QStandardPaths::writableLocation(QStandardPaths::DataLocation);
    QFile file(path.append("/shift.db"));
    file.remove();
}
#endif