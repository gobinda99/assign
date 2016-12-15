package com.gobinda.assignment.commons;

import com.gobinda.assignment.App;

/**
 * Created by gobinda on 7/9/16.
 */
public class PrefHelper {

   public static void setCurrentEmail(String email){
        App.getPrefs().edit().putString("email",email).commit();
    }

    public static String getCurrentEmail(){
      return App.getPrefs().getString("email",null);
    }
    public static void removeCurrentEmail() {
        App.getPrefs().edit().remove("email").commit();
    }
}
