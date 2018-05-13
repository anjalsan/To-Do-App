package todolist.task.com.todolist.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import todolist.task.com.todolist.model.Todo;
import todolist.task.com.todolist.R;
import todolist.task.com.todolist.viewModel.ItemTodoViewModel;
import todolist.task.com.todolist.databinding.TodoItemBinding;

public class TodoListRecyclerViewAdapter extends RecyclerView.Adapter<TodoListRecyclerViewAdapter.RecyclerViewHolder> {

    private List<Todo> todoModelList;
    private Context context;
    private boolean isActiveTodo;
    private OnTodoInteractionListener onTodoInteractionListener;

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TodoItemBinding todoItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.todo_item,parent, false);
        return new RecyclerViewHolder(todoItemBinding);
    }

    public TodoListRecyclerViewAdapter(List<Todo> todoModelList, Context context, boolean isActiveTodo, OnTodoInteractionListener onTodoInteractionListener) {
        this.todoModelList = todoModelList;
        this.context = context;
        this.isActiveTodo = isActiveTodo;
        this.onTodoInteractionListener = onTodoInteractionListener;
    }

    public interface OnTodoInteractionListener {
        void setActiveStatus(Todo todo, Boolean status);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.bindTodo(todoModelList.get(position));
    }


    @Override
    public int getItemCount() {
        return todoModelList.size();
    }

    public void addItems(List<Todo> todoModelList) {
        List<Todo> todos = new ArrayList<>();
        for (Todo todo: todoModelList) {
            if (isActiveTodo && todo.isActive()){
                todos.add(todo);
            }
            if (!isActiveTodo && !todo.isActive()){
                todos.add(todo);
            }
        }
        this.todoModelList = todos;
        notifyDataSetChanged();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TodoItemBinding todoItemBinding;

        RecyclerViewHolder(TodoItemBinding todoItemBinding) {
            super(todoItemBinding.rootView);
            this.todoItemBinding = todoItemBinding;
        }

        void bindTodo(Todo todo){
            if(todoItemBinding.getTodoViewModel() == null){
                todoItemBinding.setTodoViewModel(new ItemTodoViewModel(todo, context, isActiveTodo, onTodoInteractionListener));
            } else {
                todoItemBinding.getTodoViewModel().setTodo(todo, isActiveTodo, onTodoInteractionListener);
            }
        }
    }
}
