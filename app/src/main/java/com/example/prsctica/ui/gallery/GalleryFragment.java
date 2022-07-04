package com.example.prsctica.ui.gallery;

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
import com.example.prsctica.databinding.FragmentGalleryBinding;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button btnRegistrar = binding.btnRegistrar;
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            final EditText nombre = binding.nombre;
            final EditText apellidoPaterno = binding.apellidoPaterno;
            final EditText apellidoMaterno = binding.apellidoMaterno;
            final EditText correo = binding.correo;
            final EditText contrasenia = binding.contrasenia;
            final EditText contrasenia2 = binding.contrasenia2;
            final EditText fechaNacimiento = binding.fechaNacimiento;
            final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            @Override
            public void onClick(View view) {
                String Gnombre = nombre.getText().toString().trim();
                String GapellidoPaterno = apellidoPaterno.getText().toString().trim();
                String GapellidoMaterno = apellidoMaterno.getText().toString().trim();
                String Gcorreo = correo.getText().toString().trim();
                String Gcontrasenia = contrasenia.getText().toString().trim();
                String Gcontrasenia2 = contrasenia2.getText().toString().trim();
                String GfechaNacimiento = fechaNacimiento.getText().toString().trim();

                logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.addInterceptor(logging);
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("https://jnmlvuvn.lucusvirtual.es/api/auth/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
                Registro reg = retrofit.create(Registro.class);
                Call<Usuario> call = reg.REGISTRO_CALL(Gnombre, GapellidoPaterno, GapellidoMaterno,Gcorreo,Gcontrasenia,Gcontrasenia2,GfechaNacimiento);
                call.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if(response.isSuccessful() && response.body() != null){
                            nombre.getText().clear();
                            apellidoPaterno.getText().clear();
                            apellidoMaterno.getText().clear();
                            correo.getText().clear();
                            contrasenia.getText().clear();
                            contrasenia2.getText().clear();
                            fechaNacimiento.getText().clear();
                            Usuario user = response.body();
                            if(user.getEstatus().toString().equals("Error")){
                                Toast.makeText(getContext(), user.getmensaje().toString(), Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getContext(), user.getmensaje().toString(), Toast.LENGTH_SHORT).show();
                            }
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