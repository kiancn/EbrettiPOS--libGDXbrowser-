# EbrettiPOS
 An example of the libGDXbrowser in the shape an Ebretti Spare Part POS mock up

I made this browser to spice an assignment up. It is at a functional alpha stage.

To run the example with content, you need to change line 25 in the EbrettiPOS.java file: the String-attribute at line 25 defines the location of the partsCSV.txt file.
The CSV file is in the core/assets-folder in the project:
- The browser runs fine without the content, but it is a boring experience.

If you never looked at a libGDX application before, my advice is to start with 
<path on your drive>\desktop\src\kcn\libGDXbrowser\EbrettiPOS\desktop\DesktopLauncher.java
	and work your way into the heirarchy (ApplicationAdaptor, Browser, menu package, content package; and then EbrettiPOS.java, going to specific menus, content and buttons).

Class diagram:
![Class diagram](https://github.com/kiancn/EbrettiPOS--libGDXbrowser-/blob/master/Ebretti%20POS%20Class%20Diagram.png)

Project is built in IntelliJ and I have no idea if it will run (out of the box) in another IDE.

To import a libGDX project into IntelliJ, import whole project folder.
And to setup a run configuration, this video gives a clear example at linked time (~1 minute long):
https://youtu.be/tuVjurLVPO4?list=LLD3XdWDOYzZKtk7FW_XS0FA&t=128
(The first part of the video is about creating an empty project, NOT importing it )


Any further developments on the libGDXbrowser will appear in future projects. Already underway.
