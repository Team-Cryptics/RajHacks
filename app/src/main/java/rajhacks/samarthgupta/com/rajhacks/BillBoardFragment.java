package rajhacks.samarthgupta.com.rajhacks;


import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class BillBoardFragment extends Fragment {
    TextView tvSlotStartTime, tvSlotStart, tvSlotEndTime, tvSlotEnd, tvEst;
    ImageView ivGraph;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String imUrl = this.getArguments().getString("url");
        int from = this.getArguments().getInt("from");
        int to = this.getArguments().getInt("to");
        String fromAmPm, toAmPm;
        String est = Integer.toString((int)this.getArguments().getDouble("est"));
        Log.d("TAG","Image"+ imUrl);

        View v = inflater.inflate(R.layout.fragment_bill_board, container, false);
        tvSlotStartTime = (TextView) v.findViewById(R.id.tv_start_time);
        tvSlotStart = (TextView) v.findViewById(R.id.tv_start);
        tvSlotEndTime = (TextView) v.findViewById(R.id.tv_end_time);
        tvSlotEnd= (TextView) v.findViewById(R.id.tv_end);
        tvEst = (TextView) v.findViewById(R.id.tv_estimated_traffic);

        if(from>12){
            from = from%12;
            fromAmPm = "pm";
        }
        else{
            fromAmPm = "am";
        }

        if(to>12){
            to = to%12;
            toAmPm = "pm";
        }
        else{
            toAmPm = "am";
        }


        tvSlotStart.setText(fromAmPm);
        tvSlotStartTime.setText(Integer.toString(from));

        tvSlotEnd.setText(toAmPm);
        tvSlotEndTime.setText(Integer.toString(to));

        tvEst.setText(est);

        ivGraph = (ImageView) v.findViewById(R.id.iv_slot);
        Picasso.with(getActivity()).load("http://"+imUrl).into(ivGraph);



        return v;
    }

}
