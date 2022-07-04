package com.example.prsctica.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prsctica.DTO.Usuario;
import com.example.prsctica.databinding.FragmentHomeBinding;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button btnIniciar = binding.btnIniciar;
        btnIniciar.setOnClickListener(new View.OnClickListener() {

            final EditText correo = binding.correo;
            final EditText contrasenia = binding.contrasenia;
            final HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();

            @Override
            public void onClick(View view) {
                String Gcorreo = correo.getText().toString().trim();
                String Gcontrasenia = contrasenia.getText().toString().trim();

                loggin.setLevel(HttpLoggingInterceptor.Level.BODY);
                final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.addInterceptor(loggin);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://jnmlvuvn.lucusvirtual.es/api/auth/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
                Login log = retrofit.create(Login.class);
                Call<Usuario> call = log.USUARIO_CALL(Gcorreo, Gcontrasenia);
                call.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if(response.isSuccessful() && response.body() != null){
                            correo.getText().clear();
                            contrasenia.getText().clear();
                            Toast.makeText(getContext(), "Bienvenido", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            contrasenia.getText().clear();
                            Toast.makeText(getContext(), "Error de credenciales", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        Toast.makeText(getContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}