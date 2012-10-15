package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.util.Duration;
import models.Task;
import org.codehaus.jackson.JsonNode;
import play.Logger;
import play.libs.Akka;
import play.libs.Comet;
import play.libs.F;
import play.libs.Json;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * User: lchane
 */
public class TasksRefresher extends UntypedActor {

  public static ActorRef instance = Akka.system().actorOf(new Props(TasksRefresher.class));

  static {
    Akka.system().scheduler().schedule(
            Duration.Zero(),
            Duration.create(1, TimeUnit.SECONDS),
            instance,
            "TICK"
    );
  }

  private static HashSet<Comet> connexions = new HashSet<Comet>();

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof Comet) {
      final Comet comet = (Comet) message;

      if (!connexions.contains(comet)) {
        comet.onDisconnected(new F.Callback0() {
          @Override
          public void invoke() throws Throwable {
            connexions.remove(comet);
            Logger.info("Un browser s'est déconnecté !");
          }
        });

        connexions.add(comet);
        Logger.info("Un browser s'est connecté !");
      }
    }

    if (message.equals("TICK")) {
      List<Task> tasks = Task.all();
      JsonNode tasksJson = Json.toJson(tasks);
      for (Comet connexion : connexions) {
        connexion.sendMessage(tasksJson);
      }
    }
  }
}
