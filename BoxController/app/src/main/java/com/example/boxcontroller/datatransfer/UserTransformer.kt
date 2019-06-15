package com.example.boxcontroller.datatransfer

import com.example.boxcontroller.domain.database.dataclass.UserDC
import com.example.boxcontroller.view.viewmodel.User

object UserTransformer {
    fun transform(userDC: UserDC) = User(userDC.id, userDC.name, userDC.email)
}