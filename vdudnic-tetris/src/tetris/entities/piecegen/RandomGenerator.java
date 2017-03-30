/*
 * An implementation of the classic game "Tetris".
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

package tetris.entities.piecegen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tetris.entities.Point;
import tetris.entities.pieces.IPiece;
import tetris.entities.pieces.JPiece;
import tetris.entities.pieces.LPiece;
import tetris.entities.pieces.OPiece;
import tetris.entities.pieces.Piece;
import tetris.entities.pieces.SPiece;
import tetris.entities.pieces.TPiece;
import tetris.entities.pieces.ZPiece;

/**
 * A piece generator that uses random numbers to generate pieces based on an
 * initial seed value; it follows the Tetris convention of putting the seven
 * pieces in random order, giving them all, then repeating as necessary.
 * 
 * @author Daniel M. Zimmerman (dmz@acm.org)
 * @version 17 November 2008
 */
public class RandomGenerator implements PieceGenerator
{
  // Static Fields

  /**
   * The number of possible pieces.
   */
  public static final int NUMBER_OF_PIECES = 7;

  // Instance Fields
  /**
   * The random number generator used by this piece generator.
   */
  private final/* @ non_null @ */Random my_random;
  /*
   * @ in random; public model non_null Random random; in random; private
   * represents random = my_random; @
   */

  /**
   * The origin to use for random pieces.
   */
  private final/* @ non_null @ */Point my_origin;

  /*
   * @ public model non_null Point origin; public constraint \old(origin) ==
   * origin; private represents origin = my_origin; @
   */

  /**
   * The piece list, used to keep track of each "set" of pieces.
   */
  private final/* @ non_null @ */List<Piece> my_piece_list;

  /*
   * @ public model non_null List piece_list; public constraint \old(piece_list)
   * == piece_list; public constraint \old(piece_list.size()) == 0 ==>
   * piece_list.size() == NUMBER_OF_PIECES; public invariant 0 <
   * piece_list.size() && piece_list.size() <= NUMBER_OF_PIECES; public
   * invariant (\forall Piece p, q; piece_list.contains(p) &&
   * piece_list.contains(q); \typeof(p) != \typeof(q)); public invariant
   * (\forall Piece p; piece_list.contains(p); \typeof(p) <: \type(Piece));
   * private represents piece_list = my_piece_list; @
   */

  // Constructors

  /**
   * Constructs a new RandomGenerator with the specified random seed and the
   * specified piece origin.
   * 
   * @param the_seed The random seed.
   * @param the_origin The origin.
   */
  // @ ensures origin == the_origin;
  public RandomGenerator(final long the_seed, final/* @ non_null @ */Point the_origin)
  {
    my_random = new Random(the_seed);
    my_origin = the_origin;
    my_piece_list = new ArrayList<Piece>();
    fillPieceList();
  }

  // Instance Methods

  /*
   * @ also public behavior assignable random, last_piece, piece_list; ensures
   * \result.origin.equals(origin); ensures \result == last_piece; @
   */
  /**
   * {@inheritDoc}
   */
  public/* @ non_null @ */Piece next()
  {
    final int choice = my_random.nextInt(my_piece_list.size());
    final Piece result = (Piece) my_piece_list.get(choice);
    my_piece_list.remove(choice);
    if (my_piece_list.isEmpty())
    {
      fillPieceList();
    }
    /* @ set last_piece = result; @ */
    return result;
  }

  /*
   * @ private behavior requires my_piece_list.isEmpty(); assignable piece_list;
   * ensures my_piece_list.size() == NUMBER_OF_PIECES; @
   */
  /**
   * Populates the piece list with 7 pieces, one of each type.
   */
  private/* @ helper @ */void fillPieceList()
  {
    final Piece[] pieces =
        new Piece[] {new IPiece(), new JPiece(), new LPiece(), new OPiece(), new SPiece(),
          new TPiece(), new ZPiece()};
    for (Piece p : pieces)
    {
      my_piece_list.add(p.setOrigin(my_origin));
    }
  }
}
