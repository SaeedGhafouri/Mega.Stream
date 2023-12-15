package com.serpider.service.megastream.ui.dialog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.serpider.service.megastream.R;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.databinding.FragmentFilterBinding;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.Country;
import com.serpider.service.megastream.model.Genre;
import com.serpider.service.megastream.model.Network;
import com.serpider.service.megastream.util.SnackBoard;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterFragment extends DialogFragment {
    private List<Genre> listGenre = new ArrayList<>();
    private List<Country> listCountry = new ArrayList<>();
    private List<Network> listNetwork = new ArrayList<>();
    private ApiInterFace requestGenre, requestCountry, requestNetwork;
    private FragmentFilterBinding mBinding;
    private String type, genre, country, network, year, imdb, age, language;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentFilterBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadType();
        loadGenre();
        loadCountry();
        loadNetwork();
        loadYear();
        loadImdb();
        loadAge();
        loadLangueg();

        mBinding.btnSubmit.setOnClickListener(view1 -> {
            Toast.makeText(getActivity(), type+genre+country+network+year+imdb+age+language, Toast.LENGTH_SHORT).show();
        });

    }

    private void loadType() {
        List<String> typesList = new ArrayList<>();
        typesList.add("فیلم سینمایی");
        typesList.add("سریال");
        typesList.add("هر دو");

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getActivity(),
                        R.layout.dropdown_menu_popup_item,
                        typesList);
        adapter.setDropDownViewResource(R.layout.dropdown_menu_popup_item);
        mBinding.spinnerType.setAdapter(adapter);

        mBinding.spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedType = adapterView.getItemAtPosition(i).toString();
                type = selectedType;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private void loadGenre() {
        List<String> genreNames = new ArrayList<>();
        requestGenre = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);
        requestGenre.getGenres().enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
                listGenre = response.body();
                if (listGenre != null && listGenre.size() > 0) {
                    for (Genre genre : listGenre) {
                        genreNames.add(genre.getName_fa());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_menu_popup_item, genreNames);
                    adapter.setDropDownViewResource(R.layout.dropdown_menu_popup_item);
                    mBinding.spinnerGenre.setAdapter(adapter);

                    mBinding.spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String selectedType = adapterView.getItemAtPosition(i).toString();
                            genre = selectedType;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Genre>> call, Throwable t) {
                SnackBoard.show(getActivity(),"خطای سمت سرور", 0);
            }
        });
    }
    private void loadCountry() {
        List<String> name = new ArrayList<>();
        requestCountry = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);
        requestCountry.getCountrys().enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                listCountry = response.body();
                if (listCountry != null && listCountry.size() > 0) {
                    for (Country country : listCountry) {
                        name.add(country.getFa_name());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_menu_popup_item, name);
                    adapter.setDropDownViewResource(R.layout.dropdown_menu_popup_item);
                    mBinding.spinnerCountry.setAdapter(adapter);

                    mBinding.spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String selectedType = adapterView.getItemAtPosition(i).toString();
                            country = selectedType;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                SnackBoard.show(getActivity(),"خطای سمت سرور", 0);
            }
        });
    }
    private void loadNetwork() {
        List<String> name = new ArrayList<>();
        requestNetwork = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);
        requestNetwork.getNetworks().enqueue(new Callback<List<Network>>() {
            @Override
            public void onResponse(Call<List<Network>> call, Response<List<Network>> response) {
                listNetwork = response.body();
                if (listNetwork != null && listNetwork.size() > 0) {
                    for (Network network : listNetwork) {
                        name.add(network.getName());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_menu_popup_item, name);
                    adapter.setDropDownViewResource(R.layout.dropdown_menu_popup_item);
                    mBinding.spinnerNetwork.setAdapter(adapter);

                    mBinding.spinnerNetwork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String selectedType = adapterView.getItemAtPosition(i).toString();
                            network = selectedType;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Network>> call, Throwable t) {
                SnackBoard.show(getActivity(),"خطای سمت سرور", 0);
            }
        });
    }
    private void loadYear() {
        List<String> yearList = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int year = currentYear; year >= 1900; year--) {
            yearList.add(String.valueOf(year));
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getActivity(),
                        R.layout.dropdown_menu_popup_item,
                        yearList);
        adapter.setDropDownViewResource(R.layout.dropdown_menu_popup_item);
        mBinding.spinnerYear.setAdapter(adapter);

        mBinding.spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedType = adapterView.getItemAtPosition(i).toString();
                year = selectedType;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void loadImdb() {
        List<String> imdbList = new ArrayList<>();
        for (int i = 9; i >= 1; i--) {
            for (int j = 9; j >= 1; j--) {
                imdbList.add(String.valueOf(i + "." + j));
            }
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getActivity(),
                        R.layout.dropdown_menu_popup_item,
                        imdbList);
        adapter.setDropDownViewResource(R.layout.dropdown_menu_popup_item);
        mBinding.spinnerImdb.setAdapter(adapter);

        mBinding.spinnerImdb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedType = adapterView.getItemAtPosition(i).toString();
                imdb = selectedType;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private void loadAge() {
        List<String> ageList = new ArrayList<>();
        ageList.add("G");
        ageList.add("PG");
        ageList.add("PG-13");
        ageList.add("NC-17");
        ageList.add("TV-Y");
        ageList.add("TV-Y");
        ageList.add("TV-Y7");
        ageList.add("TV-G");
        ageList.add("TV-PG");
        ageList.add("TV-14");
        ageList.add("TV-MA");

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getActivity(),
                        R.layout.dropdown_menu_popup_item,
                        ageList);
        adapter.setDropDownViewResource(R.layout.dropdown_menu_popup_item);
        mBinding.spinnerAge.setAdapter(adapter);

        mBinding.spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedType = adapterView.getItemAtPosition(i).toString();
                age = selectedType;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void loadLangueg() {
        List<String> lanList = new ArrayList<>();
        lanList.add("دوبله");
        lanList.add("زیرنویس");

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getActivity(),
                        R.layout.dropdown_menu_popup_item,
                        lanList);
        adapter.setDropDownViewResource(R.layout.dropdown_menu_popup_item);
        mBinding.spinnerLangueg.setAdapter(adapter);

        mBinding.spinnerLangueg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedType = adapterView.getItemAtPosition(i).toString();
                language = selectedType;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}