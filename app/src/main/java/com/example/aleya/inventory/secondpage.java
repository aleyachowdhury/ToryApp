package com.example.aleya.inventory;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

public class secondpage extends AppCompatActivity {
    private static final String database_url = "jdbc:mysql://pugpen.org:3306/pugpen67_inventory_mobile";
    private static final String database_user = "pugpen67_dev";
    private static final String database_pass = "Group7&";

    private TextView getData1;
    private TextView getData2;
    private TextView getData3;
    private TextView getData4;
    private TextView getData5;

    Button homebutton;
    ArrayList<tory> toryArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondpage);



        getData1 = (TextView) findViewById(R.id.textView21);
        getData2 = (TextView) findViewById(R.id.textView22);
        getData3 = (TextView) findViewById(R.id.textView23);

        getData4 = (TextView) findViewById(R.id.textView24);
        getData5= (TextView) findViewById(R.id.textView25);






        //getData = (TextView) findViewById(R.id.tableView);
        new getDataFromDatabase().execute();
        homebutton = (Button) findViewById(R.id.homebutton);
        homebutton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              startActivity(new Intent(getApplicationContext(), inventory.class));
                                          }//end getDataFromDatabase
                                      }//end OnClickListener
        );//end firstQueryButton.setOnClickListener




       /* Button itemList = (Button) findViewById(R.id.itemList);
        //set the onClick listener for the button
        itemList.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            new getDataFromDatabase().execute();
                                        }//end getDataFromDatabase
                                    }//end OnClickListener
        );//end loadDataButton.setOnClickListener*/


    }


    class tory {
        int id;
        String name;
        int qty;
        float price;
        float unit_cost;
        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }



        public int getQty() {
            return qty;
        }
        public void setQty(int qty) {
            this.qty = qty;
        }
        public float getPrice() {
            return price;
        }
        public void setPrice(float price) {
            this.price = price;
        }
        public int getId()
        {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public float getUnit_cost()
        {
            return unit_cost;
        }

        public void setUnit_cost(float unit_cost) {
            this.unit_cost = unit_cost;
        }
    }




    private class getDataFromDatabase extends AsyncTask<Void, Void, Void> {
        //references: http://developer.android.com/reference/android/os/AsyncTask.html
        //            https://www.youtube.com/watch?v=N0FLT5NdSNU (about the 7 min mark)
        private String queryResult;
        private String queryResult1="";
        private String queryResult2="";
        private String queryResult3="";
        private String queryResult4="";
        private String queryResult5="";



        protected Void doInBackground(Void... arg0) {
            try {
                queryResult = "Database connection success\n";

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(database_url, database_user, database_pass);
                String queryString = "SELECT * FROM item_lst";

                Statement st = con.createStatement();
                final ResultSet rs = st.executeQuery(queryString);
                ResultSetMetaData rsmd = rs.getMetaData();


                while (rs.next()) {

                    tory inv = new tory();
                    inv.setId(rs.getInt(1));
                    inv.setName(rs.getString(2));
                    inv.setQty(rs.getInt(3));
                    inv.setPrice(rs.getFloat(4));
                    inv.setUnit_cost(rs.getFloat(5));
                    toryArrayList.add(inv);

                    // queryResult += inv.getId() +"  "+inv.getName()+"  "+inv.getQty()+"   "+inv.getPrice()+"  "+inv.getUnit_cost()+'\n';
                    queryResult1 += inv.getId() +"\n" ;
                    queryResult2 += inv.getName() +'\n';
                    queryResult3 += inv.getQty() +"\n";
                    queryResult4 += inv.getPrice() +"\n";
                    queryResult5 += inv.getUnit_cost() +  "\n";


                }

                con.close(); //close database connection
            } catch (Exception e) {
                e.printStackTrace();
                //put the error into the TextView on the app screen
                queryResult = "Database connection failure\n" + e.toString();
            }

            return null;
        }//end database connection via doInBackground

        //after processing is completed, post to the screen
        protected void onPostExecute(Void result) {

            getData1.setText(queryResult1);
            getData2.setText(queryResult2);
            getData3.setText(queryResult3);
            getData4.setText(queryResult4);
            getData5.setText(queryResult5);




        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_db, menu);
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
}


