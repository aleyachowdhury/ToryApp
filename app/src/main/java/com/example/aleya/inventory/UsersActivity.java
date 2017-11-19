package com.example.aleya.inventory;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
public class UsersActivity extends Activity {
    private static final String database_url = "jdbc:mysql://pugpen.org:3306/pugpen67_inventory_mobile";
    private static final String database_user = "pugpen67_dev";
    private static final String database_pass = "Group7&";
    // placeholder that you will be updating with the database data
    private TextView getData;
    private TextView getData1;
    private TextView getData2;
    ArrayList<userTory>  userToryArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_layout);
        // find the screen element that you need


       /* Button scan = (Button) findViewById(R.id.scanbutton);
        scan.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), ScanActivity.class));
            }

        });*/
        getData1 = (TextView) findViewById(R.id.textView21);
        getData2 = (TextView) findViewById(R.id.textView22);


        Button home = (Button) findViewById(R.id.homebutton);
        home.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), inventory.class));
            }

        });
        //   getData = (TextView) findViewById(R.id.queryResults1);


        Button loadDataButton = (Button) findViewById(R.id.getDataButton1);
        //set the onClick listener for the button
        loadDataButton.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View v){
                                                  new getDataFromDatabase().execute();
                                              }//end getDataFromDatabase
                                          }//end OnClickListener
        );//end loadDataButton.setOnClickListener
    }//end onCreate






    class userTory {

        String name;
        int roleId;

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }



        public int getRoleId()
        {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }
    }



    private class getDataFromDatabase extends AsyncTask<Void, Void, Void> {
        //references: http://developer.android.com/reference/android/os/AsyncTask.html
        //            https://www.youtube.com/watch?v=N0FLT5NdSNU (about the 7 min mark)
        private String queryResult;
        private String queryResult1="";
        private String queryResult2="";

        protected Void doInBackground(Void... arg0)  {
            try {
                queryResult = "Database connection success\n";

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(database_url, database_user, database_pass);
                String queryString = "select * from pugpen67_inventory_mobile.user_lst";
                Statement st = con.createStatement();
                final ResultSet rs = st.executeQuery(queryString);
                ResultSetMetaData rsmd = rs.getMetaData();
                //do some things with the data you've retrieved
                while (rs.next()) {
                    userTory userinv = new userTory();

                    userinv.setName(rs.getString(2));
                    userinv.setRoleId(rs.getInt(5));

                    userToryArrayList.add(userinv);

                    queryResult1 += userinv.getName() +'\n';
                    queryResult2 += userinv.getRoleId() +"\n";

                }
                con.close(); //close database connection
            } catch (Exception e) {
                e.printStackTrace();
                //put the error into the TextView on the app screen
                queryResult = "Database connection failure\n" +  e.toString();
            }

            return null;
        }
        //after processing is completed, post to the screen
        protected void onPostExecute(Void result) {
            //put the results into the TextView on the app screen
           // getData.setText(queryResult);
            getData1.setText(queryResult1);
            getData2.setText(queryResult2);
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

