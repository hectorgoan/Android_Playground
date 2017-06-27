package com.example.hectorgoan.sliderok;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks
{
    //DECLARACIÓN DE UN TAG PARA LLEVAR A CABO PRUEBAS DE DESARROLLO
    private static final String TAG = "CHUNGOAN";


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Se supone que montamos la interfaz main, aunque nunca se vé
                                                //salta directamente a la primera sección

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Montamos el slider, aquí llamado drawer_layout
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override //Se lanza cuando seleccionamos un elemento del slider
    public void onNavigationDrawerItemSelected(int position)
    {
        // update the main content by replacing fragments, lo que en cristiano significa que actualiza lo que vemos
        // replacing the fragments, que es como están montadas las secciones.
        FragmentManager fragmentManager = getFragmentManager();
        //  Las posiciones para hacer el switch comienzan en el 0
        //  Nos declaramos un fragment, y más adelante, en el switch, le damos cierto valor, que será el que se lanze
        //  cuando hacemos la selección.
        Fragment fragmento = null;

        switch (position)
        {
            case 0:
                //  Hemos seleccionado la primera opción, que además será la que se lanze por defecto
                //  Debemos actualizar nuestro fragment a la vista que queramos, la lay_area1
                fragmento = new Area1();
                break;
            case 1:
                //  Seleccionamos la segunda opción, con lo que queremos lanzar lay_area2
                fragmento = new Area2();
                break;
            case 2:
                //  Seleccionamos la tercera opción, lanzaremos lay_area3
                fragmento = new Area3();
                break;
        }
        //Ahora que ya sabemos a que fragmento queremos ir, hacemos que el fragment manager empieze la transición a ese fragment
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragmento)
                .commit();
    }

    public void onSectionAttached(int number)
    {
        switch (number)
        {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar()
    {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (!mNavigationDrawerFragment.isDrawerOpen())
        {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
