# EasyDraw
A simple tool to draw and use predefined GUI components.


# History
This API was originally written first because I wanted to build a solid structure in order
to build 2D games more easily.
Because the UI was the first thing for me before I could go on this project was unavoidable.
In future,
EasyDraw should help me to create a simple 2D strategy game which is deprecated in terms of graphics
but it should offer a wide range of features,
including political strucures, diplomacy, limited resources, global markets, wars and countries.
It should be based on a multiplayer concept so every country is actually represented by a player.
The player can hire ministers (also players) and is the head the country.
He / She can change the governmental form of rule,
e.g. to dictatorship or to a true democrcacy.
At least that's plan.


# Component Features
- Texts
- Buttons
- Text-fields
- Images/pictures
- Paths
- Rectangles


# Runtime Features
- Components tasks can be executed using threads (real-time mode)
- Draw context can be run in "Gaming Mode"
- "Gaming Mode" is a new feature which just infinitely executes the repaint-method as fast as possible.
- The other way around (turned off) the draw context is only redrawn on specific events to reduce CPU usage.


# CPU Usage
- Gaming Mode on: >85%
- Gaming Mode off: <60%


# Efficiency first
The main goal is it to reduce the CPU usage and other time intensive processes.
Before a new feature is introduced,
it is tried as best as possible to work on the efficiency.


# Planed features
- add video components
- add simple background sound tracks.
- add complex buttons: a button which has a complex shape.
- add scalable content: components can be zoomed in/out with different levels of detail (especially for creating maps or games).
- add hyperlinks to open links on the web.
