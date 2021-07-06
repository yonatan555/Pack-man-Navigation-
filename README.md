# Pack-man-Navigation-

This Project has direct connection to the previous task.

In our task we have 3 mainly classes which:

1)stdDraw- we are using in this class to paint the data in the screen, 
	  and also to make connection between myGameGUI AND play,
	  by theard that getting info from the user if to execute manual or auto 
	  using at the graph that choosen by the user.

2)MyGameGui- this class is exectuing and make actions that show to the user,
	         the graph on screen by paint functions by getting information 
	         from the server ,and print it to screen ,
	        by manual using has the abillty move the robots at screen and	
	        move them throught the fruits , and by eating these fruits 
	        the user accumulatting score.
	        MyGameGui is includding :X,Y Min/Max for scales
                         , play Class , x and y values;
3)Play- This class repressents the whole part that given from the server and send it 
             to MyGameGui for reading from server objects and make using at the class(myGameGui).
	the paly class is incldue: robot ,fruits , and grpah fildes
