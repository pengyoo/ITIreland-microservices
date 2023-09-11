package works.itireland.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import works.itireland.dto.BasicStatisticsResponse;
import works.itireland.dto.LinearStatisticsDataResponse;
import works.itireland.dto.LinearStatisticsResponse;
import works.itireland.service.StatisticsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/stats")
@AllArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping
    public List<BasicStatisticsResponse> getAll() {
        return statisticsService.getAll();
    }

    @GetMapping("/linear")
    public LinearStatisticsResponse mock() {
        LinearStatisticsDataResponse data1 =
                new LinearStatisticsDataResponse("Users", new float[]{12, 18, 19, 20, 34, 45, 32, 23, 56}, 0.4, "#748FFC", "#748FFC");
        LinearStatisticsDataResponse data2 =
                new LinearStatisticsDataResponse("Posts", new float[]{30, 45, 33, 20, 33, 54, 35, 32, 22}, 0.4, "#0891b2", "#0891b2");
        LinearStatisticsResponse list = new LinearStatisticsResponse();
        list.getDatasets().add(data1);
        list.getDatasets().add(data2);
        return list;
    }
}