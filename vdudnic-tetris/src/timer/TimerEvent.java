package timer;
/**
 * 
 * @author Vladimir Dudnic
 * @version 1.0
 */
public class TimerEvent
{
  /**
   * the object.
   */
  private final Object my_source; 
  
  /** 
   * Creates a new instance of TimerEvent.
   * @param the_source is a object.
   */ 
  public TimerEvent(final Object the_source) 
  {
    my_source = the_source;
  }
  /**
   * 
   * @return my_source 
   */
  public Object getSource()
  {
    return my_source;
  } 
  
}
