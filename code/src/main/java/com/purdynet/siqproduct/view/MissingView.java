package com.purdynet.siqproduct.view;

import com.purdynet.siqproduct.model.MissingItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MissingView extends AbstractView {
    public String makeTable(List<MissingItem> missingItems) {
        return wrapHtmlBody(toHTMLTableFromMising(missingItems));
    }
}
