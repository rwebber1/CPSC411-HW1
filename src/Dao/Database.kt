package com.example.Dao

import com.almworks.sqlite4java.SQLiteConnection
import java.io.File

class Database constructor (var dbName : String = "")  {

    init {
        //Create the database, create tables, and keep the db connection
        dbName = "C:\\Users\\Riley Webber\\Desktop\\Fall 2020\\CPSC 411\\Homework1\\testDB.db"
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        val sqlStmt = "create table if not exists claim (id text, title text, date text, isSolved int)"
        dbConn.exec(sqlStmt)
    }

    fun getDbConnection() : SQLiteConnection {
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        return dbConn
    }

    //singleton object pattern
    companion object {
        private var dbObj : Database? = null

        fun getInstance() : Database? {
            if(dbObj == null) {
                dbObj = Database()
            }
            return dbObj
        }
    }

}