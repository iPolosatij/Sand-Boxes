package space.dlsunity.arctour.utils.tools

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.caverock.androidsvg.SVG
import com.digitallabstudio.sandboxes.MainActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException


object ImageManager {

    fun getBitmapToDrawable(bitmap: Bitmap, context: Context): Drawable{
        return BitmapDrawable(context.resources, bitmap)
    }

    fun setImageFromSvgString(svg: String, view: ImageView, context: Context){

//convert SVG string to an object of type SVG
        val svg = SVG.getFromString(svg)

//create a drawable from svg
        val drawable = PictureDrawable(svg.renderToPicture())

//finally load the drawable with Glide.
        Glide.with(context)
            .load(drawable)
            .into(view)
    }

    //for image picker
    private const val MAX_IMAGE_SIZE = 1800
    private const val WIDTH = 0
    private const val HEIGHT = 1

    private fun getImageSize(uri : Uri, act: Activity) : List<Int>{
        val inStream = act.contentResolver.openInputStream(uri)
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeStream(inStream, null, options)
        return listOf(options.outWidth, options.outHeight)
    }

    fun chooseScaleType(im : ImageView, bitmap: Bitmap){
        im.scaleType = ImageView.ScaleType.CENTER_CROP
    }

    suspend fun imageResize(uris: List<Uri>, act: Activity): List<Bitmap> = withContext(Dispatchers.IO){
        val tempList = ArrayList<List<Int>>()
        val bitmapList = ArrayList<Bitmap>()
        for(n in uris.indices){
            val size = getImageSize(uris[n], act)
            val imageRatio = size[WIDTH].toFloat()/size[HEIGHT].toFloat()
            if(imageRatio > 1){
                if(size[WIDTH] > MAX_IMAGE_SIZE){
                    tempList.add(listOf(MAX_IMAGE_SIZE, (MAX_IMAGE_SIZE / imageRatio).toInt()))
                }else{
                    tempList.add(listOf(size[WIDTH], size[HEIGHT]))
                }
            }else{
                if(size[HEIGHT] > MAX_IMAGE_SIZE){
                    tempList.add(listOf((MAX_IMAGE_SIZE * imageRatio).toInt(), MAX_IMAGE_SIZE))
                }else{
                    tempList.add(listOf(size[WIDTH], size[HEIGHT]))
                }
            }
        }
        for(i in uris.indices){
            kotlin.runCatching {
                val bitmap = Picasso.get().load(uris[i]).resize(tempList[i][WIDTH], tempList[i][HEIGHT]).get()
                val returnedValue = bmpRotate(bitmap, act as MainActivity, uris[i]) ?: bitmap
                val radius = if (returnedValue.width < returnedValue.height) returnedValue.width / 2 else returnedValue.height / 2
                getCroppedBitmap(
                    returnedValue,
                    returnedValue.width / 2,
                    returnedValue.height / 2,
                    radius
                )?.let { bitmapList.add(it) }
            }
        }
        return@withContext bitmapList
    }

    private fun bmpRotate(bitmap: Bitmap, act: MainActivity, imageUri: Uri): Bitmap?{
        var exif: ExifInterface? = null
        try {
            exif = when (Build.VERSION.SDK_INT) {
                in Int.MIN_VALUE..24 -> imageUri.path?.let { ExifInterface(it) }
                else -> act.contentResolver.openInputStream(imageUri)?.let { ExifInterface(it) }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val orientation = exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        return rotateBitmap(bitmap, orientation ?: ExifInterface.ORIENTATION_NORMAL)
    }

    private fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap? {
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_NORMAL -> return bitmap
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                matrix.setRotate(180f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_TRANSPOSE -> {
                matrix.setRotate(90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            ExifInterface.ORIENTATION_TRANSVERSE -> {
                matrix.setRotate(-90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(-90f)
            else -> return bitmap
        }
        try {
            val bmRotated =
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            bitmap.recycle()
            return bmRotated
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            return null
        }
    }

    suspend fun getBitmapsFromUris(uris : List<String?>): List<Bitmap> = withContext(
        Dispatchers.IO){

        val bitmapList = ArrayList<Bitmap>()
        for(i in uris.indices){
            kotlin.runCatching {
                bitmapList.add(Picasso.get().load(uris[i]).get())
            }
        }
        return@withContext bitmapList
    }

    fun getCroppedBitmap(bitmap: Bitmap, cx: Int, cy: Int, radius: Int): Bitmap? {
        val diam = radius shl 1
        val targetBitmap = Bitmap.createBitmap(diam, diam, Bitmap.Config.RGBA_F16)
        val canvas = Canvas(targetBitmap)
        val color = -0xbdbdbe
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawCircle(radius.toFloat(), radius.toFloat(), radius.toFloat(), paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, (-cx + radius).toFloat(), (-cy + radius).toFloat(), paint)
        return targetBitmap
    }
}