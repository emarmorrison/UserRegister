package com.emar.userregister.view_models;

import android.app.Application;

import com.emar.userregister.entities.User;
import com.emar.userregister.repositories.UserRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private MutableLiveData<User> selectedUser;

    public UserViewModel(@NonNull Application application) {
        super(application);

        selectedUser = new MutableLiveData<>();
        userRepository = new UserRepository(application);
    }

    public MutableLiveData<User> getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser.setValue(selectedUser);
    }
}
