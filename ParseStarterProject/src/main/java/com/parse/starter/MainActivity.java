/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  EditText userNameField;
  EditText passwordField;
  TextView changeSignUpMode;
  Button loginButton;
  Boolean signUpModeActive;

  @Override
  public void onClick(View v) {

    if (v.getId() == R.id.changeSignMode) {
      // Log.i("appinfo", "change Signup ");

      if (signUpModeActive == true)
      {
        signUpModeActive = false;
        changeSignUpMode.setText("Login");
        loginButton.setText("Sign Up");
      }
      else
      {
        signUpModeActive = true;
        changeSignUpMode.setText("Sign Up");
        loginButton.setText("Login");
      }

    }
  }

  public void SignUpOrLogin(View view)
  {
    if(signUpModeActive==false) {
      ParseUser user = new ParseUser();
      user.setUsername(String.valueOf(userNameField.getText()));
      user.setPassword(String.valueOf(passwordField.getText()));

      user.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException e) {

          if (e == null)
          {
            Log.i("appinfo", "Signup Succsseful");
            Toast.makeText(getApplicationContext(), R.string.WelcomeToast, Toast.LENGTH_LONG).show();
          } else
            Toast.makeText(getApplicationContext(), e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_LONG).show();
        }
      });
    }
    else
    {
      ParseUser.logInInBackground(String.valueOf(userNameField.getText()), String.valueOf(passwordField.getText()), new LogInCallback() {
        @Override
        public void done(ParseUser user, ParseException e) {

          if (user != null) {

            Log.i("AppInfo", "Login Successful");
            Toast.makeText(getApplicationContext(), R.string.WelcomeToast, Toast.LENGTH_LONG).show();

          } else {

            Toast.makeText(getApplicationContext(), e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_LONG).show();

          }

        }
      });

    }

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    userNameField =(EditText)findViewById(R.id.UserText);
    passwordField =(EditText)findViewById(R.id.PassText);
    changeSignUpMode = (TextView)findViewById(R.id.changeSignMode);
    changeSignUpMode.setOnClickListener(this);
    loginButton = (Button)findViewById(R.id.LogInButton);
    signUpModeActive=true;


    ParseAnalytics.trackAppOpenedInBackground(getIntent());

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
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
