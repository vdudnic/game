/*
 * An implementation of the classic game "Tetris".
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

package tetris.entities.piecegen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tetris.entities.pieces.OPiece;
import tetris.entities.pieces.Piece;

/**
 * A piece generator that repeatedly cycles through a specified sequence of
 * pieces.
 * 
 * @author Daniel M. Zimmerman (dmz@acm.org)
 * @version 29 July 2008
 */
public class SequenceGenerator implements PieceGenerator
{
  // Instance Fields

  /**
   * The sequence of pieces used by this piece generator.
   */
  private final/* @ non_null @ */List<Piece> my_sequence;

  /*
   * @ public model non_null List sequence; public constraint \old(sequence) ==
   * sequence; public constraint \old(sequence).equals(sequence); public
   * constraint (\forall int i; 0 <= i && i < sequence.size(); sequence.get(i)
   * != null && \typeof(sequence.get(i)) <: \type(Piece)); private represents
   * sequence = my_sequence; private constraint
   * \old(my_sequence).equals(my_sequence); private constraint (\forall int i; 0
   * <= i && i < my_sequence.size(); my_sequence.get(i) != null &&
   * \typeof(sequence.get(i)) <: \type(Piece)); @
   */

  /**
   * The current index in the sequence of pieces.
   */
  private int my_index;

  /*
   * @ in index; public model int index; in index; public invariant 0 <= index;
   * public invariant index < sequence.size(); public constraint \old(index) <
   * sequence.size() - 1 ==> index == \old(index) || index == \old(index) + 1;
   * public constraint \old(index) == sequence.size() - 1 ==> index ==
   * \old(index) || index == 0; private represents index = my_index; private
   * constraint \old(my_index) < my_sequence.size() - 1 ==> my_index ==
   * \old(my_index) || my_index == \old(my_index) + 1; private constraint
   * \old(my_index) == my_sequence.size() - 1 ==> my_index == \old(my_index) ||
   * my_index == 0; @
   */

  // Constructors

  // @ requires 0 < the_sequence.size();
  /*
   * @ requires (\forall int i; 0 <= i && i < the_sequence.size();
   * the_sequence.get(i) != null && \typeof(the_sequence.get(i)) <:
   * \type(Piece)); @
   */
  // @ ensures sequence.equals(the_sequence);
  /**
   * Constructs a new SequenceGenerator using the specified sequence.
   * 
   * @param the_sequence The sequence.
   */
  public SequenceGenerator(final/* @ non_null @ */List<Piece> the_sequence)
  {
    my_sequence = Collections.unmodifiableList(new ArrayList<Piece>(the_sequence));
    my_index = 0;
    /* @ set last_piece = null; @ */
  }

  // Instance Methods

  /*
   * @ also public behavior assignable index, last_piece; ensures
   * \result.equals((Piece) sequence.get(\old(index))); @
   */
  /**
   * {@inheritDoc}
   */
  public/* @ non_null @ */Piece next()
  {
    Piece result;
    try
    {
      result = (Piece) ((Piece) my_sequence.get(my_index)).clone();
      my_index = (my_index + 1) % my_sequence.size();
    }
    catch (final CloneNotSupportedException e)
    {
      // this should never happen
      // @ assert false;
      assert false;
      result = new OPiece();
    }
    /* @ set last_piece = result; @ */
    return result;
  }
}
