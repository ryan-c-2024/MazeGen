import java.lang.Math;

public class MazeGenerator {

	private UnionFind uf;

    public void run(int n) {

	// creates all cells
	Cell[][] mazeMap = new Cell[n][n];
	initializeCells(mazeMap);

	// create a list of all internal walls, and links the cells and walls
	Wall[] walls = getWalls(mazeMap);

	createMaze(walls, mazeMap);

	printMaze(mazeMap);

    }

    boolean validToRemove(Wall w)
	{
		Cell a = w.first;
		Cell b = w.second;

		if (w.visible == true && a != null && b != null)
		{
			if (uf.find(a) != uf.find(b)) return true;
			else
			{
				return false;
			}
		}
		return false;
	}

    public void createMaze(Wall[] walls, Cell[][] mazeMap) {
	// FILL IN THIS METHOD
		uf = new UnionFind(); // initialize our union_find class

		// Create set for all cells in advance to simplify things
		for (Cell[] cell_array : mazeMap)
		{
			for (Cell c : cell_array)
			{
				uf.makeset(c);
			}
		}

		int max_wall_ind = walls.length - 1; // largest index a wall can have
		int counter = mazeMap.length * mazeMap.length - 1;

		// for each wall (randomly picked)
		while (counter > 0)
		{
			// pick random wall to remove
			int rand_ind = (int)(Math.random()*(max_wall_ind-0+1)+0);
			Wall rand_wall = walls[rand_ind];

			// if eligible to remove (ie hasnt been removed yet, won't mess up maze)
			if (validToRemove(rand_wall))
			{
				// remove wall and add to set so it cant be removed again
				rand_wall.visible = false;
				uf.union(rand_wall.first, rand_wall.second);
				counter--;
			}

		}

		int max_index = mazeMap.length-1;
		mazeMap[0][0].left.visible = false; // create top left entrance
		mazeMap[max_index][max_index].right.visible = false; // create bottom right entrance
    }


    // print out the maze in a specific format
    public void printMaze(Cell[][] maze) {
	for(int i = 0; i < maze.length; i++) {
	    // print the up walls for row i
	    for(int j = 0; j < maze.length; j++) {
		Wall up = maze[i][j].up;
		if(up != null && up.visible) System.out.print("+--");
		else System.out.print("+  ");
	    }
	    System.out.println("+");

	    // print the left walls and the cells in row i
	    for(int j = 0; j < maze.length; j++) {
		Wall left = maze[i][j].left;
		if(left != null && left.visible) System.out.print("|  ");
		else System.out.print("   ");
	    }

	    //print the last wall on the far right of row i
	    Wall lastRight = maze[i][maze.length-1].right;
	    if(lastRight != null && lastRight.visible) System.out.println("|");
	    else System.out.println(" ");
	}

	// print the last row's down walls
	for(int i = 0; i < maze.length; i++) {
	    Wall down = maze[maze.length-1][i].down;
	    if(down != null && down.visible) System.out.print("+--");
	    else System.out.print("+  ");
	}
	System.out.println("+");


    }

    // create a new Cell for each position of the maze
    public void initializeCells(Cell[][] maze) {
	for(int i = 0; i < maze.length; i++) {
	    for(int j = 0; j < maze[0].length; j++) {
		maze[i][j] = new Cell();
	    }
	}
    }

    // create all walls and link walls and cells
    public Wall[] getWalls(Cell[][] mazeMap) {

	int n = mazeMap.length;

	Wall[] walls = new Wall[2*n*(n+1)];
	int wallCtr = 0;

	// each "inner" cell adds its right and down walls
	for(int i = 0; i < n; i++) {
	    for(int j = 0; j < n; j++) {
		// add down wall
		if(i < n-1) {
		    walls[wallCtr] = new Wall(mazeMap[i][j], mazeMap[i+1][j]);
		    mazeMap[i][j].down = walls[wallCtr];
		    mazeMap[i+1][j].up = walls[wallCtr];
		    wallCtr++;
		}
		
		// add right wall
		if(j < n-1) {
		    walls[wallCtr] = new Wall(mazeMap[i][j], mazeMap[i][j+1]);
		    mazeMap[i][j].right = walls[wallCtr];
		    mazeMap[i][j+1].left = walls[wallCtr];
		    wallCtr++;
		}
	    }
	}

	// "outer" cells add their outer walls
	for(int i = 0; i < n; i++) {
	    // add left walls for the first column
	    walls[wallCtr] = new Wall(null, mazeMap[i][0]);
	    mazeMap[i][0].left = walls[wallCtr];
	    wallCtr++;

	    // add up walls for the top row
	    walls[wallCtr] = new Wall(null, mazeMap[0][i]);
	    mazeMap[0][i].up = walls[wallCtr];
	    wallCtr++;

	    // add down walls for the bottom row
	    walls[wallCtr] = new Wall(null, mazeMap[n-1][i]);
	    mazeMap[n-1][i].down = walls[wallCtr];
	    wallCtr++;

	    // add right walls for the last column
	    walls[wallCtr] = new Wall(null, mazeMap[i][n-1]);
	    mazeMap[i][n-1].right = walls[wallCtr];
	    wallCtr++;
	}


	return walls;
    }


    public static void main(String [] args) {
	if(args.length > 0) {
	    int n = Integer.parseInt(args[0]);
	    new MazeGenerator().run(n);
	}
	else new MazeGenerator().run(5);
    }

}