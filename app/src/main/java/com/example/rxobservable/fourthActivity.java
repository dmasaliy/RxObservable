package com.example.rxobservable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.Flowable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class fourthActivity extends AppCompatActivity {
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        Flowable<Integer> flowable = getFlowable();
        SingleObserver<Integer> observer = getFlowableSum();

        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .reduce(0, new BiFunction<Integer, Integer, Integer>(){
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception{
                        return integer + integer2;
                    }
                })
                .subscribe(observer);
    }


    private Flowable<Integer> getFlowable(){
        return Flowable.range(1, 1000);
    }

    private SingleObserver<Integer> getFlowableSum(){
        return  new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {
                Log.d(MainActivity.TAG, "Int: " + integer);

            }

            @Override
            public void onError(Throwable e) {

            }
        };
    }
}