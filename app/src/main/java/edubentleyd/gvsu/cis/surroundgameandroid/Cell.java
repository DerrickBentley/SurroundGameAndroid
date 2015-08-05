package edubentleyd.gvsu.cis.surroundgameandroid;

/**
 * Created by DerrickBentley on 8/5/2015.
 */
public class Cell {
    /** represents which player has current control of the cell */
    private int playerNumber;
    /** represents how close the cell is to actually being captured */
    private int defenseLevel;

    public Cell() {
        //this.playerNumber = playerNumber;
    }

    /******************************************************************
     * returns the player that has this cell
     * @return player who owns this cell.
     *****************************************************************/
    public int getPlayerNumber() {
        return playerNumber;
    }
    /******************************************************************
     * used when a player captures the cell and places his number in the
     * cell
     * @param player is the player that captured it
     *****************************************************************/
    public void setPlayerNumber(int player){
        this.playerNumber = player;
    }

    /*****************************************************************
     * is the number of squares around a cell that can potentially
     * help in its surround. 4 being completely surrounded, and 0 being
     * by itself, it is unable to be captured
     * @return 0 to 4 how close it is to getting captured
     *****************************************************************/
    public int getDefenseLevel(){
        return defenseLevel;
    }
    /******************************************************************
     * used to set the defense level variable, used as to set it to 0.
     * @param defenseLevel inputs the number of squares it has
     * surrounding it
     ******************************************************************/
    public void setDefenseLevel(int defenseLevel){
        this.defenseLevel = defenseLevel;
    }
    /******************************************************************
     * Used to increase the defense level by 1. Typically when a player
     * moves directly next to a piece abled to be captured.
     ******************************************************************/
    public void incDefenselevel(){
        this.defenseLevel++;
    }

    /******************************************************************
     * resets the player numbers within the cell and its defense level
     * back to 0
     ******************************************************************/
    public void reset(){
        playerNumber = 0;
        defenseLevel = 0;
    }
}
