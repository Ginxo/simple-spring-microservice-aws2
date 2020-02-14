package mingorance.cano.simplespringmicroserviceaws2.resource.criteria;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import mingorance.cano.simplespringmicroserviceaws2.resource.service.filter.LongFilter;
import mingorance.cano.simplespringmicroserviceaws2.resource.service.filter.StringFilter;
import mingorance.cano.simplespringmicroserviceaws2.resource.service.filter.ZonedDateTimeFilter;

@Getter
@Setter
public class ProcessCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringFilter name;
    private ZonedDateTimeFilter startingDate;
}
