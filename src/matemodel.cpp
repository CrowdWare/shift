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

#include "matemodel.h"


MateModel::MateModel(QObject*parent): 
    QAbstractListModel(parent)
{
    m_roleNames[NameRole] = "name";
    m_roleNames[UuidRole] = "uuid";
    m_roleNames[ScoopingRole] = "scooping";
}

MateModel::~MateModel()
{
}

QHash<int, QByteArray> MateModel::roleNames() const
{
    return m_roleNames;
}

void MateModel::insert(int index, Mate *mate)
{
    if(index < 0 || index > m_mates.count()) 
        return;

    emit beginInsertRows(QModelIndex(), index, index);
    m_mates.insert(index, mate);
    emit endInsertRows();
}

void MateModel::append(Mate *mate)
{
    insert(m_mates.count(), mate);
}

void MateModel::clear()
{
    m_mates.clear();
}

int MateModel::count()
{
    return m_mates.count();
}

Mate *MateModel::get(int index)
{
    return m_mates.at(index);
}

int MateModel::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return m_mates.count();
}
 
QVariant MateModel::data(const QModelIndex &index,int role) const
{
    int row = index.row();

    if(row < 0 || row >= m_mates.count()) 
    {
        return QVariant();
    }
    Mate *mate = qobject_cast<Mate *>(m_mates.at(row));
    switch(role) 
    {
        case NameRole:
            return mate->name();
        case UuidRole:
            return mate->uuid();
        case ScoopingRole:
            return mate->scooping();
    }
    return QVariant();
}
