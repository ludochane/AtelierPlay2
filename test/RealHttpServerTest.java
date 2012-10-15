import org.junit.Test;
import play.libs.F;
import play.libs.WS;
import play.test.TestBrowser;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

/**
 * User: lchane
 */
public class RealHttpServerTest {

  @Test
  public void testInServer() {
    running(testServer(3333), new Runnable() {
      @Override
      public void run() {
        assertThat(WS.url("http://localhost:3333").get().get().getStatus()).isEqualTo(OK);
      }
    });
  }

  @Test
  public void runInBrowser() {
    running(testServer(3333), HTMLUNIT, new F.Callback<TestBrowser>() {
      public void invoke(TestBrowser browser) {
        browser.goTo("http://localhost:3333");
        assertThat(browser.$("title").getTexts().get(0)).isEqualTo("Todo list");
        browser.$("#label").text("tache auto");
        browser.$("form").submit();
        assertThat(browser.url()).isEqualTo("http://localhost:3333/tasks");
      }
    });
  }
}
