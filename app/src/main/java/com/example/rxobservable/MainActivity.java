package com.example.rxobservable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Disposable disposable;

    public static final String TAG = "Rx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Single<Boat> single =getBoatSingle();
        SingleObserver<Boat> singleObserver = getBoatObserver();

        single.observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(singleObserver);

    }

    private SingleObserver<Boat> getBoatObserver(){
        return new SingleObserver<Boat>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                Log.d(TAG, " onSuccess: ");
            }

            @Override
            public void onSuccess(Boat boat) {
                Log.d(TAG, " onSuccess: " + boat.toString());

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, " onSuccess: ");

            }
        };
    }

    private Single<Boat> getBoatSingle(){
        return Single.create(new SingleOnSubscribe<Boat>() {
            @Override
            public void subscribe(SingleEmitter<Boat> emitter) throws Exception {
                Boat boat = new Boat(20, "Beda");
                Boat trouble = new Boat(20, "Trouble");
                emitter.onSuccess(boat);
                emitter.onSuccess(trouble);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}