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

#ifndef MENUMODEL_H
#define MENUMODEL_H

#include <QObject>
#include <QString>
#include <QAbstractListModel>
#include "menu.h"

class MenuModel : public QAbstractListModel
{
    Q_OBJECT 
public:
    enum RoleNames
    {
        TitleRole = Qt::UserRole,
        SourceRole = Qt::UserRole+1
    };

    explicit MenuModel(QObject*parent = 0);
    ~MenuModel();

    Q_INVOKABLE void insert(int index, Menu *menu);
    Q_INVOKABLE void append(Menu *menu);
    Q_INVOKABLE void clear();
    Q_INVOKABLE int count();
    Q_INVOKABLE void remove(int index);
    Q_INVOKABLE Menu *get(int index);

protected:
    virtual QHash<int, QByteArray> roleNames() const override;

public:
    virtual int rowCount(const QModelIndex &parent) const override;
    virtual QVariant data(const QModelIndex &index, int role) const override;

private:
    QList<Menu *> m_menus;
    QHash<int, QByteArray> m_roleNames;
};
#endif // MENUMODEL_H