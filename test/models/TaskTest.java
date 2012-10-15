package models;

import org.junit.Test;
import play.test.Helpers;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.*;

/**
 * User: lchane
 */
public class TaskTest {

  public static final String TASK_LABEL = "faire le ménage";

  @Test
  public void toStringShouldReturnLabel() {
    Task task = new Task();
    task.label = TASK_LABEL;
    assertEquals(TASK_LABEL, task.toString());
  }

  @Test
  public void createTask() {
    running(fakeApplication(), new Runnable() {
      @Override
      public void run() {
        Task task = new Task();
        task.label = TASK_LABEL;
        Task.create(task);

        List<Task> tasks = Task.all();
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(task, tasks.get(0));
      }
    });
  }
}
