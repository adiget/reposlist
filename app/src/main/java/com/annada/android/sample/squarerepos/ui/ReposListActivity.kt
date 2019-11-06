package com.annada.android.sample.squarerepos.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.annada.android.sample.squarerepos.R
import kotlinx.android.synthetic.main.activity_repos_list.*

class ReposListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repos_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.repo_fragment, ReposListFragment.newInstance())
                .commitNow()
        }
    }
}
