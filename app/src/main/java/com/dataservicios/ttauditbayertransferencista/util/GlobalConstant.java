package com.dataservicios.ttauditbayertransferencista.util;
/**
 * Created by usuario on 11/11/2014.
 */
public final class GlobalConstant {

    public static String dominio = "http://ttaudit.com";
//    public static String dominio = "http://192.168.8.100";
    public static String directory_images = "/Pictures/" ;
    public static final String JPEG_FILE_PREFIX = "_ttauditbayertrans_";
    public static final String JPEG_FILE_SUFFIX = ".jpg";


    public static final String URL_USER_IMAGES = dominio + "/media/users/";
    public static final String URL_PUBLICITY_IMAGES = dominio + "/media/images/alicorp/publicities/";

    //  Directorios para  almacenamiento
    public static final String ALBUN_NAME = "Bayer_M_Photo";
    public static final String ALBUN_NAME_TEMP = "Bayer_M_Photo_Temp";
    public static final String ALBUN_NAME_BACKUP = "Bayer_M_Photo_Backup";
    public static final String DATA_BASE_DIR_NAME_BACKUP = "ibk_db_Backup";

    //   Variable  apps externos
    public static final String MARKET_OPEN_APP_ESFILEEXPLORE = "market://details?id=com.estrongs.android.pop&hl=es";
    public static final String DATABASE_PATH_DIR ="/data/com.dataservicios.ttauditibkregular/databases/db_ibk";
}