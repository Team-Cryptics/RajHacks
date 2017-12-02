package rajhacks.samarthgupta.com.rajhacks;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;

public class BillboardActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private MapsActivity.TabsPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billboard);

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new MapsActivity.TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());

    }




}
