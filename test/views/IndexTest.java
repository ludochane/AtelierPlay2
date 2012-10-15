package views;

import models.Task;
import org.junit.Test;
import play.mvc.Content;

import java.util.ArrayList;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Controller.form;
import static play.test.Helpers.*;

/**
 * User: lchane
 */
public class IndexTest {

  @Test
  public void renderTemplate() {
    Content html = views.html.index.render(new ArrayList<Task>(), form(Task.class));
    assertThat(contentType(html)).isEqualTo("text/html");
    assertThat(contentAsString(html)).contains("0 task");
  }
}
