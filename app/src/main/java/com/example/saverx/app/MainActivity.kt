package com.example.saverx.app

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saverx.R
import com.example.saverx.app.animations.AnimationHelper
import com.example.saverx.app.model.LinkModelApp
import com.example.saverx.app.views.CustomProgressLoadView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerLinks: RecyclerView
    private lateinit var editTextLinks: EditText
    private lateinit var editTextName: EditText
    private lateinit var textView: TextView
    private lateinit var loadingView: CustomProgressLoadView

    private val animHelper = AnimationHelper()

    private val vm by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.title_text)
        recyclerLinks = findViewById(R.id.links)
        editTextLinks = findViewById(R.id.input_link)
        loadingView = findViewById(R.id.custom_button)
        editTextName = findViewById(R.id.input_filename)

        vm.activityState.observe(this) { setState(it) }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                vm.onItemClicked(100)
                Log.e("TAG", "РЕЗ: SETSTATE = 100")
            }
        })

        textView.setOnClickListener {
            try {
                vm.fetchData(LinkModelApp(editTextLinks.text.toString(), "${editTextName.text}.mp4"))
            } catch (e: Exception) {
                Log.e("TAG", "РЕЗ: ошибка $e")
            }
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                it.windowToken,
                0
            )
        }

        vm.resultLiveData.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "OK!", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "РЕЗ: скачалось")
            } else {
                Toast.makeText(this, "Вы не ввставили ссылку!", Toast.LENGTH_SHORT).show()
            }
        }

        vm.progress.observe(this) {
            loadingView.setProgress(it)
        }

        lifecycleScope.launch {
            vm.downloadProgress.collect { progress ->
                loadingView.setProgress(progress)
            }
        }
    }

    private fun startWindow() {
        textView.text = "Откуда скачиваем?"
        recyclerLinks.visibility = View.VISIBLE
        editTextLinks.visibility = View.GONE
        editTextName.visibility = View.GONE
        recyclerLinks.layoutManager = GridLayoutManager(this@MainActivity, 3)
        loadingView.visibility = View.GONE
        vm.linkAdapter.observe(this) { recyclerLinks.adapter = it }
        Toast.makeText(this, "started", Toast.LENGTH_SHORT).show()
    }

    private fun state0() {
        recyclerLinks.visibility = View.GONE
        editTextLinks.visibility = View.VISIBLE
        editTextName.visibility = View.VISIBLE
        loadingView.visibility = View.VISIBLE
        animHelper.apply {
            startSlideUpAnimForEditText(editTextLinks)
            startSlideUpAnimForEditText(editTextName)
            startSlideUpAnimForTextView(textView)
        }
        textView.text = "Вставьте ссылку и нажмите кнопку для начала загрузки "
        Toast.makeText(this, "state0", Toast.LENGTH_SHORT).show()
    }

    private fun state1() {
        Toast.makeText(this, "state1", Toast.LENGTH_SHORT).show()
    }

    private fun state2() {
        Toast.makeText(this, "state2", Toast.LENGTH_SHORT).show()
    }

    private fun state3() {
        Toast.makeText(this, "state3", Toast.LENGTH_SHORT).show()
        System.gc()
    }

    private fun setState(state: Int) {
        when (state) {
            100 -> startWindow()
            0 -> state0()
            1 -> state1()
            2 -> state2()
            3 -> state3()
        }
    }
}