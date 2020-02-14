package mingorance.cano.simplespringmicroserviceaws2.resource.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessDTO {

    @Null(message = "ID can't be set")
    private Long id;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Surname cannot be null")
    private String surname;
}
