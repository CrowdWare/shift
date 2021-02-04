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
#include <QList>
#include <QQuickView>
#include "backend.h"
#include "shareutils.h"
#include "notificationclient.h"

BackEnd backend;
NotificationClient notificationClient;
    
void myMessageOutput(QtMsgType type, const QMessageLogContext &context, const QString &msg)
{
    QByteArray localMsg = msg.toLocal8Bit();
    QString typ = "Undefined";
    const char *file = context.file ? context.file : "undefined";
    const char *function = context.function ? context.function : "undefined";

    switch (type) 
    {
    case QtDebugMsg:
        typ = "Debug";
        break;
    case QtInfoMsg:
        typ = "Info";
        break;
    case QtWarningMsg:
        typ = "Warning";
        break;
    case QtCriticalMsg:
        typ = "Critical";
        break;
    case QtFatalMsg:
        typ = "Fatal";
        break;
    }
    backend.setLastError(typ + ":" + QString(localMsg.constData()) + " (" + file + ":" + QString(context.line) + "," + function + ")");
}

int main(int argc, char *argv[])
{
    QGuiApplication::setApplicationName("SHIFT");
    QGuiApplication::setOrganizationName("CrowdWare");
    QGuiApplication::setAttribute(Qt::AA_EnableHighDpiScaling);

    backend.setLastError("No errors");
    backend.loadChain();

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
    engine.rootContext()->setContextProperty("bookings", QVariant::fromValue(backend.getBookings()));
    engine.rootContext()->setContextProperty("backend", &backend);
    engine.rootContext()->setContextProperty("notificationClient", &notificationClient);
    engine.load(QUrl("qrc:/shift.qml"));
    if (engine.rootObjects().isEmpty())
        return -1;
    return app.exec();
}