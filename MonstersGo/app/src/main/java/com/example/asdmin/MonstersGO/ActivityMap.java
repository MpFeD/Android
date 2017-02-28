package com.example.asdmin.MonstersGO;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asdmin.MonstersGO.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;
import java.util.Map;
import static com.example.asdmin.MonstersGO.R.id.map;

/**
 * Created by Toty on 14/01/2017.
 * Please read de README file in the
 * main project folder.
 */

public class ActivityMap extends AppCompatActivity implements OnMapReadyCallback, LocationListener,NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    final Context context = this;
    private GoogleMap mMap;
    private FloatingActionButton fab;
    private FloatingActionButton fabTimer;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location location;
    private boolean creation;
    private Modele modele;
    private Circle circle;
    private Marker selectedMonster;
    private Marker markerMoi;
    private MediaPlayer mediaPlayer;
    private boolean isMusicPlaying;
    private boolean isInChallenge;
    private int cptChallenge;
    private FragmentProfile fragmentProfile;
    private FragmentMonsters fragmentMonsters;
    private TextView timerTextView;
    private long remainingTime;
    public static final String PREFS_NAME = "Toty_Tran_Save_Monsters_Go";
    long start;


    /*Timer for the mission
    60 sec to catch the most monsters you can
     */
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - start;
            int seconds = (int) (millis / 1000);
            seconds = seconds % 60;

            if(seconds==0)
                timerTextView.setText("1:00");
            else
                timerTextView.setText(String.format("0:%02d", 60 - seconds));

            timerTextView.setVisibility(View.VISIBLE);

            remainingTime--;
            if(remainingTime==0){
                remainingTime=61;
                String txt = "Finish ! You have caught "+ cptChallenge +" monsters.";
                if(modele.getChallenge() < cptChallenge) {
                    txt += " New record !";
                    modele.setChallenge(cptChallenge);
                }
                Toast.makeText(getApplicationContext(), txt , Toast.LENGTH_SHORT).show();
                isInChallenge=false;
                timerHandler.removeCallbacks(timerRunnable);
                timerTextView.setVisibility(View.INVISIBLE);
                cptChallenge=0;
            }
            else
                timerHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_nav_drawer);

        //get the name
        Intent intent = getIntent();
        String username = intent.getStringExtra("modele_username");
        modele = new Modele(username);

        //persistance durable
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        for(int i=0;i<5;i++) {
            int tmp = settings.getInt("withNb"+i, 0);
            if(tmp!=0)
                modele.getMap().put(settings.getString("withMonster"+i, ""),tmp);
        }

        isMusicPlaying=true;
        remainingTime=61;
        isInChallenge=false;
        cptChallenge=0;

        //creation of monsters
        creation = true;

        fragmentProfile = new FragmentProfile();
        fragmentMonsters = new FragmentMonsters();

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timerTextView = (TextView) findViewById(R.id.timer);

        //Navigation drawer
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fabTimer = (FloatingActionButton) findViewById(R.id.fabTimer);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);


        //Localisation
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();


    }

    //On stop we save the data (monsters caught and name)
    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        mGoogleApiClient.disconnect();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("withName", modele.getName());
        editor.commit();

        int cpt=0;
        for ( Map.Entry<String, Integer> entry : modele.getMap().entrySet()) {
            String key = entry.getKey();
            Integer tab = entry.getValue();
            editor.putString("withMonster"+cpt, key);
            editor.commit();

            editor.putInt("withNb"+cpt, tab);
            editor.commit();

            cpt++;
        }
    }


    //start the music, set the google map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.plonge);
        mediaPlayer.start();
    }

    //when you move on the map :
    //update the circle, your postion, create the monster the first time
    @Override
    public void onLocationChanged(final Location location) {
        this.location = location;

        if(circle != null)
            circle.remove();

        if(markerMoi != null)
            markerMoi.remove();

        //Monsters appear the first time you move
        if(creation) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
            BitmapDescriptor bmDackass = BitmapDescriptorFactory.fromResource(R.drawable.dackass);
            BitmapDescriptor bmFantomaxus = BitmapDescriptorFactory.fromResource(R.drawable.fantomaxus);
            BitmapDescriptor bmPuzzleyes = BitmapDescriptorFactory.fromResource(R.drawable.puzzleyes);
            BitmapDescriptor bmRacailleu = BitmapDescriptorFactory.fromResource(R.drawable.racailleu);
            BitmapDescriptor bmTaupiqure = BitmapDescriptorFactory.fromResource(R.drawable.taupiqure);

            BitmapDescriptor tab[] = new BitmapDescriptor[]{bmDackass,bmFantomaxus,bmPuzzleyes,bmRacailleu,bmTaupiqure};
            String pkNom[] = new String[]{ context.getString(R.string.pk_d),context.getString(R.string.pk_f),context.getString(R.string.pk_p),context.getString(R.string.pk_r),context.getString(R.string.pk_t)};

            for (int i = 0; i < 300; i++) {
                int j = (int)(Math.random()*5);
                LatLng ll = new LatLng(location.getLatitude() - 0.03 + Math.random() * 0.06, location.getLongitude() - 0.03 + Math.random() * 0.06);
                mMap.addMarker(new MarkerOptions().title(pkNom[j]).icon(tab[j]).anchor(0.5f, 0.5f).position(ll));
            }
            creation = false;
        }

        BitmapDescriptor moi = BitmapDescriptorFactory.fromResource(R.drawable.hero_map);
        markerMoi = mMap.addMarker(new MarkerOptions().title("Me").icon(moi).anchor(0.5f, 0.5f).position(new LatLng(location.getLatitude(), location.getLongitude())));

        Color color = new Color();

        circle = mMap.addCircle(new CircleOptions()
                .radius(500)
                .center(new LatLng(location.getLatitude(), location.getLongitude()))
                .strokeColor(Color.RED)
                .fillColor(color.argb(100,255,0,0)));

        circle.setStrokeWidth(4);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arg0.getPosition(),15));
                if(arg0.isInfoWindowShown()) {
                    arg0.hideInfoWindow();
                } else {
                    arg0.showInfoWindow();
                }
                selectedMonster = arg0;
                return true;
            }
        });


        //A floattting to catch the monsters
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedMonster!=null) {
                    Location monsterLocation = new Location("");
                    monsterLocation.setLongitude(selectedMonster.getPosition().longitude);
                    monsterLocation.setLatitude(selectedMonster.getPosition().latitude);

                    if (!selectedMonster.equals(markerMoi)) {
                        if (location.distanceTo(monsterLocation) < circle.getRadius()) {
                            if(isInChallenge)
                                cptChallenge++;
                            modele.ajouterMonsters(selectedMonster.getTitle());
                            String txt="You have caught a "+selectedMonster.getTitle() +" !";
                            if(modele.getNbCaughtForKey(selectedMonster.getTitle()) != 1)
                                txt+=" (You allready have " + modele.getNbCaughtForKey(selectedMonster.getTitle()) + " " + selectedMonster.getTitle() +" )";
                            Toast.makeText(getApplicationContext(), txt , Toast.LENGTH_SHORT).show();
                            selectedMonster.remove();
                        } else
                            Snackbar.make(view, "You must first select a valid monster", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }

                }
            }
        });

        //A floattting action button is used to launch the mission timer
        fabTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isInChallenge)
                    Toast.makeText(getApplicationContext(), "You are allready in a challenge !" , Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getApplicationContext(), "Challenge started !" , Toast.LENGTH_SHORT).show();
                    timerTextView.setVisibility(View.VISIBLE);
                    start = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    isInChallenge=true;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_nav_drawer, menu);
        return true;
    }

    //Play or pause the music
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemPlay) {
            mediaPlayer.start();
            if(!isMusicPlaying) {
                isMusicPlaying=true;
                Toast.makeText(getApplicationContext(), "Music resumed", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if (id == R.id.itemPause) {
            mediaPlayer.pause();
            if(isMusicPlaying) {
                isMusicPlaying=false;
                Toast.makeText(getApplicationContext(), "Music paused", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //Navigation drawer buttons
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.d("xx","lol");
        if (id == R.id.nav_map) {
            Log.d("xx","lol");
            getSupportFragmentManager().beginTransaction().remove(fragmentProfile).commit();
            getSupportFragmentManager().beginTransaction().remove(fragmentMonsters).commit();
            fab.show();
            fabTimer.show();

        } else if (id == R.id.nav_gallery) {
            fab.hide();
            fabTimer.hide();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentMonsters).commit();

        } else if (id == R.id.nav_manage) {
            fab.hide();
            fabTimer.hide();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentProfile).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Fail to connect with google map
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Toast.makeText(this, "Fail to connect", Toast.LENGTH_SHORT).show();
    }

    //Connected with google map
    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    //Disconnect with google map
    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public Modele getModele(){
        return this.modele;
    }
}
