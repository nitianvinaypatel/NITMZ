package com.example.nitmz.ui.Home;

import android.annotation.SuppressLint;
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
import com.example.nitmz.Anunaad;
import com.example.nitmz.Attendance;
import com.example.nitmz.CSS_Society;
import com.example.nitmz.CalenderU;
import com.example.nitmz.Ce_dept;
import com.example.nitmz.ChemistryDept;
import com.example.nitmz.Assignment;
import com.example.nitmz.CseDept;
import com.example.nitmz.Drishti;
import com.example.nitmz.ECE_Society;
import com.example.nitmz.EEE_Society;
import com.example.nitmz.Ece_dept;
import com.example.nitmz.Eee_dept;
import com.example.nitmz.Events;
import com.example.nitmz.Faculty;
import com.example.nitmz.Fees;
import com.example.nitmz.LibrarySelection;
import com.example.nitmz.ME_Society;
import com.example.nitmz.MainActivity;
import com.example.nitmz.MathsDept;
import com.example.nitmz.Me_dept;
import com.example.nitmz.MessMenu;
import com.example.nitmz.Morphosis;
import com.example.nitmz.NitBuses;
import com.example.nitmz.NotesSelection;
import com.example.nitmz.PhysicsDept;
import com.example.nitmz.PyqSelection;
import com.example.nitmz.R;
import com.example.nitmz.Shaurya;
import com.example.nitmz.Staff;
import com.example.nitmz.SyllabusSelection;
import com.example.nitmz.TimeTable;
import com.example.nitmz.TnpCell;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private SliderLayout sliderLayout_top;

    private CardView timeTableCV;
    private CardView erplinkCV;
    private CardView nitBusesCV;
    private   CardView messMenuCV;
    private CardView cssCV;
    private CardView eeeCV;
    private CardView eceCV;
    private CardView meCV;
    private CardView attendanceCV;

    private CardView syllabusCV;
    private CardView notesCV;
    private CardView pyqsCV;
    private CardView assignmentCV;
    private CardView calenderCV;
    private CardView libraryCV;
    private  CardView facultyCV;
    private CardView tnpCellCV;
    private  CardView feesCV;
    private  CardView staffCV;
    private CardView eventsCV;
    private CardView cseDeptCV;
    private CardView eeeDeptCV;
    private CardView eceDeptCV;
    private CardView meDeptCV;
    private CardView ceDeptCV;
    private CardView mathsDeptCV;
    private CardView phyDeptCV;
    private CardView chemDeptCV;
    private CardView anunaadCV;
    private CardView morphisisCV;
    private CardView drishtiCV;
    private CardView shauryaCV;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sliderLayout_top = view.findViewById(R.id.slider_top);
        timeTableCV = view.findViewById(R.id.timetableCV);
        messMenuCV = view.findViewById(R.id.messMenuCV);
        nitBusesCV = view.findViewById(R.id.nitBusesCV);
        erplinkCV = view.findViewById(R.id.erpCV);
        attendanceCV = view.findViewById(R.id.attendanceCV);
        notesCV = view.findViewById(R.id.notesCV);
        syllabusCV = view.findViewById(R.id.syllabusCV);
        pyqsCV = view.findViewById(R.id.pyqsCV);
        assignmentCV = view.findViewById(R.id.assignmentCV);
        calenderCV = view.findViewById(R.id.calenderCV);
        libraryCV = view.findViewById(R.id.libraryCV);
        facultyCV = view.findViewById(R.id.facultyCV);
        tnpCellCV = view.findViewById(R.id.tnpcellCV);
        feesCV = view.findViewById(R.id.feesCV);
        staffCV = view.findViewById(R.id.staffCV);
        eventsCV = view.findViewById(R.id.eventsCV);
        cssCV = view.findViewById(R.id.cssCV);
        eeeCV = view.findViewById(R.id.eeeCV);
        eceCV = view.findViewById(R.id.eceCV);
        meCV = view.findViewById(R.id.meCV);
        cseDeptCV = view.findViewById(R.id.CSEdpCV);
        eceDeptCV = view.findViewById(R.id.ECEdpCV);
        eeeDeptCV = view.findViewById(R.id.EEEdpCV);
        meDeptCV = view.findViewById(R.id.MEdpCV);
        ceDeptCV = view.findViewById(R.id.CEdpCV);
        mathsDeptCV = view.findViewById(R.id.mathsdpCV);
        phyDeptCV = view.findViewById(R.id.physicsCV);
        chemDeptCV = view.findViewById(R.id.chemistryCV);
        anunaadCV = view.findViewById(R.id.anunaadCV);
        morphisisCV = view.findViewById(R.id.morphosisCV);
        drishtiCV = view.findViewById(R.id.drishtiCV);
        shauryaCV = view.findViewById(R.id.shauryaCV);




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


        timeTableCV.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), TimeTable.class);
            startActivity(intent);
        });
        messMenuCV.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), MessMenu.class);
            startActivity(intent);
        });
        nitBusesCV.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), NitBuses.class);
            startActivity(intent);
        });
        erplinkCV.setOnClickListener(view12 -> {
            // Open the ERPlink website URL in a browser
            String url = "https://erp.nitmz.ac.in/iitmsv4eGq0RuNHb0G5WbhLmTKLmTO7YBcJ4RHuXxCNPvuIw=?enc=EGbCGWnlHNJ/WdgJnKH8DA==";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
        notesCV.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), NotesSelection.class);
            startActivity(intent);
        });
        syllabusCV.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), SyllabusSelection.class);
            startActivity(intent);
        });
        pyqsCV.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), PyqSelection.class);
            startActivity(intent);
        });
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
