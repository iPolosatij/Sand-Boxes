package com.digitallabstudio.sandboxes.utils.extensions

import org.jivesoftware.smack.packet.ExtensionElement
import org.jivesoftware.smack.provider.EmbeddedExtensionProvider
import org.jivesoftware.smack.provider.ProviderManager
import org.jivesoftware.smack.util.XmlStringBuilder
import org.jxmpp.util.XmppStringUtils

class MamExtensionElement : ExtensionElement {
    val archiveId: String?
    private val by: String?

    internal constructor() {
        archiveId = ""
        by = ""
    }

    internal constructor(archiveId: String?, by: String?) {
        this.archiveId = archiveId
        this.by = by
    }

    override fun getNamespace(): String {
        return Namespace
    }

    override fun toXML(enclosingNamespace: String?): CharSequence {
        TODO("Not yet implemented")
    }

    override fun getElementName(): String {
        return Element
    }

    fun toXML(): CharSequence {
        val xmlBuilder = XmlStringBuilder(this)
        xmlBuilder.attribute("by", by)
        xmlBuilder.attribute("id", archiveId)
        xmlBuilder.closeEmptyElement()
        return xmlBuilder.toString()
    }

    internal class MamExtensionProvider :
        EmbeddedExtensionProvider<MamExtensionElement>() {
        override fun createReturnExtension(
            currentElement: String,
            currentNamespace: String,
            attributeMap: Map<String, String>,
            content: List<ExtensionElement>
        ): MamExtensionElement {
            val id = attributeMap["id"]
            val by = attributeMap["by"]
            return MamExtensionElement(id, by)
        }
    }

    val mucId: String
        get() = XmppStringUtils.parseLocalpart(by)

    companion object {
        const val Namespace = "urn:xmpp:mam:tmp"
        const val Element = "archived"
        fun registerProvider() {
            ProviderManager.addExtensionProvider(Element, Namespace, MamExtensionProvider())
        }
    }
}
