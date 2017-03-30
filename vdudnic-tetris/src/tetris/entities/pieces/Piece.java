/*
 * An implementation of the classic game "Tetris". Class Piece.
 * 
 * @author "Daniel M. Zimmerman (dmz@acm.org)"
 * 
 * @module "TCSS 305"
 * 
 * @creation_date "July 2008"
 * 
 * @last_updated_date "July 2008"
 * 
 * @keywords "Tetris", "game"
 */

package tetris.entities.pieces;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tetris.entities.Point;

/**
 * A set of rotations, with position and color information.
 * 
 * @author Daniel M. Zimmerman (dmz@acm.org)
 * @version 29 July 2008
 */
public abstract class Piece implements Cloneable
{
  // partially subsumes BLOCK (color)
  // Static Fields
  /**
   * The number of blocks in a piece.
   */
  public static final int NUMBER_OF_BLOCKS = 4;

  /**
   * The character used in the String representation to represent an empty
   * block.
   */
  public static final char EMPTY_BLOCK_CHAR = ' ';

  /**
   * The character used in the String representation to represent a full block.
   */
  public static final char FULL_BLOCK_CHAR = '+';

  // Instance Fields

  /**
   * The current rotation number.
   */
  private int my_current_rotation;

  /*
   * @ public model int current_rotation; public invariant 0 <= current_rotation
   * && current_rotation < rotations.size(); private represents current_rotation
   * = my_current_rotation; @
   */

  /**
   * The color.
   */
  private final/* @ non_null @ */Color my_color;

  /*
   * @ public model non_null Color color; public constraint \old(color) ==
   * color; private represents color = my_color;
   */

  /**
   * The list of rotations.
   */
  private final/* @ non_null @ */List<Rotation> my_rotations;

  /*
   * @ public model non_null List rotations; public constraint
   * \old(rotations).equals(rotations); public invariant (\forall int i; 0 <= i
   * && i < rotations.size(); rotations.get(i) != null); public invariant
   * rotations.elementType == \type(Rotation); private represents rotations =
   * my_rotations; private invariant (\forall int i; 0 <= i && i <
   * my_rotations.size(); my_rotations.get(i) != null); private invariant
   * my_rotations.elementType == \type(Rotation); @
   */

  /**
   * The origin.
   */
  private/* @ non_null @ */Point my_origin;

  /*
   * @ public model non_null Point origin; private represents origin =
   * my_origin;
   */

  /* the current set of blocks */
  /*
   * @ public model non_null Point[] blocks; public represents blocks =
   * ((Rotation) rotations.get(current_rotation)).blocks; @
   */

  // Constructor

  // @ requires the_rotations.elementType == \type(Rotation);
  /*
   * @ requires (\forall Rotation r; the_rotations.contains(r); r.blocks.length
   * == NUMBER_OF_BLOCKS); @
   */
  /*
   * @ requires the_rotations.size() == 1 || the_rotations.size() == 2 ||
   * the_rotations.size() == 4; @
   */
  // @ ensures color == the_color;
  // @ ensures rotations.equals(the_rotations);
  // @ ensures rotations.elementType == \type(Rotation);
  // TODO: the above shouldn't be needed
  // @ ensures current_rotation == 0;
  // @ ensures origin.x == 0 && origin.y == 0;
  /**
   * These are your rotations! This is your color!
   * 
   * @param the_color The color.
   * @param the_rotations The rotations.
   */
  public Piece(final/* @ non_null @ */Color the_color,
               final/* @ non_null @ */List<Rotation> the_rotations)
  {
    my_color = the_color; // colors are immutable
    // @ assume the_rotations.size()*1.1 <= Integer.MAX_VALUE;
    // TODO: shouldn't be necessary, as it must be 1, 2 or 4
    final List<Rotation> temp =
        Collections.unmodifiableList(new ArrayList<Rotation>(the_rotations));
    // TODO: No specification for Collections? That's why we need "temp"...
    /*
     * @ assume temp != null && (temp.size() == 1 || temp.size() == 2 ||
     * temp.size() == 4) && temp.elementType == \type(Rotation); @
     */
    my_rotations = temp;
    my_current_rotation = 0;
    my_origin = new Point(0, 0);
  }

  // Queries

  /**
   * @return What are your rotations?
   */
  // @ ensures \result.equals(rotations);
  public/* @ pure non_null */List<Rotation> rotations()
  {
    return my_rotations;
  }

  /**
   * @return What is your current set of blocks?
   */
  // @ ensures \result == blocks;
  public/* @ pure non_null */Point[] blocks()
  {
    // @ assume \typeof(rotations().get(my_current_rotation)) ==
    // \type(Rotation);
    // TODO: shouldn't be necessary because of invariant above
    return ((Rotation) rotations().get(my_current_rotation)).blocks();
  }

  /**
   * @return What is your origin?
   */
  // @ ensures \result == origin;
  public/* @ pure non_null */Point origin()
  {
    return my_origin;
  }

  /**
   * @return What is your color?
   */
  // @ ensures \result == color;
  public/* @ pure non_null */Color color()
  {
    return my_color;
  }

  /**
   * @param the_index The index.
   * @return What is the absolute position of block number the_index?
   */
  // @ requires 0 <= the_index && the_index <= blocks.length;
  // @ ensures \result.x == origin.x + blocks[the_index].x;
  // @ ensures \result.y == origin.y + blocks[the_index].y;
  public/* @ pure non_null */Point absolutePosition(final int the_index)
  {
    return new Point(origin().x() + blocks()[the_index].x(), origin().y() +
                                                             blocks()[the_index].y());
  }

  /**
   * @return What piece results from moving you left?
   */
  // @constraint Moving left decreases the x-coordinate of
  // the origin by 1.
  // @ ensures \result.equalsExceptOrigin(this);
  // @ ensures \result.origin.x == origin.x - 1;
  // @ ensures \result.origin.y == origin.y;
  public/* @ pure non_null @ */Piece moveLeft()
  {
    Piece result = null;
    try
    {
      result = (Piece) clone();
      result.my_origin = new Point(my_origin.x() - 1, my_origin.y());
    }
    catch (final CloneNotSupportedException e)
    {
      // this should never happen
      // @ assert false;
      assert false;
    }
    return result;
  }

  /**
   * @return What piece results from moving you right?
   */
  // @constraint Moving right increases the x-coordinate of
  // the origin by 1.
  // @ ensures \result.equalsExceptOrigin(this);
  // @ ensures \result.origin.x == origin.x + 1;
  // @ ensures \result.origin.y == origin.y;
  public/* @ pure non_null @ */Piece moveRight()
  {
    Piece result = null;
    try
    {
      result = (Piece) clone();
      result.my_origin = new Point(my_origin.x() + 1, my_origin.y());
    }
    catch (final CloneNotSupportedException e)
    {
      // this should never happen
      // @ assert false;
      assert false;
    }
    return result;
  }

  /**
   * @return What piece results from moving you down?
   */
  // @constraint Moving down decreases the y-coordinate of
  // the origin by 1.
  // @ ensures \result.equalsExceptOrigin(this);
  // @ ensures \result.origin.x == origin.x;
  // @ ensures \result.origin.y == origin.y - 1;
  public/* @ pure non_null @ */Piece moveDown()
  {
    Piece result = null;
    try
    {
      result = (Piece) clone();
      result.my_origin = new Point(my_origin.x(), my_origin.y() - 1);
    }
    catch (final CloneNotSupportedException e)
    {
      // this should never happen
      // @ assert false;
      assert false;
    }
    return result;
  }

  // @constraint Rotating clockwise changes from rotation k
  // to rotation (k + 1) mod number of rotations.
  // @ ensures \result.equalsExceptRotation(this);
  /*
   * @ ensures \result.current_rotation == (current_rotation + 1) %
   * rotations.size(); @
   */
  /**
   * @return What piece results from rotating you clockwise?
   */
  public/* @ pure non_null @ */Piece rotateClockwise()
  {
    Piece result = null;
    try
    {
      result = (Piece) clone();
      result.my_current_rotation = (my_current_rotation + 1) % rotations().size();
    }
    catch (final CloneNotSupportedException e)
    {
      // this should never happen
      // @ assert false;
      assert false;
    }
    return result;
  }

  // @constraint Rotating counterclockwise changes from rotation k
  // to rotation (k - 1) mod number of rotations.
  // @ ensures \result.equalsExceptRotation(this);
  /*
   * @ ensures \result.current_rotation == (current_rotation + rotations.size()
   * - 1) % rotations.size(); @
   */// java's % operator isn't really "mod"
  /**
   * @return What piece results from rotating you counterclockwise?
   */
  public/* @ pure non_null @ */Piece rotateCounterclockwise()
  {
    Piece result = null;
    try
    {
      result = (Piece) clone();
      result.my_current_rotation =
          (my_current_rotation + rotations().size() - 1) % rotations().size();
    }
    catch (final CloneNotSupportedException e)
    {
      // this should never happen
      // @ assert false;
      assert false;
    }
    return result;
  }

  /**
   * @return What piece results from setting your origin to the_origin?
   * 
   * @param the_origin The new origin.
   */
  // @ ensures \result.equalsExceptOrigin(this);
  // @ ensures \result.origin.equals(the_origin);
  public/* @ pure non_null @ */Piece setOrigin(final/* @ non_null */Point the_origin)
  {
    Piece result = null;
    try
    {
      result = (Piece) clone();
      result.my_origin = the_origin;
    }
    catch (final CloneNotSupportedException e)
    {
      // this should never happen
      // @ assert false;
      assert false;
    }
    return result;
  }

  // Comparison Methods

  /*
   * @ ensures \result <==> the_other_piece.color.equals(color) &&
   * the_other_piece.origin.equals(origin) && the_other_piece.rotations.size()
   * == rotations.size() && (\forall int i; 0 <= i && i < rotations.size();
   * the_other_piece.rotations.get(i).equals(rotations.get(i))); @
   */
  /**
   * @param the_other_piece The other piece.
   * @return true if this piece and the_other_piece are equivalent in every
   *         aspect except their current rotation, false otherwise.
   */
  public/* @ pure */boolean equalsExceptRotation(final/* @ non_null */Piece the_other_piece)
  {
    boolean result =
        the_other_piece.color().equals(color()) &&
            the_other_piece.my_origin.equals(my_origin) &&
            the_other_piece.rotations().size() == rotations().size();
    for (int i = 0; result && i < rotations().size(); i++)
    {
      result = result && the_other_piece.rotations().get(i).equals(rotations().get(i));
    }
    return result;
  }

  /*
   * @ ensures \result <==> the_other_piece.color.equals(color) &&
   * the_other_piece.current_rotation == current_rotation &&
   * the_other_piece.rotations.size() == rotations.size() && (\forall int i; 0
   * <= i && i < rotations.size();
   * the_other_piece.rotations.get(i).equals(rotations.get(i))); @
   */
  /**
   * @param the_other_piece The other piece.
   * @return true if this piece and the_other_piece are equivalent in every
   *         aspect except their current origin, false otherwise.
   */
  public/* @ pure */boolean equalsExceptOrigin(final/* @ non_null */Piece the_other_piece)
  {
    boolean result =
        the_other_piece.color().equals(color()) &&
            the_other_piece.my_current_rotation == my_current_rotation &&
            the_other_piece.rotations().size() == rotations().size();
    for (int i = 0; result && i < rotations().size(); i++)
    {
      result = result && the_other_piece.rotations().get(i).equals(rotations().get(i));
    }
    return result;
  }

  // Clone Methods

  /**
   * {@inheritDoc}
   */
  public/* @ pure non_null */Object clone() throws CloneNotSupportedException
  {
    return (Piece) super.clone();
  }

  /*
   * @ also public behavior requires the_other != null && \typeof(the_other) ==
   * \typeof(this); ensures \result <==> ((Piece) the_other).color.equals(color)
   * && ((Piece) the_other).origin.equals(origin) && ((Piece)
   * the_other).current_rotation == current_rotation && ((Piece)
   * the_other).rotations.size() == rotations.size() && (\forall int i; 0 <= i
   * && i < rotations.size(); ((Piece) the_other).rotations.get(i).equals
   * (rotations.get(i))); @
   */
  /*
   * @ also public behavior requires the_other == null || \typeof(the_other) !=
   * \typeof(this); ensures !\result; @
   */
  /**
   * {@inheritDoc}
   */
  public/* @ pure */boolean equals(final/* @ nullable @ */Object the_other)
  {
    boolean result = this == the_other;
    if (!result && the_other != null && the_other.getClass() == getClass())
    {
      final Piece other_piece = (Piece) the_other;
      result = other_piece.color().equals(color());
      result = result && other_piece.my_origin.equals(my_origin);
      result = result && (other_piece.my_current_rotation == my_current_rotation);
      for (int i = 0; result && i < rotations().size(); i++)
      {
        result = result && other_piece.rotations().get(i).equals(rotations().get(i));
      }
    }
    return result;
  }

  /*
   * @ public represents theHashCode = color.theHashCode + origin.theHashCode +
   * current_rotation; @
   */
  /**
   * {@inheritDoc}
   */
  public/* @ pure */int hashCode()
  {
    return my_color.hashCode() + my_origin.hashCode() + my_current_rotation; // @
                                                                             // nowarn
                                                                             // Modifies;
  }

  /**
   * @return What is your printable representation?
   */
  public/* @ non_null @ */String toString()
  {
    final StringBuffer sb = new StringBuffer();
    // we'll generate a String by going line by line and checking for blocks
    for (int y = NUMBER_OF_BLOCKS - 1; 0 <= y; y--)
    {
      for (int x = 0; x < NUMBER_OF_BLOCKS; x++)
      {
        for (int i = 0; i < NUMBER_OF_BLOCKS; i++)
        {
          final Point pos = blocks()[i];
          if (pos.x() == x && pos.y() == y)
          {
            sb.append(FULL_BLOCK_CHAR);
          }
          else
          {
            sb.append(EMPTY_BLOCK_CHAR);
          }
        }
      }
      sb.append('\n');
    }
    sb.append("\nOrigin: ");
    sb.append(origin());
    sb.append('\n');
    return sb.toString();
  }

  // Constraints (not assigned to methods)

  // @constraint A piece has 4 blocks.
  // @ public invariant blocks.length == NUMBER_OF_BLOCKS;

  // @constraint All rotations of a piece have 4 blocks.
  /*
   * @ public invariant (\forall int i; 0 <= i && i < rotations.size();
   * ((Point[]) ((Rotation) rotations.get(i)).blocks).length ==
   * NUMBER_OF_BLOCKS); @
   */

  // @constraint A piece has either 1, 2 or 4 rotations.
  /*
   * @ public invariant rotations.size() == 1 || rotations.size() == 2 ||
   * rotations.size() == 4; @
   */
}
