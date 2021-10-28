package br.com.bootcampinter.data.service

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.bootcampinter.data.model.Contact

/**
 * Classe responsável pelo acesso e tráfego dos dados
 */
@Dao
interface ContactDao {

    @Query("SELECT * FROM Contact")
    fun getAll(): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact)

    @Query("UPDATE Contact SET name = :name, phone = :phone WHERE id = :id")
    suspend fun update(id: Int, name: String, phone: String)

    @Delete
    suspend fun delete(contact: Contact)

}