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
        View root = inflater.inflate(R.layout.fragment_statistiky, container, false);
        //final TextView textView = root.findViewById(R.id.text_statistiky);
        statsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            //    textView.setText(s);
            }
        });

        recyclerView = root.findViewById(R.id.statistikyTank);
        setRecyclerView(listRefuel());
        recyclerView = root.findViewById(R.id.statistikyUdrzba);
        setRecyclerView(listMaint());
        recyclerView = root.findViewById(R.id.statistikySTK);
        setRecyclerView(listInsp());
        recyclerView = root.findViewById(R.id.statistikyOpravy);
        setRecyclerView(listRepairs());
        recyclerView = root.findViewById(R.id.statistikyVseob);
        setRecyclerView(listAll());
        return root;
    }

    private void setRecyclerView(List<StatsModel> model) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        statsAdapter = new StatsAdapter(getContext(), model);
        recyclerView.setAdapter(statsAdapter);
    }

    private List<StatsModel> listRefuel() {
        List<StatsModel> listStat = new ArrayList<>();
        listStat.add(new StatsModel("", ""));
        listStat.add(new StatsModel("Posl. priemerná spotreba" , calculations.averageConsCur(((MainActivity)getActivity()).getDBHelper()) + " l/100km"));
        listStat.add(new StatsModel("Celk. priemerná spotreba" , calculations.averageConsAver(((MainActivity)getActivity()).getDBHelper()) + " l/100km"));
        listStat.add(new StatsModel("Posl. cena za liter" , calculations.costLitreCur(((MainActivity)getActivity()).getDBHelper()) + " €/liter"));
        listStat.add(new StatsModel("Celk. priemerná cena za liter" , calculations.costLitreAver(((MainActivity)getActivity()).getDBHelper()) + " €/liter"));
        listStat.add(new StatsModel("Celk. utratená suma na PHM" , calculations.totalRefuelCost(((MainActivity)getActivity()).getDBHelper()) + " €"));
        listStat.add(new StatsModel("Posl. cena za KM" , calculations.pricePerKMLast(((MainActivity)getActivity()).getDBHelper()) + " €/km"));
        listStat.add(new StatsModel("Celk. cena za KM" , calculations.pricePerKMAver(((MainActivity)getActivity()).getDBHelper()) + " €/km"));
        listStat.add(new StatsModel("", ""));
        return listStat;
    }

    private List<StatsModel> listMaint() {
        List<StatsModel> listStat = new ArrayList<>();
        listStat.add(new StatsModel("", ""));
        listStat.add(new StatsModel("Posl. výmena oleja" , calculations.lastOil(((MainActivity)getActivity()).getDBHelper())));
        listStat.add(new StatsModel("Posl. záručná prehliadka" , calculations.lastWarranty(((MainActivity)getActivity()).getDBHelper())));
        listStat.add(new StatsModel("Celk. utratená suma na údržbu" , calculations.totalMaintCost(((MainActivity)getActivity()).getDBHelper()) + " €"));;
        listStat.add(new StatsModel("", ""));
        return listStat;
    }

    private List<StatsModel> listInsp() {
        List<StatsModel> listStat = new ArrayList<>();
        listStat.add(new StatsModel("", ""));
        listStat.add(new StatsModel("Posl. technická kontrola" , calculations.lastInspection(((MainActivity)getActivity()).getDBHelper())));
        listStat.add(new StatsModel("Posl. emisná kontrola" , calculations.lastEmisInspection(((MainActivity)getActivity()).getDBHelper())));
        listStat.add(new StatsModel("Posl. kontrola originality" , calculations.lastOrigControl(((MainActivity)getActivity()).getDBHelper())));
        listStat.add(new StatsModel("", ""));
        return listStat;
    }

    private List<StatsModel> listRepairs() {
        List<StatsModel> listStat = new ArrayList<>();
        listStat.add(new StatsModel("", ""));
        listStat.add(new StatsModel("Celková utratená suma na opravy" , calculations.totalRepairCost(((MainActivity)getActivity()).getDBHelper()) + " €"));
        listStat.add(new StatsModel("Dátum poslednej opravy" , calculations.lastRepair(((MainActivity)getActivity()).getDBHelper())));
        listStat.add(new StatsModel("", ""));
        return listStat;
    }

    private List<StatsModel> listAll() {
        List<StatsModel> listStat = new ArrayList<>();
        listStat.add(new StatsModel("", ""));
        listStat.add(new StatsModel("Celková suma utratená na auto" , calculations.totalCostAll(((MainActivity)getActivity()).getDBHelper()) + " €"));
        listStat.add(new StatsModel("", ""));
        return listStat;
    }
}
