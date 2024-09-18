package com.example.practico_2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practico_2.R
import com.example.practico_2.model.ComidaModel

class MyComidaAdapter (
    private val context: Context,
    private val list: List<ComidaModel>
): RecyclerView.Adapter<MyComidaAdapter.MycomidaViewHolder>() {

    class MycomidaViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        var imageView:ImageView? = null
        var txtName:TextView? = null
        var txtPrice:TextView? = null

        init {
            imageView = itemView.findViewById(R.id.imageView) as ImageView
            txtName = itemView.findViewById(R.id.txtName) as TextView;
            txtPrice = itemView.findViewById(R.id.txtPrice) as TextView;
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MycomidaViewHolder {
        return MycomidaViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.layout_comida_item,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MycomidaViewHolder, position: Int) {
        Glide.with(context)
            .load(list[position].image)
            .into(holder.imageView!!)
        holder.txtName!!.text = StringBuilder().append(list[position].name)
        holder.txtName!!.text = StringBuilder("$").append(list[position].price)

    }
}