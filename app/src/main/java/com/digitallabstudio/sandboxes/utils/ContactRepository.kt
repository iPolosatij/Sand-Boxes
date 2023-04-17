package com.digitallabstudio.sandboxes.utils

import com.digitallabstudio.sandboxes.data.room.data.Bd_data

object ContactRepository {

    val names = arrayListOf( "Alex", "Sergey", "Oleg", "Vitali", "Nik", "Anton", "Petr")
    val lastNames = arrayListOf( "Petrov", "Ivanov", "Sidorov", "Vatutin", "Nekrasov", "Pushkin", "Korolev")
    val avatarsList = arrayListOf(
        "https://u.kanobu.ru/articles/pics/2eaeebac-6128-4bd7-a1be-ec6b415db0e5.jpg",
        "https://i.pinimg.com/originals/01/c7/b1/01c7b181419e15cc614b2297a0e0b959.jpg",
        "https://i.pinimg.com/originals/ba/1e/dc/ba1edc6334faa704c42f69a0a77bdf84.jpg",
        "https://cdn-st4.rtr-vesti.ru/vh/pictures/hd/330/070/3.jpg",
        "https://cq.ru/storage/uploads/posts/1466727/1.jpg"
    )

    fun getContacts(count: Int): List<Bd_data> {
        val temp = arrayListOf<Bd_data>()
        var counter = 0
        while (counter != count) {
            counter++
            temp.add(
                Bd_data(
                    id = counter.toString(),
                    name = names.random(),
                    lastName = lastNames.random(),
                    tel = "+${listOf(0,1,2,3,4,5,6,7,8,9).random()}" +
                            "${listOf(0,1,2,3,4,5,6,7,8,9).random()}" +
                            "${listOf(0,1,2,3,4,5,6,7,8,9).random()}" +
                            "${listOf(0,1,2,3,4,5,6,7,8,9).random()}" +
                            "${listOf(0,1,2,3,4,5,6,7,8,9).random()}" +
                            "${listOf(0,1,2,3,4,5,6,7,8,9).random()}" +
                            "${listOf(0,1,2,3,4,5,6,7,8,9).random()}" +
                            "${listOf(0,1,2,3,4,5,6,7,8,9).random()}" +
                            "${listOf(0,1,2,3,4,5,6,7,8,9).random()}" +
                            "${listOf(0,1,2,3,4,5,6,7,8,9).random()}" +
                            "${listOf(0,1,2,3,4,5,6,7,8,9).random()}",
                    avatar = avatarsList.random()
                )
            )
        }
        return temp
    }

}