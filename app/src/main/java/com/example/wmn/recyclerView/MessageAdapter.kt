package com.example.wmn.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wmn.chat.ChatViewType
import com.example.wmn.chat.MyMessage
import com.example.wmn.databinding.BotChatMessageBinding
import com.example.wmn.databinding.UserChatMessageBinding

class MessageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = ArrayList<MyMessage>()
    interface OnItemClickListener {
        fun OnChatBotClick(message: String)
    }

    var itemClickListener: OnItemClickListener?= null

    inner class BotViewHolder (val botbinding: BotChatMessageBinding): RecyclerView.ViewHolder(botbinding.root){
        private  val chatText = botbinding.txtMessage
        fun bind(item: MyMessage){
            chatText.text = item.message
        }

        init {
            botbinding.txtMessage.setOnClickListener {
                itemClickListener?.OnChatBotClick(items[adapterPosition].message)

            }
        }
    }
    inner class UserViewHolder(val userbinding: UserChatMessageBinding): RecyclerView.ViewHolder(userbinding.root){
        private  val chatText = userbinding.txtMessage
        fun bind(item: MyMessage){
            chatText.text = item.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == ChatViewType.LEFT){
            return BotViewHolder(
                BotChatMessageBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
            )
        }
        return UserViewHolder(
            UserChatMessageBinding.inflate(
                LayoutInflater.from(parent.context),
            parent,
            false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(items[position].viewType) {
            ChatViewType.LEFT -> {
                (holder as BotViewHolder).bind(items[position])
            }
            ChatViewType.RIGHT -> {
                (holder as UserViewHolder).bind(items[position])
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: ArrayList<MyMessage>) {
        items = list
        notifyDataSetChanged()
    }
}