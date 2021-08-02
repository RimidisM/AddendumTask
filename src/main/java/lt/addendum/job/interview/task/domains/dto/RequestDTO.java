package lt.addendum.job.interview.task.domains.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RequestDTO {

    @NotEmpty(message = "Name is mandatory")
    private String name;
}
