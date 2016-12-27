package com.example.teuljung.practice2.coordinatorLayout;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.teuljung.practice2.DiagnosisActivity;
import com.example.teuljung.practice2.R;

/**
 * Created by teul jung on 2016-12-27.
 */
public class checkListFragment extends Fragment{
    Dialog dlg;
    CheckBox ch1,ch2,ch3,ch4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int a= getArguments().getInt("selectedColor");
        Button resultBtn;
        View v;
        switch(a) {
            case 1:
                v = inflater.inflate(R.layout.ch_dog_head, container, false);
                resultBtn = (Button)v.findViewById(R.id.getResult);
                ch1=(CheckBox)v.findViewById(R.id.h1);
                ch2=(CheckBox)v.findViewById(R.id.h2);
                ch3=(CheckBox)v.findViewById(R.id.h3);
                ch4=(CheckBox)v.findViewById(R.id.h4);
                resultBtn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dlg = new Dialog(getActivity());
                        dlg.setContentView(R.layout.diag_result_dlg);
                        dlg.setTitle("Show result");
                        TextView res = (TextView)dlg.findViewById(R.id.tv_result);
                        res.setText("예상 진단 결과\n");
                        if(ch1.isChecked()) res.append("호흡곤란일 때, 신장병, 치아불균형이 있을 수 있습니다.\n");
                        if(ch2.isChecked()) res.append("열이 있을 때, 독감, 심장사상충을 조심해야합니다.\n");
                        if(ch3.isChecked()) res.append("눈이 충혈됐을 때, 눈병, 눈에 부상을 입었을 수 있습니다.\n");
                        if(ch4.isChecked()) res.append("기침할 때, 폐암, 심장사상충, 감염균을 조심해야합니다.\n");
                        dlg.show();
                    }
                });
                break;
            case 2:
                v = inflater.inflate(R.layout.ch_dog_leg, container, false);
                resultBtn = (Button)v.findViewById(R.id.getResult);
                ch1=(CheckBox)v.findViewById(R.id.l1);
                ch2=(CheckBox)v.findViewById(R.id.l2);
                ch3=(CheckBox)v.findViewById(R.id.l3);
                ch4=(CheckBox)v.findViewById(R.id.l4);
                resultBtn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dlg = new Dialog(getActivity());
                        dlg.setContentView(R.layout.diag_result_dlg);
                        dlg.setTitle("Show result");
                        TextView res = (TextView)dlg.findViewById(R.id.tv_result);
                        res.setText("예상 진단 결과\n");
                        if(ch1.isChecked()) res.append("다리를 불편하게 움직일 때, 척추 질환, 성장불균형이 있을 수 있습니다.\n");
                        if(ch2.isChecked()) res.append("뼈가 부러졌을 때, 뼈 관련 질환을 조심해야합니다.\n");
                        if(ch3.isChecked()) res.append("다리를 떨고 있을 때, 영양 부족, 발에 상처를 입었을 수 있습니다.\n");
                        if(ch4.isChecked()) res.append("상처가 곪았을 때, 감염균을 조심해야합니다.\n");
                        dlg.show();
                    }
                });
                break;
            case 3:
                v = inflater.inflate(R.layout.ch_dog_behavior, container, false);
                resultBtn = (Button)v.findViewById(R.id.getResult);
                ch1=(CheckBox)v.findViewById(R.id.b1);
                ch2=(CheckBox)v.findViewById(R.id.b2);
                ch3=(CheckBox)v.findViewById(R.id.b3);
                ch4=(CheckBox)v.findViewById(R.id.b4);
                resultBtn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dlg = new Dialog(getActivity());
                        dlg.setContentView(R.layout.diag_result_dlg);
                        dlg.setTitle("Show result");
                        TextView res = (TextView)dlg.findViewById(R.id.tv_result);
                        res.setText("예상 진단 결과\n");
                        if(ch1.isChecked()) res.append("예민할 때, 혼자 있기를 싫어하거나 공포증이 있을 수 있습니다.\n");
                        if(ch2.isChecked()) res.append("평소보다 자주 짖을 때, 타이레놀 중독일 수 있습니다.\n");
                        if(ch3.isChecked()) res.append("식욕이 왕성할 때, 소화불량, 단백질 부족일 수 있습니다.\n");
                        if(ch4.isChecked()) res.append("구토증세를 보일 때, 마그네슘 과다일 수 있습니다.\n");
                        dlg.show();
                    }
                });
                break;
            case 4:
                v = inflater.inflate(R.layout.ch_dog_body, container, false);
                resultBtn = (Button)v.findViewById(R.id.getResult);
                break;
            default:
                v = inflater.inflate(R.layout.ch_dog_body,container,false);
        }
        return v;
    }
}