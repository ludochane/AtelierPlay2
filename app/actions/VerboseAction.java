package actions;

import play.Logger;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

/**
 * User: lchane
 */
public class VerboseAction extends Action.Simple {
  @Override
  public Result call(Http.Context context) throws Throwable {
    Logger.info("Calling action for " + context.request());
    return delegate.call(context);
  }
}
