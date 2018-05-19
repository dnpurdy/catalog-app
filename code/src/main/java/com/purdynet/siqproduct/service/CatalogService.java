package com.purdynet.siqproduct.service;

import com.purdynet.siqproduct.biqquery.BigqueryUtils;
import com.purdynet.siqproduct.model.CatalogItem;
import org.apache.commons.text.similarity.HammingDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.purdynet.siqproduct.biqquery.BigqueryUtils.convertTableRowToModel;
import static org.apache.commons.lang3.StringUtils.rightPad;

@Service
public class CatalogService {
    private static final String CATALOG_SELECT_SQL = "SELECT * FROM [swiftiq-master:siq.Catalog]";
    private static final int TRY_AGAIN_FOR_MORE = 5;
    private static final int MAX_RETURN = 15;

    private List<CatalogItem> catalog;

    @Autowired
    public CatalogService() {
        this.catalog = new ArrayList<>();
    }

    public List<CatalogItem> getCatalog() {
        return catalog;
    }

    public List<CatalogItem> genNearMatches(final String upc) {
        return genNearMatches(upc, upc.length(), new ArrayList<>());
    }

    private List<CatalogItem> genNearMatches(final String upc, final int length, final List<CatalogItem> bestGuess) {
        if (length == 2) return sort(bestGuess, upc);
        else {
            String partial = upc.substring(0,length);
            List<CatalogItem> nextBestGuess = getCatalogPartialItemId(partial);
            if (nextBestGuess.size() < TRY_AGAIN_FOR_MORE) {
                return genNearMatches(upc, length-1, nextBestGuess);
            } else {
                return sort(nextBestGuess, upc);
            }
        }
    }

    private List<CatalogItem> sort(final List<CatalogItem> in, final String upc) {
        Comparator<CatalogItem> hammingComparator = (c1, c2) -> {
            Integer c1D = hamming(c1.getItemId(), upc);
            Integer c2D = hamming(c2.getItemId(), upc);
            return c1D.compareTo(c2D);
        };
        Comparator<CatalogItem> linearComparator = (c1, c2) -> {
            BigInteger c1D = linear(c1.getItemId(), upc);
            BigInteger c2D = linear(c2.getItemId(), upc);
            return c1D.compareTo(c2D);
        };

        return in.stream().sorted(linearComparator).limit(MAX_RETURN).collect(Collectors.toList());
    }

    public List<CatalogItem> getCatalogPartialItemId(final String partial) {
        return catalog.stream().filter(ci -> ci.getItemId().startsWith(partial)).collect(Collectors.toList());
    }

    public void updateCatalog() {
        BigqueryUtils bigqueryUtils = new BigqueryUtils();
        bigqueryUtils.beginQuery(CATALOG_SELECT_SQL);
        bigqueryUtils.pollForCompletion();
        catalog = convertTableRowToModel(bigqueryUtils.getBqTableData(), CatalogItem::of);
    }

    protected static Integer hamming(final String a, final String b) {
        Integer maxLength = Math.max(a.length(), b.length());
        HammingDistance hammingDistance = new HammingDistance();
        return hammingDistance.apply(rightPad(a, maxLength),rightPad(b, maxLength));
    }

    private static BigInteger linear(final String a, final String b) {
        BigInteger intA = toInt(a.replaceAll("S",""));
        BigInteger intB = toInt(b.replaceAll("S",""));
        return intA.subtract(intB).abs();
    }

    private static BigInteger toInt(String s) {
        try {
            return new BigInteger(s);
        } catch (NumberFormatException e) {
            return BigInteger.ZERO;
        }
    }
}
