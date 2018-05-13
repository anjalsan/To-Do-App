package todolist.task.com.todolist.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.View;
import java.util.Calendar;

import todolist.task.com.todolist.model.Todo;
import todolist.task.com.todolist.R;
import todolist.task.com.todolist.view.adapters.TodoListRecyclerViewAdapter;

public class ItemTodoViewModel extends BaseObservable {

    private Todo todo;
    private Context context;
    private boolean isActiveTodo;
    private TodoListRecyclerViewAdapter.OnTodoInteractionListener onTodoInteractionListener;

    public ItemTodoViewModel(Todo todo, Context context, boolean isActiveTodo, TodoListRecyclerViewAdapter.OnTodoInteractionListener onTodoInteractionListener){
        this.todo = todo;
        this.context = context;
        this.isActiveTodo = isActiveTodo;
        this.onTodoInteractionListener = onTodoInteractionListener;
    }

    public String getTitle() { return todo.getTitle(); }

    public String getDescription() { return todo.getDescription(); }

    public int getDescription_visibility() {
        if (todo.getDescription().length() == 0) {
            return View.GONE;
        } else {
            return View.VISIBLE;
        }
    }

    public String getTodo_schedule_at() {
        return getFormattedDate(todo.getTodo_schedule_at());
    }

    public int getReminder_visibility() {
        if (todo.getTodo_schedule_at() == 0 || !isActiveTodo) {
            return View.GONE;
        } else {
            return View.VISIBLE;
        }
    }

    public Drawable getReminder_image() {
        if (todo.getTodo_schedule_at() > System.currentTimeMillis()) {
            return ContextCompat.getDrawable(context, R.drawable.ic_add_alert_green_24dp);
        } else {
            return ContextCompat.getDrawable(context, R.drawable.ic_add_alert_red_24dp);
        }
    }

    public Drawable getAction_button_image() {
        if (isActiveTodo) {
            return ContextCompat.getDrawable(context, R.drawable.ic_done_black_24dp);
        } else {
            return ContextCompat.getDrawable(context, R.drawable.ic_undo_black_24dp);
        }
    }

    public void onItemClick(View v){
        if (isActiveTodo) {
            onTodoInteractionListener.setActiveStatus(todo, false);
        } else {
            onTodoInteractionListener.setActiveStatus(todo, true);
        }
    }

    public String getFormattedDate(double scheculedTimeInMilis) {
        Calendar scheculedTimeTime = Calendar.getInstance();
        scheculedTimeTime.setTimeInMillis((long) scheculedTimeInMilis);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "h:mm aa";
        final String dateTimeFormatString = "dd MMM yyyy h:mm aa";
        if (now.get(Calendar.DATE) == scheculedTimeTime.get(Calendar.DATE) ) {
            return "Today " + DateFormat.format(timeFormatString, scheculedTimeTime);
        } else if (now.get(Calendar.DATE) - scheculedTimeTime.get(Calendar.DATE) == 1  ){
            return "Yesterday " + DateFormat.format(timeFormatString, scheculedTimeTime);
        } else if (now.get(Calendar.DATE) + scheculedTimeTime.get(Calendar.DATE) == 1  ){
            return "Tomorrow " + DateFormat.format(timeFormatString, scheculedTimeTime);
        } else  {
            return DateFormat.format(dateTimeFormatString, scheculedTimeTime).toString();
        }
    }

    public void setTodo(Todo todo, boolean isActiveTodo, TodoListRecyclerViewAdapter.OnTodoInteractionListener onTodoInteractionListener) {
        this.todo = todo;
        this.isActiveTodo = isActiveTodo;
        this.onTodoInteractionListener = onTodoInteractionListener;
        notifyChange();
    }
}
