package br.com.bootcampinter.data.repositories

import br.com.bootcampinter.data.model.Contact
import br.com.bootcampinter.data.service.ContactDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Classe que implementa as funções do dao
 */
class ContactRepository(private val dao: ContactDao) {

    fun getAll() = dao.getAll()

    fun insert(contact: Contact)  = runBlocking {
        launch(Dispatchers.IO) {
            try {
                dao.insert(contact)
            } catch (ex: Exception) {ex.printStackTrace()}
        }
    }

    fun update(contact: Contact) = runBlocking {
        launch(Dispatchers.IO){
            try {
                dao.update(contact)
            } catch (ex: Exception) {ex.printStackTrace()}
        }
    }

    fun delete(contact: Contact) = runBlocking {
        launch(Dispatchers.IO) {
            try {
                dao.delete(contact)
            } catch (ex: Exception) {ex.printStackTrace()}
        }
    }

}

