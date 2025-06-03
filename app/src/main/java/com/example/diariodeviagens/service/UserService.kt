package com.example.diariodeviagens.service

import com.example.diariodeviagens.model.Login
import com.example.diariodeviagens.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserService {

    @Headers("Content-Type: application/json")
    @POST("usuario") // endpoint de POST (exemplo: /users)
    fun insert(@Body user: User): retrofit2.Call<User>


    @Headers("Content-Type: application/json")
    @POST("login")
    fun inserir(@Body login: Login): Call<Login>

}