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

#include "bookingmodel.h"

BookingModel::BookingModel(QObject*parent): 
    QAbstractListModel(parent)
{
    m_roleNames[DescriptionRole] = "description";
    m_roleNames[AmountRole] = "amount";
    m_roleNames[DateRole] = "date";
}

BookingModel::~BookingModel()
{
}

QHash<int, QByteArray> BookingModel::roleNames() const
{
    return m_roleNames;
}

void BookingModel::insert(int index, Booking *booking)
{
    if(index < 0 || index > m_bookings.count()) 
    {
        return;
    }
    emit beginInsertRows(QModelIndex(), index, index);
    m_bookings.insert(index, booking);
    emit endInsertRows();
}

void BookingModel::append(Booking *booking)
{
    insert(m_bookings.count(), booking);
}

void BookingModel::clear()
{
    m_bookings.clear();
}

void BookingModel::remove(int index)
{
    if(index < 0 || index >= m_bookings.count()) 
    {
        return;
    }
    emit beginRemoveRows(QModelIndex(), index, index);
    m_bookings.removeAt(index);
    emit endRemoveRows();
}

int BookingModel::count()
{
    return m_bookings.count();
}

Booking *BookingModel::get(int index)
{
    return m_bookings.at(index);
}

int BookingModel::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return m_bookings.count();
}
 
QVariant BookingModel::data(const QModelIndex &index,int role) const
{
    int row = index.row();

    if(row < 0 || row >= m_bookings.count()) 
    {
        return QVariant();
    }
    Booking *booking = qobject_cast<Booking *>(m_bookings.at(row));
    switch(role) 
    {
        case DescriptionRole:
            return booking->description();
        case AmountRole:
            return booking->amount();
        case DateRole:
            return booking->date();
    }
    return QVariant();
}
