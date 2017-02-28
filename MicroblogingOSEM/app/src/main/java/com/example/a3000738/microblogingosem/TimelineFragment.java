package com.example.a3000738.microblogingosem;

import android.app.ListFragment;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Contacts;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import com.example.a3000738.microblogingosem.R;

import static android.R.attr.data;

/**
 * Created by Max Uchiha on 18/01/2017.
 */

public class TimelineFragment extends ListFragment{
    private static final String TAG = "TimelineFragment";

    private String [] dataFromDB;
    private int [] dataToXML;

    private SQLiteDatabase mDb;
    private DbHelper mDbHelper;
    private Cursor c;
    private SimpleCursorAdapter sca;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dataFromDB = new String[] {DbStructure.USER, DbStructure.MESSAGE, DbStructure.CREATED_AT};
        dataToXML = new int[]{R.id.list_user, R.id.list_message, R.id.list_created_at};

        mDbHelper = new DbHelper(getActivity());
        mDb = mDbHelper.getWritableDatabase();

        c = mDb.query(DbStructure.TABLE, new String[]{ DbStructure.ID, DbStructure.USER, DbStructure.MESSAGE, DbStructure.CREATED_AT},
                null, null, null, null, null);

        sca = new SimpleCursorAdapter(getActivity(), R.layout.list_fragment, c,dataFromDB,dataToXML,0);

        getListView().setDividerHeight( 1 );
        getListView().setOnCreateContextMenuListener( this );
        getListView().setFocusable( true );

        setListAdapter(sca);

    }
}
