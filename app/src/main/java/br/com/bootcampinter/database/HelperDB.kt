package br.com.bootcampinter.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.bootcampinter.contact.Contact

class HelperDB(
    context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private val DATABASE_NAME = "lista_contatos.db"
        private val DATABASE_VERSION = 2
    }

    val TABLE_NAME = "contatos"
    val COLUMNS_ID = "id"
    val COLUMNS_NAME = "nome"
    val COLUMNS_PHONE = "telefone"
    //val COLUMNS_PHOTO = "foto"
    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME(" +
            "$COLUMNS_ID INTEGER NOT NULL," +
            "$COLUMNS_NAME TEXT NOT NULL," +
            "$COLUMNS_PHONE TEXT NOT NULL," +
            "" +
            "PRIMARY KEY($COLUMNS_ID AUTOINCREMENT)" +
            ")"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    /**
     * Verifica se há atualizações no banco de dados
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db?.execSQL(DROP_TABLE)
        }
        onCreate(db)
    }

    fun searchContacts(contactId: Int? = null): List<Contact> {
        val db: SQLiteDatabase = readableDatabase
        val list: MutableList<Contact> = mutableListOf()
        var sqlCommand: String? = null
        var args: Array<String> = arrayOf()

        if (contactId != null) {
            sqlCommand = "$COLUMNS_ID = ?"
            args = arrayOf("$contactId")
        }

        val cursor: Cursor = db.query(TABLE_NAME, null, sqlCommand, args, null, null, null)

        val columnIndexId = cursor.getColumnIndex(COLUMNS_ID)
        val columnIndexName = cursor.getColumnIndex(COLUMNS_NAME)
        val columnIndexPhone = cursor.getColumnIndex(COLUMNS_PHONE)

        while (cursor.moveToNext()) {
            val contact = Contact(
                id = cursor.getInt(columnIndexId),
                name = cursor.getString(columnIndexName),
                phone = cursor.getString(columnIndexPhone)
                )
            list.add(contact)
        }
        cursor.close()
        db.close()
        return  list
    }

    fun newContact(contact: Contact) {
        val db: SQLiteDatabase = writableDatabase
        val args = arrayOf(contact.name, contact.phone)
        val sqlCommand = "INSERT INTO $TABLE_NAME ($COLUMNS_NAME, $COLUMNS_PHONE)" +
                "VALUES (?, ?)" // Por conta de vulnerabilidade, evitar o uso de {$contact.name}, {$contact.phone}
        db.execSQL(sqlCommand, args)
        db.close()
    }

    fun editContact(contact: Contact) {
        val db: SQLiteDatabase = writableDatabase
        val args = arrayOf(contact.name, contact.phone, contact.id)
        val sqlCommand = "UPDATE $TABLE_NAME " +
                "SET $COLUMNS_NAME = ?, $COLUMNS_PHONE = ?" +
                "WHERE $COLUMNS_ID = ?"
        db.execSQL(sqlCommand, args)
        db.close()
    }

    fun deleteContact(contactId: Int?) {
        if (contactId != null ) {
            val db: SQLiteDatabase = writableDatabase
            val args = arrayOf("$contactId")
            val sqlCommand = "DELETE FROM $TABLE_NAME " +
                    "WHERE $COLUMNS_ID = ?"
            db.execSQL(sqlCommand, args)
            db.close()
        }
    }
}