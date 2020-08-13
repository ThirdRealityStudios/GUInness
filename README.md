A simple tool to draw graphical objects and create GUI environments.

If you want to test this API first, you can just download the binaries ("bin"-folder) and run the Main.class file.
It will give you a look through all the features which are available currently.
Don't get too frustrated by the old graphics as you can actually overwrite the whole design easily.
Currently, the focus of the development goes on solidity and stability before anything else.


# History
This API was originally written first because I wanted to build a solid structure in order
to build 2D games more easily.
At all I want to build a simple strategy game which probably lacks of graphics but is rich in terms of features.
I could have used available game engines from the web but my plan was it to build everything up from scratch,
so that's the challenge for me.


# Who is this API for?
As you read above the API is mainly being developed for simple 2D games which do not require such an efficient rendering API.
GUInness already uses the "paintMethod(Graphics g)"-method in order to realize the displaying of components, graphics and more.
As you probably know, that's not the approach for AAA-Games, nor you can expect high-resoluted graphics.
Anyway, the limits of GUInness and Swing have not been reached yet in terms of rendering and quality.
If you just want to build a simple GUI which is easy-to-use and fast-to-build then you are also defintely right here.
GUInness is also an alternative to the yet largely-used Swing library, meaning the components it offers.
Of course, there are still a lot of features to be added in order to be at least a little competetive.
Last but not least GUInness is especially suitable for Java beginners due to the simple structure.
You also do not need that much background knowledge like with Swing.


# Features
- GUI components organized in "layers" with priorities (kind of comparable to HTML).
- all GUI components are very easy-to-use in terms of methods and attributes.
- dozens of very detailed error logs and comments, amongst them for better and quicker maintenance (even advices).
- simple way to create you own design / look (by just using the classic design ("Classic.java") as a base).
- capable of multi-threading.


# Component Features
- Texts
- Buttons
- Text-fields
- Images/pictures
- Rectangles


# Runtime Features
- Components tasks / actions can be executed by enabling "multi-threading" per object method.


# Stability and solidity first
As already mentioned, the main goal is it to keep and improve the stability.
For the solidity it is the case that the code is tried to be kept as small and clean as possible.
It is also important for me not to get messy with the code structure and to deliver enough comments and error messages for best maintenance.
If these points are also really guaranteed the next step would it be theoretically to add a new feature.


# Planed features
- add paths
- add checkboxes
- add combo boxes
- add video components
- add simple background sound tracks.
- add complex buttons: a button which has a complex shape.
- add scalable content: components can be zoomed in/out with different levels of detail (especially for creating maps or games).
- add hyperlinks to open links on the web.
