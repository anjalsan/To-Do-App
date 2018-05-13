package todolist.task.com.todolist.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import rx.functions.Action1;
import todolist.task.com.todolist.R;
import todolist.task.com.todolist.utils.LocalNotification;
import todolist.task.com.todolist.utils.TodoNotificationManager;
import todolist.task.com.todolist.view.adapters.HomeViewPagerAdapter;
import todolist.task.com.todolist.view.adapters.TodoListRecyclerViewAdapter;
import todolist.task.com.todolist.view.fragments.TodoFragment;
import todolist.task.com.todolist.model.Todo;
import todolist.task.com.todolist.viewModel.TodoListViewModel;
import todolist.task.com.todolist.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements TodoListRecyclerViewAdapter.OnTodoInteractionListener{

    private TodoListViewModel viewModel;
    private Context context;
    private HomeViewPagerAdapter homeViewPagerAdapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(TodoListViewModel.class);
        setViewPager();

        // observe changes in the data.
        viewModel.getTodoList().observe(MainActivity.this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable final List<Todo> todos) {
                Collections.reverse(todos);
                TodoFragment fragment = (TodoFragment) homeViewPagerAdapter.getFragment(0);
                if (fragment != null) {
                    fragment.updateItems(todos);
                }
                fragment = (TodoFragment) homeViewPagerAdapter.getFragment(1);
                if (fragment != null) {
                    fragment.updateItems(todos);
                }
            }
        });

        RxView.clicks(binding.fab).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(context, AddTodoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setViewPager() {
        homeViewPagerAdapter = new HomeViewPagerAdapter(this.getSupportFragmentManager(), context);
        binding.viewpager.setAdapter(homeViewPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewpager);
        binding.viewpager.setSaveEnabled(false);
    }

    @Override
    public void setActiveStatus(Todo todo, Boolean status) {
        todo.setActive(status);
        viewModel.updateTodo(todo);

        // Scheduling and Deleting Notification According to To-do Status(Active/Completed).
        if (!status && todo.getTodo_schedule_at() > System.currentTimeMillis()) {
            TodoNotificationManager.cancelNotificationForTodo(context, todo);
        } else if (status && todo.getTodo_schedule_at() > System.currentTimeMillis()) {
            TodoNotificationManager.createNotificationForTodo(context, todo);
        }
    }
}
