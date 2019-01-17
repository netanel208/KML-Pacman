# Ex4 
This file is intended to show and explain the purpose of the project, what it does, and what capabilities it gives to those who use it.
This project represents a Pacman-style game. There are pacmas and fruits. The purpose of the pacmas is to eat all the fruits on the board.
The game board is a map of Ariel University when each pixel point on the map is mapped to a GPS point.
### The progress of the game:
There is a menu that will allow you to upload a game file (csv) by clicking on file menu and then you need to click on "Add csv" and the File Chooser appears on the screen .<br />
![alt text](http://imagizer.imageshack.com/img924/4943/eXGgwu.png)

Another option is to create a game with the click of the mouse to put pacman and fruit on the screen, you can switch between them in the "Element" menu . then you need to save the game file.
After you chose one of this option you need to run the game and there is 2 option

### System structure 
1. GIS 
Elements that contain two types : Pacmans and fruit . They have common characteristics such as location, id. And various characteristics, for example fruit has weight and Pacmans has speed and eating radius.
2. MyFrame
A graphical class that allows robots and fruits to be displayed on the map, showing the activity of the algorithms on the board ( by eating the fruits that are in the Pacmans path and displays the path routes using lines. )
3. File format
This package is about File Conversion. its capabilities  to create a game from a csv file and convert from a game object to a csv file .
Another class, Convert2Kml, converts a game file into a kml file with time signatures to allow viewing in Google Earth with the time when the fruit was eaten by the Pacmans.
4. coords 
A basic coordinate system converter. This package supports this method:
find the 3D vector between two lat,lon, alt points ; Adding a 3D vector in meters to a global point; convert a 3D vector from meters to polar coordinates; Calculate distance 

### Automated system and comparison of results
for this information go to WIKI page : https://github.com/netanel208/Ex4/wiki
<br />
<br />
<br />
<br />

In converting from Java to kml we used Jak library: https://labs.micromata.de/projects/jak.html 
