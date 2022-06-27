package com.example.newgoalweek3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.newgoalweek3.fragments.FragmentGoal
import com.example.newgoalweek3.fragments.FragmentNotes
import com.example.newgoalweek3.model.BIG
import kotlinx.android.synthetic.main.activity_main.*


var big = BIG() //ГЛОБАЛЬНАЯ ПЕРЕМЕННАЯ ДЛЯ ХРАНЕНИЯ МАССИВОВ В ДВУХ ФРАГМЕНТАХ

class MainActivity : AppCompatActivity() {


    private val fragment_goal = FragmentGoal()
    private val fragment_notes = FragmentNotes()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ПРИЛОЖЕНИЕ ВО ВЕСЬ ЭКРАН/////////////////////
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        ///////////////////////////////////////////////


        //ОБРАБОТКА НАЖАТИЯ КНОПОК В МЕНЮ+ПОКАЗ ФРАГМЕНТОВ///////////////////
        replaceFragment(fragment_goal)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_notes ->replaceFragment(fragment_notes)
                R.id.menu_goal ->replaceFragment(fragment_goal)
            }
            true
        }
        //////////////////////////////////////////////////////////////////////
    }

    //ФУНКЦИЯ ПОКАЗА ФРАГМЕНТА///////////////////////////////////////////
    private fun replaceFragment(fragment: Fragment){
        if(fragment!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.commit()
        }
    }
    /////////////////////////////////////////////////////////////////////

}