package com.nitmizoram.nitmz.ui.Home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.nitmizoram.nitmz.Anunaad;
import com.nitmizoram.nitmz.Assignment;
import com.nitmizoram.nitmz.Attendance;
import com.nitmizoram.nitmz.CSS_Society;
import com.nitmizoram.nitmz.CalenderU;
import com.nitmizoram.nitmz.Ce_dept;
import com.nitmizoram.nitmz.ChemistryDept;
import com.nitmizoram.nitmz.CseDept;
import com.nitmizoram.nitmz.Drishti;
import com.nitmizoram.nitmz.ECE_Society;
import com.nitmizoram.nitmz.EEE_Society;
import com.nitmizoram.nitmz.Ece_dept;
import com.nitmizoram.nitmz.Eee_dept;
import com.nitmizoram.nitmz.Events;
import com.nitmizoram.nitmz.Faculty;
import com.nitmizoram.nitmz.Fees;
import com.nitmizoram.nitmz.LibrarySelection;
import com.nitmizoram.nitmz.ME_Society;
import com.nitmizoram.nitmz.MainActivity;
import com.nitmizoram.nitmz.MathsDept;
import com.nitmizoram.nitmz.Me_dept;
import com.nitmizoram.nitmz.MessMenu;
import com.nitmizoram.nitmz.Morphosis;
import com.nitmizoram.nitmz.NitBuses;
import com.nitmizoram.nitmz.NotesShow;
import com.nitmizoram.nitmz.PhysicsDept;
import com.nitmizoram.nitmz.PyqShow;
import com.nitmizoram.nitmz.R;
import com.nitmizoram.nitmz.Shaurya;
import com.nitmizoram.nitmz.Staff;
import com.nitmizoram.nitmz.SyllabusShow;
import com.nitmizoram.nitmz.TimeTable;
import com.nitmizoram.nitmz.TnpCell;
import com.nitmizoram.nitmz.utils.FirebaseUtils;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private SliderLayout sliderLayout_top;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        

        sliderLayout_top = view.findViewById(R.id.slider_top);
        CardView timeTableCV = view.findViewById(R.id.timetableCV);
        CardView messMenuCV = view.findViewById(R.id.messMenuCV);
        CardView nitBusesCV = view.findViewById(R.id.nitBusesCV);
        CardView erplinkCV = view.findViewById(R.id.erpCV);
        CardView attendanceCV = view.findViewById(R.id.attendanceCV);
        CardView notesCV = view.findViewById(R.id.notesCV);
        CardView syllabusCV = view.findViewById(R.id.syllabusCV);
        CardView pyqsCV = view.findViewById(R.id.pyqsCV);
        CardView assignmentCV = view.findViewById(R.id.assignmentCV);
        CardView calenderCV = view.findViewById(R.id.calenderCV);
        CardView libraryCV = view.findViewById(R.id.libraryCV);
        CardView facultyCV = view.findViewById(R.id.facultyCV);
        CardView tnpCellCV = view.findViewById(R.id.tnpcellCV);
        CardView feesCV = view.findViewById(R.id.feesCV);
        CardView staffCV = view.findViewById(R.id.staffCV);
        CardView eventsCV = view.findViewById(R.id.eventsCV);
        CardView cssCV = view.findViewById(R.id.cssCV);
        CardView eeeCV = view.findViewById(R.id.eeeCV);
        CardView eceCV = view.findViewById(R.id.eceCV);
        CardView meCV = view.findViewById(R.id.meCV);
        CardView cseDeptCV = view.findViewById(R.id.CSEdpCV);
        CardView eceDeptCV = view.findViewById(R.id.ECEdpCV);
        CardView eeeDeptCV = view.findViewById(R.id.EEEdpCV);
        CardView meDeptCV = view.findViewById(R.id.MEdpCV);
        CardView ceDeptCV = view.findViewById(R.id.CEdpCV);
        CardView mathsDeptCV = view.findViewById(R.id.mathsdpCV);
        CardView phyDeptCV = view.findViewById(R.id.physicsCV);
        CardView chemDeptCV = view.findViewById(R.id.chemistryCV);
        CardView anunaadCV = view.findViewById(R.id.anunaadCV);
        CardView morphisisCV = view.findViewById(R.id.morphosisCV);
        CardView drishtiCV = view.findViewById(R.id.drishtiCV);
        CardView shauryaCV = view.findViewById(R.id.shauryaCV);




        ArrayList<SlideModel> imageList1 = new ArrayList<>(); // Create image list

// imageList.add(new SlideModel("String Url" or R.drawable)
// imageList.add(new SlideModel("String Url" or R.drawable, "title")); You can add title

        imageList1.add(new SlideModel(R.drawable.kanchan_shekhawat,"Kanchan Shekhawat\n  Amazon", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.saurav_saha, "Saurav Saha\n  BPCL", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.l_baisali_singla, "L Baisali Singla\nLarsen & Toubro", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.ankur_singh, "Ankur Singh\n Microsoft", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.bharti_rani, "Bharti Rani\n Vedanta", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.arjun_kumar, "Arjun Kumar\n Alstom", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.rajni_kant, "Rajni Kant\n Capgemini", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.biraj_singh, "Biraj Singh\n Deloitte", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.payal_kumari, "Payal Kumari\n Alstom", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.niraj_raut, "Niraj Raut\n C-DOT", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.dharamraj_gupta, "Dharamraj Gupta\n Accenture", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.amar_pratik, "Amar Pratik\n Oracle", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.dheeraj_singh, "Dheeraj Singh\n Amazon", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.akansha_shree, "Akanksha Shree\n Alstom", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.nikhil_singh, "Nikhil Singh\n Adobe", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.akash_singh, "Akash Singh\n Oracle", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.priya_singh, "Priya Singh\n Vedanta", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.bhargav_dinavahi, "Bhargav Dinavahi\n Deloitte", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.veera_venkata, "Veera Venkata\n Alstom", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.isha_singh, "Isha Singh\n C-DOT", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.sandeep_kumar, "Sandeep Kumar\n  IBM", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.shivam_raj, "Shivam Raj\n Alstom", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.akshay_maurya, "Akshay Maurya\n Vedanta", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.nishu_vats, "Nishu Vats\n Microsoft", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.nitish_kumar, "Nitish Kumar\n Accenture", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.gifty_abhisek, "Gifty Abhisek\n Alstom", ScaleTypes.CENTER_CROP));
        imageList1.add(new SlideModel(R.drawable.akash_r_thakur, "Akash R Thakur\n Accenture", ScaleTypes.CENTER_CROP));



        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList1);

        ArrayList<SlideModel> imageList2 = new ArrayList<>(); // Create image list

// imageList.add(new SlideModel("String Url" or R.drawable)
// imageList.add(new SlideModel("String Url" or R.drawable, "title")); You can add title

        imageList2.add(new SlideModel(R.drawable.lntlogo, ScaleTypes.CENTER_CROP));
        imageList2.add(new SlideModel(R.drawable.tmlogo, ScaleTypes.CENTER_CROP));
        imageList2.add(new SlideModel(R.drawable.allogo, ScaleTypes.CENTER_CROP));
        imageList2.add(new SlideModel(R.drawable.cdot, ScaleTypes.CENTER_CROP));
        imageList2.add(new SlideModel(R.drawable.intellectlogo, ScaleTypes.CENTER_CROP));
        imageList2.add(new SlideModel(R.drawable.jiologo, ScaleTypes.CENTER_CROP));
        imageList2.add(new SlideModel(R.drawable.alstomlogo, ScaleTypes.CENTER_CROP));
        imageList2.add(new SlideModel(R.drawable.deloittelogo, ScaleTypes.CENTER_CROP));
        imageList2.add(new SlideModel(R.drawable.mitcslogo, ScaleTypes.CENTER_CROP));
        imageList2.add(new SlideModel(R.drawable.indeedlogo, ScaleTypes.CENTER_CROP));
        imageList2.add(new SlideModel(R.drawable.bhellogo, ScaleTypes.CENTER_CROP));
        imageList2.add(new SlideModel(R.drawable.johnsonlogo, ScaleTypes.CENTER_CROP));
        imageList2.add(new SlideModel(R.drawable.josh, ScaleTypes.CENTER_CROP));
        imageList2.add(new SlideModel(R.drawable.indigologo, ScaleTypes.CENTER_CROP));


        ImageSlider imageSlider2 = view.findViewById(R.id.image_slider2);
        imageSlider2.setImageList(imageList2);


        timeTableCV.setOnClickListener(view1 -> FirebaseUtils.checkForSemester(isSemesterValid -> {
            if (isSemesterValid) {
                // Semester is valid, proceed with sending notice
                Intent intent = new Intent(getActivity(),TimeTable.class);
                startActivity(intent);
            } else {
                showInvalidSemesterDialog();
            }
        }));

        messMenuCV.setOnClickListener(view1 -> FirebaseUtils.checkForHostel(isHostelValid -> {

            if (isHostelValid) {
                // Semester is valid, proceed with sending notice
                Intent intent = new Intent(getActivity(), MessMenu.class);
                startActivity(intent);
            } else {
                showInvalidHostelDialog();
            }
        }));
        nitBusesCV.setOnClickListener(view1 -> FirebaseUtils.checkForHostel(isHostelValid -> {
            if (isHostelValid) {
                // Semester is valid, proceed with sending notice
                Intent intent = new Intent(getActivity(), NitBuses.class);
                startActivity(intent);
            } else {
                showInvalidHostelDialog();
            }
        }));
        erplinkCV.setOnClickListener(view12 -> {
            // Open the ERPlink website URL in a browser
            String url = "https://erp.nitmz.ac.in/iitmsv4eGq0RuNHb0G5WbhLmTKLmTO7YBcJ4RHuXxCNPvuIw=?enc=EGbCGWnlHNJ/WdgJnKH8DA==";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
        notesCV.setOnClickListener(view1 -> FirebaseUtils.checkForSemester(isSemesterValid -> {
            if (isSemesterValid) {
                // Semester is valid, proceed with sending notice
                Intent intent = new Intent(getActivity(), NotesShow.class);
                startActivity(intent);
            } else {
                showInvalidSemesterDialog();
            }
        }));
        syllabusCV.setOnClickListener(view1 -> FirebaseUtils.checkForSemester(isSemesterValid -> {
            if (isSemesterValid) {
                // Semester is valid, proceed with sending notice
                Intent intent = new Intent(getActivity(), SyllabusShow.class);
                startActivity(intent);
            } else {
                showInvalidSemesterDialog();
            }
        }));
        pyqsCV.setOnClickListener(view1 -> FirebaseUtils.checkForSemester(isSemesterValid -> {
            if (isSemesterValid) {
                // Semester is valid, proceed with sending notice
                Intent intent = new Intent(getActivity(), PyqShow.class);
                startActivity(intent);
            } else {
                showInvalidSemesterDialog();
            }
        }));
        attendanceCV.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), Attendance.class);
            startActivity(intent);
        });
        assignmentCV.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), Assignment.class);
            startActivity(intent);
        });
        calenderCV.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), CalenderU.class);
            startActivity(intent);
        });
        libraryCV.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), LibrarySelection.class);
            startActivity(intent);
        });
        feesCV.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), Fees.class);
            startActivity(intent);
        });
        facultyCV.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), Faculty.class);
            startActivity(intent);
        });
        tnpCellCV.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), TnpCell.class);
            startActivity(intent);
        });
        staffCV.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), Staff.class);
            startActivity(intent);
        });
        eventsCV.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), Events.class);
            startActivity(intent);
        });
        cssCV.setOnClickListener(view13 -> {
            Intent intent = new Intent(getActivity(), CSS_Society.class);
            startActivity(intent);
        });

        eeeCV.setOnClickListener(view15 -> {
            Intent intent = new Intent(getActivity(), EEE_Society.class);
            startActivity(intent);
        });
        eceCV.setOnClickListener(view15 -> {
            Intent intent = new Intent(getActivity(), ECE_Society.class);
            startActivity(intent);
        });

        meCV.setOnClickListener(view16 -> {
            Intent intent = new Intent(getActivity(), ME_Society.class);
            startActivity(intent);
        });
        cseDeptCV.setOnClickListener(view16 -> {
            Intent intent = new Intent(getActivity(), CseDept.class);
            startActivity(intent);
        });
        eceDeptCV.setOnClickListener(view16 -> {
            Intent intent = new Intent(getActivity(), Ece_dept.class);
            startActivity(intent);
        });
        eeeDeptCV.setOnClickListener(view16 -> {
            Intent intent = new Intent(getActivity(), Eee_dept.class);
            startActivity(intent);
        });
        meDeptCV.setOnClickListener(view16 -> {
            Intent intent = new Intent(getActivity(), Me_dept.class);
            startActivity(intent);
        });
        ceDeptCV.setOnClickListener(view16 -> {
            Intent intent = new Intent(getActivity(), Ce_dept.class);
            startActivity(intent);
        });
        mathsDeptCV.setOnClickListener(view16 -> {
            Intent intent = new Intent(getActivity(), MathsDept.class);
            startActivity(intent);
        });
        phyDeptCV.setOnClickListener(view16 -> {
            Intent intent = new Intent(getActivity(), PhysicsDept.class);
            startActivity(intent);
        });
        chemDeptCV.setOnClickListener(view16 -> {
            Intent intent = new Intent(getActivity(), ChemistryDept.class);
            startActivity(intent);
        });
        anunaadCV.setOnClickListener(view16 -> {
            Intent intent = new Intent(getActivity(), Anunaad.class);
            startActivity(intent);
        });
        morphisisCV.setOnClickListener(view16 -> {
            Intent intent = new Intent(getActivity(), Morphosis.class);
            startActivity(intent);
        });
        drishtiCV.setOnClickListener(view16 -> {
            Intent intent = new Intent(getActivity(), Drishti.class);
            startActivity(intent);
        });
        shauryaCV.setOnClickListener(view16 -> {
            Intent intent = new Intent(getActivity(), Shaurya.class);
            startActivity(intent);
        });



        sliderLayout_top.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout_top.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderLayout_top.setScrollTimeInSec(1);

        setSliderViewsTop();

        return view;
    }

    private void showInvalidHostelDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Alert!");
        builder.setCancelable(false);
        builder.setMessage("Please Update Your Hostel From Profile Section");

        builder.setPositiveButton("OK", (dialog, which) -> {
            // Close the AlertDialog or perform any other action
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showInvalidSemesterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Alert!");
        builder.setCancelable(false);
        builder.setMessage("Please Update Your Semester From Profile Section");

        builder.setPositiveButton("OK", (dialog, which) -> {
            // Close the AlertDialog or perform any other action
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setSliderViewsTop() {
        for (int i = 0; i < 4; i++) {
            DefaultSliderView sliderView = new DefaultSliderView(getContext());

            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.banner1);
                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.banner2);
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.banner3);
                    break;
                case 3:
                    sliderView.setImageDrawable(R.drawable.banner4);
                    break;
            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

            sliderLayout_top.addSliderView(sliderView);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) requireActivity()).setToolbarTitle("Dashboard");

    }
}
