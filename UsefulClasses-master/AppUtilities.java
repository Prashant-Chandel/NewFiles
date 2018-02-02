package taxi.driverApp.taxiApp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.Locale;

import taxi.driverApp.taxiApp.R;


/**
 * Created by SFS on 5/16/16.
 */
public class AppUtilities {

    static Context _context;
    private static AppUtilities mInstance;
    private AlertDialog alertDialog;


    public AppUtilities(Context context){
        this._context = context;
    }

    /**
     * toast a message
     * **/
    public  void toastMsg(String msg){
        Toast.makeText(_context, msg, Toast.LENGTH_SHORT).show();

    }


    public Dialog initProgressDialog(Context context) {
        Dialog progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.progress_dialog_view);
        progressDialog.setCancelable(false);
        return progressDialog;
    }
    public void initAlertDialog(Context context, final String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.show();
    }

    public void hide_keyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String convertToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
    public static String convertUTF8ToString(String s) {
        String out = null;
        try {
            out = new String(s.getBytes(), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
    public static boolean updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return true;
    }
}
