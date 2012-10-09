package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lchane
 * Date: 09/10/12
 * Time: 10:36
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Task extends Model {

  @Id
  public Long id;
  @Constraints.Required
  public String label;

  public static Finder<Long, Task> find = new Finder<Long, Task>(Long.class, Task.class);

  public static List<Task> all() {
    return find.all();
  }

  public static void create(Task task) {
    task.save();
  }

  public static void delete(Long id) {
    find.ref(id).delete();
  }
}
