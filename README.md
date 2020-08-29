A simple tool to draw graphical objects and create custom GUI environments.

If you want to test this API first, you can just download the binaries ("bin"-folder) and run the Main.class file.
It will give you a look through all the features which are available currently.
Don't get too frustrated by the old graphics as you can actually overwrite the whole design easily.
Currently, the focus of the development goes on solidity and stability before anything else.


# First Impression
![Application sample](https://i.ibb.co/wYDhDDP/Screenshot-from-2020-08-21-16-22-50.jpg)


# History
This API was originally written first because I wanted to build a solid structure in order
to build 2D games more easily.
At all I want to build a simple strategy game which probably lacks of graphics but is rich in terms of features.
I could have used available game engines from the web but my plan was it to build everything up from scratch,
so that's the challenge for me.


# Who Is This API For?
As you read above the API is mainly being developed for simple 2D games which do not require such an efficient rendering API.
GUInness already uses the "paintMethod(Graphics g)"-method in order to realize the displaying of components, graphics and more.
As you probably know, that's not the approach for AAA-Games, nor you can expect high-resoluted graphics.
Anyway, the limits of GUInness and Swing have not been reached yet in terms of rendering and quality.
If you just want to build a simple GUI which is easy-to-use and fast-to-build then you are also defintely right here.
GUInness is also an alternative to the yet largely-used Swing library, meaning the components it offers.
Of course, there are still a lot of features to be added in order to be at least a little "competetive".
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
- Textfields
- Checkboxes
- Selection-boxes (with radio buttons)
- Images/pictures
- Rectangles


# Runtime Features
- Components tasks / actions can be executed by enabling "multi-threading" per object method.


# Stability And Solidity First
As already mentioned, the main goal is it to keep and improve the stability.
For the solidity it is the case that the code is tried to be kept as small and clean as possible.
It is also important for me not to get messy with the code structure and to deliver enough comments and error messages for best maintenance.
If these points are also really guaranteed the next step would it be theoretically to add a new feature.


# Planed Features
- add paths
- add combo boxes
- add video components
- add simple background sound tracks.
- add complex buttons: a button which has a complex shape.
- add scalable content: components can be zoomed in/out with different levels of detail (especially for creating maps or games).
- add hyperlinks to open links on the web.


# Documentation
Will follow at the final release because several things / features can still change "fundamentally".
This means, it isn't the effort worth until now to note every single change.
Anyway, there are of course dozens of comments and a sample application (Main.java) for better understanding.
It really shouldn't lack of brief comments in the code actually..👍


# Stats / Nice To Know (as of Aug 15 2020)
- Total lines of code: 2515
- Code size: ~85 kB
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/bdca864677bc462ab2d2f97a09de45fa)](https://app.codacy.com/manual/ThirdRealityStudios/GUInness?utm_source=github.com&utm_medium=referral&utm_content=ThirdRealityStudios/GUInness&utm_campaign=Badge_Grade_Dashboard)
- Rating of code by Codacy: [![Codacy Badge](https://app.codacy.com/project/badge/Grade/ce99cd3f8f5242e0944f216bf975edb8)](https://www.codacy.com/manual/SucukiFarmer/GUInness?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=SucukiFarmer/GUInness&amp;utm_campaign=Badge_Grade)


# Donations (Via LiteCoin)
If you maybe have some LiteCoins left and you wouldn't mind to support this project,
you can do so with this wallet address:

MNh6pVYPHW21EtsAEgTfPBAYDXfQKEM2n6

or

litecoin:MNh6pVYPHW21EtsAEgTfPBAYDXfQKEM2n6?label=Donation