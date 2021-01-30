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
    m_settings->setValue("balance", balance);
    m_settings->sync();
}

int BackEnd::getBalance()
{
    qint64 time = QDateTime::currentSecsSinceEpoch();
    return mintedBalance(time);
}

int BackEnd::mintedBalance(qint64 time)
{
    int balance = m_settings->value("balance","1").toInt();
    qint64 scooping = m_settings->value("scooping", "0").toInt();
    if(scooping == 0) // not scooping
        return balance;
    int seconds = (time - scooping);
    int hours = seconds / 60 / 60;
    if(hours > 20)
    {
        hours = 20;
        // stop scooping after 20 hours
        m_settings->setValue("scooping", 0);
        m_settings->setValue("balance", balance + hours * .5);
        m_settings->sync();
    }
    return balance + (hours * .5);
}

qint64 BackEnd::scooping()
{
    return m_settings->value("scooping", "0").toInt();
}

void BackEnd::start()
{
    qint64 scooping = QDateTime::currentSecsSinceEpoch();   
    m_settings->setValue("scooping", scooping);
    m_settings->sync();
}