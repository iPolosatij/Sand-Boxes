package com.digitallabstudio.sandboxes.utils.tools

import android.graphics.Bitmap
import android.net.Uri
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import com.digitallabstudio.sandboxes.MainActivity
import com.digitallabstudio.sandboxes.utils.auxiliary.Event
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.helpers.pixFragment
import io.ak1.pix.models.Flash
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import space.dlsunity.arctour.utils.tools.ImageManager

class ImagePiker(private val act: MainActivity) {
    companion object{
        const val MAX_IMAGE_COUNT = 3
    }

    private fun getOptionsForPhotos(imageCounter: Int): Options {
        return Options().apply {
            ratio = Ratio.RATIO_AUTO
            count = imageCounter
            spanCount = 4
            path = "Pix/Camera"
            isFrontFacing = false
            mode = Mode.Picture
            flash = Flash.Auto
            preSelectedUrls = ArrayList<Uri>()
        }
    }

    fun getSinglePhotos(fm: FragmentManager, id: Int, data: MutableLiveData<Event<Bitmap>>) {
        var pixFrag = pixFragment(getOptionsForPhotos(1)) {result ->
            when (result.status) {
                PixEventCallback.Status.SUCCESS -> {
                    CoroutineScope(Dispatchers.Default).launch {
                        data.postValue(Event(ImageManager.imageResize(result.data, act)[0]))
                        closePixFragment(fm)
                    }
                }
                PixEventCallback.Status.BACK_PRESSED -> {
                    closePixFragment(fm)
                }
            }
        }
        fm.beginTransaction()
            .replace(id, pixFrag).commit()
    }

    private fun closePixFragment(childFragmentManager: FragmentManager) {
        childFragmentManager.fragments.forEach { fragment ->
            if (fragment.isVisible)
                childFragmentManager
                    .beginTransaction()
                    .remove(fragment)
                    .commit()
        }
    }
}
