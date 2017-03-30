/*
 * An implementation of the classic game "Tetris". Class Board.
 * 
 * @author "Daniel M. Zimmerman (dmz@acm.org)"
 * 
 * @module "TCSS 305"
 * 
 * @creation_date "July 2008"
 * 
 * @last_updated_date "December 2009"
 * 
 * @keywords "Tetris", "game"
 */

package tetris.entities;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;

import tetris.entities.piecegen.PieceGenerator;
import tetris.entities.piecegen.RandomGenerator;
import tetris.entities.piecegen.SequenceGenerator;
import tetris.entities.pieces.Piece;

/* @ model import java.lang.reflect.Array; @ */

/**
 * A list of rows comprising the gameplay area.
 * 
 * @author Daniel M. Zimmerman (dmz@acm.org)
 * @version 17 November 2008
 */
public class Board extends Observable
{
  // Subsumes ROW

  // @constraint 4 rows exist above the visible board.
  /**
   * The number of rows that exist above the visible board area.
   */
  public static final int ROWS_ABOVE_BOARD = Piece.NUMBER_OF_BLOCKS;

  /**
   * The character used in the String representation to represent a side border.
   */
  public static final char SIDE_BORDER_CHAR = '|';

  /**
   * The character used in the String representation to represent the bottom
   * border.
   */
  public static final char BOTTOM_BORDER_CHAR = '-';

  /**
   * The character used in the String representation to represent an empty
   * block.
   */
  public static final char EMPTY_BLOCK_CHAR = ' ';

  /**
   * The character used in the String representation to represent a frozen
   * block.
   */
  public static final char FROZEN_BLOCK_CHAR = 'X';

  /**
   * The character used in the String representation to represent a block of the
   * current piece.
   */
  public static final char CURRENT_PIECE_BLOCK_CHAR = '+';

  // Instance Fields

  /**
   * The height.
   */
  private final int my_height;

  // @constraint The height and width do not change.
  // @constraint The height and width are both positive and finite.
  /*
   * @ in height; public model int height; in height; public invariant 0 <
   * height; public constraint \old(height) == height; private represents height
   * = my_height; private invariant 0 < my_height; @
   */

  /**
   * The width.
   */
  private final int my_width;

  // @constraint The height and width do not change.
  // @constraint The height and width are both positive and finite.
  /*
   * @ in width; public model int width; in width; public invariant 0 < width;
   * public constraint \old(width) == width; private represents width =
   * my_width; private invariant 0 < my_width; @
   */

  /**
   * The list of rows.
   */
  private final/* @ non_null @ */List<Color[]> my_row_list;

  /*
   * @ in row_list; public model non_null List row_list; in row_list; public
   * invariant row_list.size() == height + ROWS_ABOVE_BOARD; public invariant
   * (\forall int i; 0 <= i && i < row_list.size(); row_list.get(i) != null);
   * private represents row_list = my_row_list; private invariant
   * my_row_list.size() == height + ROWS_ABOVE_BOARD; private invariant (\forall
   * int i; 0 <= i && i < row_list.size(); row_list.get(i) != null); @
   */

  /**
   * The piece generator used by this board.
   */
  private final/* @ non_null @ */PieceGenerator my_piece_generator;

  /*
   * @ in piece_generator; public model non_null PieceGenerator piece_generator;
   * in piece_generator; public constraint \old(piece_generator) ==
   * piece_generator; private represents piece_generator = my_piece_generator; @
   */

  /**
   * The current piece.
   */
  private/* @ non_null @ */Piece my_current_piece;

  // @constraint All blocks in the current and next pieces have valid positions.
  /*
   * @ in current_piece; public model non_null Piece current_piece; in
   * current_piece; public invariant (\forall int i; 0 <= i && i <
   * Piece.NUMBER_OF_BLOCKS; isWithinBoard(current_piece.absolutePosition(i)));
   * private represents current_piece = my_current_piece; private invariant
   * (\forall int i; 0 <= i && i < Piece.NUMBER_OF_BLOCKS;
   * isWithinBoard(my_current_piece.absolutePosition(i))); @
   */

  /**
   * The next piece.
   */
  private/* @ non_null @ */Piece my_next_piece;

  // @constraint All blocks in the current and next pieces have valid positions.
  /*
   * @ in next_piece; public model non_null Piece next_piece; in next_piece;
   * public invariant (\forall int i; 0 <= i && i < Piece.NUMBER_OF_BLOCKS;
   * isWithinBoard(next_piece.absolutePosition(i))); private represents
   * next_piece = my_next_piece; private invariant (\forall int i; 0 <= i && i <
   * Piece.NUMBER_OF_BLOCKS; isWithinBoard(my_next_piece.absolutePosition(i)));
   * @
   */

  /**
   * The number of lines removed as a result of the last move or drop.
   */
  private int my_last_lines_removed;

  // @constraint The number of lines removed as a result of an action is
  // between 0 and 4 inclusive.
  /*
   * @ in last_lines_removed; public model int last_lines_removed; in
   * last_lines_removed; public invariant 0 <= last_lines_removed &&
   * last_lines_removed <= Piece.NUMBER_OF_BLOCKS; private represents
   * last_lines_removed = my_last_lines_removed; private invariant 0 <=
   * my_last_lines_removed && my_last_lines_removed <= Piece.NUMBER_OF_BLOCKS; @
   */

  /**
   * The number of blocks placed as a result of the last move or drop.
   */
  private int my_last_blocks_placed;

  // @constraint The number of blocks placed as a result of an action is
  // 0 or 4.
  /*
   * @ in last_blocks_placed; public model int last_blocks_placed; in
   * last_blocks_placed; public invariant last_blocks_placed == 0 ||
   * last_blocks_placed == Piece.NUMBER_OF_BLOCKS; private represents
   * last_blocks_placed = my_last_blocks_placed; private invariant
   * my_last_blocks_placed == 0 || my_last_blocks_placed ==
   * Piece.NUMBER_OF_BLOCKS; @
   */

  /**
   * A flag indicating whether or not the board changed as a result of the last
   * action.
   */
  private boolean my_changed_flag;

  /*
   * @ in changed; public model boolean changed; in changed; private represents
   * changed = my_changed_flag; @
   */

  /**
   * A flag indicating whether or not the board is full.
   */
  private boolean my_full_flag;

  // @constraint Once the board is full, it remains full and no
  // commands change the board state.
  /*
   * @ in full; public model boolean full; in full; public constraint \old(full)
   * ==> full; private represents full = my_full_flag; private constraint
   * \old(my_full_flag) == my_full_flag; @
   */

  // Constructor

  // @ requires 0 < the_height;
  // @ requires 0 < the_width;
  // @ ensures height == the_height;
  // @ ensures width == the_width;
  // @ ensures \fresh(current_piece);
  /*
   * @ ensures (\forall int i; 0 <= i && i < Piece.NUMBER_OF_BLOCKS;
   * isWithinBoard(current_piece.absolutePosition(i))); @
   */
  // @ ensures \fresh(next_piece);
  /*
   * @ ensures (\forall int i; 0 <= i && i < Piece.NUMBER_OF_BLOCKS;
   * isWithinBoard(next_piece.absolutePosition(i))); @
   */
  // @ ensures last_lines_removed == 0;
  // @ ensures last_blocks_placed == 0;
  // @ ensures changed;
  // @ ensures !full;
  /**
   * Constructs a new board with the specified dimensions and the specified
   * random seed for the piece generator.
   * 
   * @param the_height The height.
   * @param the_width The width.
   * @param the_seed The random seed.
   */
  public Board(final int the_height, final int the_width, final long the_seed)
  {
    super();
    my_height = the_height;
    my_width = the_width;
    my_piece_generator = new RandomGenerator(the_seed, new Point(the_width / 2, the_height));
    my_row_list = new LinkedList<Color[]>();
    initialize();
  }

  // @ requires 0 < the_height;
  // @ requires 0 < the_width;
  // @ requires 0 < the_sequence.size();
  /*
   * @ requires (\forall int i; 0 <= i && i < the_sequence.size();
   * the_sequence.get(i) != null && \typeof(the_sequence.get(i)) <:
   * \type(Piece)); @
   */
  // @ ensures height == the_height;
  // @ ensures width == the_width;
  // @ ensures \fresh(current_piece);
  /*
   * @ ensures (\forall int i; 0 <= i && i < Piece.NUMBER_OF_BLOCKS;
   * isWithinBoard(current_piece.absolutePosition(i))); @
   */
  // @ ensures \fresh(next_piece);
  /*
   * @ ensures (\forall int i; 0 <= i && i < Piece.NUMBER_OF_BLOCKS;
   * isWithinBoard(next_piece.absolutePosition(i))); @
   */
  // @ ensures last_lines_removed == 0;
  // @ ensures last_blocks_placed == 0;
  // @ ensures changed;
  // @ ensures !full;
  /**
   * Constructs a new board with the specified dimensions and the specified
   * sequence of pieces to use.
   * 
   * @param the_height The height.
   * @param the_width The width.
   * @param the_sequence The sequence.
   */
  public Board(final int the_height, final int the_width,
               final/* @ non_null @ */List<Piece> the_sequence)
  {
    super();
    my_height = the_height;
    my_width = the_width;
    my_piece_generator = new SequenceGenerator(the_sequence);
    my_row_list = new LinkedList<Color[]>();
    initialize();
  }

  /**
   * @return What is your height?
   */
  // @ ensures \result == height;
  public/* @ pure @ */int height()
  {
    return my_height;
  }

  /**
   * @return What is your width?
   */
  // @ ensures \result == width;
  public/* @ pure @ */int width()
  {
    return my_width;
  }

  // @constraint Valid row positions are between 0 and
  // (height + (rows above board) - 1), inclusive.
  // @ requires 0 <= the_y && the_y < height + ROWS_ABOVE_BOARD;
  // @ ensures Array.getLength(\result) == Array.getLength(row_list.get(the_y));
  /*
   * @ ensures (\forall int i; 0 <= i && i < \result.length; Array.get(\result,
   * i) == Array.get(row_list.get(the_y), i)); @
   */
  /**
   * @param the_y The y-coordinate of the desired row.
   * @return What is the row of frozen pieces at position the_y? (note that this
   *         returns a clone)
   */
  public/* @ pure non_null @ */Color[] rowAt(final int the_y)
  {
    /* @ assume my_row_list.get(the_y) instanceof Color[]; @ */// no generics
    Color[] result = (Color[]) my_row_list.get(the_y);
    result = (Color[]) result.clone();
    return result;
  }

  // @constraint Valid row positions are between 0 and
  // (height + (rows above board) - 1), inclusive.
  // @ requires 0 <= the_y && the_y < height + ROWS_ABOVE_BOARD;
  /*
   * @ ensures \result <==> (\forall int x; 0 <= x && x < width; rowAt(the_y)[x]
   * == null) && (\forall int i; 0 <= i && i < Piece.NUMBER_OF_BLOCKS;
   * current_piece.absolutePosition(i).y != the_y); @
   */
  /**
   * @param the_y The row position.
   * @return Is the row at position the_y, _not_ including the current piece,
   *         empty?
   */
  public/* @ pure @ */boolean isRowEmpty(final int the_y)
  {
    boolean result = true;
    for (int i = 0; i < Piece.NUMBER_OF_BLOCKS; i++)
    {
      result = result && !(currentPiece().absolutePosition(i).y() == the_y);
    }
    for (int x = 0; x < my_width; x++)
    {
      result = result && rowAt(the_y)[x] == null;
    }
    return result;
  }

  // @constraint Valid row positions are between 0 and
  // (height + (rows above board) - 1), inclusive.
  // @ requires 0 <= the_y && the_y < height + ROWS_ABOVE_BOARD;
  /*
   * @ ensures \result <==> (\forall int x; 0 <= x && x < width; rowAt(the_y)[x]
   * != null || (\exists int i; 0 <= i && i < Piece.NUMBER_OF_BLOCKS;
   * current_piece.absolutePosition(i).x == x &&
   * current_piece.absolutePosition(i).y == the_y)); @
   */
  /**
   * @param the_y The row position.
   * @return Is the row at position the_y, including the current piece, full?
   */
  public/* @ pure @ */boolean isRowFull(final int the_y)
  {
    final Color[] row = rowAt(the_y);
    for (int i = 0; i < Piece.NUMBER_OF_BLOCKS; i++)
    {
      final Point block_pos = currentPiece().absolutePosition(i);
      if (block_pos.y() == the_y)
      {
        row[block_pos.x()] = currentPiece().color();
      }
    }
    boolean result = true;
    for (int x = 0; x < my_width; x++)
    {
      result = result && row[x] != null;
    }
    return result;
  }

  /**
   * @param the_y The row position.
   * @return Is the row at position the_y, not including the current piece,
   *         full?
   */
  private/* @ pure @ */boolean isFrozenRowFull(final int the_y)
  {
    final Color[] row = rowAt(the_y);
    boolean result = true;
    for (int x = 0; x < my_width; x++)
    {
      result = result && row[x] != null;
    }
    return result;
  }

  // @constraint Valid row positions are between 0 and
  // (height + (rows above board) - 1), inclusive.
  // @ requires 0 <= the_y && the_y < height + ROWS_ABOVE_BOARD;
  /*
   * @ ensures \result <==> (\forall int x; 0 <= x && x < width; rowAt(the_y)[x]
   * != null || (\exists int i; 0 <= i && i < Piece.NUMBER_OF_BLOCKS;
   * projection().absolutePosition(i).x == x &&
   * projection().absolutePosition(i).y == the_y)); @
   */
  /**
   * @param the_y The row position.
   * @return Is the row at position the_y, including the projection of the
   *         current piece, full?
   */
  public/* @ pure @ */boolean isRowFullUnderProjection(final int the_y)
  {
    boolean result = true;
    final Piece projection = projection();
    for (int i = 0; i < my_width; i++)
    {
      if (rowAt(the_y)[i] == null)
      {
        boolean occupied = false;
        for (int j = 0; j < Piece.NUMBER_OF_BLOCKS; j++)
        {
          final Point block_pos = projection.absolutePosition(i);
          if (block_pos.x() == i && block_pos.y() == the_y)
          {
            occupied = true;
          }
        }
        result = result && occupied;
      }
    }
    return result;
  }

  // @constraint Valid block positions are between (0, 0) and
  // (width - 1, height + (rows above board) - 1), inclusive.
  /*
   * @ public behavior requires isWithinBoard(the_point); requires (\exists int
   * i; 0 <= i && i < Piece.NUMBER_OF_BLOCKS;
   * current_piece.absolutePosition(i).equals(the_point)); ensures \result ==
   * current_piece.color; @
   */
  /*
   * @ also public behavior requires isWithinBoard(the_point); requires
   * !(\exists int i; 0 <= i && i < Piece.NUMBER_OF_BLOCKS;
   * current_piece.absolutePosition(i).equals(the_point)); ensures \result ==
   * rowAt(the_point.y)[the_point.x]; @
   */
  /**
   * @param the_point The position.
   * @return What color is the block at position (the_x, the_y) (including
   *         blocks in the current piece)?
   */
  public/* @ pure nullable @ */Color color(final Point the_point)
  {
    Color result = rowAt(the_point.y())[the_point.x()];
    if (result == null)
    {
      // see if the current piece has a block there
      for (int i = 0; i < Piece.NUMBER_OF_BLOCKS && (result == null); i++)
      {
        final Point p = currentPiece().absolutePosition(i);
        if (p.equals(the_point))
        {
          result = currentPiece().color();
        }
      }
    }
    return result;
  }

  /*
   * @ ensures \result <==> (\exists int b; 0 <= b && b <
   * Piece.NUMBER_OF_BLOCKS; !isWithinBoard(the_piece.absolutePosition(b)) ||
   * rowAt(the_piece.absolutePosition(b).y) [the_piece.absolutePosition(b).x] !=
   * null); @
   */
  // the next postcondition is to help ESC/Java2
  /*
   * @ ensures !\result ==> (\forall int b; 0 <= b && b <
   * Piece.NUMBER_OF_BLOCKS; isWithinBoard(the_piece.absolutePosition(b))); @
   */
  /**
   * @param the_piece The piece.
   * @return Does the_piece collide with the already-frozen pieces or the
   *         boundaries of the board?
   */
  public/* @ pure @ */boolean collides(final/* @ non_null @ */Piece the_piece)
  {
    boolean result = false;
    for (int i = 0; !result && i < Piece.NUMBER_OF_BLOCKS; i++)
    {
      final Point block_pos = the_piece.absolutePosition(i);
      result = result || !isWithinBoard(block_pos);
      result = result || rowAt(block_pos.y())[block_pos.x()] != null;
    }
    return result;
  }

  /**
   * @return How many lines were removed as a result of the last action?
   */
  // @ ensures \result == last_lines_removed;
  public/* @ pure @ */int lastLinesRemoved()
  {
    return my_last_lines_removed;
  }

  /**
   * @return How many blocks were placed as a result of the last action?
   */
  // @ ensures \result == last_blocks_placed;
  public/* @ pure @ */int lastBlocksPlaced()
  {
    return my_last_blocks_placed;
  }

  /**
   * @return Did the state of the board change as a result of the last action?
   */
  // @ ensures \result == changed;
  public/* @ pure @ */boolean changed()
  {
    return my_changed_flag;
  }

  /**
   * @return What is the current piece?
   */
  // @ ensures \result == current_piece;
  public/* @ pure non_null @ */Piece currentPiece()
  {
    return my_current_piece;
  }

  /*
   * @ ensures (\forall int i; 0 <= i && i < Piece.NUMBER_OF_BLOCKS;
   * isWithinBoard(\result.absolutePosition(i))); @
   */
  /**
   * @return What is the projection of the current piece?
   */
  public/* @ pure non_null @ */Piece projection()
  {
    Piece projection = my_current_piece;
    Piece next_move = projection.moveDown();
    while (!collides(next_move))
    {
      projection = next_move;
      next_move = projection.moveDown();
    }
    return projection;
  }

  /**
   * @return What is the next piece?
   */
  // @ ensures \result == next_piece;
  public/* @ pure non_null @ */Piece nextPiece()
  {
    return my_next_piece;
  }

  /**
   * @return Is the board full?
   */
  // @ ensures \result == full;
  public/* @ pure @ */boolean isFull()
  {
    return my_full_flag;
  }

  // Commands

  // @constraint A rotate, move left, or move right that would cause the
  // current piece to collide with a block or extend past the side or
  // bottom of the board has no effect.
  /*
   * @ public behavior requires collides(current_piece.moveLeft()); assignable
   * last_lines_removed, last_blocks_placed, changed; ensures !changed; ensures
   * current_piece == \old(current_piece); ensures last_lines_removed == 0;
   * ensures last_blocks_placed == 0; @
   */
  /*
   * @ also public behavior requires !collides(current_piece.moveLeft());
   * assignable current_piece, last_lines_removed, last_blocks_placed, changed,
   * _changed; ensures changed; ensures
   * current_piece.equals(\old(current_piece.moveLeft())); ensures
   * last_lines_removed == 0; ensures last_blocks_placed == 0; @
   */
  /**
   * Move the current piece left, if possible!
   */
  public void moveLeft()
  {
    my_last_lines_removed = 0;
    my_last_blocks_placed = 0;
    my_changed_flag = false;
    final Piece moved = currentPiece().moveLeft();
    if (!collides(moved))
    {
      my_current_piece = moved;
      my_changed_flag = true;
      setChanged();
    }
    notifyObservers();
  }

  // @constraint A rotate, move left, or move right that would cause the
  // current piece to collide with a block or extend past the side or
  // bottom of the board has no effect.
  /*
   * @ public behavior requires collides(current_piece.moveRight()); assignable
   * last_lines_removed, last_blocks_placed, changed; ensures !changed; ensures
   * current_piece == \old(current_piece); ensures last_lines_removed == 0;
   * ensures last_blocks_placed == 0; @
   */
  /*
   * @ also public behavior requires !collides(current_piece.moveRight());
   * assignable current_piece, last_lines_removed, last_blocks_placed, changed,
   * _changed; ensures changed; ensures
   * current_piece.equals(\old(current_piece.moveRight())); ensures
   * last_lines_removed == 0; ensures last_blocks_placed == 0; @
   */
  /**
   * Move the current piece right, if possible!
   */
  public void moveRight()
  {
    my_last_lines_removed = 0;
    my_last_blocks_placed = 0;
    my_changed_flag = false;
    final Piece moved = currentPiece().moveRight();
    if (!collides(moved))
    {
      my_current_piece = moved;
      my_changed_flag = true;
      setChanged();
    }
    notifyObservers();
  }

  /*
   * @ public behavior requires !collides(current_piece.moveDown()); assignable
   * current_piece, last_lines_removed, last_blocks_placed, changed, _changed;
   * ensures current_piece.equals(\old(current_piece.moveDown())); ensures
   * last_lines_removed == 0; ensures last_blocks_placed == 0; ensures changed;
   * @
   */
  // @constraint When a row is removed, the position of each row above it is
  // reduced by 1 and an empty row is inserted at position (height - 1).
  // @constraint The board becomes full when a piece is frozen with any block
  // above the board.
  /*
   * @ also public behavior requires collides(current_piece.moveDown());
   * assignable current_piece, next_piece, last_lines_removed,
   * last_blocks_placed, changed, _changed, row_list, full; ensures
   * last_lines_removed == (\num_of int y; 0 <= y && y < height +
   * ROWS_ABOVE_BOARD; \old(isRowFull(y))); ensures last_blocks_placed ==
   * Piece.NUMBER_OF_BLOCKS; ensures current_piece == \old(next_piece); ensures
   * \fresh(next_piece); ensures next_piece == piece_generator.last_piece;
   * ensures changed; ensures (\exists int y; height <= y && y < height +
   * ROWS_ABOVE_BOARD; !isRowEmpty(y)) ==> full; ensures (\forall int y; 0 <= y
   * && y < height + ROWS_ABOVE_BOARD; \old(isRowFull(y)) <==>
   * !row_list.contains(\old(rowAt(y)))) && (\forall int b, c;
   * !\old(isRowFull(b)) && !\old(isRowFull(c)) && b < c;
   * row_list.indexOf(\old(rowAt(b))) < row_list.indexOf(\old(rowAt(c))));
   * ensures (\forall int y; height() + ROWS_ABOVE_BOARD - last_lines_removed <=
   * y && y < height() + ROWS_ABOVE_BOARD; isRowEmpty(y)); ensures (\forall int
   * b; 0 <= b && b < Piece.NUMBER_OF_BLOCKS; \old(current_piece.color.equals
   * (rowAt(current_piece.absolutePosition(b).y)
   * [current_piece.absolutePosition(b).x]))); @
   */
  /*
   * the last assertion requires that we actually fill in the row correctly
   * before it disappears; however, this is exactly what we do, so it's OK.
   */
  /**
   * Move the current piece down, if possible!
   */
  public void moveDown()
  {
    final Piece moved = currentPiece().moveDown();
    my_last_lines_removed = 0;
    if (collides(moved))
    {
      // freeze the current piece
      for (int i = 0; i < Piece.NUMBER_OF_BLOCKS; i++)
      {
        final Point block_pos = currentPiece().absolutePosition(i);
        ((Color[]) my_row_list.get(block_pos.y()))[block_pos.x()] = currentPiece().color();
      }

      // clear all full rows
      clearFullRows();

      // check for end of game
      for (int y = height(); !my_full_flag && y < my_row_list.size(); y++)
      {
        my_full_flag = !isRowEmpty(y);
      }

      // add empty rows at the top
      for (int i = 0; i < my_last_lines_removed; i++)
      {
        my_row_list.add(new Color[my_width]);
      }

      // replace the current piece with the next piece, and adjust
      // my_last_blocks_placed
      my_current_piece = my_next_piece;
      my_next_piece = my_piece_generator.next();
      my_last_blocks_placed = Piece.NUMBER_OF_BLOCKS;
    }
    else
    {
      // we actually just move the piece down if it doesn't collide
      my_current_piece = moved;
      my_last_blocks_placed = 0;
    }
    my_changed_flag = true;
    setChanged();
    notifyObservers();
  }

  // @constraint A rotate, move left, or move right that would cause the
  // current piece to collide with a block or extend past the side or
  // bottom of the board has no effect.
  /*
   * @ public behavior requires collides(current_piece.rotateClockwise());
   * assignable last_lines_removed, last_blocks_placed, changed; ensures
   * !changed; ensures current_piece == \old(current_piece); ensures
   * last_lines_removed == 0; ensures last_blocks_placed == 0; @
   */
  /*
   * @ also public behavior requires !collides(current_piece.rotateClockwise());
   * assignable current_piece, last_lines_removed, last_blocks_placed, changed,
   * _changed; ensures changed; ensures
   * current_piece.equals(\old(current_piece.rotateClockwise())); ensures
   * last_lines_removed == 0; ensures last_blocks_placed == 0; @
   */
  /**
   * Rotate the current piece clockwise, if possible!
   */
  public void rotateClockwise()
  {
    my_last_lines_removed = 0;
    my_last_blocks_placed = 0;
    my_changed_flag = false;
    final Piece moved = currentPiece().rotateClockwise();
    if (!collides(moved))
    {
      my_current_piece = moved;
      my_changed_flag = true;
      setChanged();
    }
    notifyObservers();
  }

  // @constraint A rotate, move left, or move right that would cause the
  // current piece to collide with a block or extend past the side or
  // bottom of the board has no effect.
  /*
   * @ public behavior requires
   * collides(current_piece.rotateCounterclockwise()); assignable
   * last_lines_removed, last_blocks_placed, changed; ensures !changed; ensures
   * current_piece == \old(current_piece); ensures last_lines_removed == 0;
   * ensures last_blocks_placed == 0; @
   */
  /*
   * @ also public behavior requires
   * !collides(current_piece.rotateCounterclockwise()); assignable
   * current_piece, last_lines_removed, last_blocks_placed, changed, _changed;
   * ensures changed; ensures current_piece.equals
   * (\old(current_piece.rotateCounterclockwise())); ensures last_lines_removed
   * == 0; ensures last_blocks_placed == 0; @
   */
  /**
   * Rotate the current piece counterclockwise, if possible!
   */
  public void rotateCounterclockwise()
  {
    my_last_lines_removed = 0;
    my_last_blocks_placed = 0;
    my_changed_flag = false;
    final Piece moved = currentPiece().rotateCounterclockwise();
    if (!collides(moved))
    {
      my_current_piece = moved;
      my_changed_flag = true;
      setChanged();
    }
    notifyObservers();
  }

  // @constraint When a row is removed, the position of each row above it is
  // reduced by 1 and an empty row is inserted at position (height - 1).
  // @constraint The board becomes full when a piece is frozen with any block
  // above the board.
  /*
   * @ public behavior assignable current_piece, next_piece, last_lines_removed,
   * last_blocks_placed, changed, _changed, row_list, full; ensures
   * last_lines_removed == (\num_of int y; 0 <= y && y < height +
   * ROWS_ABOVE_BOARD; \old(isRowFullUnderProjection(y))); ensures
   * last_blocks_placed == Piece.NUMBER_OF_BLOCKS; ensures current_piece ==
   * \old(next_piece); ensures \fresh(next_piece); ensures next_piece ==
   * piece_generator.last_piece; ensures changed; ensures (\exists int y; height
   * <= y && y < height + ROWS_ABOVE_BOARD; !isRowEmpty(y)) ==> full; ensures
   * (\forall int y; 0 <= y && y < height + ROWS_ABOVE_BOARD;
   * \old(isRowFullUnderProjection(y)) <==> !row_list.contains(\old(rowAt(y))))
   * && (\forall int b, c; !\old(isRowFullUnderProjection(b)) &&
   * !\old(isRowFullUnderProjection(c)) && b < c;
   * row_list.indexOf(\old(rowAt(b))) < row_list.indexOf(\old(rowAt(c))));
   * ensures (\forall int y; height() + ROWS_ABOVE_BOARD - last_lines_removed <=
   * y && y < height() + ROWS_ABOVE_BOARD; isRowEmpty(y)); ensures (\forall int
   * b; 0 <= b && b < Piece.NUMBER_OF_BLOCKS; \old(projection().color.equals
   * (rowAt(projection().absolutePosition(b).y)
   * [projection().absolutePosition(b).x]))); @
   */
  /*
   * the last assertion requires that we actually fill in the row correctly
   * before it disappears; however, this is exactly what we do, so it's OK.
   */
  /**
   * Drop the current piece!
   */
  public void drop()
  {
    // replace the current piece with its projection to the bottom, then it's
    // just a normal moveDown.
    my_current_piece = projection();
    moveDown();
  }

  /**
   * @return What is your printable representation?
   */
  public/* @ non_null @ */String toString()
  {
    final StringBuffer sb = new StringBuffer();
    // we'll generate a String by going row by row down the list of rows;
    // we only render the visible parts of the board
    for (int y = height() - 1; 0 <= y; y--)
    {
      sb.append(SIDE_BORDER_CHAR);
      for (int x = 0; x < width(); x++)
      {
        if (color(new Point(x, y)) == null)
        {
          sb.append(EMPTY_BLOCK_CHAR);
        }
        else if (rowAt(y)[x] == null)
        {
          sb.append(CURRENT_PIECE_BLOCK_CHAR);
        }
        else
        {
          sb.append(FROZEN_BLOCK_CHAR);
        }
      }
      sb.append(SIDE_BORDER_CHAR);
      sb.append('\n');
    }
    for (int x = 0; x <= width() + 1; x++)
    {
      sb.append(BOTTOM_BORDER_CHAR);
    }
    sb.append('\n');
    return sb.toString();
  }

  // Helper Methods

  /*
   * @ ensures \result <==> 0 <= the_point.x() && the_point.x() < width() && 0
   * <= the_point.y() && the_point.y() < height() + ROWS_ABOVE_BOARD; @
   */
  /**
   * @param the_point The point.
   * @return true if the specified point is within the boundaries of the board,
   *         including the play area above the board, and false otherwise.
   */
  public/* @ pure @ */boolean isWithinBoard(final/* @ non_null @ */Point the_point)
  {
    return 0 <= the_point.x() && the_point.x() < width() && 0 <= the_point.y() &&
           the_point.y() < height() + ROWS_ABOVE_BOARD;
  }

  /**
   * Initializes some of the data structures.
   */
  private/* @ helper @ */void initialize()
  {
    my_current_piece = my_piece_generator.next();
    my_next_piece = my_piece_generator.next();
    my_last_lines_removed = 0;
    my_last_blocks_placed = 0;
    my_changed_flag = true;
    my_full_flag = false;
    my_row_list.clear();
    for (int i = 0; i < my_height + ROWS_ABOVE_BOARD; i++)
    {
      my_row_list.add(new Color[my_width]);
    }
  }

  /*
   * @ private behavior assignable my_last_lines_removed, my_row_list;
   */
  /**
   * Clears all full rows.
   */
  private/* @ helper @ */void clearFullRows()
  {
    final ListIterator<Color[]> iterator = my_row_list.listIterator();
    while (iterator.hasNext())
    {
      iterator.next();
      if (isFrozenRowFull(iterator.previousIndex()))
      {
        iterator.remove();
        my_last_lines_removed = my_last_lines_removed + 1;
      }
    }
  }

  // Constraints (that are not in method contracts)

  // @constraint All rows have length equal to the board width.
  /*
   * @ invariant (\forall int i; 0 <= i && i < height + ROWS_ABOVE_BOARD;
   * ((Color[]) row_list.get(i)).length == width); @
   */

  // @constraint After a move or drop, no row has all blocks frozen.
  /*
   * @ invariant (\forall int i; 0 <= i && i < height() + ROWS_ABOVE_BOARD;
   * (\exists int j; 0 <= j && j < width(); color(new Point(i, j)) == null)); @
   */
}
