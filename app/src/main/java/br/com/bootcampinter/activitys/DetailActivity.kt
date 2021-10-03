package br.com.bootcampinter.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import br.com.bootcampinter.contact.Contact
import br.com.bootcampinter.database.DataBaseContacts
import br.com.bootcampinter.R
import br.com.bootcampinter.application.ContactApplication

class DetailActivity : AppCompatActivity() {

    private var contact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_detail)

        initToolbar()
        getExtras()
        bindView()
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Função responsável pelo recebimento das informações vindas das
     * activitys que a iniciou
     */
    private fun getExtras() {
        contact = intent.getParcelableExtra(EXTRA_CONTACT)
    }

    /**
     * Seta as informações do contato nas views correspondentes
     */
    private fun bindView() {
        val tvName = findViewById<TextView>(R.id.tv_name)
        val tvPhone = findViewById<TextView>(R.id.tv_phone)
        val ivPhoto = findViewById<ImageView>(R.id.iv_photograph)

        tvName.text = contact?.name
        tvPhone.text = contact?.phone
        ivPhoto.setImageResource(contact!!.photograph)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    /**
     * Função responsável pela criação do Options Menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.contact_options_menu, menu)
        return true
    }

    /**
     * Função responsável por tratar os cliques nas opções do options menu
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.opt_edit_contact -> {
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra(EXTRA_CONTACT, contact)
                startActivity(intent)
                return true
            }
            R.id.opt_delete_contact -> {
                showAlertDialogDeleteContact()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    /**
     * Cria um AlertDialog e configura as ações dos botões
     * Caso positivo - deleta o contato e encerra a atividade
     * Caso negativo - não faz nada
     */
    private fun showAlertDialogDeleteContact() {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.apply {
            setPositiveButton(R.string.ad_positive) { _, _ ->
                ContactApplication.instance.helperDB?.deleteContact(contact?.id)
                showToast("Contato excluído!")
                finish()
            }
            setNegativeButton(R.string.ad_negative, null)
        }

        builder
            .setTitle(R.string.ad_delete_contact_title)
            .setMessage(R.string.ad_delete_contact_text)
            .show()
    }

    override fun onRestart() {
        super.onRestart()

        /**
         * Após o retorno para esta activity, busca as informações do contato no banco de dados utilizando o id
         * Caso a lista retornada esteja vazia, significa que o contato foi deletado e finaliza a activity
         * Caso contrário, atualiza as informações do contato
         */
        val contactList: List<Contact> = ContactApplication.instance.helperDB?.searchContacts(contact?.id) ?: mutableListOf()

        if (contactList.isEmpty()) finish()
        else{
            contact = contactList[0]
            bindView()
        }
    }

    companion object {
        const val EXTRA_CONTACT: String = "EXTRA_CONTACT"
    }
}