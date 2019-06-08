package dev.rezastallone.cartrackchallange.ui.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.Result.Loading
import dev.rezastallone.cartrackchallange.data.Users
import dev.rezastallone.cartrackchallange.data.source.UsersRepository
import kotlinx.coroutines.launch

class SigninViewModel(private val usersRepository: UsersRepository) : ViewModel(){

    lateinit var signinLiveData: MutableLiveData<Result<Users>>

    fun getUserByUsernameAndPassword(username: String, password: String): MutableLiveData<Result<Users>> {
        signinLiveData = MutableLiveData(Loading(Any()))
        viewModelScope.launch {
            val signinResult = usersRepository.getUserByUsernameAndPassword(username, password)
            signinLiveData.postValue(signinResult)
        }
        return signinLiveData
    }
}