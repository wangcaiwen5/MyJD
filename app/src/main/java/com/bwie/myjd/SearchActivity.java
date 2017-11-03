package com.bwie.myjd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.tv_search)
    TextView tvSearch;
    @InjectView(R.id.editText)
    EditText et_search;

    private int page=1;
    private int sort=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);


        initView();

    }

    private void initView() {
        tvSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tv_search:
                String trim = et_search.getText().toString().trim();
                Intent intent = new Intent(SearchActivity.this,ChildClassifyListActivity.class);
                intent.putExtra("keywords",trim);
                intent.putExtra("flag","1");
                startActivity(intent);
                break;
        }

    }


}
