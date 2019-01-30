package com.example.yogra.tourplanner.BottomSheetDialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.nfc.Tag;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogra.tourplanner.R;

import org.w3c.dom.Text;

public class BottomSheetFilter extends BottomSheetDialogFragment
{
    public static final String TAG = "BottomSheetDialog";

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        final View view = View.inflate(getContext(), R.layout.bottom_sheet_filter, null);
        dialog.setContentView(view);

        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox_click);
        //final TextView textView = (TextView) view.findViewById(R.id.tv_budget);
        final RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.bottom_sheet_container);
        final TextView textviewOne = (TextView) view.findViewById(R.id.tv_One) ;
        final TextView textviewTwo = (TextView) view.findViewById(R.id.tv_two );
        final TextView textviewThree = (TextView) view.findViewById(R.id.tv_three);

        textviewOne.setVisibility(View.GONE);
        textviewTwo.setVisibility(View.GONE);
        textviewThree.setVisibility(View.GONE);
        //final TextView textView = (TextView) view.findViewById(R.id.dummy);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    /*for (int i = 0; i < relativeLayout.getChildCount(); i++) {
                        View view = relativeLayout.getChildAt(i);
                        view.setVisibility(View.GONE);

                        Log.d(TAG, "Visible");*/
                        textviewOne.setVisibility(View.VISIBLE);
                        textviewTwo.setVisibility(View.VISIBLE);
                        textviewThree.setVisibility(View.VISIBLE);
                    }
                //}

                else {

                   /* for (int i = 0; i < relativeLayout.getChildCount(); i++) {
                        View view = relativeLayout.getChildAt(i);
                        view.setVisibility(View.VISIBLE);*/
                    textviewOne.setVisibility(View.GONE);
                    textviewTwo.setVisibility(View.GONE);
                    textviewThree.setVisibility(View.GONE);
                    }
                }
            //}

        });

        CheckBox checkBoxDuration = (CheckBox) view.findViewById(R.id.checkbox_two);
        final TextView textViewDurationOne = (TextView) view.findViewById(R.id.tv_day_one);
        final TextView textViewDurationTwo = (TextView) view.findViewById(R.id.tv_day_two );
        final TextView textViewDurationThree = (TextView) view.findViewById(R.id.tv_day_three);

        textViewDurationOne.setVisibility(View.GONE);
        textViewDurationTwo.setVisibility(View.GONE);
        textViewDurationThree.setVisibility(View.GONE);

        checkBoxDuration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    textViewDurationOne.setVisibility(View.VISIBLE);
                    textViewDurationTwo.setVisibility(View.VISIBLE);
                    textViewDurationThree.setVisibility(View.VISIBLE);
                }
                else {
                    textViewDurationOne.setVisibility(View.GONE);
                    textViewDurationTwo.setVisibility(View.GONE);
                    textViewDurationThree.setVisibility(View.GONE);
                }
            }
        });
    }
}


 //checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkBox.isChecked()){
//                    for ( int i = 0; i < linearLayout.getChildCount();  i++ ){
//                        View view = linearLayout.getChildAt(i);
//                        view.setVisibility(View.VISIBLE); // Or whatever you want to do with the view.
//                    }
//
////                    linearLayout.setVisibility(View.VISIBLE);
//                }
//                else {
//                    for ( int i = 0; i < linearLayout.getChildCount();  i++ ){
//                        View view = linearLayout.getChildAt(i);
//                        view.setVisibility(View.GONE); // Or whatever you want to do with the view.
//                    }
//
//                  //  linearLayout.setVisibility(View.GONE);
//                }
//            }
//        });
