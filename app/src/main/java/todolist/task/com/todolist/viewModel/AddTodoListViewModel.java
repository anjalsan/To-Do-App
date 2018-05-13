package todolist.task.com.todolist.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import todolist.task.com.todolist.Database.AppDatabase;
import todolist.task.com.todolist.model.Todo;
import todolist.task.com.todolist.utils.TodoNotificationManager;

public class AddTodoListViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public AddTodoListViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getAppDatabase(this.getApplication());
    }

    public void addTodo(final Todo todo, final Context context) {
        Observable<Long> observable = insetObserver(appDatabase, todo);
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long todoId) {
                        todo.setId((int) (long) todoId);

                        //Scheduling Notification for To-do.
                        if (todo.getTodo_schedule_at() > System.currentTimeMillis()) {
                            TodoNotificationManager.createNotificationForTodo(context, todo);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private Observable<Long> insetObserver(final AppDatabase db, final Todo todo) {
        return Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                try {
                    long todo_id = db.todoDao().insert(todo);
                    emitter.onNext(todo_id);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }
}
