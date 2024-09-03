package com.adobe.aem.guides.wknd.core.models.impl;

import java.util.List;

import com.adobe.aem.guides.wknd.core.models.Byline;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;

import com.adobe.cq.wcm.core.components.models.Image;
import org.apache.sling.models.factory.ModelFactory;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import javax.annotation.PostConstruct;

@Model(
adaptables = {SlingHttpServletRequest.class},
adapters = {Byline.class},
resourceType = {BylineImpl.RESOURCE_TYPE},
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class BylineImpl implements Byline {
protected static final String RESOURCE_TYPE = "wknd/components/byline";

@OSGiService
private ModelFactory modelFactory;

@Self
private SlingHttpServletRequest request;


@ValueMapValue
private String name;

@Override
public String getName() {
return name;
}

@ValueMapValue
private List<String> occupations;

@Override
public List<String> getOccupations() {
    

if (occupations != null) {
    Collections.sort(occupations);
    return new ArrayList<String>(occupations);
} else {
    return Collections.emptyList();
}
}




@Override
public boolean isEmpty() {
final Image componentImage = getImage();
if (StringUtils.isBlank(name)) {
    // Name is missing, but required
    return true;
} else if (occupations == null || occupations.isEmpty()) {
    // At least one occupation is required
    return true;
} else if (componentImage == null || StringUtils.isBlank(componentImage.getSrc())) {
    // A valid image is required
    return true;
} else {
    // Everything is populated, so this component is not considered empty
    return false;
}
}

private Image image;
@PostConstruct
private void init() {
image = modelFactory.getModelFromWrappedRequest(request,
                                                request.getResource(),
                                                Image.class);
}

private Image getImage() {

return image;
}

}





