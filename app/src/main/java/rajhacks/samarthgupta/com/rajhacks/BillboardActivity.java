package rajhacks.samarthgupta.com.rajhacks;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static rajhacks.samarthgupta.com.rajhacks.MapsActivity.baseUrl;
public class BillboardActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private MapsActivity.TabsPagerAdapter mAdapter;
    Button btPlaceBet;
    Bitmap bitmap;

    Slots[] list;

    int user =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billboard);

        list = new GsonBuilder().create().fromJson(getIntent().getStringExtra("data"),Slots[].class);

        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new MapsActivity.TabsPagerAdapter(getSupportFragmentManager());
        btPlaceBet = (Button) findViewById(R.id.bt_place_bet);
        btPlaceBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                btPlaceBet.setVisibility(View.GONE);
                final Dialog dial = new Dialog(BillboardActivity.this);
                dial.setContentView(R.layout.layout_dialogue);
                final EditText etBid;
                etBid = (EditText) dial.findViewById(R.id.et_enter_bid);
                TextView tvCancelBid;
                Button btConfirmBid;
                tvCancelBid = (TextView) dial.findViewById(R.id.bt_cancel);
                btConfirmBid = (Button) dial.findViewById(R.id.bt_confirm);
                dial.setCancelable(false);
                dial.show();
                tvCancelBid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dial.dismiss();
                        btPlaceBet.setVisibility(View.VISIBLE);
                    }
                });

                btConfirmBid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Check input value
                        Double bid = Double.parseDouble(etBid.getText().toString());
                        int pos = viewPager.getCurrentItem();
                        Double maxBid = Double.parseDouble(list[pos].getMaxBid());

                        postBidOnServer(bid,user,list[pos].getFromTime());


//                        else{
//
//                            Log.d("Max",maxBid+"");
//                            Toast.makeText(BillboardActivity.this, "Your bid is less than the current maximum bid", Toast.LENGTH_SHORT).show();
//                        }



                    }
                });




            }
        });



        viewPager.setAdapter(mAdapter);
        viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());

    }

    private void postBidOnServer(Double bid, int user, int startTimeFrag) {
        String url = baseUrl.replace(":33","")+"/" + "win_bid.php?"+"maxbid="+bid+"&winner="+user+"&time="+startTimeFrag;

        Log.d("URL",url);
        Volley.newRequestQueue(BillboardActivity.this).add(new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if(response.equals("true")){
                    Toast.makeText(BillboardActivity.this, "Bid posted successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BillboardActivity.this, MapsActivity.class));
                    finish();
                }

                else{
                    Toast.makeText(BillboardActivity.this, "Current maximum bid has changed to "+response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BillboardActivity.this, MapsActivity.class));
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ad, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ad:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 123);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 123:

                    Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        SharedPreferences.Editor editor = getSharedPreferences("Billboard", MODE_PRIVATE).edit();
                        editor.putString("bitmap", b2s(bitmap)).apply();
                        Toast.makeText(this, "Document Uploaded", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    public static String b2s(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 75, baos);
        byte[] data = baos.toByteArray();
        return Base64.encodeToString(data, 0, data.length, Base64.DEFAULT);
    }

    public static Bitmap s2b(String string) {
        byte[] data = Base64.decode(string, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
