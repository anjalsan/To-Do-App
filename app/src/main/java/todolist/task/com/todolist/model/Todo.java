package todolist.task.com.todolist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "todo")
public class Todo {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    @NonNull
    private String title;

    @SerializedName("description")
    @ColumnInfo(name = "description")
    private String description;

    @SerializedName("active")
    @ColumnInfo(name = "active")
    private boolean active;

    @SerializedName("todo_schedule_at")
    @ColumnInfo(name = "todo_schedule_at")
    private double todo_schedule_at;


    public Todo(@NonNull String title, String description, Double todo_schedule_at) {
        this.title = title;
        this.description = description;
        this.active = true;
        this.todo_schedule_at = todo_schedule_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getTodo_schedule_at() {
        return todo_schedule_at;
    }

    public void setTodo_schedule_at(double todo_schedule_at) {
        this.todo_schedule_at = todo_schedule_at;
    }
}