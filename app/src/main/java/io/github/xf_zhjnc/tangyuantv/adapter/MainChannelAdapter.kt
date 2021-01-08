package io.github.xf_zhjnc.tangyuantv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import io.github.xf_zhjnc.tangyuantv.ChannelInfo
import io.github.xf_zhjnc.tangyuantv.R

/**
 * NAME: 柚子啊
 * DATE: 2021/1/8
 * DESC:
 */
class MainChannelAdapter(private val context: Context,
                         private val mChannelList: ArrayList<ChannelInfo>) :
        RecyclerView.Adapter<MainChannelAdapter.ViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var button: Button = itemView.findViewById(R.id.btnChannel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_main_channel_list, parent, false))

    override fun getItemCount(): Int = mChannelList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val channelInfo = mChannelList[position]
        holder.button.text = channelInfo.channelTitle
        holder.button.setOnClickListener { mListener.onItemClick(position) }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}