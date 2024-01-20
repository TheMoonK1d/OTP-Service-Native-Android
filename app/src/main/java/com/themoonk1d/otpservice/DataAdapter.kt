package com.themoonk1d.otpservice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.snackbar.Snackbar

class DataAdapter(private val lst : ArrayList<DataModel>): RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

        class DataViewHolder(item : View):ViewHolder(item){
        var otp : TextView
        var to : TextView
        init {
            otp = item.findViewById(R.id.otp_txt)
            to = item.findViewById(R.id.to_txt)
            item.setOnClickListener {
                if (otp.text.equals("Welcome")){
                    Snackbar.make(it, "You have connected 📡", Snackbar.LENGTH_LONG).show()
                }else{
                    Snackbar.make(it, "📨 ${otp.text} 📱 ${to.text}", Snackbar.LENGTH_LONG).show()
                }
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.lst_item,parent, false)
        return DataViewHolder(v)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.otp.text = lst[position].otp
        holder.to.text = lst[position].to
    }

    override fun getItemCount(): Int {
        return lst.size
    }

}