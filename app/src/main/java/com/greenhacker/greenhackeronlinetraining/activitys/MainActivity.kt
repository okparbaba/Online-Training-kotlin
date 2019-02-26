package com.greenhacker.greenhackeronlinetraining.activitys

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout

import com.facebook.shimmer.ShimmerFrameLayout
import com.greenhacker.greenhackeronlinetraining.R
import com.greenhacker.greenhackeronlinetraining.adapter.MainCatRecyclerAdapter
import com.greenhacker.greenhackeronlinetraining.api.APIService
import com.greenhacker.greenhackeronlinetraining.api.APIUrl
import com.greenhacker.greenhackeronlinetraining.fragment.BlogFragment
import com.greenhacker.greenhackeronlinetraining.fragment.CompanyProfileFragment
import com.greenhacker.greenhackeronlinetraining.fragment.CourseFragment
import com.greenhacker.greenhackeronlinetraining.models.CoruseMainCat
import com.greenhacker.greenhackeronlinetraining.utils.RecyclerViewAnimation
import com.greenhacker.greenhackeronlinetraining.utils.SharedPrefManager

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {
    internal lateinit var recyclerView: RecyclerView
    private var mShimmerViewContainer: ShimmerFrameLayout? = null
    internal lateinit var courseMainCatlist: MutableList<CoruseMainCat>
    internal lateinit var mainCatRecyclerAdapter: MainCatRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()

        //        tvShowEmail = findViewById(R.id.tvShowEmail);
        //
        //        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
        //            String email = SharedPrefManager.getInstance(this).getUser().getEmail();
        //            if (email != null) {
        //                tvShowEmail.setText(email);
        //            }
        //
        //        }


        //for Toolbar and NavBar
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        val menu = navigationView.menu
        val navLogin = menu.findItem(R.id.nav_Login)
        val navRegis = menu.findItem(R.id.nav_register)
        if (SharedPrefManager.getInstance(this).isLoggedIn) {
            try {
                navLogin.title = "Log Out"
                navRegis.isVisible = false
            } catch (e: Exception) {

            }

        }
    }

    /*
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        val search = menu.findItem(R.id.app_bar_search)
        val searchView = MenuItemCompat.getActionView(search) as SearchView
        searchView.setOnQueryTextListener(this)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        displaySelectedFragment(item.itemId)
        return true
    }


    private fun displaySelectedFragment(menuItemId: Int) {
        var fragment: Fragment? = null
        when (menuItemId) {
            R.id.nav_course -> fragment = CourseFragment()
            R.id.nav_blog -> fragment = BlogFragment()
            R.id.nav_about -> fragment = CompanyProfileFragment()
            R.id.nav_share -> {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey Use My app")
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            }
            R.id.nav_Login -> if (!SharedPrefManager.getInstance(this).isLoggedIn) {
                val i2 = Intent(this@MainActivity, SignInActivity::class.java)
                startActivity(i2)
            } else {
                SharedPrefManager.getInstance(this).logout()
                val i3 = Intent(this@MainActivity, SignInActivity::class.java)
                startActivity(i3)
                finish()
            }
            R.id.nav_register -> {
                val i3 = Intent(this@MainActivity, SignUpActivity::class.java)
                startActivity(i3)
            }
            else -> {
            }
        }//handle settings fragment

        //replace the current fragment
        if (fragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.container_frame, fragment, null)
            ft.addToBackStack(null)
            ft.commit()
        }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
    }

    public override fun onResume() {
        super.onResume()
        mShimmerViewContainer!!.startShimmerAnimation()
    }

    public override fun onPause() {
        mShimmerViewContainer!!.stopShimmerAnimation()
        super.onPause()
    }

    private fun loadData() {
        recyclerView = findViewById(R.id.post_recyclerView)
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container)
        courseMainCatlist = ArrayList()
        val retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create<APIService>(APIService::class.java!!)
        val call = service.mainCat
        call.enqueue(object : Callback<List<CoruseMainCat>> {
            override fun onResponse(call: Call<List<CoruseMainCat>>, response: Response<List<CoruseMainCat>>) {

                try {
                    val list = response.body()
                    var coruseMainCat: CoruseMainCat? = null
                    for (i in list!!.indices) {
                        coruseMainCat = CoruseMainCat()
                        val categoryMain = list[i].category_name
                        val categoryId = list[i].id
                        coruseMainCat.category_name = categoryMain
                        coruseMainCat.id = categoryId
                        courseMainCatlist.add(coruseMainCat)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                mainCatRecyclerAdapter = MainCatRecyclerAdapter(courseMainCatlist, this@MainActivity)
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                RecyclerViewAnimation.runLayoutAnimation(recyclerView)
                recyclerView.adapter = mainCatRecyclerAdapter
                mShimmerViewContainer!!.stopShimmerAnimation()
                mShimmerViewContainer!!.visibility = View.GONE

            }

            override fun onFailure(call: Call<List<CoruseMainCat>>, t: Throwable) {
                val frameLayout = findViewById<FrameLayout>(R.id.container_frame)
                val snackbar = Snackbar
                        .make(frameLayout, "No internet connection!...", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry") {
                            val snackbar1 = Snackbar.make(frameLayout, "Connected", Snackbar.LENGTH_SHORT)
                            snackbar1.setActionTextColor(Color.GREEN)
                            snackbar1.show()
                        }
                snackbar.show()
            }

        })
    }

    override fun onQueryTextSubmit(s: String): Boolean {
        return false
    }

    override fun onQueryTextChange(s: String): Boolean {
        var s = s
        s = s.toLowerCase()
        val newList = ArrayList<CoruseMainCat>()
        for (beneficiary in courseMainCatlist) {
            val name = beneficiary.category_name!!.toLowerCase()
            if (name.contains(s)) {
                newList.add(beneficiary)

            }
        }
        mainCatRecyclerAdapter.setFilter(newList)
        return true
    }

    companion object {
        private val TAG = "Activity"
    }
}
