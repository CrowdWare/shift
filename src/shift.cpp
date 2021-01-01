/****************************************************************************
# Copyright (C) 2020 Olaf Japp
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

#include <QGuiApplication>
#include <QQmlApplicationEngine>
#include <QQmlContext>
#include <QSettings>
#include <QQuickStyle>
#include <QIcon>
#include "backend.h"
#include "shareutils.h"

BackEnd backend;

void myMessageOutput(QtMsgType type, const QMessageLogContext &context, const QString &msg)
{
    QByteArray localMsg = msg.toLocal8Bit();
    // const char *file = context.file ? context.file : "";
    // const char *function = context.function ? context.function : "";

    switch (type) 
    {
    case QtDebugMsg:
        backend.setLastError("Debug:" + QString(localMsg.constData()));
        break;
    case QtInfoMsg:
        backend.setLastError("Info:" + QString(localMsg.constData()));
        break;
    case QtWarningMsg:
        backend.setLastError("Warning: " + QString(localMsg.constData()));
        break;
    case QtCriticalMsg:
        backend.setLastError("Critical:" + QString(localMsg.constData()));
        break;
    case QtFatalMsg:
        backend.setLastError("Fatal:" + QString(localMsg.constData()));
        //sprintf(msg, "Fatal: %s (%s:%u, %s)\n", localMsg.constData(), file, context.line, function);
        break;
    }
}

int main(int argc, char *argv[])
{
    QGuiApplication::setApplicationName("SHIFT");
    QGuiApplication::setOrganizationName("CrowdWare");
    QGuiApplication::setAttribute(Qt::AA_EnableHighDpiScaling);

    backend.setBalance(1001);
    backend.setLastError("No errors");

    qInstallMessageHandler(myMessageOutput);
    QGuiApplication app(argc, argv);
    qmlRegisterType<BackEnd>("at.crowdware.backend", 1, 0, "BackEnd");
    qmlRegisterType<ShareUtils> ("com.lasconic", 1, 0, "ShareUtils");

    QIcon::setThemeName("shift");
    QQuickStyle::setStyle("Material");

    QSettings settings;
    QString style = QQuickStyle::name();
    if (!style.isEmpty())
        settings.setValue("style", style);
    else
        QQuickStyle::setStyle(settings.value("style").toString());

    QQmlApplicationEngine engine;
    engine.rootContext()->setContextProperty("backend", &backend);
    engine.load(QUrl("qrc:/shift.qml"));
    if (engine.rootObjects().isEmpty())
        return -1;

    return app.exec();
}
