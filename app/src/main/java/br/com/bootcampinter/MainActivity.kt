package br.com.bootcampinter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.bootcampinter.DetailActivity.Companion.EXTRA_CONTACT
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
        fetchListContact()
        bindViews()
    }

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
     * Simula uma chamada de API e salva os dados retornados no SharedPreferences
     */
    private fun fetchListContact() {
        val contact1 = Contact("Alison Viana", "(11)00000-0000", R.drawable.male_avatar)
        val contact2 = Contact("Maria José", "(12) 11111-1111", R.drawable.female_avatar)
        val contact3 = Contact("Carlos", "(21) 92222-2222")

        val list = arrayListOf(contact1, contact2, contact3)

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

    override fun onClickItemContact(contact: Contact) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_CONTACT, contact)
        startActivity(intent)
    }
}
