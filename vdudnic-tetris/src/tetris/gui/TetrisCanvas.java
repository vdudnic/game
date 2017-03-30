package tetris.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import tetris.entities.Board;
import tetris.entities.pieces.Piece;
import tetris.entities.pieces.Rotation;



/**
 * Handles the drawing of the Tetris game to the screen.
 * 
 * @author Vladimir Dudnic
 * @version 1.0
 */
@SuppressWarnings("serial")
public class TetrisCanvas extends Canvas
{
  /**
   * the frame width.
   */
  private static final int FRAME_WIDTH = 300;
  /**
   * the frame height.
   */
  private static final int FRAME_HEIGHT = 360;
  /**
   * Block's width.
   */
  private static final  int BLOCK_WIDTH = 16;
  /**
   * drawing border.
   */
  private static final  int BORDER = 5;
  /**
   * The font.
   */
  private static final  int FONT = 13;
  /**
   * width for game area.
   */
  private static final int GAME_AREA_WIDTH = 165;
  /**
   * height for game area.
   */
  private static final int GAME_AREA_HEIGHT = 323;
  /**
   * x coord. for game area.
   */
  private static final int GAME_AREA_X_LOC = 1;
  /**
   * y coord. for game area.
   */
  private static final int GAME_AREA_Y_LOC = 1;
  /**
   * x coord for next piece area.
   */
  private static final int NEXT_PIECE_AREA_X_LOC = 170;
  /**
   * y coord for next piece area.
   */
  private static final int NEXT_PIECE_AREA_Y_LOC = 1;
  /**
   * width for next piece area.
   */
  private static final int NEXT_PIECE_WIDTH = 120;
  /**
   * height for next piece area.
   */
  private static final int NEXT_PIECE_HEIGHT = 80;
  /**
   * 
   */
  private static final int GAME_INFO_WIDTH = 120;
  /**
   * 
   */
  private static final int GAME_INFO_HEIGHT = 239;
  /**
   * 
   */
  private static final int GAME_INFO_X_LOC = 170;
  /**
   * 
   */
  private static final int GAME_INFO_Y_LOC = 85;

  /**
   * The size to draw the block, BLOCK_WIDTH is the block size with space.
   */
  private static final int BLOCK_DRAW_SIZE = 14;
  /**
   * 
   */
  private static final int SCORE_X_LOC = 172;
  /**
   * 
   */
  private static final int SCORE_Y_LOC = 105;
  /**
   * 
   */
  private static final int ROWS_X_LOC = 172;
  /**
   * 
   */
  private static final int ROWS_Y_LOC = 125;
  /**
   * 
   */
  private static final int LEVEL_X_LOC = 172;
  /**
   * 
   */
  private static final int LEVEL_Y_LOC = 150;
  /**
   * 
   */

  private static final int TIME_LEFT_X_LOC = 172;
  /**
   * 
   */
  private static final int TIME_LEFT_Y_LOC = 175;
  /**
   * 
   */
  private static final int BOARD_WIDTH = 10;
  /**
   * 
   */
  private static final int BOARD_HEIGHT = 20;
 
  /**
   * 
   */
  private Piece my_pieces;
  /**
   * 
   */
  private Board my_board;
  /**
   * 
   */
  private int my_column_multiplyer;
  /**
   * 
   */
  private int my_row_multiplyer;
  /**
   * game over.
   */
  private boolean my_draw_game_over;
  /**
   *  pause.
   */
  private boolean my_draw_pause;
  /**
   * draw number of complete rows.
   */
  private int my_completed_rows;
  /**
   * The score.
   */  
  private long my_score;
  /**
   * 
   */
  private String my_rotation_direction;
  /**
   * 
   */
  private int my_level;
  /**
   * 
   */
  private int my_till_next_level;

  /**
   *  The image that will contain everything that has been drawn.
   */ 
  private Image my_offscreen;

  /**
   *  The object we will use to write with.
   */
  private Graphics my_graphics;
 
  /**
   * Sets a 2d array to be drawn. Which should be the current piece in play.
   * 
   * @param the_piece is piece
   */
  public void setPiece(final Piece the_piece)
  {
    my_pieces = the_piece;
  }

  /**
   * Sets the next piece to be drawn.
   * 
   * @param the_piece is piece
   */
  public void setNextPiece(final Piece the_piece)
  {
    my_pieces = the_piece;
  }

  /**
   * Sets if the game is over or not.
   * 
   * @param the_end If true, game over will drawn to the screen
   */
  public void setGameOver(final boolean the_end)
  {
    my_draw_game_over = the_end;
  }

  /**
   * Sets the board to be drawn.
   * 
   * @param the_board is the board.
   */
  public void setBoard(final Board the_board)
  {
    my_board = the_board;
  }

  /**
   * Sets the location to paint the in play Tetris piece.
   * 
   * @param the_column_location Column which in play game piece will be drawn
   * @param the_row_location Row which in play game piece will be drawn
   */
  public void setPaintLocation(final int the_column_location, final int the_row_location)
  {
    my_column_multiplyer = the_column_location;
    my_row_multiplyer = the_row_location;
  }

  /**
   * Sets the number of completed rows to be drawn.
   * 
   * @param the_rows The number of completed rows
   */
  public void setRowsComplete(final int the_rows)
  {
    my_completed_rows = the_rows;
  }

  /**
   * Sets the players score to be drawn.
   * 
   * @param the_score The score
   */
  public void setScore(final long the_score)
  {
    my_score = the_score;
  }

  /**
   * Shows as game paused.
   * @param the_pause is pause
   */
  public void setPaused(final boolean the_pause)
  {
    my_draw_pause = the_pause;
  }

  /**
   * Sets the level # to draw.
   * @param the_level is level
   */
  public void setLevel(final int the_level)
  {
    my_level = the_level;
  }

  /**
   * sets the seconds left to draw.
   * @param the_secs is seconds
   */
  public void setSeconds(final int the_secs)
  {
    my_till_next_level = the_secs;
  }

  /**
   * Sets a word the rotation direction to draw. 
   * @param the_direction is word direction
   */
  public void setRotationDirection(final String the_direction)
  {
    my_rotation_direction = the_direction;
  }

  /**
   * Overridden from super.
   * @param the_g is graphics
   */
  public void update(final Graphics the_g)
  {
    this.paint(the_g);
  }

  /**
   * Overridden from super.
   * @param  the_g is graphics
   */
  public void paint(final Graphics the_g)
  {
    super.paint(the_g);
    
    if (my_offscreen == null)
    {
      my_offscreen = createImage(this.getWidth(), this.getHeight());
    }

    my_graphics = my_offscreen.getGraphics();


    // Draw a black background
    my_graphics.setColor(Color.BLACK);
    my_graphics.fillRect(0, 0, this.getWidth(), this.getHeight());

    // Draw a white border around the board
    my_graphics.setColor(Color.WHITE);
    my_graphics.drawRect(GAME_AREA_X_LOC, GAME_AREA_Y_LOC, 
                            GAME_AREA_WIDTH, GAME_AREA_HEIGHT);

    // Draw a white border to make the 'next piece area'
    my_graphics.drawRect(NEXT_PIECE_AREA_X_LOC, NEXT_PIECE_AREA_Y_LOC, 
                         NEXT_PIECE_WIDTH, NEXT_PIECE_HEIGHT);

    // Draw a white border to make a 'game info area'
    my_graphics.drawRect(GAME_INFO_X_LOC, GAME_INFO_Y_LOC, 
                            GAME_INFO_WIDTH, GAME_INFO_HEIGHT);

    // Draws the piece
  
    int row = BLOCK_WIDTH * my_row_multiplyer + 3; 
    int col = BLOCK_WIDTH * my_column_multiplyer;
  
    for (int i = 0; i < BOARD_WIDTH; i++)
    {

      my_graphics.drawRect(col + BORDER, row, BLOCK_DRAW_SIZE, BLOCK_DRAW_SIZE);
    }

   // my_board = new ArrayList<List<Color[]>>();
    for (int i = 0; i < BOARD_HEIGHT; i++)
    {
      for (int j = 0; j < BOARD_WIDTH; j++)
      {
       // my_board.add(new ArrayList<Color[]>());
        my_graphics.fillRect(col + BORDER, row, BLOCK_DRAW_SIZE, BLOCK_DRAW_SIZE);
        col += BLOCK_WIDTH;
      }
      col = 0;
      row += BLOCK_WIDTH;

    }
    
   //Draws side info status stuff
    my_graphics.setColor(Color.WHITE);
    my_graphics.drawString("Full Rows: " + my_completed_rows, ROWS_X_LOC, ROWS_Y_LOC);

    my_graphics.drawString("Level: " + my_level, LEVEL_X_LOC, LEVEL_Y_LOC);

    my_graphics.drawString("Next level in: " + my_till_next_level, TIME_LEFT_X_LOC,
                              TIME_LEFT_Y_LOC);
    my_graphics.drawString("Total score: " + my_score, SCORE_X_LOC, SCORE_Y_LOC);
 
    if (this.my_draw_pause)
    {
      my_graphics.setFont(new Font("", Font.BOLD, FONT));
      my_graphics.setColor(Color.BLUE);
      my_graphics.drawString("PAUSED", 0, 0);
    }

    // Draws game over, if it is set to
    if (this.my_draw_game_over)
    {
      my_graphics.setFont(new Font("", Font.BOLD, FONT));
      my_graphics.setColor(Color.RED);
      my_graphics.drawString("GAME OVER!", 0, 0);
    }
   
    my_graphics.dispose();
    the_g.drawImage(my_offscreen, 0, 0, this);

  }

  /**
   * @param the_args the command line arguments
   */
  public static void main(final String[] the_args)
  {
      
      //Create a new game
    final TetrisGame frame = new TetrisGame();
    final Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setBounds((int) screen_size.getWidth() - FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
    frame.setTitle("TETRIS");
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

}
