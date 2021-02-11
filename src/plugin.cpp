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

#include "plugin.h"


Plugin::Plugin(QObject *parent) :
    QObject(parent)
{
    m_title = "";
    m_source = "";
}

QString Plugin::title()
{
    return m_title;
}

void Plugin::setTitle(const QString &title)
{
    m_title = title;
    emit titleChanged();
}

QString Plugin::source()
{
    return m_source;
}

void Plugin::setSource(const QString &source)
{
    m_source = source;
    emit sourceChanged();
}