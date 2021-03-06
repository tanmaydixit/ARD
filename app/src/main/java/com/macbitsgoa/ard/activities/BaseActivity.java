package com.macbitsgoa.ard.activities;

import android.content.SharedPreferences;
import android.support.annotation.IntRange;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.macbitsgoa.ard.BuildConfig;
import com.macbitsgoa.ard.utils.AHC;

/**
 * Base activity with useful methods.
 *
 * @author Vikramaditya Kukreja
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * Get {@link SharedPreferences} for the app.
     * @return app shared pref {@link AHC#SP_APP} in private mode.
     */
    public SharedPreferences getDefaultSharedPref() {
        return getSharedPreferences(AHC.SP_APP, MODE_PRIVATE);
    }

    /*
    /**
     * Get string value from resource.
     *
     * @param stringResource Id of resource.
     * @return String value.
     *
    public String getStringRes(@StringRes final int stringResource) {
        return getResources().getString(stringResource);
    }

    /**
     * Get integer value from resource.
     *
     * @param intResource Id of resource.
     * @return int value.
     *
    public int getIntegerRes(@IntegerRes final int intResource) {
        return getResources().getInteger(intResource);
    }
    */

    /**
     * Returns the root reference for the Firebase Database.
     * It varies depending on the build.
     *
     * @return root reference for the current build type.
     */
    public DatabaseReference getRootReference() {
        return FirebaseDatabase.getInstance().getReference().getRoot().child(BuildConfig.BUILD_TYPE);
    }


    /**
     * Displays a toast in current activity. In this method the duration
     * supplied is {@link Toast#LENGTH_SHORT} that the toast shows.
     *
     * @param message Message to be displayed.
     */
    public void showToast(final String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    /**
     * Displays a toast in current activity. The duration can of two types:
     * <ul>
     * <li>{@link Toast#LENGTH_SHORT}</li>
     * <li>{@link Toast#LENGTH_LONG}</li>
     * </ul>
     *
     * @param message   Message that the toast must show.
     * @param toastType Duration for which the toast must be visible.
     */
    public void showToast(final String message,
                          @IntRange(from = Toast.LENGTH_SHORT,
                                  to = Toast.LENGTH_LONG) final int toastType) {
        Toast.makeText(this, message, toastType).show();
    }

    /**
     * Show simple snack. Default duration is {@link Snackbar#LENGTH_SHORT}.
     * Text color is {@link Color#WHITE} and background color is {@link Color#BLACK}.
     *
     * @param message Message to be displayed.
     *
    public void showSnack(@NonNull final String message) {
        showSnack(message, Snackbar.LENGTH_SHORT);
    }

    /**
     * Show simple snack. Default duration is {@link Snackbar#LENGTH_SHORT}.
     * Text color is {@link Color#WHITE} and background color is {@link Color#BLACK}.
     * <p>
     * Length can be defined as
     * <ul>
     * <li>{@link Snackbar#LENGTH_SHORT}</li>
     * <li>{@link Snackbar#LENGTH_LONG}</li>
     * <li>{@link Snackbar#LENGTH_INDEFINITE}</li>
     * </ul>
     * </p>
     *
     * @param message Message to be displayed.
     * @param length  Int value to be used as length.
     *
    public void showSnack(@NonNull final String message, final int length) {
        showSnack(getWindow().getDecorView(), message, length);
    }

    /**
     * Show simple snack. Default duration is {@link Snackbar#LENGTH_SHORT}.
     * Text color is {@link Color#WHITE} and background color is {@link Color#BLACK}.
     * <p>
     * Length can be defined as
     * <ul>
     * <li>{@link Snackbar#LENGTH_SHORT}</li>
     * <li>{@link Snackbar#LENGTH_LONG}</li>
     * <li>{@link Snackbar#LENGTH_INDEFINITE}</li>
     * </ul>
     * </p>
     *
     * @param view View to be used.
     * @param message Message to be displayed.
     * @param length  Int value to be used as length.
     *
    public void showSnack(@NonNull final View view,
                          @NonNull final String message, final int length) {
        showSnack(message, length, getWindow().getDecorView(), R.color.white, R.color.black);
    }


    /**
     * Show simple snack. Default duration is {@link Snackbar#LENGTH_SHORT}.
     * Text color is {@link Color#WHITE} and background color is {@link Color#BLACK}.
     * <p>
     * Length can be defined as
     * <ul>
     * <li>{@link Snackbar#LENGTH_SHORT}</li>
     * <li>{@link Snackbar#LENGTH_LONG}</li>
     * <li>{@link Snackbar#LENGTH_INDEFINITE}</li>
     * </ul>
     * </p>
     *
     * @param view View to be used.
     * @param message Message to be displayed.
     * @param length  Int value to be used as length.
     * @param textColor Color res for textColor.
     * @param backgroundColor Color res for backgroundColor.
     *
    public void showSnack(@NonNull final String message, final int length,
                          @NonNull final View view, @ColorRes final int textColor,
                          @ColorRes final int backgroundColor) {
        final Snackbar snackbar = Snackbar.make(view, message, length);
        final TextView snackBarText = (TextView) snackbar.getView()
                .findViewById(R.id.snackbar_text);
        snackBarText.setTextColor(ContextCompat.getColor(this, textColor));
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, backgroundColor));
        snackbar.show();
    }

    @Override
    public void onClick(final View v) {

    }*/
}
