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

#ifndef BOOKINGMODEL_H
#define BOOKINGMODEL_H

#include <QAbstractListModel>
#include "booking.h"

class BookingModel : public QAbstractListModel
{
    Q_OBJECT 
public:
    enum RoleNames
    {
        DescriptionRole = Qt::UserRole,
        AmountRole = Qt::UserRole + 2,
        DateRole = Qt::UserRole + 3
    };

    explicit BookingModel(QObject*parent = 0);
    ~BookingModel();

    Q_INVOKABLE void insert(int index, Booking *booking);
    Q_INVOKABLE void append(Booking *booking);
    Q_INVOKABLE void clear();
    Q_INVOKABLE int count();
    Q_INVOKABLE void remove(int index);
    Q_INVOKABLE Booking *get(int index);

protected:
    virtual QHash<int, QByteArray> roleNames() const override;

public:
    virtual int rowCount(const QModelIndex &parent) const override;
    virtual QVariant data(const QModelIndex &index, int role) const override;

private:
    QList<Booking *> m_bookings;
    QHash<int, QByteArray> m_roleNames;
};
#endif // BOOKINGMODEL_H