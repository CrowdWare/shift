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
    		text: Number(backend.balance / 1000.0).toLocaleString(Qt.locale("de_DE"), 'f', 3)
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
			running: backend.scooping == 0 ? false : true 
			repeat: true
        	onTriggered: balance.text =  Number(backend.balance / 1000.0).toLocaleString(Qt.locale("de_DE"), 'f', 3)
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

	Text 
	{
		id: caption
		anchors.top: start.bottom
		anchors.left: start.left
		anchors.topMargin: page.height / 100
		text: "Latest Bookings"
	} 

	Rectangle 
	{
		id: list
		anchors.top: start.bottom
		anchors.margins: page.width / 10
     	anchors.left: parent.left
        anchors.right: parent.right
		height: page.height / 3
	    color: "#EEEEEE"
	   	ListView 
		{
	   		clip: true
	    	anchors.fill: parent
	   		anchors.margins: page.width / 100
	   		spacing: page.width / 100
	    	
	    	delegate: listDelegate
	    	
	   		Component 
			{
	   			id: listDelegate
	    		
	   			Rectangle 
				{
	 				width: parent.width 
	   				height: page.height / 20
	   				Text 
					{
	   					id: date
	   					text: model.date
	   					font.pixelSize: page.height / 40
	   				} 
	   				Text 
					{
	   					anchors.left: date.right
	   					anchors.leftMargin: 15
	   					text: model.text 
	   					font.pixelSize: page.height / 40
	 				} 
	 				Text 
					{
						anchors.right: parent.right
	 					text: model.amount + " THX"
	 					font.pixelSize: page.height / 40
	 				} 
	    		} 
	    	} 
	    	
	   		model: ListModel 
			{
                ListElement 
				{
                	date: "23.04.2020"
               	   	text: "Liquid created"
               	   	amount: 10
       	       	}
               	ListElement 
				{
               		date: "22.04.2020"
               		text: "Liquid created"
               		amount: 10
       	       	}
              	ListElement 
				{
              		date: "21.04.2020"
               		text: "Liquid created"
               		amount: 10
       	      	}
       	      	ListElement 
				{
              		date: "21.04.2020"
               		text: "Payment"
               		amount: - 60
       	      	}
       	      	ListElement 
				{
              		date: "21.04.2020"
               		text: "Massage"
               		amount: 90
       	      	}
       	      	ListElement 
				{
              		date: "20.04.2020"
               		text: "Liquid created"
               		amount: 10
       	      	}
				ListElement 
				{
              		date: "19.04.2020"
               		text: "In App Purch."
              		amount: 1000
       	      	}	
	   		}
        }
	}
	/*
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
	*/
	Button 
	{
        id: invite
    	anchors.top: list.bottom
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
