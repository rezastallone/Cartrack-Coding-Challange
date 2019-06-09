package dev.rezastallone.cartrackchallange.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.Result.Loading
import dev.rezastallone.cartrackchallange.data.Users
import dev.rezastallone.cartrackchallange.data.source.UsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SignupViewModel(private val usersRepository: UsersRepository) : ViewModel(){

    lateinit var insertUserLiveData: MutableLiveData<Result<Users>>

    fun insertUser(user: Users): MutableLiveData<Result<Users>> {
        insertUserLiveData = MutableLiveData()
        insertUserLiveData.postValue(Loading(user))
        viewModelScope.launch {
            val insertionResult = usersRepository.insertUser(user)
            insertUserLiveData.postValue(insertionResult)
        }
        return insertUserLiveData
    }

}