package cs213.chessapp71;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Created by Steven on 11/30/2017.
 */
public class PlayActivity extends AppCompatActivity implements OnClickListener
{
    protected TableLayout chessBoardLayout;
    protected TextView playersTurn;
    protected Button undoButton;
    public String tag1 = null;
    public String tag2 = null;
    public String temp = null;
    public String curColor = "White";
    private String m_Text;
    public ArrayList<String> movesMade = new ArrayList<>();
    public boolean hasUndone = false;
    int itemsSelected = 0;
    Board chessBoard;
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
        undoButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(hasUndone == false && movesMade.size() > 0)
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
    }
    public void resignGame(View view)
    {
        MyDialogFragment myDiag = new MyDialogFragment();
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
                    CharSequence text = curColor + " wins";
                    Snackbar mySnackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.Checkmate), 1000);
                    mySnackbar.show();
                    saveFinishedGame();
                    // System.exit(0);
                }
            }
            else
            {
                Log.i("i", curColor);
                Log.i("i", input);
                chessBoard.move(curColor, input);
                hasUndone = false;
                movesMade.add(input);
                Log.i("i", "performed move");
                updateBoard();
            }
            if(chessBoard.inCheckmate(curColor))
            {
                updateBoard();
                Context context = getApplicationContext();
                CharSequence text = getString(R.string.Checkmate);
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                toast.show();

                // saveFinishedGame();
                // System.exit(0);
            }
            if(chessBoard.inCheckmate(flipColor(curColor)))
            {
                updateBoard();
                Context context = getApplicationContext();
                CharSequence text = getString(R.string.Checkmate);
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                toast.show();
                saveFinishedGame();
                // System.exit(0);
            }
            if(chessBoard.inCheck(flipColor(curColor), null))
            {
                updateBoard();
                Context context = getApplicationContext();
                CharSequence text = getString(R.string.Check);
                int duration = Toast.LENGTH_SHORT;
                Snackbar mySnackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.Check), 1000);
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
            Snackbar mySnackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.illegalMove), 1000);
            mySnackbar.show();
            updateBoard();
            printBoard = true;
        }
    }
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
    public void writeToFile(ArrayList<String> moves, String filename, View view) throws IOException
    {
        File moveFile = new File(view.getContext().getFilesDir(), filename);
        FileWriter write = new FileWriter(moveFile);
        write.flush();
        write.close();
    }

    public void saveFinishedGame() {

        MySaveDialogFragment mySave = new MySaveDialogFragment();
        mySave.show(getFragmentManager(), "Diag");
        if(mySave.getResign())
        {

        }
        else
        {

        }



    }


    public void saveDialog() {

        MySaveDiag mySave = new MySaveDiag();
        mySave.show(getFragmentManager(), "Diag");


        String name = mySave.getName();


    }

}