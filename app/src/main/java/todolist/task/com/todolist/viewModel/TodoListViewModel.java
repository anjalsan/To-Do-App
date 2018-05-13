package todolist.task.com.todolist.viewModel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import todolist.task.com.todolist.Database.AppDatabase;
import todolist.task.com.todolist.model.Todo;

public class TodoListViewModel extends AndroidViewModel {

    private final LiveData<List<Todo>> todos;

    private AppDatabase appDatabase;

    public TodoListViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getAppDatabase(this.getApplication());
        todos = appDatabase.todoDao().getAll();
    }


    public LiveData<List<Todo>> getTodoList() {
        return todos;
    }

    public void updateTodo(final Todo todo) {
        Observable<Boolean> observable = updateObserver(appDatabase, todo);
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Observable<Boolean> updateObserver(final AppDatabase db, final Todo todo) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                try {
                    db.todoDao().update(todo);
                    emitter.onNext(true);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

}