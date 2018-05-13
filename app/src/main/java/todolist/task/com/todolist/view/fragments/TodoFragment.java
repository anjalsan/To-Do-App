package todolist.task.com.todolist.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import todolist.task.com.todolist.view.adapters.TodoListRecyclerViewAdapter;
import todolist.task.com.todolist.model.Todo;
import todolist.task.com.todolist.R;
import todolist.task.com.todolist.utils.FontUtils;


public class TodoFragment extends Fragment {
    private static final String ARG_PARAM1 = "is_active";
    private boolean isActiveTodo;
    private Context context;
    private TodoListRecyclerViewAdapter recyclerViewAdapter;
    private LinearLayout emptyContainer;

    public static TodoFragment newInstance(Boolean isActiveTodo) {
        TodoFragment fragment = new TodoFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, isActiveTodo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isActiveTodo = getArguments().getBoolean(ARG_PARAM1);
            context = getActivity();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        emptyContainer =  view.findViewById(R.id.empty_container);
        TextView emptyTitle = view.findViewById(R.id.empty_title);
        TextView emptyDesc = view.findViewById(R.id.empty_desc);

        FontUtils.setRegularFont(emptyTitle);
        FontUtils.setRegularFont(emptyDesc);

        recyclerViewAdapter = new TodoListRecyclerViewAdapter(new ArrayList<Todo>(), context, isActiveTodo, (TodoListRecyclerViewAdapter.OnTodoInteractionListener) context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(recyclerViewAdapter);

        if (isActiveTodo) {
            emptyDesc.setText(R.string.active_todo_empty_text);
        } else {
            emptyDesc.setText(R.string.completed_todo_empty_text);
        }

        return view;
    }


    public void updateItems(List<Todo> todos){
        List<Todo> filtered_todos = filterTodo(todos, isActiveTodo);
        recyclerViewAdapter.addItems(filtered_todos);

        if (filtered_todos.size() == 0) {
            emptyContainer.setVisibility(View.VISIBLE);
        } else {
            emptyContainer.setVisibility(View.GONE);
        }
    }

    private List<Todo> filterTodo(List<Todo> todos, boolean isActiveTodos) {
        List<Todo> result = new ArrayList<>();
        for (Todo todo: todos) {
            if (isActiveTodos && todo.isActive()){
                result.add(todo);
            } else if (!isActiveTodos && !todo.isActive()){
                result.add(todo);
            }
        }
        return result;
    }
}
