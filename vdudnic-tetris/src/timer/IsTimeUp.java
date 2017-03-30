package timer;
/**
 * All classes that want to receive time up events need to implement this.
 * @author Vladimir Dudnic
 * @version 1.0
 *
 */
public interface IsTimeUp 
{
  /**
   * 
   * @param the_event is an timer event.
   */
  void timesUp(TimerEvent the_event);   
}

