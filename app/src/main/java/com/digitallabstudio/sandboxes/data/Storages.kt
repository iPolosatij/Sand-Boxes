package com.digitallabstudio.sandboxes.data

import java.io.File
import java.io.IOException


class Storages {
    var externalStoragePath: String? = null
        private set
    private var cachePath: String? = null
    private var filesDir: String? = null
    private var imageStoragePath: String? = null
    private var locationStoragePath: String? = null
    private var audioStoragePath: String? = null
    private var videoStoragePath: String? = null
    private var profileImageStoragePath: String? = null
    private var fileStoragePath: String? = null
    private var avatarThumbPath: String? = null
        private get() {
            if (field == null || field != newAvatarThumbPath) field = newAvatarThumbPath
            return field
        }
    private var privateAvatarThumbPath: String? = null

    private constructor() {}
    private constructor(externalStoragePath: String?, cachePath: String?, filesDir: String?) {
        this.externalStoragePath = externalStoragePath
        this.cachePath = cachePath
        this.filesDir = filesDir
    }

    // TODO: add error handling
    private val mediaProfileImagePath: String?
        private get() {
            if (profileImageStoragePath == null) {
                val storeFile = File(externalStoragePath, "Profile")
                if (!storeFile.exists()) {
                    // TODO: add error handling
                    storeFile.mkdirs()
                }
                profileImageStoragePath = storeFile.absolutePath
            }
            return profileImageStoragePath
        }

    fun getMediaProfileImagePath(memberId: String): String {
        return "$mediaProfileImagePath/$memberId"
    }// TODO: add error handling

    /*
			 * The Image folder was renamed from "Image" to "ArcTour" because:
			 * when the device index all the images for its gallery,
			 * it creates an album named as the parent directory of the image.
			 * To make it clear to the user we show him an album
			 * with the app images named "ArcTour"
			 */
    val mediaImagePath: String?
        get() {
            if (imageStoragePath == null) {
                /*
                    * The Image folder was renamed from "Image" to "ArcTour" because:
                    * when the device index all the images for its gallery,
                    * it creates an album named as the parent directory of the image.
                    * To make it clear to the user we show him an album
                    * with the app images named "ArcTour"
                    */
                val storeFile = File(externalStoragePath, "ArcTour")
                if (!storeFile.exists()) {
                    // TODO: add error handling
                    storeFile.mkdirs()
                }
                imageStoragePath = storeFile.absolutePath
            }
            return imageStoragePath
        }

    fun getMediaImagePath(outgoing: Boolean): String? {
        if (imageStoragePath == null) {
            /*
			 * The Image folder was renamed from "Image" to "ArcTour" because:
			 * when the device index all the images for its gallery,
			 * it creates an album named as the parent directory of the image.
			 * To make it clear to the user we show him an album
			 * with the app images named "ArcTour"
			 */
            var storeFile = File(externalStoragePath, "ArcTour")
            if (outgoing) {
                storeFile = File(storeFile,
                    OUTGOING_PATH
                )
            }
            if (!storeFile.exists()) {
                // TODO: add error handling
                storeFile.mkdirs()
            }
            imageStoragePath = storeFile.absolutePath
        }
        return imageStoragePath
    }

    // TODO: add error handling
    val mediaVideoPath: String?
        get() {
            if (videoStoragePath == null) {
                val storeFile = File(externalStoragePath, "Video")
                if (!storeFile.exists()) {
                    // TODO: add error handling
                    storeFile.mkdirs()
                }
                videoStoragePath = storeFile.absolutePath
            }
            return videoStoragePath
        }

    // TODO: add error handling
    private val mediaLocationPath: String?
        private get() {
            if (locationStoragePath == null) {
                val storeFile = File(externalStoragePath, "LocationSnapshot")
                if (!storeFile.exists()) {
                    // TODO: add error handling
                    storeFile.mkdirs()
                }
                locationStoragePath = storeFile.absolutePath
            }
            return locationStoragePath
        }

    // TODO: add error handling
    private val mediaAudioPath: String?
        private get() {
            if (audioStoragePath == null) {
                val storeFile = File(externalStoragePath, "Audio")
                if (!storeFile.exists()) {
                    // TODO: add error handling
                    storeFile.mkdirs()
                }
                audioStoragePath = storeFile.absolutePath
            }
            return audioStoragePath
        }

    private val mediaFilePath: String?
        private get() {
            if (fileStoragePath == null) {
                val filesPath = File(externalStoragePath, "Files")
                if (!filesPath.exists()) {
                    filesPath.mkdirs()
                }
                fileStoragePath = filesPath.absolutePath
            }
            return fileStoragePath
        }

    fun generateMediaImagePath(mediaUid: String): String {
        return "$mediaImagePath/$mediaUid.jpg"
    }

    fun generateMediaVideoPath(mediaUid: String): String {
        return "$mediaVideoPath/$mediaUid.mp4"
    }

    fun generateMediaVideoThumbnailPath(mediaUid: String): String {
        return "$mediaVideoPath/$mediaUid.jpg"
    }

    @Throws(IOException::class)
    fun generateTempMediaVideoPath(mediaUid: String): String {
        val file = File(mediaVideoPath, "Temp")
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absolutePath + "/" + mediaUid + ".mp4"
    }

    fun generateMediaAudioPath(mediaUid: String): String {
        return "$mediaAudioPath/$mediaUid"
    }

    fun generateMediaLocationPath(mediaUid: String): String {
        return "$mediaLocationPath/$mediaUid"
    }

    fun generateFilePath(fileName: String): String {
        return "$mediaFilePath/$fileName"
    }

    fun generateStickerLocationPath(collectionId: String, stickerId: String): String {
        return "$filesDir/stickers/$collectionId/resources/$stickerId"
    }

    /**
     * Uses this only for stickers, which are not installed, but are locally cached;
     *
     * @param stickerId - sticker resourse-id
     * @return path to file with Sticker image
     */
    fun generateStickerLocationPath(stickerId: String): String {
        return "$filesDir/stickers/all/$stickerId"
    }

    @Throws(IOException::class)
    fun generateCachePath(resourceId: String): String {
        val cachePath = cachePath + '/' + resourceId
        val file = File(cachePath)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        if (!file.exists()) {
            file.createNewFile()
        }
        return cachePath
    }

    private val newAvatarThumbPath: String?
        private get() {
            if (privateAvatarThumbPath == null) {
                val storeFile = File(filesDir, "AvaThumb")
                if (!storeFile.exists()) storeFile.mkdirs()
                privateAvatarThumbPath = storeFile.absolutePath
            }
            return privateAvatarThumbPath
        }

    internal class StoragesBuilder {
        private var externalStoragePath: String? = null
        private var cachePath: String? = null
        private var filesDir: String? = null
        fun setExternalStoragePath(externalStoragePath: String?): StoragesBuilder {
            this.externalStoragePath = externalStoragePath
            return this
        }

        fun setCachePath(cachePath: String?): StoragesBuilder {
            this.cachePath = cachePath
            return this
        }

        fun setFilesDir(filesDir: String?): StoragesBuilder {
            this.filesDir = filesDir
            return this
        }

        fun build(): Storages {
            return Storages(externalStoragePath, cachePath, filesDir)
        }
    }

    companion object {
        private val TAG = Storages::class.java.simpleName
        private const val OUTGOING_PATH = "sent"
    }
}
