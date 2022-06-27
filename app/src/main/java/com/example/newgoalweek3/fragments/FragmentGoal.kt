package com.example.newgoalweek3.fragments

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import com.example.newgoalweek3.R
import com.example.newgoalweek3.big
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_goal.*
import java.lang.reflect.Type
import java.util.ArrayList


class FragmentGoal : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ПОЛУЧЕНИЕ МАССИВА ЗАДАЧ ПРИ СТАРТЕ ФРАГМЕНТА////
        if(getArrayList("key")!=null){
            big.my_goals = getArrayList("key")!!
        }
        //////////////////////////////////////////////////


        //ДОБАВЛЕНИЕ ЗАПИСЕЙ НА ЭКРАН И В ГЛОБАЛЬНУЮ ПЕРЕМЕННУЮ/////////////
        val str = ""

        id_button_celi.setOnClickListener {
            if (id_edittext_celi.text.toString() != str) {
                big.my_goals.add(id_edittext_celi.text.toString()) //ДОБАВЛЕНИЕ ЗАДАЧИ В ГЛОБАЛЬНЫЙ МАССИВ
                addCardToScroll_goal(id_edittext_celi.text.toString()) //ВЫЗОВ ФУНКЦИИ ПОКАЗА ЗАДАЧИ НА ЭКРАНЕ
                id_edittext_celi.text.clear() //ОЧИСТКА ПОЛЯ ВВОДА
                id_edittext_celi.hint = "enter a tasks"
            } else {
                id_edittext_celi.hint = "the tasks should not be empty"
            }
        }
        ///////////////////////////////////////////////////////////////////

        //ПОКАЗ ЗАДАЧ НА ЭКРАНЕ////////
        val taskArray = big.my_goals
        for (task in taskArray){
            addCardToScroll_goal(task)
        }
        ///////////////////////////////


        /////////////////////////////////////////////////////////////////////////

    }
    //ФУНКЦИЯ ДОБАВЛЕНИЯ ЗАПИСИ НА ЭКРАН/////////////////////////////////////////////////////////////
    @SuppressLint("ResourceAsColor")
    private fun addCardToScroll_goal(Input: String?) {
        val blockView = View.inflate(context, R.layout.item_goal, null)//Создаём 1 block
        val blockText = blockView.findViewById<TextView>(R.id.id_item_textview_celi)//Инициализируем поле Text
        val blockCheck = blockView.findViewById<CheckBox>(R.id.id_item_chekbox_celi)//Инициализируем поле Чек

        blockText.text = Input
        //УДАЛЕНИЕ С ЗАДЕРЖКОЙ////////////////////////
        blockCheck.setOnClickListener {

            Handler().postDelayed({

                big.delete_goal(Input!!) //ФУНКЦИЯ УДАЛЕНИЯ ЗАДАЧИ ИЗ МАССИВА
                id_listview_celi.removeView(blockView) //УДАЛЕНИЕ ЗАДАЧИ НА ЭКРАНЕ
            },2000)

        }
        //////////////////////////////////////////////

        id_listview_celi.addView(blockView) //ДОБАВЛЕНИЕ ЗАДАЧИ НА ЭКРАН

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    //СОХРАНЕНИЯ МАССИВА ЗАДАЧ ПОСЛЕ ВЫХОДА//
    override fun onStop() {
        super.onStop()
        saveArrayList(big.my_goals,"key")
    }
    ////////////////////////////////////////

    //ФУНКЦИЯ СОХРАНЕНИЯ МАССИВА/////////////////////////////////////////////////////////////
    private fun saveArrayList(list: ArrayList<String?>?, key: String) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }
    ////////////////////////////////////////////////////////////////////////////////////////

    //ФУНКЦИЯ ПОЛУЧЕНИЯ СОХРАНЕННОГО МАССИВА//////////////////////////////////////////////////
    private fun getArrayList(key: String): ArrayList<String?>? {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()
        val json: String? = prefs.getString(key, null)
        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.getType()
        return gson.fromJson(json, type)
    }
    //////////////////////////////////////////////////////////////////////////////////////////

}