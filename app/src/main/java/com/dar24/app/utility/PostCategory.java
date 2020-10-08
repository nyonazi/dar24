package com.dar24.app.utility;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */

public class PostCategory {

    public static final String NAME_AJIRA = "ajira";
    public static final int ID_AJIRA = 320;

    public static final String NAME_AFYA = "afya";
    public static final int ID_AFYA = 13;

    public static final String NAME_BIASHARA = "biashara";
    public static final int ID_BIASHARA = 16;

    public static final String NAME_BUNGENI = "bungeni";
    public static final int ID_BUNGENI = 292;

    public static final String NAME_BURUDANI = "burudani";
    public static final int ID_BURUDANI = 11;

    public static final String NAME_HABARI = "habari";
    public static final int ID_HABARI = 10;

    public static final String NAME_HAPO_KALE = "hapo kale";
    public static final int ID_HAPO_KALE = 284;

    public static final String NAME_MAGAZETI = "magazeti";
    public static final int ID_MAGAZETI = 285;

    public static final String NAME_MAISHA = "maisha";
    public static final int ID_MAISHA = 14;

    public static final String NAME_MICHEZO = "michezo";
    public static final int ID_MICHEZO = 12;

    public static final String NAME_SAUTI_ZETU = "sauti zetu";
    public static final int ID_SAUTI_ZETU = 278;

    public static final String NAME_SIASA_ZETU = "siasa zetu";
    public static final int ID_SIASA_ZETU = 17;

    public static final String NAME_TEKNOLOJIA = "teknolojia";
    public static final int ID_TEKNOLOJIA = 15;

    public static int[] all() {
        int[] ints = {ID_AFYA, ID_BIASHARA, ID_BUNGENI, ID_BURUDANI, ID_HABARI, ID_HAPO_KALE,
                ID_MAGAZETI, ID_MAISHA, ID_MICHEZO, ID_SAUTI_ZETU, ID_SIASA_ZETU,
                ID_TEKNOLOJIA};
        return ints;
    }

    public static String getCategoryById(int id) {
        switch (id) {
            case ID_AFYA:
                return NAME_AFYA;
            case ID_BIASHARA:
                return NAME_BIASHARA;
            case ID_BUNGENI:
                return NAME_BUNGENI;
            case ID_BURUDANI:
                return NAME_BURUDANI;
            case ID_HABARI:
                return NAME_HABARI;
            case ID_HAPO_KALE:
                return NAME_HAPO_KALE;
            case ID_MAGAZETI:
                return NAME_MAGAZETI;
            case ID_MAISHA:
                return NAME_MAISHA;
            case ID_MICHEZO:
                return NAME_MICHEZO;
            case ID_SAUTI_ZETU:
                return NAME_SAUTI_ZETU;
            case ID_SIASA_ZETU:
                return NAME_SIASA_ZETU;
            case ID_TEKNOLOJIA:
                return NAME_TEKNOLOJIA;
            default:
                return "";
        }
    }

}