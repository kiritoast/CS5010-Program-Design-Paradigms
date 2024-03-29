package assignment5.concurrentSolution;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

/**
 * Represents the CSV reader consumer with the information of the concurrent map, the buffer queue,
 * and the helper latch.
 */
public class ReaderConsumer implements Runnable {

  private ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick;
  private BlockingQueue<String[]> readQueue;
  private CountDownLatch latch;
  private static final int MODULE_INDEX = 0;
  private static final int INITIAL_COUNT = 0;
  private static final int PRESENTATION_INDEX = 1;
  private static final int DATE_INDEX = 4;
  private static final int SUM_CLICK_INDEX = 5;
  private static final String COURSE_SEPARATOR = "_";

  /**
   * Creates a CSV reader consumer given the concurrent map, the buffer queue, and the helper latch
   *
   * @param courseAndClick the concurrent map
   * @param readQueue      the buffer queue
   * @param latch          the helper latch
   */
  public ReaderConsumer(ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick,
      BlockingQueue<String[]> readQueue, CountDownLatch latch) {
    this.courseAndClick = courseAndClick;
    this.readQueue = readQueue;
    this.latch = latch;
  }

  /**
   * This method is invoked to get the course information from the buffer queue, then store it to
   * the concurrent map
   */
  @Override
  public void run() {
    while (true) {
      try {
        String[] studentRecord = this.readQueue.take();
        if (Arrays.equals(studentRecord, new String[]{})) {
          break;
        }
        String courseKey =
            studentRecord[MODULE_INDEX] + COURSE_SEPARATOR + studentRecord[PRESENTATION_INDEX];
        ConcurrentMap<String, Integer> dateAndClick = this.courseAndClick.get(courseKey);
        if (dateAndClick != null) {
          synchronized (dateAndClick) {
            Integer newVal = dateAndClick.getOrDefault(studentRecord[DATE_INDEX], INITIAL_COUNT)
                + Integer.parseInt(studentRecord[SUM_CLICK_INDEX]);
            dateAndClick.put(studentRecord[DATE_INDEX], newVal);
          }
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    this.latch.countDown();
  }

  /**
   * This method is used to compare two instances of json reader class
   *
   * @param o instance to be compared
   * @return true or false
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReaderConsumer that = (ReaderConsumer) o;
    return courseAndClick.equals(that.courseAndClick);
  }

  /**
   * This method is used to generate the hash code of json reader class
   *
   * @return hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(courseAndClick);
  }

  /**
   * This method is used to generate the string representation of json reader class
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "ReaderConsumer{" +
        "courseAndClick=" + courseAndClick +
        '}';
  }
}
