package timer;


import java.util.Calendar;
import java.util.Date;
/**
 * 
 * @author Vladimir Dudnic
 * @version 1.0
 */
public class Timer
{
  /**
   * Creates a new calendar instance.
   */
  private static Calendar calendar = Calendar.getInstance(); 
  /**
   * Holds reference to the time when the timer has started.
   */
  private final long my_current_time;
  /**
   * Hold the amount of time passed since the timer has started.
   */
  private long my_time_passed;
  /**
   * Holds whether the timer is stopped.
   */
  private boolean my_stop;

  /**
   * Holds whether the timer is paused.
   */
  private boolean my_pause;
  /**
   * For calculating the pause time.
   */
  private Timer my_timer;
  /**
   * Holds the amount of time the timer has been paused.
   */
  private long my_pause_time;

  /**
   * Creates a new instance of Timer.
   */

  public Timer()
  {
    my_current_time = System.currentTimeMillis();
  }

  /**
   *  Sets the timer to stopped.
   */
  public void stop()
  {
    my_stop = true;
  }

  /**
   * Pauses or resumes a timer.
   * @param the_pause is pause
   */
  public void pause(final boolean the_pause)
  {
    if (the_pause)
    {
      my_timer = new Timer();
    }
    else
    {
      this.my_pause_time += my_timer.getTimePassed();
    }
    this.my_pause = the_pause;
  }

  /**
   *  Gets the total amount of time the timer has been paused for.
   * @return my_pause_time
   */
  public long getPauseTime()
  {
    return this.my_pause_time;
  }

  /**
   *  Gets the pause state of the timer.
   * @return my_pause is pause
   */
  public boolean isPaused()
  {
    return this.my_pause;
  }

  /**
   * Calculates and returns the amount of time the timer has been running for.
   * @return my_time_passed
   */
  public long getTimePassed()
  {
    if (!(this.my_stop && this.my_pause))
    {
      my_time_passed = System.currentTimeMillis() - this.my_current_time;
    }
    return my_time_passed;
  }

  /**
   * Takes a long value representing a date and formats it.
   * @param the_date is date
   * @return string representing date
   */
  public static String formatAsDate(final long the_date)
  {
    calendar.setTime(new Date(the_date));

    return calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" +
           calendar.get(Calendar.SECOND) + ":" + calendar.get(Calendar.MILLISECOND);
  }
}
