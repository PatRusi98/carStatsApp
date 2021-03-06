package com.rusinak.carstat.ui.stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rusinak.carstat.Calculations;
import com.rusinak.carstat.MainActivity;
import com.rusinak.carstat.R;

import java.util.ArrayList;
import java.util.List;

public class StatsFragment extends Fragment {
    private StatsViewModel statsViewModel;

    RecyclerView recyclerView;
    StatsAdapter statsAdapter;
    Calculations calculations = new Calculations();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statsViewModel =
                new ViewModelProvider(this).get(StatsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stats, container, false);
        statsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        recyclerView = root.findViewById(R.id.statsRefuel);
        setRecyclerView(listRefuel());
        recyclerView = root.findViewById(R.id.statsMaint);
        setRecyclerView(listMaint());
        recyclerView = root.findViewById(R.id.statsInsp);
        setRecyclerView(listInsp());
        recyclerView = root.findViewById(R.id.statsRepair);
        setRecyclerView(listRepairs());
        recyclerView = root.findViewById(R.id.statsAll);
        setRecyclerView(listAll());
        return root;
    }

    /**
     *
     * sets recycle view for statistics
     *
     * @param model list of statistics
     */
    private void setRecyclerView(List<StatsModel> model) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        statsAdapter = new StatsAdapter(getContext(), model);
        recyclerView.setAdapter(statsAdapter);
    }

    /**
     *
     * method that returns list of statistics related to refuelling
     *
     * @return returns list of statistics related to refuelling
     */
    private List<StatsModel> listRefuel() {
        List<StatsModel> listStat = new ArrayList<>();
        listStat.add(new StatsModel("", ""));
        listStat.add(new StatsModel("Posl. priemern?? spotreba" , calculations.averageConsCur(((MainActivity)getActivity()).getDBHelper()) + " l/100km"));
        listStat.add(new StatsModel("Celk. priemern?? spotreba" , calculations.averageConsAver(((MainActivity)getActivity()).getDBHelper()) + " l/100km"));
        listStat.add(new StatsModel("Posl. cena za liter" , calculations.costLitreCur(((MainActivity)getActivity()).getDBHelper()) + " ???/liter"));
        listStat.add(new StatsModel("Celk. priemern?? cena za liter" , calculations.costLitreAver(((MainActivity)getActivity()).getDBHelper()) + " ???/liter"));
        listStat.add(new StatsModel("Celk. utraten?? suma na PHM" , calculations.totalRefuelCost(((MainActivity)getActivity()).getDBHelper()) + " ???"));
        listStat.add(new StatsModel("Posl. cena za KM" , calculations.pricePerKMLast(((MainActivity)getActivity()).getDBHelper()) + " ???/km"));
        listStat.add(new StatsModel("Celk. cena za KM" , calculations.pricePerKMAver(((MainActivity)getActivity()).getDBHelper()) + " ???/km"));
        listStat.add(new StatsModel("", ""));
        return listStat;
    }

    /**
     *
     * method that returns list of statistics related to maintainance
     *
     * @return returns list of statistics related to maintainance
     */
    private List<StatsModel> listMaint() {
        List<StatsModel> listStat = new ArrayList<>();
        listStat.add(new StatsModel("", ""));
        listStat.add(new StatsModel("Posl. v??mena oleja" , calculations.lastOil(((MainActivity)getActivity()).getDBHelper())));
        listStat.add(new StatsModel("Posl. z??ru??n?? prehliadka" , calculations.lastWarranty(((MainActivity)getActivity()).getDBHelper())));
        listStat.add(new StatsModel("Celk. utraten?? suma na ??dr??bu" , calculations.totalMaintCost(((MainActivity)getActivity()).getDBHelper()) + " ???"));;
        listStat.add(new StatsModel("", ""));
        return listStat;
    }

    /**
     *
     * method that returns list of statistics related to inspections
     *
     * @return returns list of statistics related to inspections
     */
    private List<StatsModel> listInsp() {
        List<StatsModel> listStat = new ArrayList<>();
        listStat.add(new StatsModel("", ""));
        listStat.add(new StatsModel("Posl. technick?? kontrola" , calculations.lastInspection(((MainActivity)getActivity()).getDBHelper())));
        listStat.add(new StatsModel("Posl. emisn?? kontrola" , calculations.lastEmisInspection(((MainActivity)getActivity()).getDBHelper())));
        listStat.add(new StatsModel("Posl. kontrola originality" , calculations.lastOrigControl(((MainActivity)getActivity()).getDBHelper())));
        listStat.add(new StatsModel("", ""));
        return listStat;
    }

    /**
     *
     * method that returns list of statistics related to repairs
     *
     * @return returns list of statistics related to repairs
     */
    private List<StatsModel> listRepairs() {
        List<StatsModel> listStat = new ArrayList<>();
        listStat.add(new StatsModel("", ""));
        listStat.add(new StatsModel("Celkov?? utraten?? suma na opravy" , calculations.totalRepairCost(((MainActivity)getActivity()).getDBHelper()) + " ???"));
        listStat.add(new StatsModel("D??tum poslednej opravy" , calculations.lastRepair(((MainActivity)getActivity()).getDBHelper())));
        listStat.add(new StatsModel("", ""));
        return listStat;
    }

    /**
     *
     * method that returns list of statistics related to car
     *
     * @return returns list of statistics related to car
     */
    private List<StatsModel> listAll() {
        List<StatsModel> listStat = new ArrayList<>();
        listStat.add(new StatsModel("", ""));
        listStat.add(new StatsModel("Celkov?? suma utraten?? na auto" , calculations.totalCostAll(((MainActivity)getActivity()).getDBHelper()) + " ???"));
        listStat.add(new StatsModel("", ""));
        return listStat;
    }
}
