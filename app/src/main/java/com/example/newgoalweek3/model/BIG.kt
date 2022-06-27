package com.example.newgoalweek3.model

import java.util.ArrayList

class BIG {

    var my_goals = ArrayList<String?>() //МАССИВ ХРАНЕНИЯ ЗАДАЧ
    var my_notes = ArrayList<String?>() //МАССИВ ХРАНЕНИЯ ЗАМЕТОК

    //ФУНКЦИИ УДАЛЕНИЯ ЗАПИСЕЙ/////////////////////
    fun delete_goal (Input: String){
        for ( i in 0..(my_goals.size-1)){
            if (Input==my_goals[i]){
                my_goals.removeAt(i)
                break
            }
        }
    }

    fun delete_notes(Input:String){
        for (i in 0..(my_notes.size-1)){
            if (Input == my_notes[i]){
                my_notes.removeAt(i)
                break
            }
        }
    }
    ///////////////////////////////////////////////

}