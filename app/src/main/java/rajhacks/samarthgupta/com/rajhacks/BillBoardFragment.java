package rajhacks.samarthgupta.com.rajhacks;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    TextView tvSlotStartTime, tvSlotStart, tvSlotEndTime, tvSlotEnd;
    ImageView ivGraph;

    public BillBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String imUrl = this.getArguments().getString("Url");




        View v = inflater.inflate(R.layout.fragment_bill_board, container, false);
        tvSlotStartTime = (TextView) v.findViewById(R.id.tv_start_time);
        tvSlotStart = (TextView) v.findViewById(R.id.tv_start);
        tvSlotEndTime = (TextView) v.findViewById(R.id.tv_end_time);
        tvSlotEnd= (TextView) v.findViewById(R.id.tv_end);




        ivGraph = (ImageView) v.findViewById(R.id.iv_slot);
        Picasso.with(getActivity()).load(imUrl).into(ivGraph);



        return v;
    }

}
