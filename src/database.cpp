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

#include "database.h"
#include <QDebug>
#include <QCryptographicHash>
#include <QUuid>
//#include <QDateTime>
//#include <QDate>
#include "simplecrypt.h"

int key = 0x1313;

Database::Database(QObject *parent) :
    QObject(parent)
{
    m_db = QSqlDatabase::addDatabase("QSQLITE");
    m_db.setDatabaseName("shift");
    if(m_db.open())
    {
        if(m_db.tables().isEmpty())
        {
            QUuid uid;
            QString uuid = uid.createUuid().toString();
            qDebug() << "create account for " << uuid;
            QSqlQuery query;
            query.exec("create table account (id integer primary key not null, uuid varchar(38) not null, lastScooping integer null)");  
            query.exec("insert into account(uuid) values('" + uuid + "')");
        }
    }
    else
    {
        qDebug() << "database not open";
    }
}

Database::~Database()
{
    if(m_db.isOpen())
    {
        m_db.close();
    }
}

void Database::startScooping()
{
    if(m_db.isOpen())
    {
        //QDateTime currentDateTime = QDateTime::QDate::currentDateUtc();
        QSqlQuery query;
        query.exec("update account set lastScooping = DATETIME('now')");
    }
}

QString Database::hash(const QString &text)
{
    QString res = QString(QCryptographicHash::hash(text.toUtf8(), QCryptographicHash::Md5).toHex());
    return res;
}

QString Database::encrypt(const QString &text)
{
    SimpleCrypt crypto(key);
    
    QString result = crypto.encryptToString(text);
    return result;
}

QString Database::decrypt(const QString &text)
{
    SimpleCrypt crypto(key);

    QString result = crypto.decryptToString(text);
    return result;
}
