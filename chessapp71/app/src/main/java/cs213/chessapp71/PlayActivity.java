package cs213.chessapp71;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Steven on 11/30/2017.
 */
public class PlayActivity extends AppCompatActivity implements OnClickListener
{
    public String tag1 = null;
    public String tag2 = null;
    public String temp = null;
    public String curColor = "White";
    public ArrayList<String> movesMade = new ArrayList<>();
    public boolean hasUndone = false;
    protected TableLayout chessBoardLayout;
    protected TextView playersTurn;
    protected Button undoButton;
    protected Button drawButton;
    protected Button aiButton;
    int itemsSelected = 0;
    Board chessBoard;
    private String m_Text;
    /**
     * @param s String that is either black or white
     * @return String that is the opposite of the color it was passed. Returns white if s was black, and black if s was white
     */
    private static String flipColor(String s)
    {
        if(s.equals("White"))
            return "Black";
        return "White";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        chessBoardLayout = (TableLayout) findViewById(R.id.chessBoardLayout);
        playersTurn = (TextView) findViewById(R.id.playerTurnText);
        chessBoardLayout.setOnClickListener(PlayActivity.this);
        chessBoard = new Board();
        undoButton = (Button) findViewById(R.id.undoButton);
        drawButton = (Button) findViewById(R.id.drawButton);
        aiButton = (Button) findViewById(R.id.aiButton);

        aiButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                aiMove(curColor);
            }
        });


        undoButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!hasUndone && movesMade.size() > 0)
                {
                    movesMade.remove(movesMade.size() - 1);
                    playThrough();
                    // curColor = flipColor(curColor);
                    playersTurn.setText(curColor + "'s move");
                    updateBoard();
                    hasUndone = true;
                }
                else
                {
                    Snackbar mySnackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.undoError), 1000);
                    mySnackbar.show();
                }
            }
        });
        drawButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                askDraw();
            }
        });
    }
    public void askDraw()
    {
        MyDrawDialogFragment myDiag = new MyDrawDialogFragment();
        myDiag.populateArray(movesMade);
        myDiag.winner(curColor);
        myDiag.show(getFragmentManager(), "Diag");
    }
    public void resignGame(View view)
    {
        MyDialogFragment myDiag = new MyDialogFragment();
        myDiag.populateArray(movesMade);
        curColor = flipColor(curColor);
        myDiag.winner(curColor);
        myDiag.show(getFragmentManager(), "Diag");
        if(myDiag.getResign())
        {
            //Code saying the current player resigned
        }
        else
        {
            //Continue
        }
    }
    public void highlight(View v)
    {
        try
        {
            temp = tag1;
            tag1 = v.getTag().toString();
            if(tag1.equals(tag2))
            {   // Double-clicking same piece cancels out selection
                v.setBackground(null);
                tag1 = "";
                tag2 = "";
                temp = "";
                itemsSelected = 0;
            }
            else
            {
                Drawable highlight = getResources().getDrawable(R.drawable.highlight);
                v.setBackground(highlight);
                if(itemsSelected == 1)
                {
                    ImageView imageView = (ImageView) chessBoardLayout.findViewWithTag(tag2);
                    ImageView imageView2 = (ImageView) chessBoardLayout.findViewWithTag(tag1);
                    //Drawable i = imageView.getDrawable();
                    // Drawable j = imageView2.getDrawable();
                    // imageView.setImageDrawable(j);
                    // imageView2.setImageDrawable(i);
                    imageView.setBackground(null);
                    imageView2.setBackground(null);
                    play(tag1, tag2);
                    playersTurn.setText(curColor + "'s move");
                    itemsSelected = 0;
                    tag1 = "";
                    tag2 = "";
                    temp = "";
                }
                else
                {
                    tag2 = v.getTag().toString();
                    itemsSelected++;
                }
            }
        }
        catch(Exception c)
        {
        }
    }
    @Override
    public void onClick(View v)
    {
        highlight(v);
    }
    public void updateBoard()
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                TableRow row = (TableRow) chessBoardLayout.getChildAt(i);
                ImageView img = (ImageView) row.getChildAt(j);
                if((chessBoard.board[i][j]) == null)
                {
                    img.setImageDrawable(null);
                }
                else if((chessBoard.board[i][j]).toString().contains("wP"))
                {
                    img.setImageResource(R.drawable.whitepawn);
                }
                else if((chessBoard.board[i][j]).toString().contains("bP"))
                {
                    img.setImageResource(R.drawable.blackpawn);
                }
                else if((chessBoard.board[i][j]).toString().contains("wR"))
                {
                    img.setImageResource(R.drawable.whiterook);
                }
                else if((chessBoard.board[i][j]).toString().contains("bR"))
                {
                    img.setImageResource(R.drawable.blackrook);
                }
                else if((chessBoard.board[i][j]).toString().contains("wB"))
                {
                    img.setImageResource(R.drawable.whitebishop);
                }
                else if((chessBoard.board[i][j]).toString().contains("bB"))
                {
                    img.setImageResource(R.drawable.blackbishop);
                }
                else if((chessBoard.board[i][j]).toString().contains("wN"))
                {
                    img.setImageResource(R.drawable.whiteknight);
                }
                else if((chessBoard.board[i][j]).toString().contains("bN"))
                {
                    img.setImageResource(R.drawable.blackknight);
                }
                else if((chessBoard.board[i][j]).toString().contains("wQ"))
                {
                    img.setImageResource(R.drawable.whitequeen);
                }
                else if((chessBoard.board[i][j]).toString().contains("wK"))
                {
                    img.setImageResource(R.drawable.whiteking);
                }
                else if((chessBoard.board[i][j]).toString().contains("bQ"))
                {
                    img.setImageResource(R.drawable.blackqueen);
                }
                else if((chessBoard.board[i][j]).toString().contains("bK"))
                {
                    img.setImageResource(R.drawable.blackking);
                }
            }
        }
    }
    public void play(String tag1, String tag2)
    {
        Log.i("i", tag1);

        boolean drawAvail = false;
        boolean printBoard = true;
        tag1 = tag1.substring(6, 8);
        tag2 = tag2.substring(6, 8);
        if(printBoard)
            updateBoard();
        Log.i("i", curColor + "'s move: ");
        String input = tag2 + " " + tag1;
        Log.i("i", input);
        try
        {
            if(chessBoard.inCheck(curColor, null))
            {



                chessBoard.move(curColor, input);
                movesMade.add(input);
                hasUndone = false;
                updateBoard();
                if(chessBoard.inCheck(curColor, null))
                {
                    updateBoard();
                    Context context = getApplicationContext();
                    Snackbar mySnackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.Checkmate) + "- " + flipColor(curColor) + " wins!", 1500);
                    mySnackbar.show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            saveFinishedGame();
                        }
                    }, 1500);
                }
            }
            else
            {
                Log.i("i", curColor);
                Log.i("i", input);
                chessBoard.move(curColor, input);

                if(chessBoard.checkPromotion(curColor)) {

                    selectPromotionPiece promote = new selectPromotionPiece();
                    promote.show(getFragmentManager(), "Diag");

                    int newRow = convertRow(tag1.substring(1,2));
                    int newCol = convertCol(tag1.substring(0,1));

                    if (promote.desiredPiece.equals("rook")) {

                        chessBoard.promote("R",newRow,newCol,curColor);

                    }
                    if (promote.desiredPiece.equals("queen")) {

                        chessBoard.promote("Q",newRow,newCol,curColor);

                    }

                    if (promote.desiredPiece.equals("bishop")) {

                        chessBoard.promote("B",newRow,newCol,curColor);

                    }

                    if (promote.desiredPiece.equals("knight")) {

                        chessBoard.promote("N",newRow,newCol,curColor);

                    }






                }

                hasUndone = false;
                movesMade.add(input);
                Log.i("i", "performed move");
                updateBoard();
            }
            if(chessBoard.inCheckmate(curColor))
            {
                updateBoard();
                Context context = getApplicationContext();
                CharSequence text = curColor + " wins";
                Snackbar mySnackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.Checkmate) + "- " + flipColor(curColor) + " wins!", 1500);
                mySnackbar.show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        saveFinishedGame();
                    }
                }, 1500);
            }
            if(chessBoard.inCheckmate(flipColor(curColor)))
            {
                updateBoard();
                Context context = getApplicationContext();
                CharSequence text = curColor + " wins";
                Snackbar mySnackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.Checkmate) + "- " + flipColor(curColor) + " wins!", 1500);
                mySnackbar.show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        saveFinishedGame();
                    }
                }, 1500);
            }
            if(chessBoard.inCheck(flipColor(curColor), null))
            {
                updateBoard();
                Context context = getApplicationContext();
                CharSequence text = getString(R.string.Check);
                int duration = Toast.LENGTH_SHORT;
                Snackbar mySnackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.Check), 2000);
                mySnackbar.show();
                printBoard = true;
            }
            if(chessBoard.inStalemate(curColor))
            {
                updateBoard();
                System.out.println("Stalemate");
                System.exit(0);
            }
            else if(!chessBoard.inCheckmate(curColor))
            {
                curColor = flipColor(curColor);
                printBoard = true;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Log.i("i", "Illegal move, try again\n");
            Snackbar mySnackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.illegalMove), 2000);
            mySnackbar.show();
            updateBoard();
            printBoard = true;
        }
    }
    public void playThrough()
    {
        chessBoard = new Board();
        curColor = "White";
        for(int i = 0; i < movesMade.size(); i++)
        {
            String input = movesMade.get(i);
            try
            {
                chessBoard.move(curColor, input);
            }
            catch(Exception c)
            {
            }
            curColor = flipColor(curColor);
        }
    }
    public void saveFinishedGame()
{
    MySaveDialogFragment mySave = new MySaveDialogFragment();
    mySave.populateArray(movesMade);
    mySave.show(getFragmentManager(), "Diag");
    if(mySave.getResign())
    {
    }
    else
    {
    }
}
    public void saveDialog()
    {
        MySaveDiag mySave = new MySaveDiag();
        mySave.populateArray(movesMade);
        mySave.show(getFragmentManager(), "Diag");
    }


    public void aiMove(String curColor) {

      ArrayList<String> validMoves = new ArrayList<>();


        Piece[][] tmpBoard = new Piece[8][];
        Piece curPiece;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                curPiece = chessBoard.board[i][j];
                if (curPiece != null)
                    if (curPiece.getColor().equalsIgnoreCase(curColor))
                        for (int k = 0; k < 8; k++)
                            for (int l = 0; l < 8; l++)
                                if (curPiece.checkMoveValidity(chessBoard, chessBoard.board, i, j, k, l)) {


                                    for(int m = 0; m < 8; m++)
                                        tmpBoard[m] = chessBoard.board[m].clone();
                                    tmpBoard[k][l] = curPiece;
                                    tmpBoard[i][j] = null;
                                    if(!chessBoard.inCheck(curColor, tmpBoard)){

                                        String move = convert(i, j)+" "+convert(k, l);
                                        validMoves.add(move);
                                    }
                                }
            }
        }

        Random rand = new Random();
        String aiMove = validMoves.get(rand.nextInt(validMoves.size()));

            Log.i("i", aiMove);
            String start = aiMove.substring(0,2);

            String end = aiMove.substring(3,5);

            start = "000000"+start;
            end = "000000"+end;

            Log.i("i", start);
            Log.i("i", end);

            play(end,start);
            playersTurn.setText(flipColor(curColor) + "'s move");
            itemsSelected = 0;
            tag1 = "";
            tag2 = "";
            temp = "";

    }

    public String convert(int k, int l) {

        String curCol="";
        String curRow="";
        switch (l) {
            case 0:
                curCol = "a";
                break;
            case 1:
                curCol = "b";
                break;
            case 2:
                curCol = "c";
                break;
            case 3:
                curCol = "d";
                break;
            case 4:
                curCol = "e";
                break;
            case 5:
                curCol = "f";
                break;
            case 6:
                curCol = "g";
                break;
            case 7:
                curCol = "h";
                break;

        }

        switch (k) {
            case 0:
                curRow = "8";
                break;
            case 1:
                curRow = "7";
                break;
            case 2:
                curRow = "6";
                break;
            case 3:
                curRow = "5";
                break;
            case 4:
                curRow = "4";
                break;
            case 5:
                curRow = "3";
                break;
            case 6:
                curRow = "2";
                break;
            case 7:
                curRow = "1";
                break;

        }



        return curCol+curRow;

    }


    public int convertCol(String input) {

        int x = -1;
        String start = input.substring(0,1);

        switch (start) {
            case "a":
                x = 0;
                break;
            case "b":
                x = 1;
                break;
            case "c":
                x = 2;
                break;
            case "d":
                x = 3;
                break;
            case "e":
                x = 4;
                break;
            case "f":
                x = 5;
                break;
            case "g":
               x = 6;
                break;
            case "h":
               x = 7;
                break;

        }

        return x;

    }

    public int convertRow(String z) {

        int curRow = -1;

        switch (z) {
            case "8":
                curRow = 0;
                break;
            case "7":
                curRow = 1;
                break;
            case "6":
                curRow = 2;
                break;
            case "5":
                curRow = 3;
                break;
            case "4":
                curRow = 4;
                break;
            case "3":
                curRow = 5;
            case "2":
                curRow = 6;
                break;
            case "1":
                curRow = 7;
                break;

        }

        return curRow;

    }


}