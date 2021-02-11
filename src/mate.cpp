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

#include "mate.h"

Mate::Mate(QString name, QString uuid, bool scooping, QObject *parent) :
    QObject(parent)
{ 
    m_name = name;
    m_uuid = uuid;
    m_scooping = scooping;
}

QString Mate::name()
{
    return m_name;
}

QString Mate::uuid()
{
    return m_uuid;
}

bool Mate::scooping()
{
    return m_scooping;
}
