package com.example.todolistapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, "MyApp", null, 1) {

    private val TABLE_ToDo = "ToDo"
    private val COL_ID = "ID"
    private val COL_TITLE = "TITLE"
    private val COL_BODY = "BODY"
    private val COL_isDone = "isDone"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $TABLE_ToDo (" +
                    "$COL_ID INTEGER PRIMARY kEY AUTOINCREMENT ," +
                    "$COL_TITLE TEXT  NOT NULL ," +
                    "$COL_BODY TEXT  NOT NULL," +
                    "$COL_isDone BOOLEAN  NOT NULL )"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ToDo")
        onCreate(db)
    }

    fun insertToDo(dataTodo: DataTodo) {
        val db = this.writableDatabase

        val contentValues = ContentValues().apply {
            put(COL_TITLE, dataTodo.title)
            put(COL_BODY, dataTodo.body)
            put(COL_isDone, dataTodo.isDone)
        }
        db.insert(TABLE_ToDo, null, contentValues)
        db.close()
    }

    fun updateToDo(dataTodo: DataTodo) {
        val db = this.writableDatabase
        var contentValues = ContentValues().apply {
            put(COL_TITLE, dataTodo.title)
            put(COL_BODY, dataTodo.body)
            put(COL_isDone, dataTodo.isDone)
        }
        db.update(
            TABLE_ToDo, contentValues,
            "$COL_ID=?", arrayOf(dataTodo.id.toString())
        )
        db.close()
    }

    fun deleteToDo(id: Int) {
        val db = this.writableDatabase

        db.delete(
            TABLE_ToDo, "$COL_ID=?", arrayOf(id.toString())
        )
        db.close()
    }

    fun readAllToDo(): ArrayList<DataTodo> {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_ToDo",
            null
        )
        val arrayList = ArrayList<DataTodo>()
        if (cursor.moveToFirst()) {
            do {
                var id = cursor.getInt(0)
                var title = cursor.getString(1)
                var body = cursor.getString(2)
//                var isDone1 = cursor.getString(3).toBoolean()
                var isDone: Boolean = cursor.getInt(3) > 0

                arrayList.add(DataTodo(id, title, body, isDone))

            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return arrayList
    }

    fun readOneToDoById(tId: Int): DataTodo? {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_ToDo WHERE $COL_ID=? ",
            arrayOf(tId.toString())
        )
         var toDo :DataTodo?=null

        if (cursor.moveToFirst()) {
                val id = cursor.getInt(0)
                val title = cursor.getString(1)
                val body = cursor.getString(2)
//                var isDone1 = cursor.getString(3).toBoolean()
                val isDone: Boolean = cursor.getInt(3) > 0

            toDo=DataTodo(id, title, body, isDone)

            }

        cursor.close()
        db.close()
        return toDo
    }


}