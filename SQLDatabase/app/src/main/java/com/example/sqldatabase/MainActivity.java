package com.example.sqldatabase;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Database database;
    private LayoutInflater inflater;
    private RecyclerView recyclerView;
    private CongViecAdapter congViecAdapter;
    private ArrayList<CongViec> congViecArrayList;
    private EditText editText;
    private Button btnThem, btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        congViecArrayList = new ArrayList<>();

        initAdapter();
        database = new Database(this, "ghichu.sql", 1, null);
        initData();
    }


    private void initAdapter() {
        recyclerView = findViewById(R.id.list_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        congViecAdapter = new CongViecAdapter(congViecArrayList, this);
        recyclerView.setAdapter(congViecAdapter);
    }

    private void initData() {

//        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(200))");
//        database.QueryData("INSERT INTO CongViec VALUES(null, 'Làm bài tập2')");
//        database.QueryData("INSERT INTO CongViec VALUES(null, 'Làm bài tập3')");
//        database.QueryData("INSERT INTO CongViec VALUES(null, 'Làm bài tập4')");

        Cursor cursor = database.getData("SELECT * FROM CongViec");
        while (cursor.moveToNext()) {
            String ten = cursor.getString(1);
            int id = cursor.getInt(0);
            congViecArrayList.add(new CongViec(id, ten));
            Log.v("AA", ten);
        }
        congViecAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_congviec, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_cv) {
            dialogCongViec();
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogCongViec() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dalog_add_cv);
        editText = dialog.findViewById(R.id.edt_cv);
        btnThem = dialog.findViewById(R.id.btn_them);
        btnHuy = dialog.findViewById(R.id.btn_huy);


        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tencv = editText.getText().toString();
                if (tencv.equals("")) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên công việc!", Toast.LENGTH_LONG).show();

                } else {
                    database.QueryData("INSERT INTO CongViec VALUES(null, '" + tencv + "')");
                    dialog.dismiss();
                    initData();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void dialogSua(String ten, final int id){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dalog_edit);

        final EditText editText = dialog.findViewById(R.id.edt_cv_sua);
        Button btnXacNhan = dialog.findViewById(R.id.btn_xacnhan_sua);
        Button btnHuy = dialog.findViewById(R.id.btn_huy_sua);
        editText.setText(ten);
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi = editText.getText().toString().trim();
                database.QueryData("UPDATE CongViec SET TenCV = '" + tenMoi + "'WHERE Id = '"+ id + "'");
                dialog.dismiss();
                initData();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void dalogXoa(final String tenCv, final  int id){
        AlertDialog.Builder xoa = new AlertDialog.Builder(this);
        xoa.setMessage("Bạn có muốn xóa công việc " + tenCv +" Không ?");
        xoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM CongViec WHERE Id = '" + id + "'");
                Toast.makeText(MainActivity.this, "Đã xóa" + tenCv , Toast.LENGTH_LONG).show();
                initData();
            }
        });
        xoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        xoa.show();
    }


}
