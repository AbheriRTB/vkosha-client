package abheri.com.vaijayantikosha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;



public class ListDisplay extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    ImageButton info;
    ListView simpleList;
    String nigama;
    Vkosha myVkosha[] = new Vkosha[5];
    String[] synArray = new String[20];
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_display);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);



        Intent intent = getIntent();
        String padam = intent.getStringExtra("PADAM");
        String jsonString = intent.getStringExtra("SYNONYMS");


        myVkosha = parseJson(jsonString);
        synArray = getSynonyms(myVkosha[0].synonym);



        /*JSONArray jsonArray = "[{id:\"1\", name:\"sql\"},{id:\"2\",name:\"android\"},{id:\"3\",name:\"mvc\"}]";
        JSON newJson = new JSON();*/


        String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};

        simpleList = (ListView) findViewById(R.id.synList);
        simpleList.setOnItemClickListener(this);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_view_cell_content, R.id.listCell, synArray);

        simpleList.setAdapter(arrayAdapter);



 /*       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


    String[] getSynonyms(String synonymStr) {
        String[] retStr;
        retStr = synonymStr.split(",");
        return retStr;

    }

    public void onClick(View view) {

            Toast.makeText(this, "Ref :" + myVkosha[0].nigama, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String dataStr="";
        Toast.makeText(this, "Ref :" + myVkosha[0].jathi, Toast.LENGTH_LONG).show();
        TextView tv = view.findViewById(R.id.listCell);
        try {
            Runnable gdr = new GetData((String)tv.getText());
            Thread gdt = new Thread(gdr);
            gdt.start();
            gdt.join();
            dataStr = ((GetData) gdr).getReturnStr();

            myVkosha = parseJson(dataStr);
            synArray = getSynonyms(myVkosha[0].synonym);
            arrayAdapter.clear();
            arrayAdapter.addAll(synArray);
            arrayAdapter.notifyDataSetChanged();

        }catch (Exception e){

        }
    }

    public class Vkosha {

        String synonym, jathi, adhyaya, linga, nigama, kanda;

    }

    public Vkosha[] parseJson(String jsonToParse) {

        JSONArray ja;
        Vkosha tmpVkosha;
        ArrayList<Vkosha> vKoshaArrayList = new ArrayList<Vkosha>();
        String jsonstring;

        //String dummy_json = "{ \"synset\": \"देव,निलिम्प,मरुत्,गीर्वाण,वयुन,सुर,आदित्य,ऋभु,अस्वप्न,विवस्वत्,दिवौकस्,आदितेय,दिविषद्,लेख,अदितिनन्दन,सुपर्वन्,क्रतुभुज्,निर्जर,अमृताशन,बर्हिर्मुख,विट्पति,त्रिदश,हव्ययोनि,अमर्त्य,दैवत,देवता\", \"jathi\": \"अलौकिकस्थानम्\", \"adhyaya\": \"आदिदेवाध्यायः\", \"linga\": \"पुं।\", \"nigama\": \"1।1।2।2।1\", \"kanda\": \"स्वर्गकाण्डः\" }";
        //String jsonstring = dummy_json;
        jsonstring = jsonToParse;

        try {
            ja = new JSONArray(jsonstring);
            if (ja != null) {
                for (int i = 0; i < ja.length(); ++i) {
                    tmpVkosha = new Vkosha();
                    JSONObject jo = ja.getJSONObject(i);

                    tmpVkosha.synonym = jo.getString("synset");
                    tmpVkosha.jathi = jo.getString("jathi");
                    tmpVkosha.adhyaya = jo.getString("adhyaya");
                    tmpVkosha.linga = jo.getString("linga");
                    tmpVkosha.nigama = jo.getString("nigama");
                    tmpVkosha.kanda = jo.getString("kanda");

                    vKoshaArrayList.add(tmpVkosha);
                }

            }
        }catch (Exception e){
            System.out.println(e.toString());
        }

        Object[] oba =  vKoshaArrayList.toArray();
        Vkosha[] vkArray = Arrays.copyOf(oba,oba.length,Vkosha[].class);
        return vkArray;
    }
}