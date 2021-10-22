package br.com.bootcampinter.data.service

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.bootcampinter.data.model.Contact

/**
 * Classe responsável pelo acesso e tráfego dos dados
 */
@Dao
interface ContactDao {

    @Query("SELECT * FROM CONTACT")
    fun getAll(): List<Contact>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

}