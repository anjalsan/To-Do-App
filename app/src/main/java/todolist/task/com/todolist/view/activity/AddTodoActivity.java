package todolist.task.com.todolist.view.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import rx.functions.Action1;
import todolist.task.com.todolist.model.Todo;
import todolist.task.com.todolist.R;
import todolist.task.com.todolist.viewModel.AddTodoListViewModel;
import todolist.task.com.todolist.databinding.ActivityAddTodoBinding;

public class AddTodoActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private ActivityAddTodoBinding binding;
    private boolean isInitialDateClick = true;
    private boolean isInitialTimeClick = true;

    private Context context;
    private AddTodoListViewModel addTodoViewModel;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        context = this;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_todo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setTitle(null);

        addTodoViewModel = ViewModelProviders.of(this).get(AddTodoListViewModel.class);

        RxView.clicks(binding.addButton).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (binding.title.getText() == null || binding.title.getText().length() == 0) {
                    Toast.makeText(context, R.string.please_enter_title, Toast.LENGTH_SHORT).show();
                } else if(binding.switchSocialwall.isChecked() && calendar.getTimeInMillis() < System.currentTimeMillis()){
                    Toast.makeText(context, R.string.select_valid_time, Toast.LENGTH_SHORT).show();
                } else {
                    // inserting to-do inside DB.
                    double schedule_time = binding.switchSocialwall.isChecked() ? calendar.getTimeInMillis() : 0;
                    addTodoViewModel.addTodo(new Todo(
                            binding.title.getText().toString(),
                            binding.description.getText().toString(),
                            schedule_time
                    ), context);
                    finish();
                }
            }
        });

        RxTextView.textChanges(binding.title)
            .subscribe(new Action1<CharSequence>() {
                @Override
                public void call(CharSequence charSequence) {
                    if (charSequence.length() > 0) {
                        binding.addButton.setImageResource(R.drawable.paper_airplane_white);
                    } else {
                        binding.addButton.setImageResource(R.drawable.paper_airplane_gray);
                    }
                }
        });

        RxView.clicks(binding.date).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                datePickerDialog.show();
            }
        });

        RxView.clicks(binding.time).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                timePickerDialog.show();
            }
        });

        calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        setDate();
        datePickerDialog = new DatePickerDialog(this,
                AddTodoActivity.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        timePickerDialog = new TimePickerDialog(this,
                AddTodoActivity.this,
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                false);

        binding.switchSocialwall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.dateTimeContainer.setVisibility(View.VISIBLE);
                    if (isInitialDateClick) {
                        isInitialDateClick = false;
                        datePickerDialog.show();
                    }
                } else {
                    isInitialDateClick = true;
                    isInitialTimeClick = true;
                    binding.dateTimeContainer.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setDate() {
        Date date = calendar.getTime();
        SimpleDateFormat dfDate = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        SimpleDateFormat dfTime = new SimpleDateFormat("hh:mm aa", Locale.getDefault());

        binding.date.setText(dfDate.format(date));
        binding.time.setText(dfTime.format(date));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setDate();

        if (isInitialTimeClick) {
            isInitialTimeClick = false;
            timePickerDialog.show();
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        setDate();
    }
}
