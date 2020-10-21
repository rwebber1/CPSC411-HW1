package com.example.Dao.claim

import com.example.Dao.Dao
import com.example.Dao.Database
import java.util.*

class ClaimDao : Dao() {

    fun addClaim(claimObj : Claim) {
        //get db connection
        val conn = Database.getInstance()?.getDbConnection()

        //prepare the sql statement
        sqlStmt = "insert into claim (id, title, date, isSolved) values ('${claimObj.id}', '${claimObj.title}' , " +
                                                                    "'${claimObj.date}', '${claimObj.isSolved}')"

        //submit sql statement
        conn?.exec(sqlStmt)
    }

    fun getAll() : List<Claim> {
        //get db connection
        val conn = Database.getInstance()?.getDbConnection()

        //prepare the sql statement
        sqlStmt = "select id, title, date, isSolved from claim"

        //submit sql statement
        var claimList : MutableList<Claim> = mutableListOf()
        val st = conn?.prepare(sqlStmt)

        // 4. Convert into Kotlin object format
        while(st?.step()!!) {
            val id = st.columnString(0)
            val title = st.columnString(1)
            val date = st.columnString(2)
            val isSolved = st.columnNull(3)
            claimList.add(Claim(UUID.fromString(id),title,date,isSolved))
        }
        return claimList
    }
}