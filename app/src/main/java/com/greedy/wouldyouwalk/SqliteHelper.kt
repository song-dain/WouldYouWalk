package com.greedy.wouldyouwalk

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper (context: Context, name: String, version: Int) : SQLiteOpenHelper(context, name, null, version) {

    override fun onCreate(db: SQLiteDatabase?) {

        val create = "create table timer (" +
                "num integer primary key, " +
                "date text, " +
                "labTime text " +
                ")"

        db?.execSQL(create)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    /* 1. insert */
    fun insertLabTime(record: Timer) {

        val values = ContentValues()
        values.put("date", record.date)
        values.put("labTime", record.labTime)

        val wd = writableDatabase
        wd.insert("timer", null, values)
        wd.close()
    }

    @SuppressLint("Range")
    fun selectLabTime(): MutableList<Timer> {

        if(readableDatabase == null) {
            onCreate(readableDatabase)
        }

        val rd = readableDatabase

        val select = "select * from timer"
        val list = mutableListOf<Timer>()

        var cursor = rd.rawQuery(select, null)

        while(cursor.moveToNext()) {
            val num = cursor.getLong(cursor.getColumnIndex("num"))
            val date = cursor.getString(cursor.getColumnIndex("date"))
            val labTime = cursor.getString(cursor.getColumnIndex("labTime"))
            list.add(Timer(num, date, labTime))
        }

        cursor.close()
        rd.close()

        return list
    }

    fun deleteRecord(record: Timer) {
        val delete = "delete from timer where num = ${record.num}"
        val db = writableDatabase
        db.execSQL(delete)
        db.close()
    }

}