package com.miniaturesolutions.aquire;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: rob
 * Date: 02/04/14
 * Time: 17:16
 * To change this template use File | Settings | File Templates.
 */
public class StockQuoteTest {

    @Test
    public void testEquality() {

        Corporation ca = new Corporation(NamedCorporation.AMERICAN);
        Corporation cb = new Corporation(NamedCorporation.CONTINENTAL);

        StockQuote a = new StockQuote(ca);
        StockQuote b = new StockQuote(ca);
        StockQuote c = new StockQuote(cb);

        assertTrue("same tile", a.equals(b));
        assertTrue("same tile", b.equals(a));

        assertFalse("diferent tile", c.equals(a));
        assertFalse("diferent tile", a.equals(c));

        assertFalse("different object", a.equals(new Long(1)));

        //branch coverage ;(
        Corporation cf = mock(Corporation.class);


        when(cf.getCorporation()).thenReturn(NamedCorporation.CONTINENTAL);
        when(cf.getCurrentStockPrice()).thenReturn(333);
        when(cf.getRemainingShareCount()).thenReturn(99);

        StockQuote fake = new StockQuote(cf);

        assertFalse("different stock price and remaining stock", fake.equals(c));

        when(cf.getCorporation()).thenReturn(NamedCorporation.CONTINENTAL);
        when(cf.getCurrentStockPrice()).thenReturn(cb.getCurrentStockPrice());
        when(cf.getRemainingShareCount()).thenReturn(99);

        fake = new StockQuote(cf);
        assertFalse("different stock price", fake.equals(c));

        when(cf.getCorporation()).thenReturn(NamedCorporation.CONTINENTAL);
        when(cf.getCurrentStockPrice()).thenReturn(333);
        when(cf.getRemainingShareCount()).thenReturn(cb.getRemainingShareCount());

        fake = new StockQuote(cf);
        assertFalse("different remaining stock", fake.equals(c));
    }

}
