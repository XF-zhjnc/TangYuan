package io.github.xf_zhjnc.tangyuantv.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.xf_zhjnc.tangyuantv.ChannelInfo
import io.github.xf_zhjnc.tangyuantv.VideoPlayerActivity
import io.github.xf_zhjnc.tangyuantv.adapter.MainChannelAdapter
import io.github.xf_zhjnc.tangyuantv.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mViewModel: HomeViewModel
    private var mChannelLists = ArrayList<ChannelInfo>()
    private lateinit var mMainChannelAdapter: MainChannelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        initData()
        initRecyclerView()

        mViewModel.requestList()
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
                VideoPlayerActivity.start(this@HomeActivity, mChannelLists[position].videoUrl)
            }
        })

    }
}