/****************************************************************************
* Copyright (C) 2020 Olaf Japp
*
* This file is part of SHIFT.
*
*  SHIFT is free software: you can redistribute it and/or modify
*  it under the terms of the GNU General Public License as published by
*  the Free Software Foundation, either version 3 of the License, or
*  (at your option) any later version.
*
*  SHIFT is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*  GNU General Public License for more details.
*
*  You should have received a copy of the GNU General Public License
*  along with SHIFT.  If not, see <http://www.gnu.org/licenses/>.
*
****************************************************************************/

import QtQuick 2.12
import QtQuick.Layouts 1.12
import QtQuick.Controls 2.12
import QtQuick.Controls.Material 2.12

Page 
{
	id: page
	title: "SHIFT2"

	Rectangle 
	{
    	id: display
    	height: page.height / 4
    	color: "#C0C0C0"
    	anchors.top: parent.top
    	anchors.right: parent. right
    	anchors.left: parent.left
    	anchors.margins: page.width / 10
    	Text 
		{
			font.pixelSize: page.width / 20
    		text: "Balance"
    	} 
    	Text 
		{
    		font.pixelSize: page.width / 5
    		text: 1130 - 60
    		anchors.centerIn: parent
    	} 
    	Text 
		{
    		anchors.right: parent. right
    		anchors. bottom: parent.bottom
			font.pixelSize: page.width / 20
    		text: "THX"
    	} 
	}

	Button 
	{
		id: pay
    	anchors.top: display.bottom
        anchors.left: parent.left
        anchors.right: parent.right
		anchors.leftMargin: page.width / 10
		anchors.rightMargin: page.width / 10
		height: page.height / 6
    	Material.background: Material.Green
		anchors.top: display.bottom
		anchors.left: display.left
    	Text 
		{
    		anchors.centerIn: parent
    		font.pixelSize: page.height / 30
    		color: "#ffffff"
    		text: "Create"
    	} 
    }
} 
