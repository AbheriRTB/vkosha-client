package abheri.com.vaijayantikosha;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static abheri.com.vaijayantikosha.R.color.myblue;

public class MultiTextViewDisplay extends AppCompatActivity implements View.OnClickListener {

    ArrayList<TextView> tvArrayList = new ArrayList<TextView>();
    ArrayList<ArrayList<Vkosha>> vkoshaListDetail = new ArrayList<ArrayList<Vkosha>>();
    HashMap<String, Vkosha> vkoshaHashMap = new HashMap<String, Vkosha>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_textview);

        Intent intent = getIntent();

        String padam = intent.getStringExtra("PADAM");
        String jsonString = intent.getStringExtra("SYNONYMS");
        String relation = intent.getStringExtra("RELATION");
        String input_type = intent.getStringExtra("IN_TYPE");
        String output_type = intent.getStringExtra("OUT_TYPE");

        //Toast.makeText(this, "Multi Text View Display Page",Toast.LENGTH_LONG).show();
        LinearLayout linearLayout= (LinearLayout) findViewById(R.id.multitext_linear); //find the linear layout
        TextView padamTV = (TextView)findViewById(R.id.mview_padam);
        padamTV.setText(padam);


        vkoshaListDetail = parseJson(jsonString);

        for(int i=0; i<vkoshaListDetail.size(); ++i) {

            // Creating a new Flexboxlayout
            FlexboxLayout flexboxLayout = new FlexboxLayout(this);
            flexboxLayout.setFlexWrap(FlexWrap.WRAP);

            FlexboxLayout.LayoutParams rlp = new FlexboxLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            rlp.topMargin = 30;
            flexboxLayout.setLayoutParams(rlp);
            linearLayout.addView(flexboxLayout);

            List<Vkosha> vkList = vkoshaListDetail.get(i);

            //Show Headword
            TextView hdTextView = new TextView(this); //dynamically create textview
            hdTextView.setId((int)1000);
            RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
            hdTextView.setTextSize((float)25.0);
            hdTextView.setLayoutParams(params);
            hdTextView.setTextColor(getResources().getColor(myblue));
            String arthaVarga = "अर्थः -  "+ vkList.get(0).headword + " | वर्गः - " + vkList.get(0).adhyaya + "| " + vkList.get(0).eng_meaning +", "+ vkList.get(0).sans_meaning + " | ";
            hdTextView.setText(arthaVarga); //adding text
            flexboxLayout.addView(hdTextView, params);


            for (int j= 0; j < vkList.size(); j++) { //looping to create 5 textviews

                TextView textView = new TextView(this); //dynamically create textview
                textView.setId(j + 1);
                textView.setOnClickListener(this);

                params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                int pid;
                if (j > 0) {
                    pid = tvArrayList.get(j - 1).getId();
                }else {
                    pid = hdTextView.getId();
                }
                params.addRule(RelativeLayout.ALIGN_BASELINE, pid);
                params.addRule(RelativeLayout.RIGHT_OF, pid);
                textView.setTextSize((float)25.0);
                textView.setLayoutParams(params);
                String comma=", ";
                if(j == vkList.size()-1)
                    comma = "";
                textView.setText(vkList.get(j).padam + comma); //adding text
                flexboxLayout.addView(textView, params); //inflating :)

                tvArrayList.add(textView);
            }
        }
    }

    public class Vkosha {
        String headword, padam, pratam, nigama, linga, adhyaya, kanda, eng_meaning, sans_meaning, synset, avyaya,
                parapara, janyajanaka, patipatni, swaswamy, vaishistyam, anya, ajivika, avatara, jathi, upadhi;
    }

    public ArrayList<ArrayList<Vkosha>> parseJson(String jsonToParse) {

        JSONArray ja, jaSub;
        Vkosha tmpVkosha;
        ArrayList<ArrayList<Vkosha>> vKoshaArrayList = new ArrayList<ArrayList<Vkosha>>();
        String jsonstring;

        //String dummy_json = "{ \"synset\": \"देव,निलिम्प,मरुत्,गीर्वाण,वयुन,सुर,आदित्य,ऋभु,अस्वप्न,विवस्वत्,दिवौकस्,आदितेय,दिविषद्,लेख,अदितिनन्दन,सुपर्वन्,क्रतुभुज्,निर्जर,अमृताशन,बर्हिर्मुख,विट्पति,त्रिदश,हव्ययोनि,अमर्त्य,दैवत,देवता\", \"jathi\": \"अलौकिकस्थानम्\", \"adhyaya\": \"आदिदेवाध्यायः\", \"linga\": \"पुं।\", \"nigama\": \"1।1।2।2।1\", \"kanda\": \"स्वर्गकाण्डः\" }";
        //String jsonstring = dummy_json;
        jsonstring = jsonToParse;

        try {
            ja = new JSONArray(jsonstring);
            if (ja != null) {
                for (int i = 0; i < ja.length(); ++i) {
                    ArrayList<Vkosha> tmpVKList = new ArrayList<Vkosha>();
                    jaSub = ja.getJSONArray(i);

                    for (int j = 0; j < jaSub.length(); ++j) {
                        JSONObject jo = jaSub.getJSONObject(j);
                        tmpVkosha = new Vkosha();

                        tmpVkosha.padam = jo.getString("padam");
                        tmpVkosha.headword = jo.getString("headword");
                        tmpVkosha.jathi = jo.getString("jathi");
                        tmpVkosha.adhyaya = jo.getString("adhyaya");
                        tmpVkosha.linga = jo.getString("linga");
                        tmpVkosha.nigama = jo.getString("nigama");
                        tmpVkosha.kanda = jo.getString("kanda");
                        //Extras........

                        tmpVkosha.eng_meaning = jo.getString("eng_meaning");
                        tmpVkosha.sans_meaning = jo.getString("sans_meaning");
                        //tmpVkosha.synset = jo.getString("synset");
                        tmpVkosha.avyaya = jo.getString("avyaya");
                        tmpVkosha.parapara = jo.getString("parapara");
                        tmpVkosha.janyajanaka = jo.getString("janyajanaka");
                        tmpVkosha.patipatni = jo.getString("patipatni");
                        tmpVkosha.swaswamy = jo.getString("swaswamy");
                        tmpVkosha.vaishistyam = jo.getString("vaishistyam");
                        tmpVkosha.anya = jo.getString("anya");
                        tmpVkosha.ajivika = jo.getString("ajivika");
                        tmpVkosha.avatara = jo.getString("avatara");
                        tmpVkosha.upadhi = jo.getString("upadhi");

                        tmpVKList.add(tmpVkosha);
                        vkoshaHashMap.put(tmpVkosha.padam, tmpVkosha);

                    }

                    vKoshaArrayList.add(tmpVKList);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return vKoshaArrayList;
    }

    @Override
    public void onClick(View view) {
        TextView mtv = (TextView)view;
        String str = (String)mtv.getText();
        if(str.indexOf(',') != -1) //Last padam in the list will not contain comma & space at the end
            str = str.substring(0, str.length()-2);

        String nigama = vkoshaHashMap.get(str).nigama;
        String linga = vkoshaHashMap.get(str).linga;

        Toast.makeText(this, str + " --> " + nigama + " " + linga,Toast.LENGTH_LONG).show();
    }
}