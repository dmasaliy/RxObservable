package com.example.rxobservable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SecondActivity extends AppCompatActivity {

Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Maybe<Boat> maybe = getBoatMaybe();
        MaybeObserver<Boat> observer = getMaybeObserver();

        maybe.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private Maybe<Boat> getBoatMaybe(){
        return Maybe.create(new MaybeOnSubscribe<Boat>() {
            @Override
            public void subscribe(MaybeEmitter<Boat> emitter) throws Exception {
                Boat boat = new Boat(100, "Golandec");
                if(!emitter.isDisposed()){
                    emitter.onSuccess(boat);
                }
            }
        });
    }
    private MaybeObserver<Boat> getMaybeObserver(){
        return new MaybeObserver<Boat>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
               
            }

            @Override
            public void onSuccess(Boat boat) {
                Log.d(MainActivity.TAG, "onSuccess: " + boat);

            }

            @Override
            public void onError(Throwable e) {
                Log.d(MainActivity.TAG, "onError: " + e.getMessage());

            }

            @Override
            public void onComplete() {
                Log.d(MainActivity.TAG, "onComplete: ");

            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}