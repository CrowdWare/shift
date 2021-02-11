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

#include "booking.h"

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
