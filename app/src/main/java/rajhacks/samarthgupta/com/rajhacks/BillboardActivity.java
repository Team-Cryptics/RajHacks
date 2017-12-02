package rajhacks.samarthgupta.com.rajhacks;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;

public class BillboardActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private MapsActivity.TabsPagerAdapter mAdapter;
    Button btPlaceBet;
    LinearLayout llBid;
    Button btCancelBid, btConfirmBid;
    EditText etBid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billboard);

        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new MapsActivity.TabsPagerAdapter(getSupportFragmentManager());
        llBid = (LinearLayout) findViewById(R.id.ll_enter_bid);
        etBid = (EditText) findViewById(R.id.et_enter_bid);
        btCancelBid = (Button) findViewById(R.id.bt_cancel);

        btConfirmBid = (Button) findViewById(R.id.bt_confirm);
        llBid.setVisibility(View.GONE);
        btPlaceBet = (Button) findViewById(R.id.bt_place_bet);
        btPlaceBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llBid.setVisibility(View.VISIBLE);
                btPlaceBet.setVisibility(View.GONE);




            }
        });

        viewPager.setAdapter(mAdapter);
        viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());

    }




}
