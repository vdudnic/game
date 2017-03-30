package tetris.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import tetris.entities.Board;
import tetris.entities.piecegen.PieceGenerator;
import tetris.entities.pieces.Piece;
import timer.CountDown;
import timer.IsTimeUp;
import timer.TimerEvent;

/**
 * Handles how the game plays, user key presses and  
 * the frame for the game canvas.
 *
 * @author Vladimir Dudnic
 * @version 1.0
 */
@SuppressWarnings("serial")
public class TetrisGame extends JFrame implements Runnable, KeyListener, IsTimeUp
{

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
  private static final int BOARD_SEED = 10; 
  /**
   * The points received by the player corresponding with how many rows cleared
   * at once.
   */
  private static final int ONEPOINT = 100;
  /**
   * The points received by the player corresponding with how many rows cleared
   * at once.
   */
  private static final int TWOPOINT = 300;
  /**
   * The points received by the player corresponding with how many rows cleared
   * at once.
   */
  private static final int THREEPOINT = 600;
  /**
   * The points received by the player corresponding with how many rows cleared
   * at once.
   */
  private static final int FOURPOINT = 900;

  /**
   *  The initial time between a piece drops a row.
   */
  private static final int DROP_WAIT = 500;

  /**
   * After every the level the drop will get quicker by this much.
   */
  private static final int DROP_TIME_REDUCTION = 20;

 /**
  *  Minutes until graduation to next level.
  */
  private static final int TILL_SPEED_UP = 1;
  /**
   *  If the game is over or not.
   */
  private static boolean GAME_OVER;

  /**
   *  Current and next pieces.
   */
  private Piece my_cur_piece;
  /**
   * 
   */
  private PieceGenerator my_random;
  /**
   * 
   */
  private Piece my_next_piece;
  /**
   *  Instance of the canvas where we draw our game.
   */
  private final TetrisCanvas my_game_canvas;
  /**
   *  The game board.
   */
  private Board my_board;

  /**
   *  If the game is paused or not.
   */
  private boolean my_paused_game;
  /**
   *  Total points scored by the player.
   */
  private long my_total_points;
  /**
   *  Total number of rows the player has completed.
   */
  private int my_cleared_rows;
  /**
   * The current level the player is on.
   */
  private int my_level;
  /**
   * The amount of time until the piece drops a row.
   */
  private int my_drop_wait = DROP_WAIT;

  /**
   *  Rotate direction.
   */
  private char my_rotate_direction = 'R';

  /**
   *  Creates a new timer to count time until next level.
   */
  private CountDown my_countdown = new timer.CountDown(0, TILL_SPEED_UP, 0, 0);

  /** 
   * Creates a new instance of TetrisGame. 
   */
  public TetrisGame()
  {
    super();
    my_board = new Board(BOARD_HEIGHT, BOARD_WIDTH, BOARD_SEED);

    my_countdown.addTimesUpListener(this);
    my_game_canvas = new TetrisCanvas();
    this.add(my_game_canvas);

    my_cur_piece = my_board.currentPiece();

    my_next_piece = my_board.nextPiece();

   // my_game_canvas.setFocusable(false);

    //my_game_canvas.setRotationDirection("Right");
    this.addKeyListener(this);

    this.start();
  }

  /**
   * The game loop.
   */
  public void run()
  {

    while (!GAME_OVER)
    {

      if (!my_paused_game)
      {

        my_game_canvas.setSeconds(Integer.parseInt(my_countdown.formatAsDate
          (my_countdown.getTimeToGo()).split(":")[2]));

        this.piecePlaced();
     
        my_cur_piece.moveDown();
     
        this.drawGame();

      }
      
      try
      {
        Thread.sleep(my_drop_wait);
      }
      catch (final InterruptedException ex)
      {
        ex.printStackTrace();
      }

    }
  }

  /**
   *  Places a tetris piece on the board. 
   */
  private void piecePlaced()
  {
    int rows = 0;

    my_board.projection();

    if (my_board.isFull())
    {
      TetrisGame.GAME_OVER = true;
      my_game_canvas.setGameOver(true);
    }
    else
    {
      my_cur_piece = my_next_piece;
    
      // next_piece = new Piece();
      // next_piece = random.next();
      my_next_piece = my_board.nextPiece();
      rows = my_board.lastLinesRemoved();
      this.my_cleared_rows += rows;
      this.totalPoints(rows);
    }
  }

  /**
   * Starts the game loop.
   */
  public void start()
  {
    Thread thread;
    thread = new Thread(this);
    thread.start();
  }

  /**
   * Restarts the game.
   */
  private void restart()
  {
    // Create a new timer
    my_countdown = new timer.CountDown(0, TILL_SPEED_UP, 0, 0);
    my_countdown.addTimesUpListener(this);

    my_cur_piece = my_board.currentPiece();

    my_next_piece = my_random.next();

    my_board = new Board(BOARD_HEIGHT, BOARD_WIDTH, BOARD_SEED);

    this.my_drop_wait = DROP_WAIT;

    my_paused_game = false;
    my_game_canvas.setPaused(false);
    my_game_canvas.setGameOver(false);
    my_game_canvas.setLevel(0);
    my_total_points = 0;
    my_cleared_rows = 0;
    my_level = 0;

    TetrisGame.GAME_OVER = true;

    try
    {
      Thread.currentThread();
      Thread.sleep(DROP_WAIT);
    }
    catch (final InterruptedException ex)
    {
      ex.printStackTrace();
    }

    this.start(); 
  }

 /**
  *  Works out points earned.
  *  More points for more rows completed at once.
  * @param the_rows are rows
  */
  private void totalPoints(final int the_rows)
  {
    switch (the_rows)
    {
      case 0:
        my_total_points += 0;
        break;
      case 1:
        my_total_points += TetrisGame.ONEPOINT;
        break;
      case 2:
        my_total_points += TetrisGame.TWOPOINT;
        break;
      case 3:
        my_total_points += TetrisGame.THREEPOINT;
        break;
      case 4:
        my_total_points += TetrisGame.FOURPOINT;
        break;
      default:
        break;
    }
  }

  /**
   *  Sets values to the game canvas, and redraws the canvas.
   */
  private void drawGame()
  {
    final Board board = new Board(BOARD_HEIGHT, BOARD_WIDTH, BOARD_SEED);
    my_game_canvas.setScore(this.my_total_points); 
    my_game_canvas.setRowsComplete(this.my_cleared_rows); 

    my_game_canvas.setBoard(board); 
    my_game_canvas.setPiece(my_cur_piece);
    // Sets the location to draw the current piece
    // my_game_canvas.setPaintLocation(my_cur_piece.getColumnLocation(),
    //cur_piece.getRowLocation());
    // my_game_canvas.setArrayPiece(b1.currentPiece()); //Sets the current piece
    my_game_canvas.repaint();
  }
  /**
   * @param the_event is key event.
   */
  public void keyTyped(final KeyEvent the_event)
  {
   //do nothing
  }
  /**
   * @param the_event is key event.
   */
  public void keyReleased(final KeyEvent the_event)
  {
  //do nothing
  }
  /**
   * @param the_event is key event.
   */
  public void keyPressed(final KeyEvent the_event)
  {

    if (the_event.getKeyCode() == KeyEvent.VK_DOWN)
    {
      my_board.currentPiece().moveDown();
 
    }
    else if (the_event.getKeyCode() == KeyEvent.VK_P)
    {
      my_paused_game = !my_paused_game; 
      my_game_canvas.setPaused(this.my_paused_game);
      my_countdown.pause(this.my_paused_game);
      this.drawGame(); 

   
    }
    else if (the_event.getKeyCode() == KeyEvent.VK_UP)
    {

      if (this.my_rotate_direction == 'R')
      {
        my_board.currentPiece().rotateClockwise();
   

      }
      else
      {
        my_board.currentPiece().rotateCounterclockwise();
      }

    }
    else if (the_event.getKeyCode() == KeyEvent.VK_RIGHT)
    {

      my_board.currentPiece().moveRight();
   
    }
    else if (the_event.getKeyCode() == KeyEvent.VK_LEFT)
    {
      my_board.currentPiece().moveLeft();
   
    }
    else if (the_event.getKeyCode() == KeyEvent.VK_R)
    {
 
      if (this.my_rotate_direction == 'R')
      {
        this.my_rotate_direction = 'L';
        my_game_canvas.setRotationDirection("Left");
    
      }
      else
      {
        this.my_rotate_direction = 'R';
        my_game_canvas.setRotationDirection("Right");
      }
      this.drawGame();
    }
    else if (the_event.getKeyCode() == KeyEvent.VK_N)
    {
      this.restart(); 
    }
  }

  /**
   * Called when its time to move onto next level.
   * @param the_event 
   */
  public void timesUp(final TimerEvent the_event)
  {

    this.my_drop_wait -= TetrisGame.DROP_TIME_REDUCTION; 
    this.my_game_canvas.setLevel(++this.my_level); 
    my_countdown = new timer.CountDown(0, TILL_SPEED_UP, 0, 0);
    
    
    my_countdown.addTimesUpListener(this);
  }

}