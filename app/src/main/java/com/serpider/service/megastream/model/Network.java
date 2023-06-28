package com.serpider.service.megastream.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.serpider.service.megastream.adapter.ItemAdapter;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Network {

    private String network_unique, network_name, network_desc, network_image;

    public Network(String network_unique, String network_name, String network_desc, String network_image) {
        this.network_unique = network_unique;
        this.network_name = network_name;
        this.network_desc = network_desc;
        this.network_image = network_image;
    }

    public String getNetwork_unique() {
        return network_unique;
    }

    public void setNetwork_unique(String network_unique) {
        this.network_unique = network_unique;
    }

    public String getNetwork_name() {
        return network_name;
    }

    public void setNetwork_name(String network_name) {
        this.network_name = network_name;
    }

    public String getNetwork_desc() {
        return network_desc;
    }

    public void setNetwork_desc(String network_desc) {
        this.network_desc = network_desc;
    }

    public String getNetwork_image() {
        return network_image;
    }

    public void setNetwork_image(String network_image) {
        this.network_image = network_image;
    }

}
