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
    return m_balance;
}

void BackEnd::setBalance(int newBalance)
{
    if (newBalance == m_balance)
        return;

    m_balance = newBalance;
    emit balanceChanged();
}

void BackEnd::startScooping()
{
    m_db.startScooping();
}