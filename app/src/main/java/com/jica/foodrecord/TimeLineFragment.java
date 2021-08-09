package com.jica.foodrecord;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeLineFragment extends Fragment {


    RecyclerView recyclerView;
    TimeLineAdapter adapter;
    OnDatabaseCallback callback;
    TextView tvYear;


    EditText etTodayDate;
    TextView tvTodayLocation;
    TextView tvTodayTime;
    TextView tvTodayWho;
    CheckBox cbTodayDrink;




    Context context;



    @Override
    public void onAttach(@NonNull  Context context) {
        super.onAttach(context);


        callback = (OnDatabaseCallback) getActivity();


    }

    @Override
    public void onDetach() {
        super.onDetach();


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup flContainer, Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_time_line, flContainer, false);


        context = flContainer.getContext();

        //리사이클러뷰
        recyclerView  = rootView.findViewById(R.id.rvTimeLine);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerDecoration spaceDecoration = new RecyclerDecoration(25);
        recyclerView.addItemDecoration(spaceDecoration);

        adapter = new TimeLineAdapter();
        recyclerView.setAdapter(adapter);

        //데이터 불러오기
        ArrayList<FoodItem> result = callback.selectAll();
        adapter.setItems(result);




        //리사이클러뷰 클릭시 다이얼로그 show

        adapter.setItemClickListener(new OnTimeLineSummaryClickListener() {
            @Override
            public void onItemClick(TimeLineAdapter.ViewHolder holder, View view, int position) {
                FoodItem item = adapter.getItem(position);

                final  View dialogBView = inflater.inflate(R.layout.timeline_dialog, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setView(dialogBView);

                final AlertDialog dialog = builder.create();

                dialog.setCanceledOnTouchOutside(true);

                dialog.show();

            //버튼 클릭시 이벤트 발생

                Button btnDataUpdate = dialogBView.findViewById(R.id.btnDataUpdate);
                btnDataUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        Toast.makeText(context, "click", Toast.LENGTH_LONG).show();
                    }
                });

                Button btnDataDelete = dialogBView.findViewById(R.id.btnDataDelete);
                btnDataDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //데이타베이스에서 제거
                      int _id = item.get_id();
                      callback.delete(_id);

                      //리싸이클러뷰의 원본에서 제거
                      adapter.deleteItem(position);
                      adapter.notifyItemRemoved(position);

                      Toast.makeText(context, "delete", Toast.LENGTH_LONG).show();
                    }
                });

                Toast.makeText(context, "clicked" +item.getTitle(), Toast.LENGTH_LONG ).show();
            }
        });




        //올해 년도 가지고 오기
        tvYear = rootView.findViewById(R.id.tvYear);

        Date currentYear = Calendar.getInstance().getTime();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        String year = yearFormat.format(currentYear);

        tvYear.setText(year);



        //상단 화면 보이기

        etTodayDate = rootView.findViewById(R.id.etTodayDate);


        SimpleDateFormat todayFormat = new SimpleDateFormat("yyyy. MM. dd. EE", Locale.getDefault());
        String todayDate = todayFormat.format(currentYear);

        etTodayDate.setText(todayDate);

//        SimpleDateFormat today = new SimpleDateFormat("M.d", Locale.getDefault());
//        String todayDate2 = todayFormat.format(currentYear);









        //플로팅액션바

        FloatingActionButton fabMenu = (FloatingActionButton)rootView.findViewById(R.id.fabMenu);
        FloatingActionButton fabSetting = (FloatingActionButton)rootView.findViewById(R.id.fabSetting);
        FloatingActionButton fabStats = (FloatingActionButton)rootView.findViewById(R.id.fabStats);
//        FloatingActionButton fabPlus = (FloatingActionButton)rootView.findViewById(R.id.fabPlus);


        fabMenu.setOnClickListener(new View.OnClickListener() {

            boolean isVisible;

            @Override
            public void onClick(View v) {

                isVisible = !isVisible;

                if(isVisible){
                    fabSetting.setVisibility(View.VISIBLE);
                    fabStats.setVisibility(View.VISIBLE);
//                    fabPlus.setVisibility(View.VISIBLE);
                }else {
                    fabSetting.setVisibility(View.INVISIBLE);
                    fabStats.setVisibility(View.INVISIBLE);
//                    fabPlus.setVisibility(View.INVISIBLE);

                }
            }
        });


//        fabPlus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),EditActivity.class);
//                startActivity(intent);
//            }
//        });

        fabStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StatsActivity.class);
                startActivity(intent);
            }
        });

        fabSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        return rootView;

    }


}