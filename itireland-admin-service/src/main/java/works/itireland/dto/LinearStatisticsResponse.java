package works.itireland.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinearStatisticsResponse {
    private String[] labels = new String[]{
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September"
    };
    private List<LinearStatisticsDataResponse> datasets = new ArrayList<>();
}
