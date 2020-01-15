package com.onemain.challenge.ui


import com.onemain.challenge.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.jokes_list_item.view.*


class JokeItem(private val joke:String,private val id:String): Item() {
     val jokeId=id
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

       viewHolder.itemView.tv.text=joke
    }

    override fun getLayout(): Int {
        return R.layout.jokes_list_item
    }
}