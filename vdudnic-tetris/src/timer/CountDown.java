
package timer;

import java.util.Calendar;
/**
 * 
 * @author Vladimir Dudnic 
 * @version 1.0
 */
public class CountDown
{

  /**
   * Holds the time that it will be when the time runs out.
   */
  private final Calendar my_run_time;

  /**
   * Holds a timer instance, used to get pause time.
   */
  private Timer my_pause;
  /**
   * Holds the amount of time the countdown has been paused.
   */
  private long my_pause_time;
  /**
   * Holds the amount of time to go until times up.
   */
  private long my_time_to_go;
  /**
   *  Holds the pause state of the count down.
   */
  private boolean my_paused_state;

  /**
   * count down timer. 
   */
  private IsTimeUp my_countdown_listners;

  /** 
   * Creates a new instance of CountDown. 
   * @param the_hours is hours
   * @param the_minutes is minutes
   * @param the_seconds is seconds
   * @param the_millisecs is millisecs
   */
  public CountDown(final int the_hours, final int the_minutes, 
                   final int the_seconds, final int the_millisecs)
  {
    my_run_time = Calendar.getInstance();
     
    my_run_time.set(Calendar.HOUR_OF_DAY, 
                    my_run_time.get(Calendar.HOUR_OF_DAY) + the_hours); 
    my_run_time.set(Calendar.MINUTE,
                    my_run_time.get(Calendar.MINUTE) + the_minutes); 
    my_run_time.set(Calendar.SECOND,
                    my_run_time.get(Calendar.SECOND) + the_seconds); 
    my_run_time.set(Calendar.MILLISECOND,
                    my_run_time.get(Calendar.MILLISECOND) + the_millisecs); 
  }

  /**
   *  Return the amount of time until the times up.
   * @return my_time_to_go
   */
  public long getTimeToGo()
  {
     
    if (!this.my_paused_state)
    {
      this.my_time_to_go =
          (my_run_time.getTime().getTime() + this.my_pause_time) - System.currentTimeMillis();
    
      if (this.my_time_to_go <= 0)
      {
        this.noTimeLeftEvent();
      }
    }
    // Return the amount of time to go
    return this.my_time_to_go;
  }

  /**
   * Takes a long value representing a date and formats it into
   * hours,minutes,seconds,milliseconds.
   * @param the_date is date.
   * @return string representing date.
   */
  public String formatAsDate(final long the_date)
  {
    return Timer.formatAsDate(the_date);
  }

  /**
   *  Gets the pause state of the timer.
   * @return my_paused_state
   */
  public boolean isPausedState()
  {
    return this.my_paused_state;
  }

  /**
   *  Pauses or resumes a timer.
   * @param the_pause is pause
   */
  public void pause(final boolean the_pause)
  {
    if (the_pause)
    {
      my_pause = new Timer();
    }
    else
    {
      my_pause_time += my_pause.getTimePassed();
    }
    my_paused_state = the_pause;
  }

  /**
   *  Loops through all the registered listeners  
   *  and gives them a new TimerEvent.
   */
  private void noTimeLeftEvent()
  {
    my_countdown_listners.timesUp(new TimerEvent(this));
  }

  /**
   *  Classes that want to listen for TimerEvents need to register here.
   * @param the_alarm 
   */
  public synchronized void addTimesUpListener(final IsTimeUp the_alarm)
  {
    my_countdown_listners = the_alarm;
  }
}


