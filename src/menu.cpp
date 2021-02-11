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

#include "menu.h"


Menu::Menu(QString title, QString source, QObject *parent) :
    QObject(parent)
{
    m_title = title;
    m_source = source;
}

QString Menu::title()
{
    return m_title;
}

void Menu::setTitle(const QString &title)
{
    m_title = title;
    emit titleChanged();
}

QString Menu::source()
{
    return m_source;
}

void Menu::setSource(const QString &source)
{
    m_source = source;
    emit sourceChanged();
}