package com.payquestion.payquest.App;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.payquestion.payquest.API.ApiClient;
import com.payquestion.payquest.API.ApiInterface;
import com.payquestion.payquest.AppActivity;
import com.payquestion.payquest.Collections.ErrorCollection;
import com.payquestion.payquest.Dialogs.LoadingDialog;
import com.payquestion.payquest.Models.ChangeAvatarResponse;
import com.payquestion.payquest.Models.GenericResponse;
import com.payquestion.payquest.R;
import com.payquestion.payquest.Storage.UserStorage;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment {
    ViewGroup view;
    Button changePassword;
    Uri imageUri;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    CircleImageView userAvatar;
    UserStorage userStorage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppActivity)getActivity()).navigationView.getMenu().getItem(2).setChecked(true);
        view = (ViewGroup) inflater.inflate(R.layout.app_profile_edit,container,false);
        userStorage = new UserStorage(getActivity());

        userAvatar = view.findViewById(R.id.userAvatar);
        initAvatar(userStorage.getAvatar());
        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                }else{
                    verifyStoragePermissions(getActivity());
                }
            }
        });

        changePassword = view.findViewById(R.id.changePassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePwd();
            }
        });

        return  view;
    }

    public void initAvatar(String avatar)
    {
        Glide.with(getActivity()).load(avatar).into(userAvatar);
    }


    public void changePwd()
    {
        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        EditText password = view.findViewById(R.id.password);
        EditText new_password = view.findViewById(R.id.new_password);
        EditText confirm_password = view.findViewById(R.id.confirm_password);
        CheckBox logoutOther = view.findViewById(R.id.logoutOtherDevices);


        String pw = password.getText().toString().trim();
        String npw = new_password.getText().toString().trim();
        String cnf = confirm_password.getText().toString().trim();

        if (pw.matches("") || npw.matches("") || cnf.matches(""))
        {
            Toast.makeText(getActivity(),getString(R.string.empty_area),Toast.LENGTH_SHORT).show();
            return;
        }

        loadingDialog.start();
        String logoutOtherDevices = logoutOther.isChecked() ? "logout" : "keep";
        ApiInterface api = (new ApiClient(getActivity())).getClient().create(ApiInterface.class);
        Call<GenericResponse> call = api.changePassword(pw,npw,cnf,logoutOtherDevices);
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                }else{
                    ErrorCollection collection = new ErrorCollection(response.errorBody(),getActivity());
                    Toast.makeText(getActivity(), collection.getMessage(),Toast.LENGTH_SHORT).show();
                }
                loadingDialog.stop();
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Toast.makeText(getActivity(),getString(R.string.request_error),Toast.LENGTH_SHORT).show();
                loadingDialog.stop();

            }
        });

    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 100);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100){
            if (data != null)
            {
                imageUri = data.getData();
                System.out.println(data.getExtras().toString());
                String filePath = getRealPathFromURI(imageUri);
                File file = new File(filePath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
                final LoadingDialog dialog = new LoadingDialog(getActivity());
                dialog.start();
                ApiInterface api = (new ApiClient(getActivity())).getClient().create(ApiInterface.class);
                Call<ChangeAvatarResponse> call = api.uploadImage(body);
                call.enqueue(new Callback<ChangeAvatarResponse>() {
                    @Override
                    public void onResponse(Call<ChangeAvatarResponse> call, Response<ChangeAvatarResponse> response) {
                        if (response.isSuccessful())
                        {
                            initAvatar(response.body().getAvatar());
                            userStorage.setAvatar(response.body().getAvatar());

                        }else{
                            ErrorCollection collection = new ErrorCollection(response.errorBody(),getActivity());
                            Toast.makeText(getActivity(),collection.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        dialog.stop();
                    }

                    @Override
                    public void onFailure(Call<ChangeAvatarResponse> call, Throwable t) {
                        Toast.makeText(getActivity(),getString(R.string.request_error),Toast.LENGTH_SHORT).show();
                        dialog.stop();
                    }
                });
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        System.out.println("REQUEST CODE " + requestCode);
        switch (requestCode)
        {
            case REQUEST_EXTERNAL_STORAGE : {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    System.out.println("GALLERY OPENINGF");
                    openGallery();
                }
            }
        }
    }
}
