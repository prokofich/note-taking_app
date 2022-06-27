package com.example.newgoalweek3.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.example.newgoalweek3.R
import com.example.newgoalweek3.big
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_notes.*
import java.lang.reflect.Type
import java.util.ArrayList


class FragmentNotes : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ПОЛУЧЕНИЕ МАССИВА ПРИ СТАРТЕ ФРАГМЕНТА///////
        if (getArrayList("key2")!=null){
            big.my_notes = getArrayList("key2")!!
        }
        ///////////////////////////////////////////////

        //ДОБАВЛЕНИЕ ЗАПИСЕЙ НА ЭКРАН И В ГЛОБАЛЬНУЮ ПЕРЕМЕННУЮ///////////
        val str = ""
        id_button_zametki.setOnClickListener {
            if (id_edittext_zametki.text.toString() != str) {
                big.my_notes.add(id_edittext_zametki.text.toString()) //ДОБАВЛЕНИЕ В МАССИВ ЗАМЕТОК
                addCardToScroll_notes(id_edittext_zametki.text.toString()) //ДОБАВЛЕНИЕ В ФУНКЦИЮ ПОКАЗА НА ЭКРАНЕ
                id_edittext_zametki.text.clear() //ОЧИСТКА ПОЛЯ ВВОДА
                id_edittext_zametki.hint ="enter a note"
            }else{
                id_edittext_zametki.hint = "the not should not be empty"
            }
        }
        /////////////////////////////////////////////////////////////////

        //ПОКАЗ ЗАМЕТОК НА ЭКРАНЕ//////////
        val taskArray = big.my_notes
        for (task in taskArray){
            addCardToScroll_notes(task!!)
        }
        ///////////////////////////////////
    }






    //ФУНКЦИЯ ДОБАВЛЕНИЯ ЗАПИСИ НА ЭКРАН////////////////////////////////////////////////////////////
    private fun addCardToScroll_notes(Input: String) {
        val blockView = View.inflate(context, R.layout.item_note, null)//Создаём 1 block
        val blockText = blockView.findViewById<TextView>(R.id.id_item_textview_zametki)//Инициализируем поле Text
        val blockButton = blockView.findViewById<Button>(R.id.id_button_delete_zametki)//Инициализируем поле Чек

        blockText.text = Input

        //УДАЛЕНИЕ ЗАМЕТКИ////////////////////////////
        blockButton.setOnClickListener {

            big.delete_notes(Input) //ФУНКЦИЯ УДАЛЕНИЯ ИЗ МАССИВА
            id_listview_zametki.removeView(blockView) //УДАЛЕНИЕ С ЭКРАНА

        }
        //////////////////////////////////////////////

        id_listview_zametki.addView(blockView) //ДОБАВЛЕНИЕ ЗАМЕТКИ НА ЭКРАН
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //СОХРАНЕНИЕ МАССИВА ЗАМЕТОК ПОСЛЕ ВЫХОДА///
    override fun onStop() {
        super.onStop()
        saveArrayList(big.my_notes,"key2")
    }
    ////////////////////////////////////////////

    //ФУНКЦИЯ СОХРАНЕНИЯ МАССИВА//////////////////////////////////////////////////////////////
    private fun saveArrayList(list: ArrayList<String?>?, key: String) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }
    /////////////////////////////////////////////////////////////////////////////////////////

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