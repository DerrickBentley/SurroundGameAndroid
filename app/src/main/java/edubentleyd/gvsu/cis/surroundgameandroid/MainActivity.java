package edubentleyd.gvsu.cis.surroundgameandroid;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.MenuView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

    /** is the starting player */
    int startPlayer;
    /** is the number of players in the game. */
    int players;
    /** puts all the XML buttons into an int array to be referenced easier */
    private static final int[] BUTTONS = {
            R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6,
            R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12,
            R.id.button13, R.id.button14, R.id.button15, R.id.button16, R.id.button17,
            R.id.button18, R.id.button19, R.id.button20, R.id.button21, R.id.button22,
            R.id.button23, R.id.button24, R.id.button25, R.id.button26, R.id.button27,
            R.id.button28, R.id.button29, R.id.button30, R.id.button31, R.id.button32,
            R.id.button33, R.id.button34, R.id.button35, R.id.button36, R.id.button37,
            R.id.button38, R.id.button39, R.id.button40, R.id.button41, R.id.button42,
            R.id.button43, R.id.button44, R.id.button45, R.id.button46, R.id.button47,
            R.id.button48, R.id.button49 };
    /** is the button array referencing BUTTONS */
    Button[][] board;
    /** initiates the actual game to be played */
    private SurroundGame game;
    /** gets the initial board size from the game */
    private int bdsize;
    /** reset button */
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new SurroundGame();
        reset = (Button)findViewById(R.id.reset);
        reset.setOnClickListener(this);
        drawBoard();
        displayBoard();
    }

    /******************************************************************
     * creates a new game board according to size and players and players
     * makes the 2d button array for the appropriate size given, and adds
     * the action listener as lets them join the panel.
     *
     * Variables players, bdsize, and startPlayer take the values of the
     * previous game and sets them into the new game, as it creates an
     * entire new instance of Game upon resetting the board,
     *****************************************************************/
    private void drawBoard(){
        //gets the values set into the game by the user, good or bad.
        players = game.getTotalPlayers();
        bdsize = game.getBoardSize();
        startPlayer = game.getPlayer();
        //creates the game according to those variables
        game = new SurroundGame(bdsize, players, startPlayer);

        players = game.getTotalPlayers();
        bdsize = game.getBoardSize();
        startPlayer = game.getPlayer();
        board = new Button[bdsize][bdsize];
        int i = 0;
        for(int row = 0; row < bdsize; row++){
            for(int col = 0; col < bdsize; col++){
                    board[row][col] = (Button) findViewById(BUTTONS[i++]);
                    board[row][col].setOnClickListener(this);
                    board[row][col].invalidate();
            }
        }
    }

    /******************************************************************
     * sets the 2d array of buttons to the appropriate values within the
     * cells (the player numbers) and displays the buttons as a color
     * according to their defense levels. yellow being level 1, orange
     * is level 2, and red is level 3. 4 and the piece is already
     * surrounded.
     *****************************************************************/
    public void displayBoard(){
        //checks for the winner so that the game can set warning levels
        int loser = game.isWinner();
        for(int row = 0; row < bdsize; row++){
            for(int col = 0; col < bdsize; col++){
                Cell c = game.getCell(row,col);
                //doesn't care about warning levels of pieces not taken
                if(c.getPlayerNumber() != 0){
                    board[row][col].setText("" + c.getPlayerNumber());
                    if(c.getDefenseLevel() == 0)
                        //blue
                        /*board[row][col].getBackground()
                                .setColorFilter(0x00FFFF, PorterDuff.Mode.MULTIPLY);*/
                        board[row][col].setBackgroundResource(android.R.drawable.btn_default);
                    if(c.getDefenseLevel() == 1)
                        //green
                        board[row][col].setBackgroundColor(Color.GREEN);
                    if(c.getDefenseLevel() == 2)
                        //yellow
                        board[row][col].setBackgroundColor(Color.YELLOW);
                    if(c.getDefenseLevel() == 3)
                        //red
                        board[row][col].setBackgroundColor(Color.RED);
                }
                else{
                    board[row][col].setText("");
                    //background "null" is the default button "color"
                    board[row][col].setBackgroundResource(android.R.drawable.btn_default);
                }
                board[row][col].invalidate();
            }
        }
        //checks the game if a player is being surrounded
        if(loser!=0){
            //JOptionPane.showMessageDialog(null,"Player " + loser + " Loses!");
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Attention");
            game.killPlayer(loser);
            //if indeed a player was killed we are going to check if
            //there are anymore remaining players playing the game
            if(game.foundAWinner()){
                //if there was only 1 player alive, we restart the game
                //JOptionPane.showMessageDialog(null, game.whoWon() + " " + "Player won!");
                game.reset();
                drawBoard();
                alertDialog.setMessage("Player " + game.whoWon() + " Won!");
            }
            else
                alertDialog.setMessage("Player " + loser + " Eliminated");
            alertDialog.show();
            //redraws the blank board once reset
            displayBoard(); //recursion?
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.quitButton) {
            System.exit(1);
            return true;
        }
        if (id == R.id.helpButton) {
            AlertDialog helpDialog = new AlertDialog.Builder(MainActivity.this).create();
            helpDialog.setTitle("Info.");
            helpDialog.setMessage("Surround opposing players from the north, south, east and west" +
                    "in order to eliminate them. Be the last player alive to win!");
            helpDialog.show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        // Determine which button was selected.
        for (int row = 0; row < bdsize; row++)
            for (int col = 0; col < bdsize; col++)
                if (board[row][col].getId() == id) {
                    //checks if the button is able to be gotten
                    if (game.select(row, col)) {
                        //if the button was available, that player
                        //selects the button.
                        game.nextPlayer();
                    }
                }
        if(v == reset){
            game.reset();
            drawBoard();
        }
        //after any event on the board we'll update the graphics.
        displayBoard();
    }
}
