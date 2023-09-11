package works.itireland.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicStatisticsResponse {
    private String title;
    private float value;
    private float diff;
}
