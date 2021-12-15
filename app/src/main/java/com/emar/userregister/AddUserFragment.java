package com.emar.userregister;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.emar.userregister.databinding.FragmentAddUserBinding;
import com.emar.userregister.entities.User;
import com.emar.userregister.repositories.UserRepository;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddUserFragment extends Fragment {

    private FragmentAddUserBinding binding;
    List imageIds;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageIds = new ArrayList<Integer>();

        imageIds.add(R.drawable.avatar_1);
        imageIds.add(R.drawable.avatar_2);
        imageIds.add(R.drawable.avatar_3);
        imageIds.add(R.drawable.avatar_4);
        imageIds.add(R.drawable.avatar_5);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAddUserBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SafetyNet.getClient(getActivity()).verifyWithRecaptcha("6Lco_H4dAAAAAApF7leBy1P7K4eqZdE5uXwpl_c3")
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                            @Override
                            public void onSuccess(@NonNull SafetyNetApi.RecaptchaTokenResponse recaptchaTokenResponse) {

                                UserRepository userRepository = new UserRepository(getActivity().getApplication());
                                User user = new User();

                                EditText editTextName =  binding.editTextName;
                                EditText editTextEmail = binding.editTextEmail;
                                EditText editTextPhone = binding.editTextPhone;


                                if(isEditTextEmpty(editTextName)){
                                    editTextName.setError("Please Enter Name");
                                    return;
                                }

                                if(isEditTextEmpty(editTextEmail)){
                                    editTextEmail.setError("Please Enter Email");
                                    return;
                                }

                                if(isEditTextEmpty(editTextPhone)){
                                    editTextPhone.setError("Please Enter Phone Number");
                                    return;
                                }

                                user.setName(editTextName.getText().toString());
                                user.setEmail(editTextEmail.getText().toString());
                                user.setPhoneNumber(editTextPhone.getText().toString());
                                user.setImageId(getRandomImage());

                                Log.i("ADDUSER",user.toString());

                                if(userRepository.insertUser(user)>0)
                                {
                                    Toast.makeText(getActivity(),"User created",Toast.LENGTH_SHORT).show();
                                    NavHostFragment.findNavController(AddUserFragment.this)
                                            .navigate(R.id.action_SecondFragment_to_FirstFragment);
                                }else{
                                    Toast.makeText(getActivity(),"Failed to create user",Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                    .addOnFailureListener(getActivity(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("ADDUSER",e.getMessage());
                    }
                });
//

            }



        });
    }

    public Boolean isEditTextEmpty(EditText editText){
        if(TextUtils.isEmpty(editText.getText())){
            return true;
        }

        return false;
    }

    public int getRandomImage(){
        int min = 0;
        int max = 4;

        Random random = new Random();

        int value = random.nextInt(max + min)+min;

        return (int) imageIds.get(value);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}