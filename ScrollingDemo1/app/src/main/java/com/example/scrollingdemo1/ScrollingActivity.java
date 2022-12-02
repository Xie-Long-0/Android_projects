package com.example.scrollingdemo1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scrollingdemo1.databinding.ActivityScrollingBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class ScrollingActivity extends AppCompatActivity {
    static private final ArrayList<ItemDataStruct> mDataSet = new ArrayList<>();
    RecyclerView mRecyclerView;
    MyAdapter myAdapter;
    ActivityScrollingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> Snackbar.make(view, "Scanning...", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        mRecyclerView = findViewById(R.id.item_list_view);

        if (mDataSet.isEmpty()) {
            mDataSet.add(new ItemDataStruct("Device 1", "AA:BB:CC:DD:EE:FF", "Unbound"));
            mDataSet.add(new ItemDataStruct("Device 2", "11:22:33:44:55:66", "Bound"));
        }
        myAdapter = new MyAdapter(mDataSet);
        mRecyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        MenuItem item = menu.findItem(R.id.action_add_item);
        item.setOnMenuItemClickListener(item1 -> {
            int index = myAdapter.getItemCount() + 1;
            mDataSet.add(new ItemDataStruct("Device " + index, "FF:FF:FF:FF:FF:FF", "Unbound"));

            myAdapter.notifyItemInserted(index);
            Snackbar.make(mRecyclerView, "Add Item " + index, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return true;
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_add_item) {
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_quit) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

class ItemDataStruct {
    String name;
    String mac_addr;
    String status;

    public ItemDataStruct(String n, String m, String s) {
        name = n;
        mac_addr = m;
        status = s;
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    TextView view_name;
    TextView view_mac_addr;
    TextView view_status;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        view_name = itemView.findViewById(R.id.txt_name);
        view_mac_addr = itemView.findViewById(R.id.txt_mac_addr);
        view_status = itemView.findViewById(R.id.txt_status);
    }

    public void setData(ItemDataStruct data) {
        if (data == null) {
            view_name.setText("");
            view_mac_addr.setText("");
            view_status.setText("");
        } else {
            view_name.setText(data.name);
            view_mac_addr.setText(data.mac_addr);
            view_status.setText(data.status);
        }
    }
}

class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    ArrayList<ItemDataStruct> data_sets;

    public MyAdapter(ArrayList<ItemDataStruct> dataSet) {
        if (dataSet == null)
            data_sets = new ArrayList<>();
        else
            data_sets = dataSet;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(data_sets.get(position));
    }

    @Override
    public int getItemCount() {
        return data_sets.size();
    }


}