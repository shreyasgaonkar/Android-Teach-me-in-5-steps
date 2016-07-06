package com.example.kpava.test_project;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.ArrayList;

// creating object for storing artist, song title and imageid
class object
{
    String title;
    String artist;
    int imageId;
    public object(String title, String artist, int imageId)
    {
        this.title = title;
        this.artist = artist;
        this.imageId = imageId;
    }
}




// implementing a adapter by extending from baseadapter

class adapter extends BaseAdapter
{
    ArrayList<object> al ;
    Context con;
    public adapter(Context c)
    {
        al = new ArrayList<>();
        Resources r = c.getResources();
        con = c;
        String[] titles = r.getStringArray(R.array.songs_title);
        String[] artists = r.getStringArray(R.array.artist);
        int[]    imageids = {R.drawable.guitar, R.drawable.head, R.drawable.fret,
                             R.drawable.chords, R.drawable.fourchords };
        for(int i=0; i<titles.length; i++)
        {
            object o = new object(titles[i],artists[i], imageids[i] );
            al.add(o);
        }
    }
    @Override
    public int getCount() {
        return al.size();
    }

    @Override
    public Object getItem(int position) {
        return al.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //getting the view
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //Inflater i =
        LayoutInflater li = (LayoutInflater) con.getSystemService(con.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.songs_list, parent, false);
        TextView tv = (TextView) v.findViewById(R.id.textView);
        TextView tv2 = (TextView) v.findViewById(R.id.textView2);
        ImageView iv = (ImageView) v.findViewById(R.id.imageView);

        object o = al.get(position);
        tv.setText(o.title);
        tv2.setText(o.artist);
        iv.setImageResource(o.imageId);

        return v;
    }
}

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    String[] artist_wiki_links;
    String[] song_wiki_links ;
    String[] song_link ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //sreading artist, song wiki links
        artist_wiki_links = this.getResources().getStringArray(R.array.artist_wiki_links);
        song_wiki_links = this.getResources().getStringArray(R.array.Theory);
        song_link = this.getResources().getStringArray(R.array.songs_link);
        // creating the view
        ListView lv = (ListView) findViewById(R.id.listView);

        //setting up adapter and defining onclick evet
        lv.setAdapter(new adapter(this));
        final String[] links = this.getResources().getStringArray(R.array.songs_link);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                String link = links[position];
                Uri u = Uri.parse(link);
                i.setData(u);
                startActivity(i);
            }
        });

        /*lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                String link = "https://www.buzzreels.com/";
                Uri u = Uri.parse(link);
                i.setData(u);
                startActivity(i);
                // Toast.makeText(this, "long click", 100);
                return true;
            }
        });*/

        registerForContextMenu(lv);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    // creating and inflating context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    // defining functionality to show contextmenu on long click
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Intent i = new Intent(Intent.ACTION_VIEW);
        int position = info.position;
        String link;
        Uri u;
        switch (item.getItemId()) {
            case R.id.song_wiki:

                link = song_wiki_links[position] ;
                u = Uri.parse(link);
                i.setData(u);
                startActivity(i);
                return true;
            //case R.id.artist_wiki:
              //  link = artist_wiki_links[position] ;
               // u = Uri.parse(link);
                //i.setData(u);
                //startActivity(i);
                //return true;
            case R.id.song_link:
                link = song_link[position] ;
                u = Uri.parse(link);
                i.setData(u);
                startActivity(i);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.kpava.test_project/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.kpava.test_project/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
