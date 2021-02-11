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

#ifndef MATE_H
#define MATE_H

#include <QObject>
#include <QString>

class Mate : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QString name READ name CONSTANT)
    Q_PROPERTY(QString uuid READ uuid CONSTANT)
    Q_PROPERTY(bool scooping READ scooping CONSTANT)
 
public:
    explicit Mate(QString name, QString uuid, bool scooping, QObject *parent = nullptr);

    QString name();
    QString uuid();
    bool scooping();

private:
    QString m_name;
    QString m_uuid;
    bool m_scooping;
};
#endif // MATE_H