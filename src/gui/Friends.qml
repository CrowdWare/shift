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

Page 
{
	id: page
	title: "Mates"

	Rectangle 
	{
		id: list
		anchors.top: parent.top
		anchors.margins: page.width / 10
     	anchors.left: parent.left
        anchors.right: parent.right
		height: page.height - page.width / 5
	    color: "#EEEEEE"
	   	ListView 
		{
	   		clip: true
	    	anchors.fill: parent
	   		anchors.margins: page.width / 100
	   		spacing: page.width / 100
			model: backend.mateModel
			delegate: listDelegate
	    	
	   		Component 
			{
	   			id: listDelegate

	   			Rectangle 
				{
	 				width: parent.width 
	   				height: page.height / 20
					
					Image 
            		{
						id: icon
						visible: model.scooping > 0
                		width: 32
                		height: 32
                		fillMode: Image.PreserveAspectFit
                		source: "qrc:/images/checked.png"
					}
					
	   				Text 
					{
	   					id: name
						height: parent.height
						anchors.left: icon.right
	   					anchors.leftMargin: 5
	   					text: model.name
						verticalAlignment: Text.AlignVCenter
	   					font.pixelSize: page.height / 40
	   				}  
	    		} 
	    	} 
        }
	}
}