package br.com.bootcampinter.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import br.com.bootcampinter.data.model.Contact
import br.com.bootcampinter.R
import br.com.bootcampinter.databinding.ContactDetailBinding
import br.com.bootcampinter.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private var contact: Contact? = null
    private var startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK) {
            contact = result.data?.getParcelableExtra(EXTRA_CONTACT)
            bindView()
        }
        else finish()
    }

    private val viewModel by viewModel<MainViewModel>()
    private val binding by lazy { ContactDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initToolbar()
        //initObserver()
        getExtras()
        bindView()
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
        contact = intent.getParcelableExtra(EXTRA_CONTACT)
    }

    /**
     * Seta as informações do contato nas views correspondentes
     */
    private fun bindView() {
        binding.contactInfoCard.tvName.text = contact?.name
        binding.contactInfoCard.tvPhone.text = contact?.phone
        binding.ivPhotograph.setImageResource(contact!!.photograph)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    /**
     * Função responsável pela criação do Options Menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.contact_options_menu, menu)
        return true
    }

    /**
     * Função responsável por tratar os cliques nas opções do options menu
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.opt_edit_contact -> {
                //val intent = Intent(this, EditActivity::class.java)
                //intent.putExtra(EXTRA_CONTACT, contact)
                //startActivity(intent)
                Intent(this, EditActivity::class.java).apply {
                    putExtra(EXTRA_CONTACT,contact)
                    startForResult.launch(this)
                }
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
                try {
                    viewModel.deleteContact(contact!!)
                    showToast("Contato excluído!")
                    finish()
                } catch (ex: Exception) { showToast(ex.toString()) }
            }
            setNegativeButton(R.string.ad_negative, null)
        }

        builder
            .setTitle(R.string.ad_delete_contact_title)
            .setMessage(R.string.ad_delete_contact_text)
            .show()
    }

    companion object {
        const val EXTRA_CONTACT: String = "EXTRA_CONTACT"
    }
}