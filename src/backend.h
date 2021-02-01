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
#include <qqml.h>
#include "simplecrypt.h"

class BackEnd : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QString lastError READ lastError WRITE setLastError NOTIFY lastErrorChanged)
    Q_PROPERTY(int balance READ getBalance)
    Q_PROPERTY(qint64 scooping READ scooping NOTIFY scoopingChanged)

public:
    explicit BackEnd(QObject *parent = nullptr);

    Q_INVOKABLE void start(); 

    QString lastError();
    void setLastError(const QString &lastError);
    int getBalance();
    qint64 scooping();

#ifndef TEST
private:
#endif
    int mintedBalance(qint64 time);
    void setBalance(int balance);

signals:
    void lastErrorChanged();
    void scoopingChanged();

private:
    QString m_lastError;
    QSettings *m_settings;
    SimpleCrypt m_crypto;
};

#endif // BACKEND_H