package weather.bhanvisangar.weather;

import android.widget.TextView;

import com.weather.bhanvisangar.weather.BuildConfig;
import com.weather.bhanvisangar.weather.LaunchActivity;
import com.weather.bhanvisangar.weather.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)


public class LaunchActivityTest{
    private LaunchActivity activity;

    @Before
    public void setUp() throws Exception{
        activity = Robolectric.buildActivity(LaunchActivity.class).create().resume().get();
    }

    @Test
    public void shouldNotBeNull() throws Exception{
        assertNotNull(activity);
    }

    @Test
    public  void shouldHaveTextview() throws Exception{
        TextView textview = (TextView) activity.findViewById(R.id.location_textview);
        assertNotNull(textview);
    }

}
