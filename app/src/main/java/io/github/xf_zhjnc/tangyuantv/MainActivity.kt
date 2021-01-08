package io.github.xf_zhjnc.tangyuantv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.xf_zhjnc.tangyuantv.adapter.MainChannelAdapter
import io.github.xf_zhjnc.tangyuantv.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private var mChannelLists = ArrayList<ChannelInfo>()
    private lateinit var mMainChannelAdapter: MainChannelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initData()
        initRecyclerView()

    }

    private fun initData() {
        var channelInfo = ChannelInfo("CCTV-1", "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8")
        mChannelLists.add(channelInfo)
        channelInfo = ChannelInfo("广东卫视", "http://ivi.bupt.edu.cn/hls/gdhd.m3u8")
        mChannelLists.add(channelInfo)
        channelInfo = ChannelInfo("湖南卫视", "http://ivi.bupt.edu.cn/hls/hunanhd.m3u8")
        mChannelLists.add(channelInfo)
        channelInfo = ChannelInfo("安徽卫视", "http://ivi.bupt.edu.cn/hls/ahhd.m3u8")
        mChannelLists.add(channelInfo)
    }

    private fun initRecyclerView() {
        mMainChannelAdapter = MainChannelAdapter(this, mChannelLists)
        val layoutManager = LinearLayoutManager(this)
        mBinding.rcyChannelList.layoutManager = layoutManager
        mBinding.rcyChannelList.adapter = mMainChannelAdapter

        mMainChannelAdapter.setOnItemClickListener(object : MainChannelAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                VideoPlayerActivity.start(this@MainActivity, mChannelLists[position].videoUrl)
            }
        })

    }
}