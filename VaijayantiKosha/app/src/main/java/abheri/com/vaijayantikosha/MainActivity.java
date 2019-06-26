package abheri.com.vaijayantikosha;


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

import java.lang.String;

public class MainActivity extends AppCompatActivity {

    String ppadam_text = "";
    String dataStr = "";
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
    public void submit_click(View view){

        EditText padam_txt;

        Intent intent = new Intent(this, MultiTextViewDisplay.class);
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

        startActivity(intent);

        //Log.v("VK","the word : "+ txt);
        //Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
    }




}
