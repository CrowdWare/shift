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

#include "menumodel.h"

MenuModel::MenuModel(QObject*parent): 
    QAbstractListModel(parent)
{
    m_roleNames[TitleRole] = "title";
    m_roleNames[SourceRole] = "source";
}

MenuModel::~MenuModel()
{
}

QHash<int, QByteArray> MenuModel::roleNames() const
{
    return m_roleNames;
}

void MenuModel::insert(int index, Menu *menu)
{
    if(index < 0 || index > m_menus.count()) 
    {
        return;
    }
    emit beginInsertRows(QModelIndex(), index, index);
    m_menus.insert(index, menu);
    emit endInsertRows();
}

void MenuModel::append(Menu *menu)
{
    insert(m_menus.count(), menu);
}

void MenuModel::clear()
{
    m_menus.clear();
}

void MenuModel::remove(int index)
{
    if(index < 0 || index >= m_menus.count()) 
    {
        return;
    }
    emit beginRemoveRows(QModelIndex(), index, index);
    m_menus.removeAt(index);
    emit endRemoveRows();
}

int MenuModel::count()
{
    return m_menus.count();
}

Menu *MenuModel::get(int index)
{
    return m_menus.at(index);
}

int MenuModel::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return m_menus.count();
}
 
QVariant MenuModel::data(const QModelIndex &index,int role) const
{
    int row = index.row();

    if(row < 0 || row >= m_menus.count()) 
    {
        return QVariant();
    }
    Menu *menu = qobject_cast<Menu *>(m_menus.at(row));
    switch(role) 
    {
        case TitleRole:
            return menu->title();
        case SourceRole:
            return menu->source();
    }
    return QVariant();
}
