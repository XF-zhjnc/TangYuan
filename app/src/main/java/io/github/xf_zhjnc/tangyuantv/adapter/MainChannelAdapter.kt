package io.github.xf_zhjnc.tangyuantv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.xf_zhjnc.tangyuantv.R
import io.github.xf_zhjnc.tangyuantv.bean.LiveChannel
import io.github.xf_zhjnc.tangyuantv.databinding.ItemMainChannelListBinding

/**
 * NAME: 柚子啊
 * DATE: 2021/1/8
 * DESC:
 */
class MainChannelAdapter(private val context: Context) :
        RecyclerView.Adapter<MainChannelAdapter.ViewHolder>() {

    private var mListener: OnItemClickListener? = null

    private val mChannelList: ArrayList<LiveChannel> = ArrayList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemMainChannelListBinding = ItemMainChannelListBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_main_channel_list, parent, false))

    override fun getItemCount(): Int = mChannelList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val channelInfo = mChannelList[position]
        holder.binding.btnChannel.text = channelInfo.name
        holder.binding.btnChannel.setOnClickListener {
            mListener?.onItemClick(position, channelInfo.objectId)
        }
        holder.binding.tvLiveDesc.text = channelInfo.shortDesc
        Glide.with(context).load(channelInfo.imageURL).into(holder.binding.ivChannelPreview)
    }

    fun setNewData(newData: List<LiveChannel>) {
        mChannelList.clear()
        mChannelList.addAll(newData)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, channelId: String)
    }
}