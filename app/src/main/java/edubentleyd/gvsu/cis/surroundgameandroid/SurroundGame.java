package edubentleyd.gvsu.cis.surroundgameandroid;

/**
 * Created by DerrickBentley on 8/5/2015.
 */
/*********************************************************************
 *The Engine for the Surround game, checks win conditions, resets board
 * @author Derrick Bentley
 ********************************************************************/

public class SurroundGame {
    /** the 2d array of cells player numbers are stored */
    private Cell[][] board;
    /** each representing either alive or dead players (dead = -1) */
    private int[] alivePlayers;
    /** cycles through representing each players turns */
    private int player = 0;
    /** represents the total amount of players dead/alive. default 2*/
    private int totalPlayers = 2;
    /** represents the dimensions of the board. default 10 */
    private int boardSize = 7;
    /** creates the maximum number of players allowed in a game */
    final private int MAXPLAYERS = 5;
    /** creates the minimum number of players allowed in a game */
    final private int MINPLAYERS = 2;
    /** creates the maximum size the board can be */
    final private int MAXBOARDSIZE = 7;
    /** creates the minimum size the board can be */
    final private int MINBOARDSIZE = 3;

    /******************************************************************
     * used when first initiating the game. primarily uses the defaults
     *****************************************************************/
    public SurroundGame(){
        board = new Cell[boardSize][boardSize];
        alivePlayers = new int[totalPlayers];
        for(int row = 0; row < boardSize; row++)
            for(int col = 0; col < boardSize; col++)
                board[row][col] = new Cell();
        reset();
    }

    /******************************************************************
     * used primarily, called more often when additional options are
     * selected to bring in the optional values of other dimensions or
     * additional players
     * @param dimensions is the board size, length x width.
     * @param players is the total number of players
     *****************************************************************/
    public SurroundGame(int dimensions, int players, int startPlayer){
        //sets the boardsize to default if their input isn't proper
        if(dimensions >= MINBOARDSIZE && dimensions <= MAXBOARDSIZE)
            boardSize = dimensions;
        else
            boardSize = 7;
        //sets the players to default if the input is improper
        if(players < MINPLAYERS || players > MAXPLAYERS)
            totalPlayers = 2;
        else
            totalPlayers = players;
        board = new Cell[boardSize][boardSize];
        alivePlayers = new int[totalPlayers];
        for(int row = 0; row < boardSize; row++)
            for(int col = 0; col < boardSize; col++)
                board[row][col] = new Cell();
        //standardized reset, sets player to 0
        reset();
        //sets the starting player to 0 if the input is improper
        if(startPlayer < 1 || startPlayer > totalPlayers)
            player = 0;
        else
            player = startPlayer-1;
    }

    /******************************************************************
     * resets the entire array of cells, the cell.reset function makes
     * all their player numbers and defense levels back equal to 0.
     * also makes the array of alive players be 1 through total players
     * starting at 1. Also resets the initial player to 0. (1?)
     *****************************************************************/
    public void reset(){
        for(int row = 0; row < boardSize; row++)
            for(int col = 0; col < boardSize; col++)
                board[row][col].reset();
        //players are represented as n+1 of their position in the array
        for(int pl = 0; pl < totalPlayers; pl++)
            alivePlayers[pl] = pl+1;
        player = 0;
    }


	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
	 * These gets and sets are used when configuring, however do not
	 - immediately replace the board with the values. instead they are
	 * held and are inputed back into it is called to be created again.
	 *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    /******************************************************************
     * sets the total players to a different number
     * @param players is the desired total players.
     *****************************************************************/
    public void setTotalPlayers(int players){
        totalPlayers = players;
    }

    /******************************************************************
     * gets the total players number currently active.
     * @return totalPlayers is the total players.
     *****************************************************************/
    public int getTotalPlayers(){
        return totalPlayers;
    }
    /******************************************************************
     * Sets the board size of the game, dimension is length by height
     * @param dimension the dimensions of the board. z = z x z
     *****************************************************************/
    public void setBoardSize(int dimension){
        boardSize = dimension;
    }
    /******************************************************************
     * returns the size of the board
     * @return boardSize is the dimensions of the board.
     *****************************************************************/
    public int getBoardSize(){
        return boardSize;
    }
    /******************************************************************
     * set which player is currently up to have their turn.
     * @param player is the player whose turn is active
     *****************************************************************/
    public void setPlayer(int player){
        this.player = player;
    }
    /******************************************************************
     * returns the player who is up.
     * @return player is the player whose turn is active
     *****************************************************************/
    public int getPlayer(){
        return player;
    }
    /******************************************************************
     * Select method tells whether or not a given Cell in the 2dArray
     * of cells is currently 0, or has a player number. True if is 0;
     * If it is zero, it selects that cell, making it the player within
     * the alivePlayers array whose turn it is.
     * @param row is the Row within the 2d array
     * @param col is the Column within the 2d array
     * @return true if the given is 0
     *****************************************************************/
    public boolean select(int row, int col){
        if(board[row][col].getPlayerNumber() == 0){
            board[row][col].setPlayerNumber(alivePlayers[player]);
            return true;
        }
        return false;
    }

    /******************************************************************
     * returns the cell at the given location
     * @param row is the row number in the array of cells
     * @param col is the column number in the array of cells
     * @return the cell in the given position.
     ******************************************************************/
    public Cell getCell(int row, int col){
        return board[row][col];
    }

    /******************************************************************
     * loops through the array of alive players for the next player.
     * @return the player whose turn is next on the board.
     *****************************************************************/
    public int nextPlayer(){
        //uses x 0 through totalPlayers in order to cycle through all
        //the players once.
        for(int x = 0; x < totalPlayers; x++){
            //however the method checks using player, in order to give
            //precedence the order that the players were in when called
            if(player == totalPlayers-1)
                player = 0;
            else player++;
            if(alivePlayers[player] != -1)
                return alivePlayers[player];
        }
        return -1;
    }

    /******************************************************************
     * if a player is killed they are cleared from the board, having all
     * their previously captured cells remade to 0. Also their position
     * within the array is changed to -1, making them skipped over with
     * in the nextPlayer() method.
     * @param deadPlayer is the player that was surrounded.
     *****************************************************************/
    public void killPlayer(int deadPlayer){
        //player 5 is position 4 in the array, and is now -1
        alivePlayers[deadPlayer-1] = -1;
        for(int row = 0; row < boardSize; row++){
            for(int col = 0; col < boardSize; col++){
                //clears their number off the board.
                if (board[row][col].getPlayerNumber() == deadPlayer)
                    board[row][col].setPlayerNumber(0);
            }
        }
        //so that if a player dies and it was there turn next it doesn't
        //have an error or display an inproper player whos dead.
        if(player == deadPlayer-1)
            nextPlayer();

    }

    /******************************************************************
     * Does the original win condition step by step so that it can set
     * cells Defense Levels depending on how many steps into the actual
     * win condition an individual piece is in.
     * @return 0 if no one is fully surrounded, or the surrounded pieces
     * player number.
     ******************************************************************/
    public int isWinner(){
        int count = 0;
        for(int row = 0; row < boardSize; row++){
            for(int col = 0; col < boardSize; col++){
                count = 0;
                board[row][col].setDefenseLevel(0);
                // n%x == (n%x+x)%x for negatives. Source= StackOverFlow
                int selected = board[row][col].getPlayerNumber();
                int testing1 = board[(row+1%boardSize+boardSize)%
                        boardSize][col].getPlayerNumber();
                int testing2 = board[(row-1%boardSize+boardSize)%
                        boardSize][col].getPlayerNumber();
                int testing3 = board[row][(col+1%boardSize+boardSize)%
                        boardSize].getPlayerNumber();
                int testing4 = board[row][(col-1%boardSize+boardSize)%
                        boardSize].getPlayerNumber();
                //Each cardinal direction enemy piece increments count
                if(selected != 0 && testing1 != 0&& testing1!=selected){
                    count++;
                    board[row][col].incDefenselevel();
                }
                if(selected!= 0 && testing2 != 0 && testing2!=selected){
                    count++;
                    board[row][col].incDefenselevel();
                }
                if(selected!=0 && testing3!= 0 && testing3!=selected){
                    count++;
                    board[row][col].incDefenselevel();
                }
                if(selected!=0&&testing4!= 0 && testing4!=selected){
                    count++;
                    board[row][col].incDefenselevel();
                }
                //if count got to 4 then it is surrounded on all 4 sides
                if(count == 4){
                    return selected;
                }
                //no matter how many are surrounding it, none matter if
                //the piece can't be surrounded at all, IE an ally piece
                //is touching it, so defense is set back to 0
                if(selected == testing1 || selected == testing2 ||
                        selected == testing3 || selected == testing4)
                    board[row][col].setDefenseLevel(0);
            }
        }
        return 0;
    }

    /******************************************************************
     * For simplicity, created another method to determine if all the
     * players within the Alive Player array are all (except 1) are -1
     * @return true if all but one player is dead.
     *****************************************************************/
    public boolean foundAWinner(){
        int count = 0;
        for(int i = 0; i < totalPlayers; i++){
            if(alivePlayers[i] == -1){
                count++;
            }
        }
        //if the number of dead players is 1 minus the total players.
        return count == totalPlayers-1;
    }

    /******************************************************************
     * used when a winner is found to return the value of the only alive
     * player left within the Alive Player array
     * @return the -1 left in the game
     ******************************************************************/
    public int whoWon(){
        for(int i = 0; i < totalPlayers; i++){
            if(alivePlayers[i] != -1){
                //returns first non dead player
                return alivePlayers[i];
            }
        }
        return 0;
    }
}
