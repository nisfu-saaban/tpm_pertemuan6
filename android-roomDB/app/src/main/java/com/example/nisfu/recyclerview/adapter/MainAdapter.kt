package com.example.nisfu.recyclerview.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nisfu.recyclerview.R
import com.example.nisfu.recyclerview.database.People
import kotlinx.android.synthetic.main.recycler_item.view.*

class MainAdapter( var context: Context,
                   val personModel: List<People>,
                   val onItemEditListener: (People, Int) -> Unit,
                   val onItemDeleteListener: (People, Int) -> Unit) : RecyclerView.Adapter<MainAdapter
                    .PersonVieHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PersonVieHolder = PersonVieHolder(
            LayoutInflater.from(p0.context).inflate(R.layout.recycler_item,p0, false))

    override fun getItemCount(): Int = personModel.size

    override fun onBindViewHolder(p0: MainAdapter.PersonVieHolder, p1: Int) {
        p0.bind(personModel[p1], onItemEditListener, onItemDeleteListener)
    }

    inner class PersonVieHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(people: People, onEditListener: (People, Int) -> Unit,
                 onDeleteListener: (People, Int) -> Unit){
            itemView.tv_name.text = people.name
            itemView.tv_address.text = people.address
            itemView.btn_edit.setOnClickListener { onEditListener(people, adapterPosition ) }
            itemView.btn_delete.setOnClickListener { onDeleteListener(people, adapterPosition) }
        }
    }
}