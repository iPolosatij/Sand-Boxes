package com.digitallabstudio.sandboxes.utils.auxiliary

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.get
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.digitallabstudio.sandboxes.R
import com.digitallabstudio.sandboxes.databinding.ListImageFragmentBinding
import com.digitallabstudio.sandboxes.presenter.base.BaseFragment
import com.digitallabstudio.sandboxes.utils.adapters.SelectImageRvAdapter
import com.digitallabstudio.sandboxes.utils.tools.DialogHelper
import com.digitallabstudio.sandboxes.utils.tools.ImagePiker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import space.dlsunity.arctour.utils.auxiliary.ItemTouchMoveCallback
import space.dlsunity.arctour.utils.tools.ImageManager

class ImageListFragment(private val fragmentCloseInterface : FragmentCloseInterface) : BaseFragment(
    R.layout.list_image_fragment),
    AdapterCallBack {

    val binding : ListImageFragmentBinding by viewBinding()

    val adapter = SelectImageRvAdapter(this)

    private val dragCallback = ItemTouchMoveCallback(adapter)
    val touchHelper = ItemTouchHelper(dragCallback)
    private var job: Job? = null
    private var addItem: MenuItem? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbar()
        binding.apply {
            touchHelper.attachToRecyclerView(rcViewSelectImage)
            rcViewSelectImage.layoutManager = LinearLayoutManager(activity)
            rcViewSelectImage.adapter = adapter
        }
    }

    override fun onItemDelete() {
        addItem?.isVisible = true
    }

    fun updateAdapterFromEdit(bitmapList : List<Bitmap>){
        adapter.updateAdapter(bitmapList, true)
    }

    private fun resizeSelectedImage(newList: ArrayList<Uri>, needClear: Boolean, act: Activity){
        job = CoroutineScope(Dispatchers.Main).launch {
            val dialog = DialogHelper.createClosingDialog(act)
            val bitmapList = ImageManager.imageResize(newList, requireActivity())
            dialog.dismiss()
            adapter.updateAdapter(bitmapList, needClear)
            if(adapter.mainArray.size == ImagePiker.MAX_IMAGE_COUNT) addItem?.isVisible = false
        }
    }

    private fun setUpToolbar() {

        binding.apply {

            tb.inflateMenu(R.menu.menu_choose_image)
            val deleteItem = tb.menu.findItem(R.id.delete_image)
            addItem = tb.menu.findItem(R.id.add_image)
            if(adapter.mainArray.size == ImagePiker.MAX_IMAGE_COUNT) addItem?.isVisible = false

            tb.setNavigationOnClickListener {
                onClose()
            }
            deleteItem.setOnMenuItemClickListener {
                adapter.updateAdapter(ArrayList(), true)
                addItem?.isVisible = true
                true
            }
            addItem?.setOnMenuItemClickListener {
                val imageCount = ImagePiker.MAX_IMAGE_COUNT - adapter.mainArray.size
                //ImagePiker.addImages(activity as MainActivity, imageCount)
                true
            }
        }
    }

    fun updateAdapter(newList : ArrayList<Uri>, act: Activity){
        resizeSelectedImage(newList, false, act)
    }

    fun onClose() {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this@ImageListFragment)?.commit()
        fragmentCloseInterface.onFragmentClose(adapter.mainArray)
        job?.cancel()
    }

    fun setSingleImage(uri : Uri, position : Int){
        val pBar = binding.rcViewSelectImage[position].findViewById<ProgressBar>(R.id.pBar)
        job = CoroutineScope(Dispatchers.Main).launch {
            pBar.visibility = View.VISIBLE
            val bitmapList = ImageManager.imageResize(arrayListOf(uri), activity as Activity)
            pBar.visibility = View.GONE
            adapter.mainArray[position] = bitmapList[0]
            adapter.notifyItemChanged(position)
        }
    }
}