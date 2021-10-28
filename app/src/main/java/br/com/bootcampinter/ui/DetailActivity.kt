package br.com.bootcampinter.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import br.com.bootcampinter.data.model.Contact
import br.com.bootcampinter.R
import br.com.bootcampinter.databinding.ContactDetailBinding
import br.com.bootcampinter.presentation.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val blankContact: Contact = Contact(-1, "Contato", "000000000")

    private var startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.setContact(result.data?.getParcelableExtra(EXTRA_CONTACT) ?: blankContact)
            bindView()
        }
        else finish()
    }
    private val viewModel by viewModel<DetailViewModel>()
    private val binding by lazy { ContactDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initToolbar()
        if (viewModel.initExtras()) getExtras()
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
        viewModel.setContact(intent.getParcelableExtra(EXTRA_CONTACT) ?: blankContact)
    }

    /**
     * Seta as informações do contato nas views correspondentes
     */
    private fun bindView() {
        binding.contactInfoCard.tvName.text = viewModel.getContact().name
        binding.contactInfoCard.tvPhone.text = viewModel.getContact().phone
        binding.ivPhotograph.setImageResource(viewModel.getContact().photograph)
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
                Intent(this, EditActivity::class.java).apply {
                    putExtra(EXTRA_CONTACT, viewModel.getContact())
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
                    viewModel.deleteContact(viewModel.getContact())
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