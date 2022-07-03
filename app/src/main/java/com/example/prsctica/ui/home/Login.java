package com.example.prsctica.ui.home;
import com.example.prsctica.DTO.Usuario;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Login {
    @FormUrlEncoded
    @POST("verificarCredenciales")
    Call<Usuario> USUARIO_CALL(
            @Field("correo") String correo,
            @Field("contrasenia") String contrasenia
    );

}
