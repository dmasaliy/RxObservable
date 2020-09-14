package com.example.rxobservable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ThirdActivity extends AppCompatActivity {
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Completable completable =  getCompletable();
        CompletableObserver completableObserver = getCompletableObserver();

        completable.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(completableObserver);
    }

    private Completable getCompletable(){
        return  Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                if(!emitter.isDisposed())
                    emitter.onComplete();
            }
        });
    }

    private CompletableObserver getCompletableObserver(){
        return new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onComplete() {
                Log.d(MainActivity.TAG, "data processing completed");

            }

            @Override
            public void onError(Throwable e) {

            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}