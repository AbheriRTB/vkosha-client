package abheri.com.vaijayantikosha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;

public class ListViewActivity extends Activity {


    String relationList[] = Util.getRelationArray();

    String input_encode[] = {"Unicode-Devanagari","WX-alphabetic"};
    String output_encode[] = {"Unicode-Devanagari", "Roman-Diacritic"};
    String listType = "1";
    String padam_text = "";
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        //Toolbar Related
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.lightGray));

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(0);


        //If the flow is coming from the List selection, use the Intent to get new values
        Intent intent = getIntent();
        if (intent != null) {
            listType = intent.getStringExtra("SEL_VALUE");
            padam_text = intent.getStringExtra("PADAM");
        }
        switch (listType){
            case "1":
                listAdapter = new ListAdapter(input_encode, this, listType, padam_text);
                toolbar.setTitle("Input Encoding");
                break;
            case "2":
                listAdapter = new ListAdapter(relationList, this, listType, padam_text);
                toolbar.setTitle("Relations");
                break;
            case "3":
                listAdapter = new ListAdapter(output_encode, this, listType, padam_text);
                toolbar.setTitle("Output Encoding");
                break;

        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

}


