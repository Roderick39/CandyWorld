package com.example.rodrigobaez.candyworld.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.rodrigobaez.candyworld.R;
import com.example.rodrigobaez.candyworld.adapter.MyAdapter;
import java.util.ArrayList;
import java.util.List;


public class GridActivity extends AppCompatActivity {
    private List<String> personas;
    private GridView gridView;
    private MyAdapter myAdapter;
    private int contador = 0;
    private int contar = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        gridView = (GridView) findViewById(R.id.gridView);
        personas = new ArrayList<String>();
        personas.add("Renato");
        personas.add("Fidel");
        personas.add("Leonor");
        personas.add("Irene");

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(GridActivity.this, "Acabas de dar Click en: "+personas.get(i), Toast.LENGTH_LONG).show();
            }
        });
        //Enlazamos con nuestro adaptador personalizado
        myAdapter = new MyAdapter(this, R.layout.grid_item, personas);
        gridView.setAdapter(myAdapter);

        registerForContextMenu(gridView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        MenuItem itemToHide = menu.findItem(R.id.Grid);
        itemToHide.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.add_item:
                //agregamos nuevo nombre

                if (contador < 0){
                    contador=0;
                }

                this.personas.add("Agregar Dulce "+(++contador));
                contar++;
                //Notificamos al adaptador del cambio producido
                this.myAdapter.notifyDataSetChanged();
                return true;
            case R.id.delete_item:
                if (contar >= 0) {
                    this.personas.remove(contar--);
                    contador--;

                }
                this.myAdapter.notifyDataSetChanged();
                return true;
            case R.id.List:
                Intent intent = new Intent(GridActivity.this, ListActivity.class);
                startActivity(intent);
                return  true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(this.personas.get(info.position));
        inflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean  onContextItemSelected (MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete_item:

                this.personas.remove(info.position);

                this.myAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
