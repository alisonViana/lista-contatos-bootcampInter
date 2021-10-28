package br.com.bootcampinter.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import br.com.bootcampinter.data.model.Contact
import br.com.bootcampinter.R
import br.com.bootcampinter.databinding.ContactEditBinding
import br.com.bootcampinter.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditActivity() : AppCompatActivity() {

    private var contact: Contact? = null
    private var newContactFlag: Boolean = false
    private val viewModel by viewModel<MainViewModel>()
    private val binding by lazy { ContactEditBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initToolbar()
        setListeners()
        getExtras()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Função responsável pelo recebimento das informações vindas das
     * activitys que a iniciou
     */
    private fun getExtras() {
        if (intent.hasCategory(NEW_CONTACT)) {
            binding.btnDeleteContact.visibility = View.GONE
            newContactFlag = true
        }
        else{
            contact = intent.getParcelableExtra(EXTRA_CONTACT)
            newContactFlag = false
            bindView()
        }
    }

    /**
     * Seta as informações do contato nas views correspondentes
     */
    private fun bindView() {
        binding.newContactCard.etName.setText(contact?.name)
        binding.newContactCard.etPhone.setText(contact?.phone)
        binding.ivPhotograph.setImageResource(contact!!.photograph)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    /**
     * Cria o listener e configra as ações do botão salvar
     */
    private fun setListeners() {
        binding.newContactCard.etPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher("BR"))

        // Listener do botão salvar
        binding.btnSaveContact.setOnClickListener {
            if (binding.newContactCard.etName.text.toString().isBlank() or binding.newContactCard.etPhone.text.toString().isBlank()) {
                showToast("Verifique os dados do contato!")
            } else {
                if (newContactFlag) {
                    val newContact = Contact(
                        name = binding.newContactCard.etName.text.toString(),
                        phone = binding.newContactCard.etPhone.text.toString())
                    viewModel.addContact(newContact)
                }
                else {
                    val editedContact = Contact(
                        id = contact!!.id,
                        name = binding.newContactCard.etName.text.toString(),
                        phone = binding.newContactCard.etPhone.text.toString())
                    viewModel.editContact(editedContact)
                    Intent().apply {
                        putExtra(EXTRA_CONTACT, editedContact)
                        setResult(RESULT_OK, this)
                    }
                }
                showToast("Contato salvo!")
                finish()
            }
        }

        // Listener do botão deletar
        binding.btnDeleteContact.setOnClickListener {
            showAlertDialogDeleteContact()
        }
    }

    /**
     * Cria um AlertDialog e configura as ações dos botões
     * Caso positivo - deleta o contato e encerra a atividade
     * Caso negativo - não faz nada
     */
    private fun showAlertDialogDeleteContact() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.apply {
            setPositiveButton(R.string.ad_positive){ _, _ ->
                viewModel.deleteContact(contact!!)
                showToast("Contato Excluido!")
                finish()
            }
            setNegativeButton(R.string.ad_negative, null)
        }
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