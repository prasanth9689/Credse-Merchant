package in.sethiya.bizzbots.bfsi.finces.merchant.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;

public class Utils {
    public static void showMessage(Context context, String message){}

    public static void showMessageInSnackbar(Context context, String message) {
        Snackbar snack = Snackbar.make(
                (((Activity) context).findViewById(android.R.id.content)),
                message, Snackbar.LENGTH_SHORT);
        snack.setDuration(Snackbar.LENGTH_SHORT);
        View view = snack.getView();
        TextView tv = (TextView) view
                .findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);//change textColor

        TextView tvAction = (TextView) view
                .findViewById(com.google.android.material.R.id.snackbar_action);
        tvAction.setTextSize(16);
        tvAction.setTextColor(Color.WHITE);
        tv.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
        snack.show();
    }

    public static void hideKeyboard(Context context){
        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void playSound1(Context context){
//        MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.juntos);
//        mPlayer.start();
    }
}