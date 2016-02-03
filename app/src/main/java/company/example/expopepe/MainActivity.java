package company.example.expopepe;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
//http://antonioleiva.com/navigation-view/
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import company.example.expopepe.View.ViewModel;
import company.example.expopepe.picasso.CircleTransform;

/**
 * Steps:
 * 1 Add the Libraries   compile 'com.android.support:appcompat-v7:23.1.1'
                         compile 'com.android.support:recyclerview-v7:23.1.1'
                         compile 'com.android.support:cardview-v7:23.1.1'
                         compile 'com.android.support:palette-v7:23.1.1'
                         compile 'com.android.support:design:23.1.1'
                         compile 'com.squareup.picasso:picasso:2.4.0'
 * 2 Add icons
 * 3 Configure dimens.xml
 * 4 Configure Strings.xml
 * 4.0 configure menu
 * 4.1 create layouts activity main
 * 4.2 drawer_header
 * 4.3 create anim folder
 * 5 Create Styles https://www.materialpalette.com/ change the style into the manifest
 * 6 create ViewModel
 * 7 Create CircleTransform class
 * 7.1 create a item recycler
 * 8 create RecyclerViewAdapter class
 * 8.1 create widgets classes
 * 9 Create Detail activity and its components
 * 10
 *
 */

public class MainActivity extends AppCompatActivity  implements RecyclerViewAdapter.OnItemClickListener  {

    public static final String AVATAR_URL = "http://lorempixel.com/200/200/people/7/";

    private static List<ViewModel> items = new ArrayList<>();

    //Method to get the images from the server
    //http://lorempixel.com/
    static {
        for (int i = 1; i <= 10; i++) {
            items.add(new ViewModel("Item " + i, "http://lorempixel.com/500/500/sports/" + i));
        }
    }
    private DrawerLayout drawerLayout;
    private View content;
    private RecyclerView recyclerView;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        initFab();
        initToolbar();
        setupDrawerLayout();

        content = findViewById(R.id.content);

        final ImageView avatar = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.avatar);
        Picasso.with(this).load(AVATAR_URL).transform(new CircleTransform()).into(avatar);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setRecyclerAdapter(recyclerView);
        }
    }

    @Override public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        setRecyclerAdapter(recyclerView);
        recyclerView.scheduleLayoutAnimation();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    private void setRecyclerAdapter(RecyclerView recyclerView) {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(items);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void initFab() {
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(content, "FAB Clicked", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Snackbar.make(content, menuItem.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override public void onItemClick(View view, ViewModel viewModel) {
        DetailActivity.navigate(this, view.findViewById(R.id.image), viewModel);
    }
}
