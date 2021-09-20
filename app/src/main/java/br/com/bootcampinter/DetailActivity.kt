package br.com.bootcampinter

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar

class DetailActivity : AppCompatActivity() {

    private var contact: Contact? = null

    private var indexContact: Int = 0

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
        indexContact = DataBaseContacts.dataBaseList.indexOf(contact)
    }

    /**
     * Seta as informações do contato nas views correspondentes
     */
    private fun bindView() {
        val tvName = findViewById<TextView>(R.id.tv_name)
        val tvPhone = findViewById<TextView>(R.id.tv_phone)
        val ivPhoto = findViewById<ImageView>(R.id.iv_photograph)

        tvName.text = contact?.name
        tvName.visibility = View.VISIBLE
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
                showAlertDialog()
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
     */
    private fun showAlertDialog() {

        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }
        builder.apply {
            setPositiveButton("Sim") { dialog, id ->
                DataBaseContacts.dataBaseList.removeAt(indexContact)
                showToast("Contato excluído!")
                finish()
            }
            setNegativeButton("Não", null)
        }

        builder
            .setTitle("Excluir contato")
            .setMessage("Deseja realmente excluir o contato?")
            .show()
    }


    override fun onRestart() {
        super.onRestart()
        contact = DataBaseContacts.dataBaseList[indexContact]
        bindView()
    }


    companion object {
        const val EXTRA_CONTACT: String = "EXTRA_CONTACT"
    }
}