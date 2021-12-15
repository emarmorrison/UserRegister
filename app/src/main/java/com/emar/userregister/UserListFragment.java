package com.emar.userregister;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emar.userregister.adapter.UserRecyclerViewAdapter;
import com.emar.userregister.databinding.FragmentUserListBinding;
import com.emar.userregister.entities.User;
import com.emar.userregister.listener.UserListener;
import com.emar.userregister.repositories.UserRepository;
import com.emar.userregister.view_models.UserViewModel;

import java.util.List;

public class UserListFragment extends Fragment implements UserListener {

    private FragmentUserListBinding binding;

    private UserRecyclerViewAdapter adapter;

    private UserRepository userRepository;

    private UserViewModel userViewModel;
    private Boolean isTablet;

    private RecyclerView recyclerView;
    private Button btnAddUser;

    View profileDetailFragment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        isTablet = getContext().getResources().getBoolean(R.bool.isTablet);

        userRepository = new UserRepository(getActivity().getApplication());
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

//        binding = FragmentUserListBinding.inflate(inflater, container, false);
        View view = inflater.inflate(R.layout.fragment_user_list,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        btnAddUser = (Button) view.findViewById(R.id.btnAddUser);

        adapter = new UserRecyclerViewAdapter(this,getResources());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);



        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userRepository.getUsersAll().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.setUserList(users);
            }
        });

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(UserListFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        profileDetailFragment = view.findViewById(R.id.profile_nav_container);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onUserSelected(User user) {
        userViewModel.setSelectedUser(user);

        if(profileDetailFragment!=null){
            Navigation.findNavController(profileDetailFragment).navigate(R.id.fragement_user_profile);

        }else{
            NavHostFragment.findNavController(UserListFragment.this)
                    .navigate(R.id.action_FirstFragment_to_userProfileFragment);
        }


    }
}