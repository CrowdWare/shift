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

#include "backend.h"
#include <QGuiApplication>
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
#include <QDir>
#include <QQmlComponent>
#include <QQmlEngine>
#include "plugin.h"

#include "../../private/shift.keys"


BackEnd::BackEnd(QObject *parent) :
    QObject(parent)
{   
    m_lastError = "";
    m_message = "Welcome, wait a few seconds to load the database";
    m_name = "";
    m_key = SHIFT_API_KEY;
    m_registerError = "";
    m_crypto.setKey(SHIFT_ENCRYPT_KEY);
    m_crypto.setCompressionMode(SimpleCrypt::CompressionAlways);
    m_crypto.setIntegrityProtectionMode(SimpleCrypt::ProtectionHash);
}

BookingModel *BackEnd::getBookingModel()
{
    return &m_bookingModel;
}

MateModel *BackEnd::getMateModel()
{
    return &m_mateModel;
}

MenuModel *BackEnd::getMenuModel()
{
    return &m_menuModel;
}

void BackEnd::setRuuid(QString ruuid)
{
    m_ruuid = ruuid;
}

void BackEnd::setName(QString name)
{
    m_name = name;
}

void BackEnd::createAccount(QString name, QString ruuid, QString country, QString language)
{
    m_uuid = QUuid::createUuid().toByteArray().toBase64();
    if (ruuid == "me")
        m_ruuid = m_uuid;
    else
        m_ruuid = ruuid;
    m_name = name;
    m_country = country;
    m_language = language;
    m_balance = 0;
    m_scooping = 0;
    registerAccount();
}

bool BackEnd::getWritepermission()
{
    return m_writepermission;
}

bool BackEnd::checkPermission()
{
    m_writepermission = false;
    QString msg_text = "Welcome, please set the permission to write to external storage in the settings of your mobile phone and restart the app.<br><br>You will find it under: Settings -> Apps -> Apps -> Shift -> Permission -> Memory";
            
    QString path = QStandardPaths::writableLocation(QStandardPaths::GenericDataLocation) + "/crowdware";
    QDir dir(path);
    if (!dir.exists())
    {
        if (!dir.mkpath(path))
        {
            m_message = msg_text;
            return false;
        }
    }
    path.append("/test.txt");
    QFile file(path);
    if(file.open(QIODevice::WriteOnly))
    {
        file.close();
        file.remove();
        m_writepermission = true;
        return true;
    }
    else
    {   
        m_message = msg_text;
        return false;
    }
}

QString BackEnd::getRegisterError()
{
    return m_registerError;
}

QString BackEnd::getVersion()
{
    return QGuiApplication::applicationVersion();
}

void BackEnd::setScooping()
{
    QNetworkRequest request;
    request.setUrl(QUrl("http://artanidosatcrowdwareat.pythonanywhere.com/setscooping"));
    request.setRawHeader("User-Agent", "Shift 1.0");
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");
    QJsonObject obj;
    obj["key"] = m_key;
    obj["uuid"] = m_uuid;
#ifdef TEST
    obj["test"] = "true";
#else
    obj["test"] = "false";
#endif
    QJsonDocument doc(obj);
    QByteArray data = doc.toJson();
    QNetworkAccessManager* networkManager = new QNetworkAccessManager(this);
    QObject::connect(networkManager, SIGNAL(finished(QNetworkReply*)), this, SLOT(onSetScoopingReply(QNetworkReply*)));
    networkManager->post(request, data);
}

void BackEnd::onSetScoopingReply(QNetworkReply* reply)
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
                    m_check = "setScooping: ok";
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
        setLastError("Reply error from webserver:" + QString::number(reply->error()));
    }
     
    reply->deleteLater();
}

void BackEnd::registerAccount()
{
    m_registerError = "";
    emit registerErrorChanged();

    QNetworkRequest request;
    request.setUrl(QUrl("http://artanidosatcrowdwareat.pythonanywhere.com/register"));
    request.setRawHeader("User-Agent", "Shift 1.0");
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");
    QJsonObject obj;
    obj["key"] = m_key;
    obj["name"] = m_name;
    obj["uuid"] = m_uuid;
    obj["ruuid"] = m_ruuid;
    obj["country"] = m_country;
    obj["language"] = m_language;
#ifdef TEST
    obj["test"] = "true";
#else
    obj["test"] = "false";
#endif
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
                        m_registerError = json_obj["message"].toString();
                        emit registerErrorChanged();
                        reply->deleteLater();
                        return;
                    }
                    // account is now registered
                    m_registerError = "";
                    emit registerErrorChanged();

                    m_balance = 1;
                    m_bookingModel.append(new Booking("Initial booking", 1, QDate::currentDate()));
                    saveChain();
                    emit uuidChanged();
                    m_message = "Welcome, " + m_name + " please tap on the logo.";
                    emit messageChanged();
    		    }
                else
                {
                    setLastError("Reply not readable");
                    m_registerError = "An error occured. Please try again later.";
                    emit registerErrorChanged();
                }
    		    break;
    		default:
                setLastError("Response error from webserver: " + QString::number(httpstatuscode));
    			m_registerError = "An error occured. Please try again later.";
                emit registerErrorChanged();
                break;
    	}
    }
    else
    {
        setLastError("Reply error from webserver: " + QString::number(reply->error()));
        m_registerError = "An error occured. Please try again later.";
        emit registerErrorChanged();
    }
     
    reply->deleteLater();
}

void BackEnd::loadMessage()
{
    // don't run right after installation
    if (m_name == "")
        return;
    QNetworkRequest request;
    request.setUrl(QUrl("http://artanidosatcrowdwareat.pythonanywhere.com/message"));
    request.setRawHeader("User-Agent", "Shift 1.0");
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");
    QJsonObject obj;
    obj["key"] = m_key;
    obj["name"] = m_name;
#ifdef TEST
    obj["test"] = "true";
#else
    obj["test"] = "false";
#endif
    QJsonDocument doc(obj);
    QByteArray data = doc.toJson();
    QNetworkAccessManager* networkManager = new QNetworkAccessManager(this);
    QObject::connect(networkManager, SIGNAL(finished(QNetworkReply*)), this, SLOT(onNetworkReply(QNetworkReply*)));
    networkManager->post(request, data);
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
                    QJsonDocument json = QJsonDocument::fromJson(reply->readAll().data());
                    QJsonObject json_obj = json.object();
                    if (json_obj["isError"].toBool())
                    {
                        setLastError(json_obj["message"].toString());
                        reply->deleteLater();
                        m_message = "Welcome back";
                        emit messageChanged();
                        return;
                    }
                    m_message = json_obj.value("data").toString();
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
        setLastError("Reply error from webserver: " + QString::number(reply->error()));
    }
     
    reply->deleteLater();
}

void BackEnd::loadMatelist()
{
    // don't run right after installation
    if (m_uuid == "")
        return;
    QNetworkRequest request;
    request.setUrl(QUrl("http://artanidosatcrowdwareat.pythonanywhere.com/matelist"));
    request.setRawHeader("User-Agent", "Shift 1.0");
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");
    QJsonObject obj;
    obj["key"] = m_key;
    obj["uuid"] = m_uuid;
#ifdef TEST
    obj["test"] = "true";
#else
    obj["test"] = "false";
#endif
    QJsonDocument doc(obj);
    QByteArray data = doc.toJson();
    QNetworkAccessManager* networkManager = new QNetworkAccessManager(this);
    QObject::connect(networkManager, SIGNAL(finished(QNetworkReply*)), this, SLOT(onMatelistReply(QNetworkReply*)));
    networkManager->post(request, data);
}

void BackEnd::onMatelistReply(QNetworkReply* reply)
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
                    m_mateModel.clear();
                    m_mates = 0;
                    foreach (const QJsonValue & value, data)
                    {
                        // only count up to 10 mates
                        if(m_mates < 10)
                            m_mates++;
                        QJsonObject obj = value.toObject();
                        m_mateModel.append(new Mate(obj["name"].toString(), obj["uuid"].toString(), obj["scooping"].toBool()));
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
        setLastError("Reply error from webserver: " + QString::number(reply->error()));
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
    if (m_lastError.length() < 200)
    {
        m_lastError += lastError + "\n";
        emit lastErrorChanged();
    }
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
            int grow = 10 + m_mates;
            m_balance = m_balance + grow; // 10 + 1 (per mate) THX per day added (20 hours / 2) + 
            // stop scooping after 20 hours
            m_scooping = 0;
            if (m_bookingModel.count() > 29)
            {
                // combine the last two bookings
                Booking *last = m_bookingModel.get(m_bookingModel.count() - 1);
                Booking *prev = m_bookingModel.get(m_bookingModel.count() - 2);
                prev->setAmount(prev->amount() + last->amount());
                prev->setDescription("Subtotal");
                m_bookingModel.remove(m_bookingModel.count() - 1);
            }
            m_bookingModel.insert(0, new Booking("Liquid scooped", grow, QDate::currentDate()));
            saveChain();
            emit scoopingChanged();
            emit balanceChanged();
        }
    }
    return m_balance * 1000 + (hours * 500.0) + (hours * m_mates * 50.0);
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
    setScooping();
    saveChain();
}

int BackEnd::saveChain()
{
    QBuffer buffer;
    buffer.open(QIODevice::WriteOnly);
    QDataStream out(&buffer);

    QString path = QStandardPaths::writableLocation(QStandardPaths::GenericDataLocation) + "/crowdware";
    QDir dir(path);
    if (!dir.exists())
        dir.mkpath(path);

    path.append("/shift.db");
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
    out << m_country;
    out << m_language;

    out << m_bookingModel.count();
    for(int i = 0; i < m_bookingModel.count(); i++)
    {
        Booking *booking = qobject_cast<Booking *>(m_bookingModel.get(i));
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

    QString path = QStandardPaths::writableLocation(QStandardPaths::GenericDataLocation) + "/crowdware";
    QFile file(path.append("/shift.db"));
    if(!file.exists())
    {
        m_message = "Welcome, please fill in all fields and tap on CREATE ACCOUNT";
        emit messageChanged();
        return FILE_NOT_EXISTS;
    }
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
        in >> m_country;
        in >> m_language;
        in >> count;
        m_bookingModel.clear();
        m_balance = 0;
        for(int i = 0; i < count; i++)
        {
            quint64 amount;
            QDate date;
            QString description;
            in >> amount;
            in >> date;
            in >> description;
            m_bookingModel.append(new Booking(description, amount, date));
            m_balance += amount;
        }
        m_message = "Welcome, back " + m_name;
        emit messageChanged();
        buffer.close();
        emit balanceChanged();
    }
    else
        return CRYPTO_ERROR;

    return CHAIN_LOADED;
}

void BackEnd::loadMenu()
{
    m_menuModel.append(new Menu("Home", "qrc:/gui/Home.qml"));
    m_menuModel.append(new Menu("Mates", "qrc:/gui/Friends.qml"));
}

void BackEnd::loadPlugins()
{
    QString path = QStandardPaths::writableLocation(QStandardPaths::GenericDataLocation) + "/crowdware";
    QDir dir(path + "/shift/plugins");
    setLastError(path);
    dir.setFilter(QDir::Dirs | QDir::NoDotAndDotDot);
    if (dir.exists())
    {
        QFileInfoList list = dir.entryInfoList();
        for (int i = 0; i < list.size(); ++i) 
        {
            QFileInfo fileInfo = list.at(i);
            QQmlEngine engine;
            QQmlComponent component(&engine);
            component.loadUrl(QUrl::fromLocalFile(path + "/shift/plugins/" + fileInfo.fileName() + "/plugin.qml"));
            QObject *obj = component.create();
            Plugin *plugin = qobject_cast<Plugin *>(obj);
            m_menuModel.append(new Menu(plugin->title(), plugin->source()));
        }
    }
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
    m_bookingModel.insert(0, booking);
}

void BackEnd::resetBookings_test()
{
    m_bookingModel.clear();
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

void BackEnd::setCountry_test(QString country)
{
    m_country = country;
}

void BackEnd::setLanguage_test(QString language)
{
    m_language = language;
}

void BackEnd::resetAccount_test()
{
    QString path = QStandardPaths::writableLocation(QStandardPaths::GenericDataLocation);
    QFile file(path.append("/shift.db"));
    file.remove();
}
#endif