package cs213.chessapp71;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by colinackerley on 12/7/17.
 */
public class PreviousGameActivity extends AppCompatActivity {
    ArrayList<String> moves = new ArrayList<String>();
    ArrayList<String> textFiles;
    public String[] result = new String[1];
    Board chessBoard;
    protected TableLayout chessBoardLayout;
    protected TextView playersTurn;
    protected Button prevButton;
    protected Button nextButton;
    protected String curColor = "White";
    protected int i = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_game_list);


        try {
            textFiles = getAllTextFiles();
        } catch (Exception c) {
        }

        setContentView(R.layout.layout_game_list);
        setTitle("Choose Saved Game");
        final ListView curView = (ListView) findViewById(R.id.gameList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, textFiles);
        curView.setAdapter(arrayAdapter);
        curView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                String selected = curView.getItemAtPosition(position).toString();
                result[0] = selected;
                setContentView(R.layout.activity_prev_game);
                Log.i("i", "File is " + result[0]);

                try {
                    readFile(selected);
                    setContentView(R.layout.activity_prev_game);

                    chessBoardLayout = (TableLayout) findViewById(R.id.chessBoardLayout);
                    playersTurn = (TextView) findViewById(R.id.playerTurnText);
                    chessBoard = new Board();
                    prevButton = (Button) findViewById(R.id.prevButton);
                    nextButton = (Button) findViewById(R.id.nextButton);
                    nextButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            prevButton.setEnabled(true);

                            if (i < moves.size()) {


                                moveForward(moves);
                            } else {

                                moveForward(moves);
                                Snackbar mySnackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.Checkmate) + "- " + curColor + " wins!", 2000);
                                mySnackbar.show();
                                nextButton.setEnabled(false);
                            }
                        }
                    });

                    prevButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            nextButton.setEnabled(true);

                            if (i > 1 && moves.size() > 0) {

                                moveBackward(moves);
                            } else {

                                prevButton.setEnabled(false);
                                Snackbar mySnackbar = Snackbar.make(findViewById(android.R.id.content), "Can't go further back", 2000);
                                mySnackbar.show();

                            }
                        }
                    });

                } catch (IOException e) {
                }
            }
        });

    }

    private ArrayList<String> getAllTextFiles() throws IOException {
        ArrayList<String> allTextFiles = new ArrayList<String>();
        String path = Environment.getExternalStorageDirectory().toString() + "/games";
        File f = new File(path);
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            String filePath = file.getPath();
            if (filePath.endsWith(".txt"))
                allTextFiles.add(file.getName());
        }
        return allTextFiles;
    }

    private void readFile(String filename) throws IOException {
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File dir = new File(sdcard.getAbsolutePath() + "/games");
            Log.i("I", "Filename is" + filename);
            File file = new File(dir, filename);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String curMove;
            while ((curMove = reader.readLine()) != null) {
                moves.add(curMove);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void moveForward(ArrayList<String> moves) {

        chessBoard = new Board();
        curColor = "White";

        Log.d("i", "value: " + i);

        int j = 0;

        while (j < i) {

            String input = moves.get(j);
            try {
                chessBoard.move(curColor, input);
            } catch (Exception c) {
            }
            curColor = flipColor(curColor);
            playersTurn.setText(curColor + "'s move");
            updateBoard();
            j++;

        }
        i++;

    }

    private void moveBackward(ArrayList<String> moves) {

        i--;

        chessBoard = new Board();
        curColor = "White";

        int j = 0;

        if (i == 1) {
            updateBoard();
        }
        while (j < (i - 1)) {

            String input = moves.get(j);
            try {
                chessBoard.move(curColor, input);
            } catch (Exception c) {
            }
            curColor = flipColor(curColor);
            playersTurn.setText(curColor + "'s move");
            updateBoard();
            j++;
        }
    }

    public void updateBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                TableRow row = (TableRow) chessBoardLayout.getChildAt(i);
                ImageView img = (ImageView) row.getChildAt(j);
                if ((chessBoard.board[i][j]) == null) {
                    img.setImageDrawable(null);
                } else if ((chessBoard.board[i][j]).toString().contains("wP")) {
                    img.setImageResource(R.drawable.whitepawn);
                } else if ((chessBoard.board[i][j]).toString().contains("bP")) {
                    img.setImageResource(R.drawable.blackpawn);
                } else if ((chessBoard.board[i][j]).toString().contains("wR")) {
                    img.setImageResource(R.drawable.whiterook);
                } else if ((chessBoard.board[i][j]).toString().contains("bR")) {
                    img.setImageResource(R.drawable.blackrook);
                } else if ((chessBoard.board[i][j]).toString().contains("wB")) {
                    img.setImageResource(R.drawable.whitebishop);
                } else if ((chessBoard.board[i][j]).toString().contains("bB")) {
                    img.setImageResource(R.drawable.blackbishop);
                } else if ((chessBoard.board[i][j]).toString().contains("wN")) {
                    img.setImageResource(R.drawable.whiteknight);
                } else if ((chessBoard.board[i][j]).toString().contains("bN")) {
                    img.setImageResource(R.drawable.blackknight);
                } else if ((chessBoard.board[i][j]).toString().contains("wQ")) {
                    img.setImageResource(R.drawable.whitequeen);
                } else if ((chessBoard.board[i][j]).toString().contains("wK")) {
                    img.setImageResource(R.drawable.whiteking);
                } else if ((chessBoard.board[i][j]).toString().contains("bQ")) {
                    img.setImageResource(R.drawable.blackqueen);
                } else if ((chessBoard.board[i][j]).toString().contains("bK")) {
                    img.setImageResource(R.drawable.blackking);
                }
            }
        }
    }

    private static String flipColor(String s) {
        if (s.equals("White"))
            return "Black";
        return "White";
    }
}