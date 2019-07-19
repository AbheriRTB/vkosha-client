package abheri.com.vaijayantikosha;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.content.Intent;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.String;

public class MainActivity extends AppCompatActivity {

    String ppadam_text = "";
    String dataStr = "";
    String relation = "", in_code= "",out_code = "";
    TextView relationTV, in_codeTV,out_codeTV;
    SharedPreferences mySP;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mySP = getSharedPreferences("VKSP", MODE_PRIVATE);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        relationTV = (TextView) findViewById(R.id.relation_edit);
        in_codeTV = (TextView) findViewById(R.id.inputEncode);
        out_codeTV = (TextView) findViewById(R.id.outputEncode);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //First read the values from the Shared preference and update TextViews
        if(mySP != null){
            if(mySP.contains("Relation"))
                relation = mySP.getString("Relation", "");
            if(mySP.contains("InputType"))
                in_code = mySP.getString("InputType", "");
            if(mySP.contains("OutputType"))
                out_code = mySP.getString("OutputType", "");

            relationTV.setText(relation);
            in_codeTV.setText(in_code);
            out_codeTV.setText(out_code);
        }

        //If the flow is coming from the List selection, use the Intent to get new values
        Intent intent = getIntent();
        if (intent != null) {
            relation = intent.getStringExtra("Relation");
            if (relation != null && relation != "")
                relationTV.setText(relation);

            in_code = intent.getStringExtra("InputType");
            if (in_code != null && in_code != "")
                in_codeTV.setText(in_code);

            out_code = intent.getStringExtra("OutputType");
            if (out_code != null && out_code != "")
                out_codeTV.setText(out_code);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        SharedPreferences.Editor ed = mySP.edit();

        if(relation != null && relation != "")
            ed.putString("Relation", relation);
        if(in_code != null && in_code != "")
            ed.putString("InputType", in_code);
        if(out_code != null && out_code != "")
            ed.putString("OutputType", out_code);

        ed.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void input_clicked(View view){
        //EditText inp_txt, out_txt;

        list_to_be_displayed("1");
    }

    public void rel_clicked(View view) {

        list_to_be_displayed("2");
    }


    public void output_clicked(View view) {
        //EditText inp_txt, out_txt;

        list_to_be_displayed("3");
    }


    public void list_to_be_displayed(String val){
        EditText inp_txt, out_txt;


        Intent intent = new Intent(this, ListDisplay.class);
        inp_txt = (EditText)findViewById(R.id.inputEncode);
        out_txt = (EditText)findViewById(R.id.outputEncode);

        String input_code = inp_txt.getText().toString();
        String output_code = out_txt.getText().toString();


        intent.putExtra("INPUT_ENCODE", input_code);
        intent.putExtra("OUTPUT_ENCODE", output_code);
        intent.putExtra("SEL_VALUE", val);



        Toast.makeText(this,"Going to next activity" + input_code, Toast.LENGTH_LONG).show();
        startActivity(intent);
    }


    public void submit_click(View view){

        EditText padam_txt;

        Intent intent = new Intent(this,MultiTextViewDisplay.class);
        padam_txt = (EditText)findViewById(R.id.ppadam);
        ppadam_text = padam_txt.getText().toString();

        try {
            Runnable gdr = new GetData(ppadam_text);
            Thread gdt = new Thread(gdr);
            gdt.start();
            gdt.join();
            dataStr = ((GetData) gdr).getReturnStr();
        }catch (Exception e){

        }

       /* Toast.makeText(MainActivity.this, ppadam_text + "---->" + dataStr, Toast.LENGTH_LONG).show(); */
        intent.putExtra("PADAM", ppadam_text);
        intent.putExtra("SYNONYMS", dataStr);
        intent.putExtra("RELATION", relation);
        intent.putExtra("IN_TYPE",in_code);
        intent.putExtra("OUT_TYPE", out_code);

        startActivity(intent);

        //Log.v("VK","the word : "+ txt);
        //Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
    }




}
