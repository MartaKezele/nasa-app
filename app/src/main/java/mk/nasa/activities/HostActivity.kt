package mk.nasa.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import mk.nasa.R
import mk.nasa.databinding.ActivityHostBinding

class HostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActionBar()
        initNavigation()
    }

    private fun initActionBar() {
        hideAppTitle()
        showHamburgerMenu()
    }

    private fun hideAppTitle() = supportActionBar?.setDisplayShowTitleEnabled(false)

    private fun showHamburgerMenu() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    private fun initNavigation() {
        var navController = Navigation.findNavController(this, R.id.navHostFragment)
        NavigationUI.setupWithNavController(binding.navigationView, navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                toggleDrawer()
                return true
            }
            R.id.menuExit -> {
                exitApp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun exitApp() {
        val customDialogView = layoutInflater.inflate(R.layout.exit_app_alert_dialog, null)
        val btnOK = customDialogView.findViewById<Button>(R.id.btnOk)
        val btnCancel = customDialogView.findViewById<Button>(R.id.btnCancel)

        val builder = AlertDialog.Builder(this).apply {
            setTheme(R.style.HostTheme)
            setView(customDialogView)
        }

        val dialog = builder.show()

        btnOK.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun toggleDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawers()
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.host_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}