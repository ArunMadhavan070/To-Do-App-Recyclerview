package com.example.coolm.realm.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coolm.realm.R;
import com.example.coolm.realm.adapters.BooksAdapter;
import com.example.coolm.realm.adapters.RealmBooksAdapter;
import com.example.coolm.realm.app.Prefs;
import com.example.coolm.realm.model.Book;
import com.example.coolm.realm.realm.RealmController;
import com.example.coolm.realm.service.ScheduleClient;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private BooksAdapter adapter;
    private Realm realm;
    private LayoutInflater inflater;
    private FloatingActionButton fab;
    private RecyclerView recycler;
    private ScheduleClient scheduleClient;
    private String task;
    private String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();

        //get realm instance
       realm = RealmController.with(this).getRealm();

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupRecycler();

        if (!Prefs.with(this).getPreLoad()) {
            RealmController.with(this).refresh();
        }

        // refresh the realm instance
        RealmController.with(this).refresh();
        // get all persisted objects
        // create the helper adapter and notify data set changes
        // changes will be reflected automatically
        setRealmAdapter(RealmController.with(this).getBooks());

        Toast.makeText(this, "Press card item for edit, long press to remove item", Toast.LENGTH_LONG).show();

        //add new item
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inflater = MainActivity.this.getLayoutInflater();
                View content = inflater.inflate(R.layout.edit_item, null);
                final EditText editTitle = (EditText) content.findViewById(R.id.title);
                final DatePicker picker = (DatePicker) content.findViewById(R.id.scheduleTimePicker);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(content)
                        .setTitle("Add Tasks")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Book book = new Book();
                                //book.setId(RealmController.getInstance().getBooks().size() + 1);
                                book.setId((int) (RealmController.getInstance().getBooks().size() + System.currentTimeMillis()));
                                book.setDescription(editTitle.getText().toString());


                                int day = picker.getDayOfMonth();
                                int month = picker.getMonth();
                                int year = picker.getYear();
                                // Create a new calendar set to the date chosen
                                // we set the time to midnight (i.e. the first minute of that day)
                                Calendar c = Calendar.getInstance();
                                c.set(year, month, day);
                                c.set(Calendar.HOUR_OF_DAY, 0);
                                c.set(Calendar.MINUTE, 0);
                                c.set(Calendar.SECOND, 0);
                                task = day +"/"+ (month+1) +"/"+ year;
                                book.setDate(task);



                                //scheduleClient.setAlarmForNotification(c,task);



                                if (editTitle.getText() == null || editTitle.getText().toString().equals("") || editTitle.getText().toString().equals(" ")) {
                                    Toast.makeText(MainActivity.this, "Entry not saved, missing title", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Persist your data easily
                                    realm.beginTransaction();
                                    realm.copyToRealm(book);
                                    realm.commitTransaction();

                                    adapter.notifyDataSetChanged();
                                    s = editTitle.getText().toString();


                                    scheduleClient.setAlarmForNotification(c,s);
                                    Toast.makeText(MainActivity.this, "Reminder set for: "+ day +"/"+ (month+1) +"/"+ year, Toast.LENGTH_SHORT).show();

                                    // scroll the recycler view to bottom
                                    recycler.scrollToPosition(RealmController.getInstance().getBooks().size() - 1);
                                }



                            }

                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

// Clear the Notification Bar after you've clicked on the message in the Notification Bar
        NotificationManager nMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nMgr.cancelAll();
    }

    public void setRealmAdapter(RealmResults<Book> books) {

        RealmBooksAdapter realmAdapter = new RealmBooksAdapter(this.getApplicationContext(), books, true);
        // Set the data and tell the RecyclerView to draw
        adapter.setRealmAdapter(realmAdapter);
        adapter.notifyDataSetChanged();
    }

    private void setupRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        // create an empty adapter and add it to the recycler view
        adapter = new BooksAdapter(this);
        recycler.setAdapter(adapter);
    }

 /*   private void setRealmData() {

        ArrayList<Book> books = new ArrayList<>();

        Book book = new Book();
        book.setId((int) (1 + System.currentTimeMillis()));
        book.setDescription("Buy some peanuts");
       // book.setTime(SystemClock.elapsedRealtime());
        books.add(book);

        book = new Book();
        book.setId((int) (2 + System.currentTimeMillis()));
        book.setDescription("Watch a movie");
      //  book.setTime(SystemClock.elapsedRealtime());
        books.add(book);

        book = new Book();
        book.setId((int) (3 + System.currentTimeMillis()));
        book.setDescription("Travel to Moscow");
     //   book.setTime(System.currentTimeMillis());
        books.add(book);

        book = new Book();
        book.setId((int) (4 + System.currentTimeMillis()));
        book.setDescription("Do laundry tasks");
      //  book.setTime(System.currentTimeMillis());
        books.add(book);

        book = new Book();
        book.setId((int) (5 + System.currentTimeMillis()));
        book.setDescription("Go for a party");
       // book.setTime(System.currentTimeMillis());
        books.add(book);


        for (Book b : books) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(b);
            realm.commitTransaction();
        }

        Prefs.with(this).setPreLoad(true);

    }*/

    @Override
    protected void onStop() {
        // When our activity is stopped ensure we also stop the connection to the service
        // this stops us leaking our activity into the system *bad*
        if(scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
    }


}
