package abheri.com.vaijayantikosha;

public class Util {

    public static String[] getRelationArray(){


        String relationList[]=
         {
                "पर्यायवाची (Synsets)",    //0
                "अवयवी(Holonym)",       //1
                "अवयवः(Meronym)",       //2
                "पराजाति:(Hypernym)",     //3
                "अपराजातिः(Hyponym)",     //4
                "जन्यः",                  //5
                "जनकः",                 //6
                "पतिः",                  //7
                "पत्नि",                  //8
                "सेवकः",                 //9
                "स्व्वमी",                 //10
                "धर्मी",                  //11
                "धर्मः",                  //12
                "गुणः",                  //13
                "गुणी",                  //14
                "संबद्धः",                 //15
                "उपजीव्यः",               //16
                "उपजीवकः",              //17
                "अवतारः",                //18
                "पदार्थतत्वविचारः (Ontology)",  //19
                "All Relations"           //20
        };

        return relationList;
    }
}

