package com.example.coolm.realm.adapters;

/**
 * Created by coolm on 10/17/2016.
 */
import android.content.Context;

import com.example.coolm.realm.model.Book;

import io.realm.RealmResults;

public class RealmBooksAdapter extends RealmModelAdapter<Book> {

    public RealmBooksAdapter(Context context, RealmResults<Book> realmResults, boolean automaticUpdate) {

        super(context, realmResults, automaticUpdate);
    }
}
