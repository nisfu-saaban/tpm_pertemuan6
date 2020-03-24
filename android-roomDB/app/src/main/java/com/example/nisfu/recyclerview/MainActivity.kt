package com.example.nisfu.recyclerview

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.example.nisfu.recyclerview.adapter.MainAdapter
import com.example.nisfu.recyclerview.database.People
import com.example.nisfu.recyclerview.database.PersonDb
import com.example.nisfu.recyclerview.database.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter: MainAdapter
    lateinit var list: MutableList<People>
    lateinit var people: People
    var position: Int? = null

    private val deleteDialog by lazy {
        AlertDialog.Builder(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("ManaGist")

        list = PersonDb.getAllData(PersonDb.getInstance(this))

        rv_main.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(this, list,{ people, position ->
            this.people = people
            this.position = position

            updateDialog(people.id!!)
        },{ people, position ->
            this.people = people
            this.position = position

            deleteStudent(people.id!!)
        })
        rv_main.adapter = adapter

        fab_add.setOnClickListener { openDialog() }
    }

    private fun openDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog)
         val lp: WindowManager.LayoutParams = WindowManager.LayoutParams().apply {
            copyFrom(dialog.window?.attributes)
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }

        val submit = dialog.findViewById<View>(R.id.bt_submit) as Button
        val name = dialog.findViewById<View>(R.id.et_name) as EditText
        val address = dialog.findViewById<View>(R.id.et_address) as EditText
        val number = dialog.findViewById<View>(R.id.et_number) as EditText

        submit.setOnClickListener {
            when {
                name.length() == 0 || address.length() == 0 || number.length() == 0 ->
                    toast("please fill all the fields")

                else -> {
                    val people = People(name.text.toString(),
                            address.text.toString(), number.text.toString())
                    PersonDb.insertData(PersonDb.getInstance(this), people)
                    dialog.dismiss()
                        list.clear()
                        list.addAll(PersonDb.getAllData(PersonDb.getInstance(this)))
                        adapter.notifyDataSetChanged()
                }
            }
        }
        dialog.show()
        dialog.getWindow()?.setAttributes(lp)
    }

    private fun updateDialog(id:Long) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog)
        val lp: WindowManager.LayoutParams = WindowManager.LayoutParams().apply {
            copyFrom(dialog.window?.attributes)
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }

        val submit = dialog.findViewById<View>(R.id.bt_submit) as Button
        val name = dialog.findViewById<View>(R.id.et_name) as EditText
        val address = dialog.findViewById<View>(R.id.et_address) as EditText
        val number = dialog.findViewById<View>(R.id.et_number) as EditText

        submit.setOnClickListener {
            when {
                name.length() == 0 || address.length() == 0 || number.length() == 0 ->
                    toast("please fill all the fields")

                else -> {
                    val people = People(name.text.toString(),
                            address.text.toString(), number.text.toString())
                    people.id = id
                    PersonDb.updateData(PersonDb.getInstance(this), people)
                    dialog.dismiss()
                    list.clear()
                    list.addAll(PersonDb.getAllData(PersonDb.getInstance(this)))
                    adapter.notifyDataSetChanged()
                }
            }
        }
        dialog.show()
        dialog.getWindow()?.setAttributes(lp)
    }

    private fun deleteStudent(id: Long) {
        deleteDialog.setNegativeButton("NO") { dialog, _ ->
            dialog.dismiss()
        }.setPositiveButton("YES") { _, _ ->
            people.id = id
            PersonDb.deleteData(PersonDb.getInstance(this),people)
            list.clear()
            list.addAll(PersonDb.getAllData(PersonDb.getInstance(this)))
            adapter.notifyDataSetChanged()
        }.setTitle("Delete People")
                .setMessage("Are You Sure?").create()

        deleteDialog.show()
    }

    companion object {
         var ID :Long? = 0
    }

}
