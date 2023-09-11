package works.itireland.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinearStatisticsDataResponse {
    private String label;
    private float[] data;
    private double tension = 0.4;
    private String borderColor = "#0891b2";
    private String backgroundColor = "#0891b2";

}
