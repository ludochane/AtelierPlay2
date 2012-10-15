package controllers;

import actions.Verbose;
import actions.VerboseAction;
import actors.TasksRefresher;
import akka.actor.ActorRef;
import models.Task;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import play.*;
import play.cache.Cache;
import play.data.Form;
import play.libs.Comet;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

import java.util.List;

public class Application extends Controller {

  static Form<Task> taskForm = form(Task.class);
  public static final String TASKS = "tasks";

  public static Result index() {
    return redirect(routes.Application.tasks());
  }

  public static Result tasks() {
    Object tasks = Cache.get(TASKS);
    if (tasks == null) {
      tasks = Task.all();
      cacheTasks(tasks);
    }

    return ok(views.html.index.render((List<Task>) tasks, taskForm));
  }

  public static Result tasksJson() {
    List<Task> tasks = Task.all();
    JsonNode json = Json.toJson(tasks);
    return ok(json);
  }

  @With(VerboseAction.class)
  public static Result newTask() {
    Form<Task> filledForm = taskForm.bindFromRequest();
    if (filledForm.hasErrors()) {
      return badRequest(views.html.index.render(Task.all(), filledForm));
    } else {
      Task.create(filledForm.get());
      cacheTasks(Task.all());
      return redirect(routes.Application.tasks());
    }
  }

  @Verbose
  public static Result deleteTask(Long id) {
    Task.delete(id);
    cacheTasks(Task.all());
    return redirect(routes.Application.tasks());
  }

  private static void cacheTasks(Object tasks) {
    Cache.set(TASKS, tasks);
  }

  private static ActorRef tasksRefresher = TasksRefresher.instance;

  public static Result liveTasks() {
    return ok(livetasks.render());
  }

  public static Result updateLiveTasks() {
    return ok(new Comet("parent.updateTasks") {
      @Override
      public void onConnected() {
        tasksRefresher.tell(this);
      }
    });
  }
}