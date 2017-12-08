package cs213.chessapp71;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.util.Log;

/**
 * Created by Steven on 11/30/2017.
 */
public class PlayActivity extends AppCompatActivity implements OnClickListener
{
    protected TableLayout chessBoardLayout;
    public String tag1 = null;
    public String tag2 = null;
    public String temp = null;
    int itemsSelected = 0;
    Board chessBoard;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        chessBoardLayout = (TableLayout) findViewById(R.id.chessBoardLayout);
        chessBoardLayout.setOnClickListener(PlayActivity.this);

        boolean drawAvail = false;
        boolean printBoard = true;

        chessBoard = new Board();

        String curColor = "White";


            if(printBoard)
                updateBoard();
            System.out.print(curColor+"'s move: ");
            String input = tag1+" "+tag2;
            System.out.println();

                try
                {
                    if(chessBoard.inCheck(curColor, null))
                    {
                        chessBoard.move(curColor, input);
                        if(chessBoard.inCheck(curColor, null))
                        {
                            updateBoard();
                            System.out.println(flipColor(curColor)+" wins");
                            System.exit(0);
                        }
                    }
                    else
                    {
                        chessBoard.move(curColor, input);
                    }
                    if(chessBoard.inCheckmate(curColor))
                    {
                        updateBoard();
                        System.out.println("Checkmate");
                        System.out.println(flipColor(curColor)+" wins");
                        System.exit(0);
                    }
                    if(chessBoard.inCheckmate(flipColor(curColor)))
                    {
                        updateBoard();
                        System.out.println("Checkmate");
                        System.out.println(curColor+" wins");
                        System.exit(0);
                    }
                    if(chessBoard.inCheck(flipColor(curColor), null))
                    {
                        System.out.println("Check");
                        System.out.println();
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
                    System.out.println("Illegal move, try again\n");
                    printBoard = false;
                }
            }

    /**
     *@param s String that is either black or white
     *@return String that is the opposite of the color it was passed. Returns white if s was black, and black if s was white
     */
    private static String flipColor(String s)
    {
        if(s.equals("White"))
            return "Black";
        return "White";
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
        try{

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
                /*
                ImageView imageView = (ImageView) chessBoardLayout.findViewWithTag(tag2);
                ImageView imageView2 = (ImageView) chessBoardLayout.findViewWithTag(tag1);
                Drawable i = imageView.getDrawable();
                Drawable j = imageView2.getDrawable();
                imageView.setImageDrawable(j);
                imageView2.setImageDrawable(i);
                imageView.setBackground(null);
                imageView2.setBackground(null); */
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
        }}

        catch(Exception c)
        {}
    }
    @Override
    public void onClick(View v)
    {
        highlight(v);
    }


    public void updateBoard() {

        for (int i=0; i < 8 ; i++){
            for (int j=0; j < 8; j++){

                TableRow row = (TableRow)chessBoardLayout.getChildAt(i);
                ImageView img = (ImageView) row.getChildAt(j);



               if((chessBoard.board[i][j]) == null) {

                img.setImageDrawable(null);

                   }

               else if((chessBoard.board[i][j]).equals("wP")) {

                   img.setImageResource(R.drawable.whitepawn);

                }

                else if((chessBoard.board[i][j]).equals("bP")) {

                    img.setImageResource(R.drawable.blackpawn);

                }

                else if((chessBoard.board[i][j]).equals("wR")) {

                    img.setImageResource(R.drawable.whiterook);

                }

                else if((chessBoard.board[i][j]).equals("bR")) {

                    img.setImageResource(R.drawable.blackrook);

                }
                else if((chessBoard.board[i][j]).equals("wB")) {

                    img.setImageResource(R.drawable.whitebishop);

                }
                else if((chessBoard.board[i][j]).equals("bB")) {

                    img.setImageResource(R.drawable.blackbishop);

                }
                else if((chessBoard.board[i][j]).equals("wN")) {

                    img.setImageResource(R.drawable.whiteknight);

                }
                else if((chessBoard.board[i][j]).equals("bN")) {

                    img.setImageResource(R.drawable.blackknight);

                }
                else if((chessBoard.board[i][j]).equals("wQ")) {

                    img.setImageResource(R.drawable.whitequeen);

                }
                else if((chessBoard.board[i][j]).equals("wK")) {

                    img.setImageResource(R.drawable.whiteking);

                }
                else if((chessBoard.board[i][j]).equals("bQ")) {

                    img.setImageResource(R.drawable.blackqueen);

                }
                else if((chessBoard.board[i][j]).equals("bK")) {

                    img.setImageResource(R.drawable.blackking);

                }
            }
        }
        }






}