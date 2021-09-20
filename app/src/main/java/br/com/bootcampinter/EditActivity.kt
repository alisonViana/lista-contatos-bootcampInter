package br.com.bootcampinter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar

class EditActivity() : AppCompatActivity() {

    private var contact: Contact? = null

    private var newContactFlag: Boolean = false

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
        val etName = findViewById<EditText>(R.id.et_name)
        val etPhone = findViewById<EditText>(R.id.et_phone)

        btnSaveContact.setOnClickListener {
            if (etName.text.toString().isNullOrBlank() or etPhone.text.toString().isNullOrBlank()) {
                showToast("Verifique os dados do contato!")
            } else {
                val newContact = Contact( etName.text.toString(), etPhone.text.toString())

                if (newContactFlag) {
                    DataBaseContacts.dataBaseList.add(newContact)
                } else {
                    val indexContact = DataBaseContacts.dataBaseList.indexOf(contact)
                    DataBaseContacts.dataBaseList[indexContact] = newContact
                    }
                showToast("Contato salvo!")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val EXTRA_CONTACT: String = "EXTRA_CONTACT"
        const val NEW_CONTACT: String = "NEW_CONTACT"
    }
}