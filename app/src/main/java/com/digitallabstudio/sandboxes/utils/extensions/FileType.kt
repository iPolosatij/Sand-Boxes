package com.digitallabstudio.sandboxes.utils.extensions

enum class FileType {
    Image, Pdf, Xls, Xlsx, Video, Audio, Doc, Docx, Ppt, Pptx, Txt, Unknown
}

fun FileType.isFileSupported(): Boolean {
    return this != FileType.Unknown
}
