package lt.addendum.job.interview.task.domains.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RequestDTO {

    @NotEmpty(message = "Name is mandatory")
    @Size(max = 50, message = "Your name is too long :)")
    private String name;
}
