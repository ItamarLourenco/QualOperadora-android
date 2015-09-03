package com.isl.operadora.Model;

import com.isl.operadora.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by webx on 10/04/15.
 +----+---------------------+------+-------+---------------------+
 | id | operadora           | spid | rn1   | created_at          |
 +----+---------------------+------+-------+---------------------+
 |  1 | Operadora           | SPID |     0 | 2015-04-06 22:12:32 |
 |  2 | TELECALL            | 101  | 55101 | 2015-04-06 22:12:32 |
 |  3 | LIFE-TELECOM        | 102  | 55102 | 2015-04-06 22:12:32 |
 |  4 | BAYDENET            | 104  | 55104 | 2015-04-06 22:12:32 |
 |  5 | TPA                 | 105  | 55105 | 2015-04-06 22:12:32 |
 |  6 | AMERICANET          | 106  | 55106 | 2015-04-06 22:12:32 |
 |  7 | MAHA-TEL            | 109  | 55109 | 2015-04-06 22:12:32 |
 |  8 | CTBC - FIXO         | 112  | 55112 | 2015-04-06 22:12:32 |
 |  9 | FONAR               | 113  | 55113 | 2015-04-06 22:12:32 |
 | 10 | BRT - FIXO          | 114  | 55114 | 2015-04-06 22:12:32 |
 | 11 | TELEFONICA          | 115  | 55115 | 2015-04-06 22:12:32 |
 | 12 | SPIN                | 118  | 55118 | 2015-04-06 22:12:32 |
 | 13 | VISAONET            | 119  | 55119 | 2015-04-06 22:12:32 |
 | 14 | GTI TELECOM         | 120  | 55120 | 2015-04-06 22:12:32 |
 | 15 | EMBRATEL            | 121  | 55121 | 2015-04-06 22:12:32 |
 | 16 | INTELIG             | 123  | 55123 | 2015-04-06 22:12:32 |
 | 17 | DIALDATA            | 124  | 55124 | 2015-04-06 22:12:32 |
 | 18 | GVT                 | 125  | 55125 | 2015-04-06 22:12:32 |
 | 19 | AEROTECH            | 127  | 55127 | 2015-04-06 22:12:32 |
 | 20 | VOXBRAS             | 128  | 55128 | 2015-04-06 22:12:32 |
 | 21 | T-LESTE             | 129  | 55129 | 2015-04-06 22:12:32 |
 | 22 | WKVE                | 130  | 55130 | 2015-04-06 22:12:32 |
 | 23 | OI - FIXO           | 131  | 55131 | 2015-04-06 22:12:32 |
 | 24 | NWI                 | 133  | 55133 | 2015-04-06 22:12:32 |
 | 25 | ETML                | 134  | 55134 | 2015-04-06 22:12:32 |
 | 26 | DSLI-VOX            | 136  | 55136 | 2015-04-06 22:12:32 |
 | 27 | GOLDENLINE          | 137  | 55137 | 2015-04-06 22:12:32 |
 | 28 | TESA                | 138  | 55138 | 2015-04-06 22:12:32 |
 | 29 | HOJE-TELECOM        | 140  | 55140 | 2015-04-06 22:12:32 |
 | 30 | TIM - FIXO          | 141  | 55141 | 2015-04-06 22:12:32 |
 | 31 | GT-GROUP            | 142  | 55142 | 2015-04-06 22:12:32 |
 | 32 | SERCOMTEL - FIXO    | 143  | 55143 | 2015-04-06 22:12:32 |
 | 33 | SUL INTERNET        | 144  | 55144 | 2015-04-06 22:12:32 |
 | 34 | BT-TELECOM          | 147  | 55147 | 2015-04-06 22:12:32 |
 | 35 | NETSERV             | 148  | 55148 | 2015-04-06 22:12:32 |
 | 36 | CAMBRIDGE           | 150  | 55150 | 2015-04-06 22:12:32 |
 | 37 | VIA REAL            | 151  | 55151 | 2015-04-06 22:12:32 |
 | 38 | CABO                | 152  | 55152 | 2015-04-06 22:12:32 |
 | 39 | ENGEVOX             | 156  | 55156 | 2015-04-06 22:12:32 |
 | 40 | BR GROUP            | 157  | 55157 | 2015-04-06 22:12:32 |
 | 41 | VOITEL              | 158  | 55158 | 2015-04-06 22:12:32 |
 | 42 | FALE 65             | 160  | 55160 | 2015-04-06 22:12:32 |
 | 43 | VONEX               | 161  | 55161 | 2015-04-06 22:12:32 |
 | 44 | MUNDIVOX            | 162  | 55162 | 2015-04-06 22:12:32 |
 | 45 | HELLOBRAZIL         | 163  | 55163 | 2015-04-06 22:12:32 |
 | 46 | SUPORTE-TECNOLOGIA  | 168  | 55168 | 2015-04-06 22:12:32 |
 | 47 | LOCAWEB             | 170  | 55170 | 2015-04-06 22:12:32 |
 | 48 | TELECOMDADOS        | 172  | 55172 | 2015-04-06 22:12:32 |
 | 49 | ALPHA-NOBILIS       | 173  | 55173 | 2015-04-06 22:12:32 |
 | 50 | OPCAONET            | 177  | 55177 | 2015-04-06 22:12:32 |
 | 51 | ULTRANET            | 185  | 55185 | 2015-04-06 22:12:32 |
 | 52 | TELETEL             | 186  | 55186 | 2015-04-06 22:12:32 |
 | 53 | TVN                 | 188  | 55188 | 2015-04-06 22:12:32 |
 | 54 | CONECTA             | 189  | 55189 | 2015-04-06 22:12:32 |
 | 55 | IP-CORP             | 191  | 55191 | 2015-04-06 22:12:32 |
 | 56 | VIACOM              | 192  | 55192 | 2015-04-06 22:12:32 |
 | 57 | VIPWAY              | 193  | 55193 | 2015-04-06 22:12:32 |
 | 58 | SMART-VOIP          | 194  | 55194 | 2015-04-06 22:12:32 |
 | 59 | IVATI               | 196  | 55196 | 2015-04-06 22:12:32 |
 | 60 | LIGUE               | 198  | 55198 | 2015-04-06 22:12:32 |
 | 61 | ENSITE              | 199  | 55199 | 2015-04-06 22:12:32 |
 | 62 | PORTO VELHO         | 200  | 55200 | 2015-04-06 22:12:32 |
 | 63 | HIT TELECOM         | 201  | 55201 | 2015-04-06 22:12:32 |
 | 64 | DIGIVOX             | 202  | 55202 | 2015-04-06 22:12:32 |
 | 65 | VALENET             | 203  | 55203 | 2015-04-06 22:12:32 |
 | 66 | AVA TELECOM         | 205  | 55205 | 2015-04-06 22:12:32 |
 | 67 | DESKTOP             | 206  | 55206 | 2015-04-06 22:12:32 |
 | 68 | PONTO TELECOM       | 208  | 55208 | 2015-04-06 22:12:32 |
 | 69 | WCS                 | 209  | 55209 | 2015-04-06 22:12:32 |
 | 70 | UNIVERSO TELECOM    | 211  | 55211 | 2015-04-06 22:12:32 |
 | 71 | TRIADE TELECOM      | 213  | 55213 | 2015-04-06 22:12:32 |
 | 72 | VIVO-FIXO           | 215  | 55215 | 2015-04-06 22:12:32 |
 | 73 | TERAPAR             | 217  | 55217 | 2015-04-06 22:12:32 |
 | 74 | SUPERIMAGEM         | 220  | 55220 | 2015-04-06 22:12:32 |
 | 75 | DATORA              | 301  | 55301 | 2015-04-06 22:12:32 |
 | 76 | CTBC - MOVEL        | 312  | 55312 | 2015-04-06 22:12:32 |
 | 77 | BRT - MOVEL         | 314  | 55314 | 2015-04-06 22:12:32 |
 | 78 | VIVO S.A.           | 320  | 55320 | 2015-04-06 22:12:32 |
 | 79 | CLARO S.A.          | 321  | 55321 | 2015-04-06 22:12:32 |
 | 80 | VIVO-MG             | 323  | 55323 | 2015-04-06 22:12:32 |
 | 81 | OI - MOVEL          | 331  | 55331 | 2015-04-06 22:12:32 |
 | 82 | TIM - MOVEL         | 341  | 55341 | 2015-04-06 22:12:32 |
 | 83 | SERCOMTEL - MOVEL   | 343  | 55343 | 2015-04-06 22:12:32 |
 | 84 | NEXTEL (SMP)        | 351  | 55351 | 2015-04-06 22:12:32 |
 +----+---------------------+------+-------+---------------------+
 */

public class Carries {
    private static Map<Integer, Integer> mCarries = new HashMap<>();

    public static void generateCarries(){
        mCarries.put(55114, R.drawable.brasil_telecom);
        mCarries.put(55321, R.drawable.claro);
        mCarries.put(55112, R.drawable.ctbc);
        mCarries.put(55312, R.drawable.ctbc);
        mCarries.put(55125, R.drawable.gvt);
        mCarries.put(55351, R.drawable.nextel);
        mCarries.put(55131, R.drawable.oi);
        mCarries.put(55331, R.drawable.oi);
        mCarries.put(55143, R.drawable.sercomtel);
        mCarries.put(55343, R.drawable.sercomtel);
        mCarries.put(55115, R.drawable.telefonica);
        mCarries.put(55141, R.drawable.tim);
        mCarries.put(55341, R.drawable.tim);
        mCarries.put(55215, R.drawable.vivo);
        mCarries.put(55320, R.drawable.vivo);
        mCarries.put(55323, R.drawable.vivo);
        mCarries.put(55121, R.drawable.embratel);
    }

    public Map<Integer, Integer> getCarries(){
        Carries.generateCarries();
        return mCarries;
    }

    public static int getCarreiImage(String rn1){
        try {
            int key = Integer.parseInt(rn1);
            Carries.generateCarries();
            Integer imageChosen = mCarries.get(key);
            if(imageChosen == null){
                return R.mipmap.ic_launcher;
            }

            return imageChosen;
        }catch(NumberFormatException e){
            return R.mipmap.ic_launcher;
        }
    }

    public static Carrie getCarriesByCode(int code){
        Map<Integer, Carrie> carries = new HashMap<>();
        carries.put(77, new Carrie("E_NEXTEL (SME)", R.drawable.nextel));
        carries.put(78, new Carrie("NEXTEL (SMP)", R.drawable.nextel));
        carries.put(23, new Carrie("TELEMIG", R.mipmap.ic_launcher));
        carries.put(12, new Carrie("CTBC", R.drawable.ctbc));
        carries.put(14, new Carrie("BRASIL TELECOM", R.drawable.brasil_telecom));
        carries.put(20, new Carrie("VIVO", R.drawable.vivo));
        carries.put(21, new Carrie("CLARO", R.drawable.claro));
        carries.put(31, new Carrie("OI", R.drawable.oi));
        carries.put(24, new Carrie("AMAZONIA", R.mipmap.ic_launcher));
        carries.put(37, new Carrie("UNICEL", R.mipmap.ic_launcher));
        carries.put(41, new Carrie("TIM", R.drawable.tim));
        carries.put(43, new Carrie("SERCOMERCIO", R.mipmap.ic_launcher));
        carries.put(81, new Carrie("Datora", R.mipmap.ic_launcher));
        carries.put(98, new Carrie("Fixo", R.mipmap.ic_launcher));
        carries.put(99, new Carrie("Não encontrado", R.mipmap.ic_launcher));


        Carrie carrie = carries.get(code);
        if(carrie == null){
            carrie = carries.get(99);
        }
        return carrie;
    }


    public static class Carrie{
        public String name;
        public int idImage;
        public Carrie(String name, int idImage) {
            this.name = name;
            this.idImage = idImage;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIdImage() {
            return idImage;
        }

        public void setIdImage(int idImage) {
            this.idImage = idImage;
        }
    }
}
