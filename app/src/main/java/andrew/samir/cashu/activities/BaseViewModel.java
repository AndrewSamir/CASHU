package andrew.samir.cashu.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Observable;

import andrew.samir.cashu.R;

public class BaseViewModel extends Observable {

    public void showMessage(@StringRes int message, Context context) {
        showMessage(null, message, context);
    }

    private void showMessage(String title, @StringRes int message, Context context) {
        MaterialDialog.Builder builder = getMaterialDialogBuilder(context);
        builder.content(message);
        if (title != null) {
            builder.title(title);
        }

        builder.content(message).positiveText(R.string.agree).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        }).autoDismiss(true).titleGravity(GravityEnum.CENTER).contentGravity(GravityEnum.CENTER).show();


    }

    private MaterialDialog.Builder getMaterialDialogBuilder(Context context) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
//        builder.typeface("TheSansArabic-Bold.otf", "TheSansArabic-Plain.otf");

        return builder;
    }

    public void showLoading() {


    }

}
