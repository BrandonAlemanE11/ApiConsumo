package com.example.prsctica.ui.gallery;

import com.example.prsctica.DTO.Usuario;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Registro {
    @FormUrlEncoded
    @POST("registroForm")
    Call<Usuario> REGISTRO_CALL(
            @Field("nombre") String nombre,
            @Field("apellidoPaterno") String apellidoPaterno,
            @Field("apellidoMaterno") String apellidoMaterno,
            @Field("correo") String correo,
            @Field("contrasenia") String contrasenia,
            @Field("contrasenia2") String contrasenia2,
            @Field("fechaNacimiento") String fechaNacimiento
    );
}
