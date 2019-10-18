package abheri.com.vaijayantikosha;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static abheri.com.vaijayantikosha.R.color.myblue;

public class DisplaySynonyms extends AppCompatActivity implements View.OnClickListener {

    ArrayList<TextView> tvArrayList = new ArrayList<TextView>();
    ArrayList<ArrayList<Vkosha>> vkoshaListDetail = new ArrayList<ArrayList<Vkosha>>();
    HashMap<String, Vkosha> vkoshaHashMap = new HashMap<String, Vkosha>();
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_synonyms);

        Intent intent = getIntent();

        String padam = intent.getStringExtra("PADAM");
        String jsonString = intent.getStringExtra("SYNONYMS");
        String relation = intent.getStringExtra("RELATION");
        String input_type = intent.getStringExtra("IN_TYPE");
        String output_type = intent.getStringExtra("OUT_TYPE");

        //Toast.makeText(this, "Multi Text View Display Page",Toast.LENGTH_LONG).show();
        TextView title = (TextView)findViewById(R.id.mview_title);
        title.setText(relation);

        linearLayout = (LinearLayout) findViewById(R.id.multitext_linear); //find the linear layout
        TextView padamTV = (TextView)findViewById(R.id.mview_padam);
        padamTV.setText(padam);


        System.out.println("Relation:" + relation);
        vkoshaListDetail = parseJson(jsonString);
        if(relation.equalsIgnoreCase("पदार्थतत्वविचारः (Ontology)")){
            DisplayOntology(vkoshaListDetail);
        }else{
            DisplaySynonyms(vkoshaListDetail);
        }
    }

    void DisplaySynonyms(ArrayList<ArrayList<Vkosha>> vkoshaListDetail) {

        for (int i = 0; i < vkoshaListDetail.size(); ++i) {

            // Creating a new Flexboxlayout
            FlexboxLayout flexboxLayout = new FlexboxLayout(this);
            flexboxLayout.setFlexWrap(FlexWrap.WRAP);

            FlexboxLayout.LayoutParams rlp = new FlexboxLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            rlp.topMargin = 30;
            flexboxLayout.setLayoutParams(rlp);

            List<Vkosha> vkList = vkoshaListDetail.get(i);
            RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
            params.topMargin = 30;

            String tvStr = "";
            //Show Header Fields
            //----- Artha ----
            TextView hTextview1 = new TextView(this); //dynamically create textview
            hTextview1.setTextSize((float) 25.0);
            hTextview1.setLayoutParams(params);
            hTextview1.setTextColor(getResources().getColor(R.color.mygreen));
            tvStr = "अर्थः -  " + vkList.get(0).headword;
            hTextview1.setText(tvStr); //adding text
            linearLayout.addView(hTextview1, params);

            params.topMargin = 0;
            //----- Meaning ----
            TextView hTextview2 = new TextView(this); //dynamically create textview
            hTextview2.setTextSize((float) 20.0);
            hTextview2.setLayoutParams(params);
            hTextview2.setTextColor(getResources().getColor(myblue));
            tvStr = "Meaning - " + vkList.get(0).eng_meaning;
            hTextview2.setText(tvStr); //adding text
            linearLayout.addView(hTextview2, params);

            //----- Vivaranam ----
            TextView hTextview3 = new TextView(this); //dynamically create textview
            hTextview3.setTextSize((float) 25.0);
            hTextview3.setLayoutParams(params);
            hTextview3.setTextColor(getResources().getColor(myblue));
            tvStr = "विवरणम् - " + vkList.get(0).sans_meaning;
            hTextview3.setText(tvStr); //adding text
            linearLayout.addView(hTextview3, params);

            //--- Varga ----
            TextView hTextview4 = new TextView(this); //dynamically create textview
            hTextview4.setTextSize((float) 25.0);
            hTextview4.setLayoutParams(params);
            hTextview4.setTextColor(getResources().getColor(R.color.myred));
            tvStr = "वर्गः - " + vkList.get(0).adhyaya;
            hTextview4.setText(tvStr); //adding text
            linearLayout.addView(hTextview4, params);

            linearLayout.addView(flexboxLayout);


            for (int j = 0; j < vkList.size(); j++) { //looping to create 5 textviews

                TextView textView = new TextView(this); //dynamically create textview
                textView.setId(j + 1);
                textView.setOnClickListener(this);
                textView.setTextColor(getResources().getColor(R.color.black));

                params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                int pid;
                if (j > 0) {
                    pid = tvArrayList.get(j - 1).getId();

                    params.addRule(RelativeLayout.ALIGN_BASELINE, pid);
                    params.addRule(RelativeLayout.RIGHT_OF, pid);
                }
                textView.setTextSize((float) 25.0);
                textView.setLayoutParams(params);
                String comma = ", ";
                if (j == vkList.size() - 1)
                    comma = "";
                textView.setText(vkList.get(j).padam + comma); //adding text
                flexboxLayout.addView(textView, params); //inflating :)

                tvArrayList.add(textView);
            }
        }
    }

    void DisplayOntology(ArrayList<ArrayList<Vkosha>> vkoshaListDetail) {

        for (int i = 0; i < vkoshaListDetail.size(); ++i) {

            // Creating a new Flexboxlayout
            RelativeLayout relativeLayout = new RelativeLayout(this);
            //relativeLayout.setFlexWrap(FlexWrap.WRAP);

            FlexboxLayout.LayoutParams rlp = new FlexboxLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            rlp.topMargin = 30;
            relativeLayout.setLayoutParams(rlp);

            List<Vkosha> vkList = vkoshaListDetail.get(i);
            RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
            params.topMargin = 30;

            String tvStr = "";
            /*
            //Show Header Fields
            //----- Artha ----
            TextView hTextview1 = new TextView(this); //dynamically create textview
            hTextview1.setTextSize((float) 25.0);
            hTextview1.setLayoutParams(params);
            hTextview1.setTextColor(getResources().getColor(R.color.mygreen));
            tvStr = "अर्थः -  " + vkList.get(0).headword;
            hTextview1.setText(tvStr); //adding text
            linearLayout.addView(hTextview1, params);

            params.topMargin = 0;
            //----- Meaning ----
            TextView hTextview2 = new TextView(this); //dynamically create textview
            hTextview2.setTextSize((float) 20.0);
            hTextview2.setLayoutParams(params);
            hTextview2.setTextColor(getResources().getColor(myblue));
            tvStr = "Meaning - " + vkList.get(0).eng_meaning;
            hTextview2.setText(tvStr); //adding text
            linearLayout.addView(hTextview2, params);

            //----- Vivaranam ----
            TextView hTextview3 = new TextView(this); //dynamically create textview
            hTextview3.setTextSize((float) 25.0);
            hTextview3.setLayoutParams(params);
            hTextview3.setTextColor(getResources().getColor(myblue));
            tvStr = "विवरणम् - " + vkList.get(0).sans_meaning;
            hTextview3.setText(tvStr); //adding text
            linearLayout.addView(hTextview3, params);

            //--- Varga ----
            TextView hTextview4 = new TextView(this); //dynamically create textview
            hTextview4.setTextSize((float) 25.0);
            hTextview4.setLayoutParams(params);
            hTextview4.setTextColor(getResources().getColor(R.color.myred));
            tvStr = "वर्गः - " + vkList.get(0).adhyaya;
            hTextview4.setText(tvStr); //adding text
            linearLayout.addView(hTextview4, params);
            */

            if(i > 0){
                View viewDivider = new View(this);
                //viewDivider.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                viewDivider.setBackgroundColor(Color.parseColor("#000000"));
                int dividerHeight = (int)getResources().getDisplayMetrics().density * 1; // 1dp to pixels
                viewDivider.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dividerHeight));

                linearLayout.addView(viewDivider);
            }

            TextView textViewJati = new TextView(this);
            textViewJati.setTextColor(getResources().getColor(R.color.mygreen));
            textViewJati.setTextSize((float) 27.0);
            textViewJati.setText("जातिः");
            linearLayout.addView(textViewJati);

            linearLayout.addView(relativeLayout);


            for (int j = 0; j < vkList.size(); j++) { //looping to create 5 textviews

                boolean upadhiProcessed = false;
                TextView textView = new TextView(this); //dynamically create textview
                textView.setId(j + 1);
                //textView.setOnClickListener(this);
                textView.setTextColor(getResources().getColor(R.color.black));

                params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                int pid;
                if (j > 0) {
                    pid = tvArrayList.get(j - 1).getId();

                    //params.addRule(RelativeLayout.ALIGN_BOTTOM, pid);
                    params.addRule(RelativeLayout.BELOW, pid);
                }
                textView.setTextSize((float) 25.0);
                textView.setLayoutParams(params);
                String comma = ", ";
                if (j == vkList.size() - 1)
                    comma = "";

                String ont = vkList.get(j).ontology;

                if(ont != null && ont.contains("_up")){
                    if(!upadhiProcessed) {
                        TextView textViewUpadhi = new TextView(this);
                        textViewUpadhi.setTextColor(getResources().getColor(R.color.mygreen));
                        textViewUpadhi.setTextSize((float) 27.0);
                        textViewUpadhi.setLayoutParams(params);
                        textViewUpadhi.setText("उपाधि");
                        textViewUpadhi.setId((int) 9999);

                        relativeLayout.addView(textViewUpadhi);

                        params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                        params.addRule(RelativeLayout.BELOW, (int) 9999);
                        upadhiProcessed = true;
                    }

                    ont = ont.substring(0, ont.length()-3);
                }

                textView.setText("===> " + ont ); //adding text
                relativeLayout.addView(textView, params); //inflating :)

                tvArrayList.add(textView);
            }
        }
    }

    public class Vkosha {
        String headword, padam, pratam, nigama, linga, adhyaya, kanda, eng_meaning, sans_meaning, synset, avyaya,
                parapara, janyajanaka, patipatni, swaswamy,dharma,guna, anya,vrutti, avatara,
                ontology;
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
                        //tmpVkosha.vaishistyam = jo.getString("vaishistyam");
                        tmpVkosha.anya = jo.getString("anya");
                        //tmpVkosha.ajivika = jo.getString("ajivika");
                        tmpVkosha.avatara = jo.getString("avatara");
                        tmpVkosha.ontology = jo.getString("onto_word");
                        tmpVkosha.dharma = jo.getString("dharma");
                        tmpVkosha.guna = jo.getString("guna");



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

        String displayStr1 = "काण्डः, अध्यायः, श्लोकः, पादः, पदसङ्ख्या :: ";
        String displayStr2 = "लिंग :: ";
        String nigama = vkoshaHashMap.get(str).nigama;
        String linga = vkoshaHashMap.get(str).linga;

        Toast toast = Toast.makeText(this, displayStr1 +
                            nigama + ", " + displayStr2 + linga,Toast.LENGTH_LONG);

        // COde block to change the color of toast
        View toastView = toast.getView();
        toastView.setPadding(5,5,5,5);
        //Gets the actual oval background of the Toast then sets the colour filter
        toastView.getBackground().setColorFilter(getResources().getColor(android.R.color.background_light), PorterDuff.Mode.SRC_IN);
        toastView.setBackgroundColor(getResources().getColor(R.color.lightYellow));
        //Gets the TextView from the Toast so it can be editted
        TextView text = toastView.findViewById(android.R.id.message);
        text.setTextSize(18);
        text.setTextColor(getResources().getColor(myblue));
        toast.show();
    }
}