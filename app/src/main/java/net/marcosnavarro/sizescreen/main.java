package net.marcosnavarro.sizescreen;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class main extends ActionBarActivity {

    private TextView width, height, model, resolution, density, type;
    private DisplayMetrics display;
    private TelephonyManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TextView width = (TextView) findViewById(R.id.width);
        TextView height = (TextView) findViewById(R.id.height);
        TextView model = (TextView) findViewById(R.id.model);
        TextView resolution = (TextView) findViewById(R.id.resolution);
        TextView density = (TextView) findViewById(R.id.density);
        TextView type = (TextView) findViewById(R.id.type);

        display = (DisplayMetrics) this.getResources().getDisplayMetrics();
        manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        width.setText(convertPixelsToDp(display.widthPixels)+"dp "+"("+display.widthPixels+"px)");
        height.setText(convertPixelsToDp(display.heightPixels)+"dp\n"+"("+display.heightPixels+"px)");

        String manufacturer = Build.MANUFACTURER;
        String models = Build.MODEL;
        String text_model = null;
        if (models.startsWith(manufacturer)) {
            text_model = models;
        } else {
            text_model = manufacturer+" "+models;
        }
        model.setText(text_model);

        // return 0.75 if it's LDPI
        // return 1.0 if it's MDPI
        // return 1.5 if it's HDPI
        // return 2.0 if it's XHDPI
        String text_density = null;
        String text_screen = null;
        switch (display.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                text_screen = "small";
                text_density = "ldpi";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                text_screen = "medium";
                text_density = "mdpi";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                text_screen = "large";
                text_density = "xdpi";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                text_screen = "xlarge";
                text_density = "xhdpi";
                break;
        }
        resolution.setText(text_screen+" screen");
        density.setText(text_density+" "+Float.toString(display.density));

        String type_device = null;
        if(manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE){
            type_device = "Tablet";
        }else{
            type_device = "Mobile";
        }
        type.setText(type_device);
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public float convertDpToPixel(float dp){
        float px = dp * (display.densityDpi / 160f);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public float convertPixelsToDp(float px){
        float dp = px / (display.densityDpi / 160f);
        return dp;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
