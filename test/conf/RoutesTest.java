package conf;

import org.junit.Test;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

/**
 * User: lchane
 */
public class RoutesTest {

  @Test
  public void badRoute() {
    Result result = routeAndCall(fakeRequest(GET, "/xx/tasks"));
    assertThat(result).isNull();
  }

  @Test
  public void tasksRoute() {
    running(fakeApplication(), new Runnable() {
      @Override
      public void run() {
        Result result = routeAndCall(fakeRequest(GET, "/tasks/json"));
        assertThat(status(result)).isEqualTo(OK);
      }
    });
  }
}
