package abheri.com.vaijayantikosha;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpListDisplay extends AppCompatActivity {

    ExpListData expLstData = new ExpListData();
    ExpandableListView expandableListView;
    ExpListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    String jsonString = "";

    HashMap<String, Vkosha> vkoshaListDetail = new HashMap<String, Vkosha>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exp_list_display);

        Intent intent = getIntent();
        String padam = intent.getStringExtra("PADAM");
        String jsonString = intent.getStringExtra("SYNONYMS");

        expandableListView = findViewById(R.id.expandableListView);

        //expandableListDetail = expLstData.getData();
        expandableListDetail = parseJson(jsonString);
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new ExpListAdapter(this, expandableListTitle, expandableListDetail);

        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String pdm = expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(childPosition);
                Vkosha vk = vkoshaListDetail.get(pdm);

                String tstStr = vk.nigama + " " + vk.linga;
                Toast.makeText(
                        getApplicationContext(),
                        tstStr, Toast.LENGTH_SHORT).show();
                /*Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();*/


                return false;
            }
        });
    }

    public class Vkosha {

        String padam, headword, jathi, adhyaya, linga, nigama, kanda;

    }

    public HashMap<String, List<String>> parseJson(String jsonToParse) {

        JSONArray ja, jaSub;
        String subJson;
        ExpListDisplay.Vkosha tmpVkosha;
        ArrayList<ExpListDisplay.Vkosha> vKoshaArrayList = new ArrayList<ExpListDisplay.Vkosha>();
        String jsonstring;

        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        List<String> tmpStringList;

        //String dummy_json = "{ \"synset\": \"देव,निलिम्प,मरुत्,गीर्वाण,वयुन,सुर,आदित्य,ऋभु,अस्वप्न,विवस्वत्,दिवौकस्,आदितेय,दिविषद्,लेख,अदितिनन्दन,सुपर्वन्,क्रतुभुज्,निर्जर,अमृताशन,बर्हिर्मुख,विट्पति,त्रिदश,हव्ययोनि,अमर्त्य,दैवत,देवता\", \"jathi\": \"अलौकिकस्थानम्\", \"adhyaya\": \"आदिदेवाध्यायः\", \"linga\": \"पुं।\", \"nigama\": \"1।1।2।2।1\", \"kanda\": \"स्वर्गकाण्डः\" }";
        //String jsonstring = dummy_json;
        jsonstring = jsonToParse;

        try {
            ja = new JSONArray(jsonstring);
            if (ja != null) {
                for (int i = 0; i < ja.length(); ++i) {
                    String heading = "";
                    tmpStringList = new ArrayList<String>();

                    jaSub = ja.getJSONArray(i);

                    for (int j = 0; j < jaSub.length(); ++j) {
                        JSONObject jo = jaSub.getJSONObject(j);
                        tmpVkosha = new ExpListDisplay.Vkosha();

                        tmpVkosha.padam = jo.getString("padam");
                        tmpVkosha.headword = jo.getString("headword");
                        tmpVkosha.jathi = jo.getString("jathi");
                        tmpVkosha.adhyaya = jo.getString("adhyaya");
                        tmpVkosha.linga = jo.getString("linga");
                        tmpVkosha.nigama = jo.getString("nigama");
                        tmpVkosha.kanda = jo.getString("kanda");
                        heading = tmpVkosha.headword;

                        vKoshaArrayList.add(tmpVkosha);

                        tmpStringList.add(tmpVkosha.padam);
                        vkoshaListDetail.put(tmpVkosha.padam, tmpVkosha);
                    }
                    expandableListDetail.put("अर्थः - " + heading, tmpStringList);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return expandableListDetail;
    }
}




