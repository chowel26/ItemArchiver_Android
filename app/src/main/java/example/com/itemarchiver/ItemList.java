package example.com.itemarchiver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ItemList extends AppCompatActivity {

    ListView listView;
    ArrayList<Item> itemList = new ArrayList<Item>();
    Item itemObj;
    public final static String ITEM = "ITEM";
    public final static String ITEMLIST = "ITEMLIST";
    private DatabaseDataManager databaseDataManager;
    ViewItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);
        setTitle("Item List");

        databaseDataManager = new DatabaseDataManager(this);

        listView = (ListView) findViewById(R.id.itemList);

        adapter = new ViewItemsAdapter(ItemList.this, R.layout.item_layout, databaseDataManager.getAllItems());
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

        Log.d("demo", String.valueOf(databaseDataManager.getAllItems()));

        //view selected item in the list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ItemList.this, ViewItem.class);
                itemObj = (Item) adapter.getItem(position);
                intent.putExtra(ITEM, itemObj);
                startActivity(intent);
            }
        });

        //delete selected item from the list
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemObj = (Item) adapter.getItem(position);
                Log.d("demo.delete", "" + position + "item: " + itemObj);

                databaseDataManager.deleteItem(itemObj);
                Toast.makeText(ItemList.this, "Item deleted successfully!", Toast.LENGTH_SHORT).show();
                adapter.clear();
                adapter.addAll(databaseDataManager.getAllItems());
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_item_list, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Delete_All:
                databaseDataManager.deleteAllItems();
                Toast.makeText(ItemList.this, "All items deleted successfully!", Toast.LENGTH_SHORT).show();
                adapter.clear();
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

//        @Override
//        protected void onDestroy(){
//            super.onDestroy();
//            databaseDataManager.close();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (listView != null){
            updateView();
        }
    }

    private void updateView(){
        adapter = new ViewItemsAdapter(ItemList.this, R.layout.item_layout, databaseDataManager.getAllItems());

        listView.setAdapter(adapter);
    }
}
