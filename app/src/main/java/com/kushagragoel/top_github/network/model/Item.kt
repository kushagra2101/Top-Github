package com.kushagragoel.top_github.network.model

import android.os.Parcel
import android.os.Parcelable

data class Item(
    val added_stars: String?,
    val avatars: List<String>?,
    val desc: String?,
    val forks: String?,
    val lang: String?,
    val repo: String?,
    val repo_link: String?,
    val stars: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(this.added_stars)
        p0?.writeStringList(this.avatars)
        p0?.writeString(this.desc)
        p0?.writeString(this.forks)
        p0?.writeString(this.lang)
        p0?.writeString(this.repo)
        p0?.writeString(this.repo_link)
        p0?.writeString(this.stars)
    }

    override fun toString(): String {
        var repoModelItem = "<b>User Name</b><br>${this.repo?.split('/')?.get(0)?:""}<br><br>" +
                "<b>Repo Name</b><br>${this.repo?.split('/')?.get(1)?:""}<br><br>" +
                "<b>Total Stars</b><br>${this.stars}<br><br>" +
                "<b>Stars Added</b><br>${this.added_stars}<br><br>" +
                "<b>Repository Description</b><br>${this.desc}<br><br>" +
                "<b>Forks</b><br>${this.forks}<br><br>" +
                "<b>Programming Language</b><br>${this.lang}<br><br>" +
                "<b>Repo Link</b><br>${this.repo_link}<br><br>"
        var avatarLinks: String = ""
        if (!avatars.isNullOrEmpty()) {
            for (avatar: String in avatars)
                avatarLinks+= "$avatar<br>"
            repoModelItem+="<b>Avatar Links</b><br><font size=12px>$avatarLinks</font>"
        }

        return repoModelItem

        }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}