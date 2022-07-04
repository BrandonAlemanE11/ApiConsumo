package com.example.prsctica.ui.slideshow;

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

import com.example.prsctica.databinding.FragmentSlideshowBinding;
import com.example.prsctica.DTO.APokemon;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SlideshowFragment extends Fragment{
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflanter,
                             ViewGroup container, Bundle savedInstanceState){
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        binding = FragmentSlideshowBinding.inflate(inflanter, container, false);
        View root = binding.getRoot();

        final Button btnConsulta = binding.btnConsulta;
        btnConsulta.setOnClickListener(new View.OnClickListener() {
            final EditText idpokemon = binding.idPokemon;
            @Override
            public void onClick(View view) {
                find(idpokemon.getText().toString());
            }
        });

        return root;
    }

    private void find(String id){
        final TextView nombre = binding.nombrePokemon;
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/pokemon/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Pokemon pokemon = retrofit.create(Pokemon.class);
        Call<APokemon> call = pokemon.find(id);
        call.enqueue(new Callback<APokemon>() {
            @Override
            public void onResponse(Call<APokemon> call, Response<APokemon> response) {
                try{
                    if(response.isSuccessful()){
                        APokemon datos = response.body();
                        nombre.setText(datos.getName());
                    }
                }catch (Exception e){
                    Toast.makeText(getActivity(),"No se encontrar el ID: "+ id, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<APokemon> call, Throwable t) {

            }
        });
    }

}
