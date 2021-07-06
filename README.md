# Pack-man-Navigation-

In our task we have some mainly classes which:

1)Dgraph - this class is include vertexes and edges that build the graph ,
		node is contain id and a point(x,y)
		Edge is describe with src (source node) and dest (the destnation of the source node 1 ---> 2)
		for each edge has a wighet that repressent the cost to arrive from one node to another
		
2)algorithms - this class is include a graph , the algorithm class , is made to calculate 
		destations between 2  diffrent points ( x -- > y ) , by using dijkstra algorithm ,
		check if the graph is connected using defintion of connected graph , check if there is a 
		valid path from EVREY node to each other node
		
		
3)MyGameGui- this class is exectuing and make actions that show to the user,
	         the graph on screen by paint functions by getting information 
	         from the server ,and print it to screen ,
	        by manual using has the abillty move the robots at screen and	
	        move them throught the fruits , and by eating these fruits 
	        the user accumulatting score.
	        MyGameGui is includding :X,Y Min/Max for scales
                         , play Class , x and y values;
			 
			 
4)Play- This class repressents the whole part that given from the server and send it 
             to MyGameGui for reading from server objects and make using at the class(myGameGui).
	the paly class is incldue: robot ,fruits , and grpah fildes


![image](https://user-images.githubusercontent.com/57701623/124603436-9ad1a280-de72-11eb-8fc6-cc82ce9cdea8.png)
