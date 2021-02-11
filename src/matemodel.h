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

#ifndef MATEMODEL_H
#define MATEMODEL_H

#include <QAbstractListModel>
#include "mate.h"

class MateModel : public QAbstractListModel
{
    Q_OBJECT 
public:
    enum RoleNames
    {
        NameRole = Qt::UserRole,
        UuidRole = Qt::UserRole + 1,
        ScoopingRole = Qt::UserRole + 2
    };

    explicit MateModel(QObject*parent = 0);
    ~MateModel();

    Q_INVOKABLE void insert(int index, Mate *fr);
    Q_INVOKABLE void append(Mate *fr);
    Q_INVOKABLE void clear();
    Q_INVOKABLE int count();
    Q_INVOKABLE Mate *get(int index);

protected:
    virtual QHash<int, QByteArray> roleNames() const override;

public:
    virtual int rowCount(const QModelIndex &parent) const override;
    virtual QVariant data(const QModelIndex &index, int role) const override;

private:
    QList<Mate *> m_mates;
    QHash<int, QByteArray> m_roleNames;
};
#endif // MATEMODEL_H