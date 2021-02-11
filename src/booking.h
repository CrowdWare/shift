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

#ifndef BOOKING_H
#define BOOKING_H

#include <QObject>
#include <QString>
#include <QDate>


class Booking : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QString description READ description WRITE setDescription NOTIFY descriptionChanged)
    Q_PROPERTY(quint64 amount READ amount WRITE setAmount NOTIFY amountChanged)
    Q_PROPERTY(QDate date READ date WRITE setDate NOTIFY dateChanged)
    
public:
    explicit Booking(QString description, quint64 amount, QDate date, QObject *parent = nullptr);

    QString description();
    quint64 amount();
    QDate date();
    void setDescription(const QString &description);
    void setAmount(quint64 amount);
    void setDate(QDate date);

signals:
    void descriptionChanged();
    void amountChanged();
    void dateChanged();

private:
    QString m_description;
    quint64 m_amount;
    QDate m_date;
};
#endif // BOOKING_H