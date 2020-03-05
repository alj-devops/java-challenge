package jp.co.axa.apidemo.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientEmployee {
    private String department;
    private Long id;
    private String name;
    private Integer salary;
}
