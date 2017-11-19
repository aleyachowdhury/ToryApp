package com.example.aleya.inventory;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class UserActivity extends Activity {
    private static final String database_url = "jdbc:mysql://pugpen.org:3306/pugpen67_inventory_mobile";
    private static final String database_user = "pugpen67_dev";
    private static final String database_pass = "Group7&";
    Button register;
    EditText name, login, password,role;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_layout);
        register = (Button) findViewById(R.id.registerbutton);
        name = (EditText) findViewById(R.id.editName);
        login = (EditText) findViewById(R.id.editLogin);
        password = (EditText) findViewById(R.id.editPassword);
        role = (EditText) findViewById(R.id.editRole);
        /*Button scan = (Button) findViewById(R.id.scanbutton);
        scan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanActivity.class));
            }

        });*/

        Button allUser = (Button) findViewById(R.id.allUser);
        allUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UsersActivity.class));
            }

        });
        Button home = (Button) findViewById(R.id.homebutton);
        home.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), inventory.class));
            }

        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckRegister checkLogin = new CheckRegister();// load on app process
                checkLogin.execute("");
            }
        });
        //End Setting up the function when button login is clicked
    }

    public class CheckRegister extends AsyncTask<String, String, String> {
        String i = "";
        Boolean isSuccess = false;

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(UserActivity.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(UserActivity.this, "Register Successfull", Toast.LENGTH_LONG).show();
                //finish();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String nam = name.getText().toString();
            String log = login.getText().toString();
            String passw = password.getText().toString();
            String rol = role.getText().toString();
            if (nam.trim().equals("") ||  log.trim().equals("") || passw.trim().equals("") || rol.trim().equals(""))
                i = "Please fill all form fields";
            else {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(database_url, database_user, database_pass);       // Connect to database
                    if (con == null) {
                        i = "Check Your Internet Access!";
                    } else {
                       // String queryString = " insert into pugpen67_inventory_mobile.user_lst = '" + nam.toString() + "' and login = '" + log.toString() + "' + password = '" + passw.toString() + "' + role = '" + rol.toString() + "'";



                        String query = " INSERT INTO pugpen67_inventory_mobile.user_lst  ( name, login, password, role_id ) VALUES (?,?,?,?)";

                               // Statement stmt = con.createStatement();
                                //stmt.executeUpdate(query);

                        PreparedStatement preparedStmt = con.prepareStatement(query);
                        preparedStmt.setString(1, String.valueOf(name.getText()));
                        preparedStmt.setString(2, String.valueOf(login.getText()));
                        preparedStmt.setString(3, String.valueOf(password.getText()));
                        preparedStmt.setString(4, String.valueOf(role.getText()));
                       // preparedStmt.setString(4, String.valueOf(role.getText()));
                        //ResultSet rs = stmt.executeUpdate();
                        preparedStmt.execute();
                        con.close();



                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    i = ex.getMessage();
                }
            }
            return i;
        }
    }


}

