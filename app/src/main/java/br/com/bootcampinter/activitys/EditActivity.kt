package br.com.bootcampinter.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import br.com.bootcampinter.contact.Contact
import br.com.bootcampinter.database.DataBaseContacts
import br.com.bootcampinter.R
import br.com.bootcampinter.application.ContactApplication

class EditActivity() : AppCompatActivity() {

    private var contact: Contact? = null

    private var newContactFlag: Boolean = false

    private var indexContact: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_edit)

        initToolbar()
        setOnClickListeners()
        getExtras()
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
        if (intent.hasCategory(NEW_CONTACT)) {
            setViewNewContact()
            newContactFlag = true
        }
        else{
            contact = intent.getParcelableExtra(EXTRA_CONTACT)
            indexContact = DataBaseContacts.dataBaseList.indexOf(contact)
            newContactFlag = false
            bindView()
        }
    }

    private fun setViewNewContact() {
        val btnDeleteContact = findViewById<Button>(R.id.btn_delete_contact)

        btnDeleteContact.visibility = View.GONE
    }

    /**
     * Seta as informações do contato nas views correspondentes
     */
    private fun bindView() {
        val etName = findViewById<TextView>(R.id.et_name)
        val etPhone = findViewById<TextView>(R.id.et_phone)
        val ivPhoto = findViewById<ImageView>(R.id.iv_photograph)

        etName.text = contact?.name
        etPhone.text = contact?.phone
        ivPhoto.setImageResource(contact!!.photograph)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    /**
     * Cria o listener e configra as ações do botão salvar
     */
    private fun setOnClickListeners() {
        val btnSaveContact = findViewById<Button>(R.id.btn_save_contact)
        val btnDeleteContact = findViewById<Button>(R.id.btn_delete_contact)
        val etName = findViewById<EditText>(R.id.et_name)
        val etPhone = findViewById<EditText>(R.id.et_phone)

        // Listener do botão salvar
        btnSaveContact.setOnClickListener {
            if (etName.text.toString().isNullOrBlank() or etPhone.text.toString().isNullOrBlank()) {
                showToast("Verifique os dados do contato!")
            } else {
                if (newContactFlag) {
                    val newContact = Contact(etName.text.toString(), etPhone.text.toString())
                    ContactApplication.instance.helperDB?.newContact(newContact)
                }
                else {
                    val editedContact = Contact(etName.text.toString(), etPhone.text.toString(), contact?.id)
                    ContactApplication.instance.helperDB?.editContact(editedContact)
                }

                showToast("Contato salvo!")
                finish()
            }
        }

        // Listener do botão deletar
        btnDeleteContact.setOnClickListener {
            showAlertDialog()
        }
    }

    /**
     * Cria um AlertDialog e configura as ações dos botões
     * Caso positivo - deleta o contato e encerra a atividade
     * Caso negativo - não faz nada
     */
    private fun showAlertDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.apply {
            setPositiveButton(R.string.ad_positive){ _, _ ->
                DataBaseContacts.dataBaseList.removeAt(indexContact)
                showToast("Contato Excluido!")
                finish()
            }
            setNegativeButton(R.string.ad_negative, null)
        }

        builder
            .setTitle(R.string.ad_delete_contact_title)
            .setMessage(R.string.ad_delete_contact_text)
            .show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val EXTRA_CONTACT: String = "EXTRA_CONTACT"
        const val NEW_CONTACT: String = "NEW_CONTACT"
    }
}