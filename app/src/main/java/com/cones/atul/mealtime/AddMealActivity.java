package com.cones.atul.mealtime;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddMealActivity extends AppCompatActivity {

    ListView list;
    List<String> items;
    private Context con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        con = this;

        StorageReference ref = FirebaseStorage.getInstance().getReference();
        items = Arrays.asList("Clear Soup", "Creme soup", "Cheese balls", "Garlic Bread", "Pretzels", "Butter roti", "Butter Naan",
                "Jeera Rice", "Peas Butter Masala", "Dal Fry", "Juice", "Lassi", "Coconut Drink");

        list = findViewById(R.id.order_list);
        list.setAdapter(new CustomAdapter(this,R.layout.order_list_item,items));
    }

    private boolean checkQty(ListView v) {
        for(int i=0;i<v.getCount();++i){
            View x = v.getChildAt(i);
            EditText et = x.findViewById(R.id.add_meal_qty);
            if(et.getText().toString().equals("")) et.setText("0");
            if(Integer.parseInt(et.getText().toString())>0)
                return true;
        }
        Toast.makeText(getApplicationContext(),"You didn't order anything!",Toast.LENGTH_SHORT).show();
        return false;
    }

    private int getItemQty(ListView v) {
        int qty=0;
        for (int i = 0; i < v.getCount(); ++i) {
            View x = v.getChildAt(i);
            if (x != null) {
                EditText et = x.findViewById(R.id.add_meal_qty);
                if (et.getText().toString().equals("")) et.setText("0");
                qty += Integer.parseInt(et.getText().toString());
            }
        }
        return qty;
    }

    private String[] getOrderDetails(ListView v) {
        ArrayList<String> list1 = new ArrayList<>();
        for(int i=0;i<v.getCount();++i){
            View x = v.getChildAt(i);
            if(x!=null) {
                EditText et = x.findViewById(R.id.add_meal_qty);
                int qty = Integer.parseInt(et.getText().toString());
                if (qty > 0)
                    list1.add(items.get(i) + " - " + qty + " nos.\n");
            }
        }
        return list1.toArray(new String[0]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meal_menu,menu);
        return true;
    }

    private void execIntent(){
        Intent intent = new Intent(con, NotifSender.class);
        PendingIntent pIt = PendingIntent.getBroadcast(con, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        Log.i("TIM", String.valueOf(calendar.getTime()));
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 15);
        Log.i("TIM", String.valueOf(calendar.getTime()));
        assert alarmManager != null;
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pIt);

        Toast.makeText(con, "Order was placed", Toast.LENGTH_SHORT).show();
        Intent track = new Intent(con, TrackActivity.class);
        track.putExtra("orderTime",calendar.getTimeInMillis());
        track.putExtra("orderDetails",getOrderDetails(list));
        startActivity(track);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.order_go){
            AlertDialog.Builder builder = new AlertDialog.Builder(con);
            int qty = getItemQty(list);
            builder.setMessage("Your order for "+qty+" items, Order value is 710.00.\nContinue?").setTitle("Confirm order");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    execIntent();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            if (checkQty(list))
                builder.create().show();
        }
        return true;
    }
}
