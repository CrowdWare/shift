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
import at.crowdware.backend 1.0
import com.lasconic 1.0

Page 
{
	id: page
	title: "HOME"

	ShareUtils 
	{
        id: shareUtils
    }

	Rectangle 
	{
    	id: display
    	height: page.height / 6
    	color: "#C0C0C0"
    	anchors.top: parent.top
    	anchors.right: parent.right
    	anchors.left: parent.left
    	anchors.margins: page.width / 10
    	Text 
		{
			font.pixelSize: page.width / 20
    		text: "Balance"
    	} 
    	Text 
		{
			id: balance
    		font.pixelSize: page.width / 5
    		text: backend.balance
    		anchors.centerIn: parent
    	} 
    	Text 
		{
    		anchors.right: parent. right
    		anchors. bottom: parent.bottom
			font.pixelSize: page.width / 20
    		text: "THX"
    	} 

		Timer 
		{
			id: timer
        	interval: 1000
			running: false 
			repeat: true
        	onTriggered: balance.text = backend.balance
    	}
	}

	Button 
	{
        id: start
    	anchors.top: display.bottom
        font.pointSize: 20
        anchors.margins: page.width / 10
     	anchors.left: parent.left
        anchors.right: parent.right
		height: page.height / 8
        text: backend.scooping == 0 ? "Start Scooping" : "Scooping..."
		enabled: backend.scooping == 0
		Material.background: Material.Green
		onClicked: 
		{
			timer.running = true;
			start.enabled = false;
			start.text = "Scooping...";
			backend.start();
			msg.text = "You are creating new liquid..." + backend.scooping + "/" + backend.balance;
		}
    }

	ScrollView 
	{
    	id: view
    	anchors.top: start.bottom
		anchors.margins: page.width / 10
     	anchors.left: parent.left
        anchors.right: parent.right
		height: page.height / 3
    	TextArea 
		{
			id: msg
        	text: "Hallo\nnice to see you again..." + backend.scooping + "/" + backend.balance
			readOnly: true
    	}
	}

	Button 
	{
        id: invite
    	anchors.top: view.bottom
        font.pointSize: 20
        anchors.margins: page.width / 10
     	anchors.left: parent.left
        anchors.right: parent.right
		height: page.height / 8
        text: "Invite Friends"
		onClicked: {
            shareUtils.share("My awesome text", "http://www.shifting.site")
        }
    }
} 
