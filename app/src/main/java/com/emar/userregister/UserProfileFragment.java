package com.emar.userregister;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emar.userregister.databinding.FragmentUserProfileBinding;
import com.emar.userregister.view_models.UserViewModel;


public class UserProfileFragment extends Fragment {

    FragmentUserProfileBinding binding;

    private UserViewModel userViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        userViewModel.getSelectedUser().observe(getViewLifecycleOwner(),user -> {
            binding.textViewName.setText(user.getName());
            binding.textViewEmail.setText(user.getEmail());
            binding.textViewPhoneNumber.setText(user.getPhoneNumber());
            binding.imageView2.setImageDrawable(getResources().getDrawable(user.getImageId()));
        });

    }
}