package com.shahab.hms;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addRoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addRoomFragment extends Fragment {
    Button uploadPic, addRoom;
    EditText price, roomId, desc;
    Uri selectedImage=null;
    ImageView picture;
    DatabaseReference storeRoomReference;
    FirebaseDatabase database;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public addRoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addRoomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static addRoomFragment newInstance(String param1, String param2) {
        addRoomFragment fragment = new addRoomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_add_room, container, false);
        uploadPic=view.findViewById(R.id.addRoom_UploadPic);
        addRoom=view.findViewById(R.id.addRoom_addRoom);
        price=view.findViewById(R.id.addRoom_Price);
        roomId=view.findViewById(R.id.addRoom_Id);
        desc=view.findViewById(R.id.addRoom_Description);
        picture=view.findViewById(R.id.addRoom_image);
        database= FirebaseDatabase.getInstance();
        storeRoomReference=database.getReference("Room");
        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.wtf("Debug", "onCreate() called");
//                Toast.makeText(getActivity(),"In here",Toast.LENGTH_SHORT).show();

                if(selectedImage!=null){
//                    Toast.makeText(getActivity(),selectedImage.toString(),Toast.LENGTH_SHORT).show();

                    StorageReference storageReference= FirebaseStorage.getInstance().getReference();
//                    Toast.makeText(getActivity(),storageReference.toString(),Toast.LENGTH_SHORT).show();

                    storageReference=storageReference.child("Room_pic/"+roomId.getText().toString()+new Date().getTime() +".jpg");
                    storageReference.putFile(selectedImage)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> task=taskSnapshot.getStorage().getDownloadUrl();
                                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String createProfile_dp=uri.toString();
                                            storeRoomReference.push().setValue(
                                                    new room(
                                                            roomId.getText().toString(),
                                                            desc.getText().toString(),
                                                            createProfile_dp,
                                                            price.getText().toString())
                                            );
                                            Toast.makeText(getActivity(),"New Room Added", Toast.LENGTH_SHORT).show();
                                            Log.wtf("Debug", "onCreate() called");
//                                            Toast.makeText(createProfile.this,"Here",Toast.LENGTH_SHORT).show();
//                                            Intent intent=new Intent(createProfile.this,MainActivity.class);
//                                            intent.putExtra("name",firstName.getText().toString()+"  "+lastName.getText().toString());
//                                            intent.putExtra("phNo",phNo.getText().toString());
//                                            startActivity(intent);
//                                            finish();

                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.wtf("Debug", "task on failure called");
//                                                    Toast.makeText(
//                                                            createProfile.this,
//                                                            "Failed to upload image and data",
//                                                            Toast.LENGTH_LONG).show();

                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(
                                            getActivity(),
                                            "Picture uploading failed",
                                            Toast.LENGTH_LONG).show();

                                }
                            });

                }
                else{
//                    Toast.makeText(
//                            createProfile.this,
//                            "Select Display Picture",
//                            Toast.LENGTH_LONG).show();
                }

            }
        });
        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,200);

            }
        });
        return view;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200 && resultCode== Activity.RESULT_OK){
            selectedImage=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),selectedImage);
                picture.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}