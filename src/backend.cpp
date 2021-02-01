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

BackEnd::BackEnd(QObject *parent) :
    QObject(parent)
{   
    m_lastError = "No Error";
    QString path = QStandardPaths::writableLocation(QStandardPaths::DataLocation);
    m_settings = new QSettings(path.append("/settings.txt"), QSettings::IniFormat);
    // todo, change and hide key
    m_crypto.setKey(1313);
}

QString BackEnd::lastError()
{
    return m_lastError;
} 

void BackEnd::setLastError(const QString &lastError)
{
    if (lastError == m_lastError)
        return;

    m_lastError = lastError;
    emit lastErrorChanged();
}

void BackEnd::setBalance(int balance)
{
    QString enc = m_crypto.encryptToString(QString::number(balance));
    m_settings->setValue("balance", enc);
    m_settings->sync();
}

int BackEnd::getBalance()
{
    qint64 time = QDateTime::currentSecsSinceEpoch();
    return mintedBalance(time);
}

int BackEnd::mintedBalance(qint64 time)
{
    bool ok;
    QString b = m_settings->value("balance","1").toString();
    QString s = m_settings->value("scooping", "0").toString();
    QString decB = m_crypto.decryptToString(b);
    QString decS = m_crypto.decryptToString(s);
    int balance = decB.toInt(&ok);
    qint64 scooping = decS.toInt(&ok);
    qreal hours = 0.0;
    if(scooping > 0) // still scooping
    {
        int seconds = (time - scooping);
        hours = seconds / 60.0 / 60.0;
        if(hours > 20.0)
        {
            hours = 20;
            balance = balance + 10; // 10 THX per day added (20 hours / 2)
            // stop scooping after 20 hours
            QString encS = m_crypto.encryptToString(QString::number(0));
            QString encB = m_crypto.encryptToString(QString::number(balance));
            m_settings->setValue("scooping", encS);
            m_settings->setValue("balance", encB);
            m_settings->sync();
            emit scoopingChanged();
        }
    }
    return balance * 1000 + (hours * 500.0);
}

qint64 BackEnd::scooping()
{
    QString s = m_settings->value("scooping","0").toString();
    QString dec = m_crypto.decryptToString(s);
    return dec.toInt();
}

void BackEnd::start()
{
    qint64 scooping = QDateTime::currentSecsSinceEpoch();
    QString enc = m_crypto.encryptToString(QString::number(scooping));   
    m_settings->setValue("scooping", enc);
    m_settings->sync();
}