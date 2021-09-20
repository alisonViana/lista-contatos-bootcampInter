package br.com.bootcampinter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.core.widget.doAfterTextChanged
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.bootcampinter.DetailActivity.Companion.EXTRA_CONTACT
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity(), ContactItemClickListener {
    /**
     * variável que recebe a RecyclerView
     */
    private val rvList: RecyclerView by lazy {
        findViewById(R.id.rv_list)
    }

    private val adapter = ContactAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_menu)

        initDrawer()
        setNavegationViewListener()
        initDataBase()
        fetchListContact()
        bindViews()
        setSearchListeners()
        setOnClickListeners()
    }

    /**
     * Inicializa o drawer menu
     */
    private fun initDrawer() {
        val drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }


    private fun bindViews() {
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(this)

        updateList()
    }

    /**
     * Inicializa os dados no banco de dados falso
     */
    private fun initDataBase() {
        val contact1 = Contact("Alison Viana", "(11)00000-0000", R.drawable.male_avatar)
        val contact2 = Contact("Maria José", "(12) 11111-1111", R.drawable.female_avatar)
        val contact3 = Contact("Carlos", "(21) 92222-2222")

        val list = arrayListOf(contact1, contact2, contact3)

        if (DataBaseContacts.dataBaseList.isEmpty()) DataBaseContacts.dataBaseList.addAll(list)
    }


    /**
     * Simula uma chamada de API e salva os dados retornados no SharedPreferences
     */
    private fun fetchListContact() {
        val list = DataBaseContacts.dataBaseList

        getInstanceSharedPreference().edit {
            val json = Gson().toJson(list)
            putString("Contacts", json)
            commit()
        }

    }

    private fun getInstanceSharedPreference() : SharedPreferences {
        return getSharedPreferences("br.com.boootcampinter.PREFERENCES", Context.MODE_PRIVATE)
    }

    /**
     * Recupera os dados salvos no SharedPreferences e converte novamente para uma lista do tipo Contact
     */
    private fun getListContacts() : List<Contact> {
        val list = getInstanceSharedPreference().getString("Contacts", "[]")
        val turnTypes = object : TypeToken<List<Contact>>() {}.type
        return  Gson().fromJson(list, turnTypes)
    }

    private fun updateList() {
        val list = getListContacts()
        adapter.updateList(list)
    }

    /**
     * Métodos responsáveis pelo gerenciamento da barra de busca
     */
    private fun setSearchListeners() {
        val btnSearch = findViewById<ImageView>(R.id.btn_search)
        val etSearch = findViewById<EditText>(R.id.et_search)

        btnSearch.setOnClickListener {
            val searchText: String = etSearch.text.toString().lowercase()
            val filterList = getListContacts().filter {
                it.name.lowercase().contains(searchText)
            }
            adapter.updateList(filterList)
            if (filterList.isEmpty()) showToast("Nenhum contato encontrado")
        }

        etSearch.doAfterTextChanged {
            if (etSearch.text.toString() == "") updateList()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    /**
     * Função responsável pela criação do Options Menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    /**
     * Função responsável pelo Listener de clique nas opções do options menu
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_menu_1 -> {
                showToast("Selecionado o item 1")
                return true
            }
            R.id.item_menu_2 -> {
                showToast("Selecionado o item 2")
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Função responsável pelo Listener de clique nas opções do drawer menu
     */
    private fun setNavegationViewListener() {
        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener{ item ->
            when(item.itemId){
                R.id.item_menu_1 -> {
                    showToast("MENU 1")
                    true
                }
                R.id.item_menu_2 -> {
                    showToast("MENU 2")
                    true
                }
                else -> { false }
            }
        }
    }

    /**
     * Função responsável por iniciar a activity com os detalhes do contato selecionado
     */
    override fun onClickItemContact(contact: Contact) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_CONTACT, contact)
        startActivity(intent)
    }

    /**
     * Listener do Floating Button, quando pressionado inicializa a nova Activity com a categoria
     * NEW_CONTACT
     */
    private fun setOnClickListeners() {
        val btnAddContact = findViewById<FloatingActionButton>(R.id.fbtn_add_contact)

        btnAddContact.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)

            intent.addCategory(EditActivity.NEW_CONTACT)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        adapter.updateList(DataBaseContacts.dataBaseList)
    }
}
