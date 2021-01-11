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
#include <QCryptographicHash>

BackEnd::BackEnd(QObject *parent) :
    QObject(parent)
{   
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

int BackEnd::balance()
{
    qint64 time = QDateTime::currentSecsSinceEpoch();
    return mintedBalance(time);
}

int BackEnd::mintedBalance(qint64 time)
{
    int balance = m_settings.value("balance","0").toInt();
    qint64 scooping = m_settings.value("lastScooping", "0").toInt();
    if(scooping == 0)
        return balance;
    int seconds = (time - scooping);
    int hours = seconds / 60 / 60;
    if(hours > 20)
    {
        hours = 20;
        m_settings.setValue("lastScooping", 0);
        m_settings.setValue("balance", balance + 10);
    }
    return balance + (hours / 2);
}

qint64 BackEnd::lastScooping()
{
    return m_settings.value("lastScooping", "0").toInt();
}

void BackEnd::startScooping()
{
    qint64 time = QDateTime::currentSecsSinceEpoch();   
    startScooping(time);
}

void BackEnd::startScooping(qint64 time)
{
    m_settings.setValue("lastScooping", time);
}