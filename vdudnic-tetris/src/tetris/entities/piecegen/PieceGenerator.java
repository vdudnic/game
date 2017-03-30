/*
 * An implementation of the classic game "Tetris".
 * @author "Daniel M. Zimmerman (dmz@acm.org)"
 * @module "TCSS 305"
 * @creation_date "July 2008"
 * @last_updated_date "July 2008"
 * @keywords "Tetris", "game"
 */

package tetris.entities.piecegen;

import tetris.entities.pieces.Piece;

/**
 * A generator for Tetris pieces.
 *
 * @author Daniel M. Zimmerman (dmz@acm.org)
 * @version 29 July 2008
 */
public interface PieceGenerator 
{
  /*@ public ghost Piece last_piece; @*/

  /**
   * @return the next piece.
   */
  //@ ensures last_piece == \result;
  /*@ non_null @*/ Piece next();
}
