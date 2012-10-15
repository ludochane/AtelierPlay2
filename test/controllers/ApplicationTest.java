package controllers;

import org.junit.Test;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

/**
 * User: lchane
 */
public class ApplicationTest {

  @Test
  public void callTasks() {
    running(fakeApplication(), new Runnable() {
      @Override
      public void run() {
        Result result = callAction(routes.ref.Application.tasks());
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("0 task");
      }
    });
  }
}
