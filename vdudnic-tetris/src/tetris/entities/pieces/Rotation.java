/*
 * An implementation of the classic game "Tetris". Class Rotation.
 * 
 * @author "Daniel M. Zimmerman (dmz@acm.org)"
 * 
 * @module "TCSS 305"
 * 
 * @creation_date "July 2008"
 * 
 * @last_updated_date "November 2008"
 * 
 * @keywords "Tetris", "game"
 */

package tetris.entities.pieces;

import tetris.entities.Point;

/* @ model import java.util.Arrays; @ */

/**
 * A set of 4 adjacent blocks that share the same color.
 * 
 * @author Daniel M. Zimmerman (dmz@acm.org)
 * @version 17 November 2008
 */
public class Rotation
{
  // Instance Fields

  /**
   * The blocks.
   */
  private final/* @ non_null @ */Point[] my_blocks;

  /*
   * @ public model Point[] blocks; public invariant \nonnullelements(blocks);
   * private represents blocks = my_blocks; private invariant
   * \nonnullelements(my_blocks); @
   */

  // Constructor

  // @ requires \nonnullelements(the_blocks);
  /*
   * @ requires (\forall int i; 0 <= i && i < blocks.length; (\exists int j; 0
   * <= j && j < blocks.length && j != i; (blocks[i].x == blocks[j].x &&
   * Math.abs(blocks[i].y - blocks[j].y) == 1) || (blocks[i].y == blocks[j].y &&
   * Math.abs(blocks[i].x - blocks[j].x) == 1))); @
   */
  /*
   * @ requires (\forall int i; 0 <= i && i < the_blocks.length; 0 <=
   * the_blocks[i].x && the_blocks[i].x < the_blocks.length && 0 <=
   * the_blocks[i].y && the_blocks[i].y < the_blocks.length); @
   */
  // @ ensures Arrays.equals(blocks, the_blocks);
  /**
   * These are your blocks!
   * 
   * @param the_blocks The blocks.
   */
  public/* @ pure @ */Rotation(final/* @ non_null */Point[] the_blocks)
  {
    // we clone the array of blocks, but blocks are immutable.
    my_blocks = (Point[]) the_blocks.clone();
    // TODO: array cloning doesn't verify
    /*
     * @ assume my_blocks.length == the_blocks.length && (\forall int i; 0 <= i
     * && i < the_blocks.length; my_blocks[i] == the_blocks[i]); @
     */
  }

  // Queries

  /**
   * @return What are your blocks?
   */
  // @ ensures Arrays.equals(\result, blocks);
  public/* @ pure non_null */Point[] blocks()
  {
    final Point[] result = (Point[]) my_blocks.clone();
    // TODO: array cloning doesn't verify
    /*
     * @ assume result.length == my_blocks.length && (\forall int i; 0 <= i && i
     * < my_blocks.length; result[i] == my_blocks[i]); @
     */
    return result;
  }

  // Object Methods

  /*
   * @ also public behavior requires the_other != null && \typeof(the_other) ==
   * \typeof(this); ensures \result <==> ((Rotation) the_other).blocks.length ==
   * blocks.length && (\forall int i; 0 <= i && i < blocks.length; ((Rotation)
   * the_other).blocks[i].equals(blocks[i])); @
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
      final Rotation other_rotation = (Rotation) the_other;
      result = true;
      if (other_rotation.my_blocks.length == my_blocks.length)
      {
        for (int i = 0; i < my_blocks.length; i++)
        {
          result = result && other_rotation.my_blocks[i].equals(my_blocks[i]);
        }
      }
      else
      {
        result = false;
      }
    }
    return result;
  }

  /*
   * @ public represents theHashCode = (\sum int i; 0 <= i && i < blocks.length;
   * blocks[i].theHashCode); @
   */
  /**
   * {@inheritDoc}
   */
  public/* @ pure */int hashCode()
  {
    int result = 0;
    // TODO: numerical quantifiers seem not to verify
    /*
     * maintaining result == (\sum int j; 0 <= j && j < i;
     * my_blocks[j].hashCode()); @
     */
    /* decreasing my_blocks.length - i; @ */
    for (int i = 0; i < my_blocks.length; i++)
    {
      result = result + my_blocks[i].hashCode(); // @ nowarn Modifies;
    }
    // TODO: numerical quantifiers seem not to verify
    /* @ assume result == theHashCode; @ */
    return result;
  }

  // @constraint A rotation has no null blocks.
  // @ public invariant \nonnullelements(blocks);

  // @constraint Each block in a rotation is adjacent horizontally or
  // vertically to another block in the rotation.
  /*
   * @ public invariant (\forall int i; 0 <= i && i < blocks.length; (\exists
   * int j; 0 <= j && j < blocks.length && j != i; (blocks[i].x == blocks[j].x
   * && Math.abs(blocks[i].y - blocks[j].y) == 1) || (blocks[i].y == blocks[j].y
   * && Math.abs(blocks[i].x - blocks[j].x) == 1))); @
   */

  // @constraint The coordinates of all blocks in a rotation are nonnegative.
  // @constraint The maximum coordinate of a block in a rotation is the number
  // of blocks in the rotation - 1.
  /*
   * @ public invariant (\forall int i; 0 <= i && i < blocks.length; 0 <=
   * blocks[i].x && blocks[i].x < blocks.length && 0 <= blocks[i].y &&
   * blocks[i].y < blocks.length); @
   */
}
