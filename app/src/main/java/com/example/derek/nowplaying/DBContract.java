//package com.example.derek.nowplaying;
//
//import android.provider.BaseColumns;
//
///**
// * Created by Derek on 10/11/2016.
// */
//
//public final class DBContract {
//    private DBContract(){}
//
//    public static class DBEntry implements BaseColumns {
//        public  static final String TABLE_NAME = "nowPlaying";
//        public  static final String COLUMN_NAME_SONG = "song";
//        public  static final String COLUMN_NAME_ARTIST = "artist";
//
//        private static final String TEXT_TYPE = " TEXT";
//        private static final String COMMA_SEP = ",";
//        private static final String SQL_CREATE_ENTRIES =
//                "CREATE TABLE " + DBEntry.TABLE_NAME + " ("+
//                DBEntry._ID + " INTEGER PRIMARY KEY, " +
//                DBEntry.COLUMN_NAME_SONG + TEXT_TYPE + COMMA_SEP +
//                DBEntry.COLUMN_NAME_ARTIST + TEXT_TYPE + " )";
//        private static final String SQL_DELETE_ENTRIES =
//                "DROP TABLE IF EXISTS " + DBEntry.TABLE_NAME;
//    }
//
//}
