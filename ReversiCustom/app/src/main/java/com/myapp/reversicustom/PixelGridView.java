package com.myapp.reversicustom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hugo on 03/04/17.
 **/

public class PixelGridView extends View {

    //================================================================================
    int currentPlayer = 0;
    private int numColumns, numRows;
    private int cellWidth, cellHeight;
    boolean done = false;//to check is game is over or not
    private Paint blackPaint = new Paint();
    private Paint blackPaint1 = new Paint();
    private Paint whitePaint = new Paint();
    private Paint validPaint = new Paint();
    Map<Integer,Integer> valid = new HashMap<Integer,Integer>();//map to store valid positions
    private static int[][] board = new int[10][10]; // matrix of 10 x 10
    private boolean[][] cellChecked;
    int greencolor = ContextCompat.getColor(getContext(), R.color.greencolor); // new
    int greendarkcolor = ContextCompat.getColor(getContext(), R.color.greendarkcolor);
    int begin = 0;
    int next = -2;
    int whowin = 2;
    int firsttour = -2;

    //=================================================================================

    public void restart()
    {
        begin = 0;
    }

    public PixelGridView(Context context)
    {
        this(context, null);
    }

    public PixelGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        whitePaint.setColor(0xffffffff);
        validPaint.setColor(greencolor);
        blackPaint1.setStrokeWidth(5);
        blackPaint.setStrokeWidth(2);

    }

    public void setNumColumns(int numColumns)
    {
        this.numColumns = numColumns;
        calculateDimensions();
    }

    public int getNumColumns()
    {
        return numColumns;
    }

    public void setNumRows(int numRows)
    {
        this.numRows = numRows;
        calculateDimensions();
    }

    public int getNumRows()
    {
        return numRows;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(h, h, oldh, oldh);
        calculateDimensions();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec+300);
    }

    private void calculateDimensions()
    {
        if (numColumns < 1 || numRows < 1) {
            return;
        }
        cellWidth = getWidth() / numColumns;
        cellHeight = getWidth() / numColumns;
        cellChecked = new boolean[numColumns][numRows];
        invalidate();
    }
    TextView score1;
    private void updateData() {
        score1 = (TextView) findViewById(R.id.edit);
        //checks the current player
        // updates score
        Integer white = countValue(0);
        score1.setText(white.toString());
    }


//==================================================================================================
    @Override
    protected void onDraw(Canvas canvas)
    {
        /*
        if(valid.isEmpty())
        {
            if (countValue(2) == 0)
            {
                currentPlayer = currentPlayer == 0?1:0;

            }
        }
        */
        //checkWinners();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawPaint(paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
       // paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);
/*
        if (pass == 0)
        {
            canvas.drawText("pass", 80, ((numRows*numRows*(numRows)+1)*3)+160, paint);
            currentPlayer = 0;
            pass = -1;
        }
        if (pass ==1)
        {
            canvas.drawText("pass", 80, ((numRows*numRows*(numRows)+1)*3)+160, paint);
            currentPlayer = 1;
            pass = -1;
        }

*/
        //player1 eats all white
        if (countValue(0) == 0)
        {
            begin = 0;
        }
        //player0 eats all black
        if (countValue(1) == 0)
        {
            begin = 0;
        }
        //Grid is full
        if (countValue(-1) == 0 && countValue(2) == 0) {
            if (countValue(0) > countValue(1)&& firsttour == 0) {
                next = 1;
                whowin = 0;
                firsttour = -2;
                begin = 0;

            }
            if (countValue(1) > countValue(0) && firsttour == 0) {
                next = 1;
                whowin = 1;
                firsttour = - 2;
                begin = 0;
            }
            /*
            else
            {
                next = 0;
            }
            // begin = 0;*/
        }
        //Start game
        if (begin == 0)
        {
            for(int i = 0;i<10;i++){
                for(int j = 0;j<10;j++ ){
                    board[i][j] = -1;
                }
            }
            board[3][3] = 0;
            board[3][4] = 1;
            board[4][3] = 1;
            board[4][4] = 0;
            firsttour = 0;
            begin = 1;
        }

        canvas.drawColor(greendarkcolor);
        if (numColumns == 0 || numRows == 0) {
            return;
        }
        int width = getWidth();
        int height = getWidth();
//draw all composent
        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                movepossible(currentPlayer);
                if (countValue(2) == 0)
                {
                    if (currentPlayer == 1)
                    {
                        currentPlayer = 0;
                        next = 3;
                        movepossible(currentPlayer);
                    }
                    else
                    {
                        currentPlayer = 1;
                        next = 3;
                        movepossible(currentPlayer);
                    }
                }

                if (!valid.containsKey(10*i+j))
                {
                    if (board[i][j] == 2)
                    {
                        board[i][j] = -1;
                    }
                }
                    if (board[i][j]== 1)
                    {
                        canvas.drawOval(i * cellWidth, j * cellHeight,
                                (i + 1) * cellWidth, (j + 1) * cellHeight,
                                blackPaint);
                    }
                    if ( board[i][j] == 0)
                    {
                        canvas.drawOval(i * cellWidth, j * cellHeight,
                                (i + 1) * cellWidth, (j + 1) * cellHeight,
                                whitePaint);
                    }
                    if (board[i][j] == 2)
                    {
                       // canvas.drawOval(i * (cellWidth), j * (cellWidth),
                         //       ((i + 1) * (cellWidth)), ((j + 1) * (cellWidth)), validPaint);
                        canvas.drawLine(i * cellWidth, j*cellWidth, i * cellWidth, (j+1)*cellWidth,blackPaint1 );
                        canvas.drawLine((i+1) * cellWidth, j*cellWidth, (i+1) * cellWidth, (j+1)*cellWidth,blackPaint1 );
                        canvas.drawLine(i * cellHeight, j * cellHeight,(i+1)* cellHeight, j * cellHeight, blackPaint1);
                        canvas.drawLine((i) * cellHeight, (j+1) * cellHeight,(i+1)* cellHeight, (j+1) * cellHeight, blackPaint1);
                    }
            }
        }
        //draw the grid
        for (int i = 1; i < numColumns; i++) {
            canvas.drawLine(i * cellWidth, 0, i * cellWidth, height,blackPaint );
        }
        for (int i = 1; i < numRows; i++) {
            canvas.drawLine(0, i * cellHeight, width, i * cellHeight, blackPaint);
        }
        String player0res = Integer.toString(countValue(0));
        String player0resultat = "score player 0: " + player0res;
        String player1res = Integer.toString(countValue(1));
        String player1resultat = "score player 1: " + player1res;



        if (next == 1)
        {
            String winner = Integer.toString(whowin);
            canvas.drawText("player " + winner + " wins", 80, width +260, paint);
            whowin = 2;
            next = 0;

        }

        if (next == 3 && whowin == 2)
        {
            String winner = Integer.toString(whowin);
            canvas.drawText("player pass", 80, width +260, paint);
            next = 0;
        }
        else
        {
            String player = Integer.toString(currentPlayer);
            canvas.drawText("Turn of player : "+player, 80, width +200, paint);
        }

        canvas.drawText(player0resultat, 80, width + 40, paint);
        canvas.drawText(player1resultat, 80, width +100, paint);




    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //movepossible(currentPlayer);
        //updateData();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int column = (int)(event.getX() / cellWidth);
            int row = (int)(event.getY() / cellHeight);
            cellChecked[column][row]  = !cellChecked[column][row];
                //turn of player

                if (currentPlayer == 0 && (board[column][row] == 2)) {
                    board[column][row] = 0;
                    Changes(currentPlayer, column, row);
                    currentPlayer = 1;
                    //checkWinners();
                    invalidate();
                }
            /*
                if (currentPlayer==0 && (board[column][row] != 2))
                 {
                    pass = 1;
                 }
                 */
                //turn of player1
                if (currentPlayer == 1 && (board[column][row] == 2)) {
                    board[column][row] = 1;
                    Changes(currentPlayer, column, row);
                    currentPlayer = 0;
                    //checkWinners();
                    invalidate();
                }

        }
        return true;
    }
    //==============================================================================================
    public void movepossible(int value){
        valid.clear();
        int[] x = {1,-1,1,-1,1,-1,0,0};
        int[] y = {0,0,1,-1,-1,1,1,-1};
        for(int m = 0;m<numColumns;m++){
            for(int n = 0;n<numRows;n++){
                if(board[m][n] == -1 || board[m][n] == 2){
                    for(int i = 0;i<8;i++){
                        int j = m;
                        int k = n;
                        boolean flipped = false;
                        while ((j<8&&j>=0) && (k>=0&&k <8)){
                            if(j > 8 && x[i] == 1)
                                break;
                            if(k > 8 && y[i] == 1)
                                break;
                            if(j < 1 && x[i] == -1)
                                break;
                            if(k < 1 && y[i] == -1)
                                break;
                            j += x[i];
                            k += y[i];
                            if(board[j][k] == -1 || board[j][k] == 2){
                                break;
                            }
                            else if(board[j][k] == value){
                                if(flipped){
                                    valid.put(10*m+n, 10*n+m);
                                    board[m][n] = 2;
                                }
                                break;
                            }
                            else {
                                flipped = true;
                            }
                        }
                    }
                }
            }

        }
    }

    public void Changes(int value,int row,int column){
        board[row][column] = value;
        int[] x = {1,-1,1,-1,1,-1,0,0};
        int[] y = {0,0,1,-1,-1,1,1,-1};
        int temp = value == 0?1:0;
        for(int i = 0;i<numColumns;i++){
            int j = row;
            int k = column;
            boolean flipped = false;
            try{
                while ((j<9&&j>=0) && (k>=0&&k <9)){
                    if(j > 8 && x[i] == 1)
                        break;
                    if(k > 8 && y[i] == 1)
                        break;
                    if(j < 1 && x[i] == -1)
                        break;
                    if(k < 1 && y[i] == -1)
                        break;
                    j += x[i];
                    k += y[i];
                    if(board[j][k] == -1 || board[j][k] == 2){
                        throw new InvalidOthelloException();
                    }
                    else if(board[j][k] == value){
                        if(!flipped)
                            throw new InvalidOthelloException();
                        break;
                    }
                    else{
                        board[j][k] = value;
                        flipped = true;
                    }
                }
            }
            catch (InvalidOthelloException ex){
                j -= x[i];
                k -= y[i];
                while(j!=row || k!= column){
                    board[j][k] = temp;
                    j -= x[i];
                    k -= y[i];
                }
            }
        }
    }
    public int countValue(int value){
        int temp = 0;
        for(int i = 0;i<numColumns;i++){
            for(int j = 0;j<numRows;j++){
                if(board[i][j] == value)
                    temp++;
            }
        }
        return temp;
    }

    public void checkWinners(){
        //check winner by checking if none of the players have valid moves
        if(valid.isEmpty()){
            if(done == true){
                if(countValue(0) >  countValue(1)){
                    begin = 0;
                }
                else if(countValue(0) <  countValue(1)){
                    begin = 0;
                }
                else {
                    begin = 0;
                }

            }
            else{

                done = true;
                currentPlayer = currentPlayer == 0?1:0;
                movepossible(currentPlayer);
                invalidate();
                updateData();
                checkWinners();
            }
        }
        else{
            done = false;
        }
    }
}