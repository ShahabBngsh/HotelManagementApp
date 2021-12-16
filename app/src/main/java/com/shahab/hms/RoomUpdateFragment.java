package com.shahab.hms;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoomUpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomUpdateFragment extends Fragment {
    Button updatePrice, updatePic;
    EditText roomId, newPrice;
    ImageView picture, uploadPic;
    DatabaseReference showRoomReference;
    FirebaseDatabase database;
    Uri selectedImage=null;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RoomUpdateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RoomUpdateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoomUpdateFragment newInstance(String param1, String param2) {
        RoomUpdateFragment fragment = new RoomUpdateFragment();
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
        View view= inflater.inflate(R.layout.fragment_room_update, container, false);
        roomId=view.findViewById(R.id.updateRoom_roomId);
        updatePic=view.findViewById(R.id.updateRoom_UpdatePic);
        uploadPic=view.findViewById(R.id.updateRoom_image);
        newPrice=view.findViewById(R.id.updateRoom_newPrice);
        picture=view.findViewById(R.id.updateRoom_image);
        updatePrice=view.findViewById(R.id.updateRoom_updatePrice);

        updatePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database= FirebaseDatabase.getInstance();
//        reference=database.getReference("Users/"+currentuser+"/Profile");
                showRoomReference=database.getReference("Room/");
                showRoomReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot data:snapshot.getChildren()){
                            if(data.child("roomId").getValue(String.class).equals(roomId.getText().toString())){
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Room").child(data.getKey().toString());
                                mDatabase.child("price").setValue(newPrice.getText().toString());
                                Toast.makeText(getActivity(),"Price Updated",Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        updatePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                            database= FirebaseDatabase.getInstance();

                                            showRoomReference=database.getReference("Room/");
                                            showRoomReference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for(DataSnapshot data:snapshot.getChildren()){
                                                        if(data.child("roomId").getValue(String.class).equals(roomId.getText().toString())){
                                                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Room").child(data.getKey().toString());
                                                            mDatabase.child("pic").setValue(createProfile_dp);
//                                                            Toast.makeText(getContext(),"Picture Updated",Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });


                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(
                                            getActivity(),
                                            "Picture update failed",
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