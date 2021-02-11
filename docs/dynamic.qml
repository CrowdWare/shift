
import QtQuick 2.12
import QtQuick.Layouts 1.12
import QtQuick.Controls 2.12
import QtQuick.Controls.Material 2.12
import at.crowdware.backend 1.0

Page 
{
	id: page
	title: "Diaspora"

	Connections
	{
		target: backend
		onResultChanged: 
		{
			caption.text = backend.result;
		}
	}

	Text 
	{	
		id: caption	
		anchors.top: parent.top
		anchors.left: parent.left
		anchors.right: parent.right
	   	text: "DIASPORA is a sample plugin."
	   	font.pixelSize: page.height / 40
		anchors.topMargin: page.width / 20
		anchors.leftMargin: page.width / 10
		anchors.rightMargin: page.width / 10
		wrapMode: Text.WordWrap
	}

	Button
	{
		anchors.bottom: parent.bottom
                font.pointSize: 20
		anchors.leftMargin: page.width / 10
		anchors.rightMargin: page.width / 10
		anchors.bottomMargin: page.width / 20
     	        anchors.left: parent.left
                anchors.right: parent.right
		height: page.height / 8
                text: "CLICK ME"
		Material.background: Material.Blue
		
		onClicked: backend.HttpGet("http://artanidosatcrowdwareat.pythonanywhere.com/")
	}
}
