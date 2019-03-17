package com.hziee.jinli.vo;

import com.hziee.jinli.pojo.DailyRevenueLogs;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LineChartData {
    private List<String> categories;
    private List<Double> data;

    public LineChartData(List<DailyRevenueLogs> dailyRevenueLogsList){
        this.categories = new ArrayList<>();
        this.data = new ArrayList<>();
        for(int i = 0;i < dailyRevenueLogsList.size();i++){
            this.categories.add(dailyRevenueLogsList.get(i).getDate());
            this.data.add(dailyRevenueLogsList.get(i).getSum());
        }
    }
}
