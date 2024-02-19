package com.nitmizoram.nitmz;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.nitmizoram.nitmz.AdminSection.AddEvents;
import com.nitmizoram.nitmz.AdminSection.AddFaculty;
import com.nitmizoram.nitmz.AdminSection.AddStaff;
import com.nitmizoram.nitmz.AdminSection.UpdateBusTiming;
import com.nitmizoram.nitmz.AdminSection.UpdateMessMenu;
import com.nitmizoram.nitmz.AdminSection.UpdateTimeTable;
import com.nitmizoram.nitmz.AdminSection.UploadBooks;
import com.nitmizoram.nitmz.AdminSection.UploadCalender;
import com.nitmizoram.nitmz.AdminSection.UploadFees;
import com.nitmizoram.nitmz.AdminSection.UploadNotes;
import com.nitmizoram.nitmz.AdminSection.UploadNotice;
import com.nitmizoram.nitmz.AdminSection.UploadPyqs;
import com.nitmizoram.nitmz.AdminSection.UploadSyllabus;
import com.nitmizoram.nitmz.utils.FirebaseUtils;

public class AdminWork extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_work);


        ImageButton back = findViewById(R.id.backbtn);
        CardView update_TT = findViewById(R.id.UpdateTT);
        CardView update_MM = findViewById(R.id.UpdateMM);
        CardView uploadNotes = findViewById(R.id.Upload_Notes);
        CardView uploadSyllabus = findViewById(R.id.UpdateSyllabus);
        CardView uploadPyqs = findViewById(R.id.UpdatePyqs);
        CardView uploadBooks = findViewById(R.id.UpdateBooks);
        CardView uploadCalender = findViewById(R.id.UpdateCalender);
        CardView update_Bus = findViewById(R.id.UpdateBus);
        CardView send_notice = findViewById(R.id.SendNotification);
        CardView add_events = findViewById(R.id.Add_Events);
        CardView add_faculty = findViewById(R.id.Add_Faculty);
        CardView add_staff = findViewById(R.id.Add_Staff);
        CardView add_fees = findViewById(R.id.Add_Fees);
        CardView update_society = findViewById(R.id.Update_society);
        CardView update_tnp = findViewById(R.id.Update_TNP);

        back.setOnClickListener(view -> onBackPressed());

        send_notice.setOnClickListener(view -> FirebaseUtils.checkForAdmin(isAdmin -> {
            if (isAdmin) {
                Intent intent = new Intent(AdminWork.this, UploadNotice.class);
                startActivity(intent);
            } else {
                showNotAdminDialog();
            }
        }));

        update_TT.setOnClickListener(view -> FirebaseUtils.checkForAdmin(isAdmin -> {
            if (isAdmin) {
                Intent intent = new Intent(AdminWork.this, UpdateTimeTable.class);
                startActivity(intent);
            } else {
                showNotAdminDialog();
            }
        }));

        update_MM.setOnClickListener(view -> FirebaseUtils.checkForAdmin(isAdmin -> {
            if (isAdmin) {
                Intent intent = new Intent(AdminWork.this, UpdateMessMenu.class);
                startActivity(intent);
            } else {
                showNotAdminDialog();
            }
        }));

        uploadNotes.setOnClickListener(view -> FirebaseUtils.checkForAdmin(isAdmin -> {
            if (isAdmin) {
                Intent intent = new Intent(AdminWork.this, UploadNotes.class);
                startActivity(intent);
            } else {
                showNotAdminDialog();
            }
        }));
        uploadSyllabus.setOnClickListener(view -> FirebaseUtils.checkForAdmin(isAdmin -> {
            if (isAdmin) {
                Intent intent = new Intent(AdminWork.this, UploadSyllabus.class);
                startActivity(intent);
            } else {
                showNotAdminDialog();
            }
        }));
        uploadPyqs.setOnClickListener(view -> FirebaseUtils.checkForAdmin(isAdmin -> {
            if (isAdmin) {
                Intent intent = new Intent(AdminWork.this, UploadPyqs.class);
                startActivity(intent);
            } else {
                showNotAdminDialog();
            }
        }));
        uploadBooks.setOnClickListener(view -> FirebaseUtils.checkForAdmin(isAdmin -> {
            if (isAdmin) {
                Intent intent = new Intent(AdminWork.this, UploadBooks.class);
                startActivity(intent);
            } else {
                showNotAdminDialog();
            }
        }));
        uploadCalender.setOnClickListener(view -> FirebaseUtils.checkForAdmin(isAdmin -> {
            if (isAdmin) {
                Intent intent = new Intent(AdminWork.this, UploadCalender.class);
                startActivity(intent);
            } else {
                showNotAdminDialog();
            }
        }));
        update_Bus.setOnClickListener(view -> FirebaseUtils.checkForAdmin(isAdmin -> {
            if (isAdmin) {
                Intent intent = new Intent(AdminWork.this, UpdateBusTiming.class);
                startActivity(intent);
            } else {
                showNotAdminDialog();
            }
        }));
        add_faculty.setOnClickListener(view -> FirebaseUtils.checkForAdmin(isAdmin -> {
            if (isAdmin) {
                Intent intent = new Intent(AdminWork.this,  AddFaculty.class);
                startActivity(intent);
            } else {
                showNotAdminDialog();
            }
        }));
        add_staff.setOnClickListener(view -> FirebaseUtils.checkForAdmin(isAdmin -> {
            if (isAdmin) {
                Intent intent = new Intent(AdminWork.this, AddStaff.class);
                startActivity(intent);
            } else {
                showNotAdminDialog();
            }
        }));
        add_events.setOnClickListener(view -> FirebaseUtils.checkForAdmin(isAdmin -> {
            if (isAdmin) {
                Intent intent = new Intent(AdminWork.this,  AddEvents.class);
                startActivity(intent);
            } else {
                showNotAdminDialog();
            }
        }));
        add_fees.setOnClickListener(view -> FirebaseUtils.checkForAdmin(isAdmin -> {
            if (isAdmin) {
                Intent intent = new Intent(AdminWork.this, UploadFees.class);
                startActivity(intent);
                startActivity(intent);
            } else {
                showNotAdminDialog();
            }
        }));
        update_society.setOnClickListener(view -> FirebaseUtils.checkForAdmin(isAdmin -> {
            if (isAdmin) {
                Intent intent = new Intent(AdminWork.this, UpdateSociety.class);
                startActivity(intent);
            } else {
                showNotAdminDialog();
            }
        }));
        update_tnp.setOnClickListener(view -> FirebaseUtils.checkForAdmin(isAdmin -> {
            if (isAdmin) {
                Intent intent = new Intent(AdminWork.this, UpdateTNP.class);
                startActivity(intent);
            } else {
                showNotAdminDialog();
            }
        }));
    }

    private void showNotAdminDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!");
        builder.setMessage("You are not an Admin, to become an Admin please contact Developer.");

        builder.setPositiveButton("OK", (dialog, which) -> {
            // Close the AlertDialog or perform any other action
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}