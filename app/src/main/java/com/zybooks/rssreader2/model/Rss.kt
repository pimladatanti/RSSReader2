package com.zybooks.rssreader2.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.io.Serializable


@Root(name = "rss", strict = false)
data class Rss constructor(
    @field:ElementList(inline = true, name = "channel")
    @param:ElementList(inline = true, name = "channel")
    var channels: List<Channel>? = null
) : Serializable {
    override fun toString(): String {
        return "Feed: \n[Channel: \n$channels]"
    }
}

    //fun getChannels(): List<Channel>? {
        //return channels
   // }







/*@Override
fun toString(): String {
    return "Feed: \n[Channel: \n$channels]"
} */


    //@ElementList(inline = true, name = "channel")
    //private var channels: List<Channel>? = null



    //fun setChannels(channels: List<Channel>?) {
       // channels = channels
    //}


