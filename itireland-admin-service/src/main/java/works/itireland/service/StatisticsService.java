package works.itireland.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import works.itireland.dto.BasicStatisticsResponse;
import works.itireland.clients.post.CommentClient;
import works.itireland.clients.post.PostClient;
import works.itireland.clients.user.UserClient;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StatisticsService {
    private final UserClient userClient;
    private final PostClient postClient;
    private final CommentClient commentClient;

    public List<BasicStatisticsResponse> getAll() {
        List<BasicStatisticsResponse> statisticsResponses = new ArrayList<>();
        Long userCount = userClient.count();
        BasicStatisticsResponse statisticsResponse = new BasicStatisticsResponse("Users", userCount, 12);
        statisticsResponses.add(statisticsResponse);
        long postCount = postClient.count();
        statisticsResponse = new BasicStatisticsResponse("Posts", postCount, 5);
        statisticsResponses.add(statisticsResponse);
        long commentCount = commentClient.count();
        statisticsResponse = new BasicStatisticsResponse("Comments", commentCount, 20);
        statisticsResponses.add(statisticsResponse);
        return statisticsResponses;
    }
}
