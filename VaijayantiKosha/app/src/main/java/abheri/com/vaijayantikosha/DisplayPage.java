package abheri.com.vaijayantikosha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

public class DisplayPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_page);

        Intent intent = getIntent();
        String padam = intent.getStringExtra("PADAM");
        String synonyms = intent.getStringExtra("SYNONYMS");

        // Capture the layout's TextView and set the string as its text
        TextView ppadam_txVw = findViewById(R.id.ppada);
        ppadam_txVw.setText(padam);

        ListView simpleList;
        String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};

       /*simpleList = (ListView)findViewById(R.id.synList);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_display_page, R.id.synTxt, countryList);
                simpleList.setAdapter(arrayAdapter);

*/


        TextView synonyms_txVw = findViewById(R.id.synonyms);
        synonyms_txVw.setText(synonyms);

        Toast.makeText(DisplayPage.this,"Synonym for " + padam + "--->" + synonyms , Toast.LENGTH_LONG).show();


    }
}


