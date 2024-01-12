package com.example.nitmz;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.nitmz.AdminSection.AddEvents;
import com.example.nitmz.AdminSection.AddFaculty;
import com.example.nitmz.AdminSection.AddStaff;
import com.example.nitmz.AdminSection.UpdateBusTiming;
import com.example.nitmz.AdminSection.UpdateMessMenu;
import com.example.nitmz.AdminSection.UpdateTimeTable;
import com.example.nitmz.AdminSection.UploadBooks;
import com.example.nitmz.AdminSection.UploadCalender;
import com.example.nitmz.AdminSection.UploadFees;
import com.example.nitmz.AdminSection.UploadNotes;
import com.example.nitmz.AdminSection.UploadNotice;
import com.example.nitmz.AdminSection.UploadPyqs;
import com.example.nitmz.AdminSection.UploadSyllabus;
import com.example.nitmz.utils.FirebaseUtils;

public class AdminWork extends AppCompatActivity {

    private CardView update_TT;
    private CardView update_MM;
    private CardView uploadNotes;
    private CardView uploadSyllabus;
    private CardView uploadPyqs;
    private CardView uploadBooks;
    private CardView uploadCalender;
    private CardView update_Bus;
    private CardView send_notice;

    private CardView add_faculty;

    private CardView add_staff;

    private CardView add_events;

    private CardView add_fees;

    private CardView update_society;

    private CardView update_tnp;
    private ImageButton back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_work);



        back = findViewById(R.id.backbtn);
        update_TT = findViewById(R.id.UpdateTT);
        update_MM = findViewById(R.id.UpdateMM);
        uploadNotes = findViewById(R.id.Upload_Notes);
        uploadSyllabus =findViewById(R.id.UpdateSyllabus);
        uploadPyqs = findViewById(R.id.UpdatePyqs);
        uploadBooks =findViewById(R.id.UpdateBooks);
        uploadCalender = findViewById(R.id.UpdateCalender);
        update_Bus = findViewById(R.id.UpdateBus);
        send_notice = findViewById(R.id.SendNotification);
        add_events = findViewById(R.id.Add_Events);
        add_faculty = findViewById(R.id.Add_Faculty);
        add_staff = findViewById(R.id.Add_Staff);
        add_fees =findViewById(R.id.Add_Fees);
        update_society = findViewById(R.id.Update_society);
        update_tnp = findViewById(R.id.Update_TNP);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        send_notice.setOnClickListener(view -> {
            FirebaseUtils.checkForAdmin(isAdmin -> {
                if (isAdmin) {
                    Intent intent = new Intent(AdminWork.this, UploadNotice.class);
                    startActivity(intent);
                } else {
                    showNotAdminDialog();
                }
            });
        });

        update_TT.setOnClickListener(view -> {
            FirebaseUtils.checkForAdmin(isAdmin -> {
                if (isAdmin) {
                    Intent intent = new Intent(AdminWork.this, UpdateTimeTable.class);
                    startActivity(intent);
                } else {
                    showNotAdminDialog();
                }
            });
        });

        update_MM.setOnClickListener(view -> {
            FirebaseUtils.checkForAdmin(isAdmin -> {
                if (isAdmin) {
                    Intent intent = new Intent(AdminWork.this, UpdateMessMenu.class);
                    startActivity(intent);
                } else {
                    showNotAdminDialog();
                }
            });
        });

        uploadNotes.setOnClickListener(view -> {
            FirebaseUtils.checkForAdmin(isAdmin -> {
                if (isAdmin) {
                    Intent intent = new Intent(AdminWork.this, UploadNotes.class);
                    startActivity(intent);
                } else {
                    showNotAdminDialog();
                }
            });
        });
        uploadSyllabus.setOnClickListener(view -> {
            FirebaseUtils.checkForAdmin(isAdmin -> {
                if (isAdmin) {
                    Intent intent = new Intent(AdminWork.this, UploadSyllabus.class);
                    startActivity(intent);
                } else {
                    showNotAdminDialog();
                }
            });
        });
        uploadPyqs.setOnClickListener(view -> {
            FirebaseUtils.checkForAdmin(isAdmin -> {
                if (isAdmin) {
                    Intent intent = new Intent(AdminWork.this, UploadPyqs.class);
                    startActivity(intent);
                } else {
                    showNotAdminDialog();
                }
            });
        });
        uploadBooks.setOnClickListener(view -> {
            FirebaseUtils.checkForAdmin(isAdmin -> {
                if (isAdmin) {
                    Intent intent = new Intent(AdminWork.this, UploadBooks.class);
                    startActivity(intent);
                } else {
                    showNotAdminDialog();
                }
            });
        });
        uploadCalender.setOnClickListener(view -> {
            FirebaseUtils.checkForAdmin(isAdmin -> {
                if (isAdmin) {
                    Intent intent = new Intent(AdminWork.this, UploadCalender.class);
                    startActivity(intent);
                } else {
                    showNotAdminDialog();
                }
            });
        });
        update_Bus.setOnClickListener(view -> {
            FirebaseUtils.checkForAdmin(isAdmin -> {
                if (isAdmin) {
                    Intent intent = new Intent(AdminWork.this, UpdateBusTiming.class);
                    startActivity(intent);
                } else {
                    showNotAdminDialog();
                }
            });
        });
        add_faculty.setOnClickListener(view -> {
            FirebaseUtils.checkForAdmin(isAdmin -> {
                if (isAdmin) {
                    Intent intent = new Intent(AdminWork.this,  AddFaculty.class);
                    startActivity(intent);
                } else {
                    showNotAdminDialog();
                }
            });
        });
        add_staff.setOnClickListener(view -> {
            FirebaseUtils.checkForAdmin(isAdmin -> {
                if (isAdmin) {
                    Intent intent = new Intent(AdminWork.this, AddStaff.class);
                    startActivity(intent);
                } else {
                    showNotAdminDialog();
                }
            });
        });
        add_events.setOnClickListener(view -> {
            FirebaseUtils.checkForAdmin(isAdmin -> {
                if (isAdmin) {
                    Intent intent = new Intent(AdminWork.this,  AddEvents.class);
                    startActivity(intent);
                } else {
                    showNotAdminDialog();
                }
            });
        });
        add_fees.setOnClickListener(view -> {
            FirebaseUtils.checkForAdmin(isAdmin -> {
                if (isAdmin) {
                    Intent intent = new Intent(AdminWork.this, UploadFees.class);
                    startActivity(intent);
                    startActivity(intent);
                } else {
                    showNotAdminDialog();
                }
            });
        });
        update_society.setOnClickListener(view -> {
            FirebaseUtils.checkForAdmin(isAdmin -> {
                if (isAdmin) {
                    Intent intent = new Intent(AdminWork.this, UpdateSociety.class);
                    startActivity(intent);
                } else {
                    showNotAdminDialog();
                }
            });
        });
        update_tnp.setOnClickListener(view -> {
            FirebaseUtils.checkForAdmin(isAdmin -> {
                if (isAdmin) {
                    Intent intent = new Intent(AdminWork.this, UpdateTNP.class);
                    startActivity(intent);
                } else {
                    showNotAdminDialog();
                }
            });
        });
    }

    private void showNotAdminDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!");
        builder.setMessage("Sorry...You are not an Admin, to become an Admin please contact the Developer.");

        builder.setPositiveButton("OK", (dialog, which) -> {
            // Close the AlertDialog or perform any other action
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}