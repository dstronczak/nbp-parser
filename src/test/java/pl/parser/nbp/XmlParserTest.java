package pl.parser.nbp;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class XmlParserTest {
    private static String sampleXML = "<?xml version=\"1.0\" encoding=\"ISO-8859-2\"?>\n" +
            "<tabela_kursow typ=\"C\">\n" +
            "    <numer_tabeli>73/C/NBP/2007</numer_tabeli>\n" +
            "    <data_notowania>2007-04-12</data_notowania>\n" +
            "    <data_publikacji>2007-04-13</data_publikacji>\n" +
            "    <pozycja>\n" +
            "        <nazwa_waluty>dolar amerykañski</nazwa_waluty>\n" +
            "        <przelicznik>1</przelicznik>\n" +
            "        <kod_waluty>USD</kod_waluty>\n" +
            "        <kurs_kupna>2,8210</kurs_kupna>\n" +
            "        <kurs_sprzedazy>2,8780</kurs_sprzedazy>\n" +
            "    </pozycja>\n" +
            "    <pozycja>\n" +
            "        <nazwa_waluty>dolar australijski</nazwa_waluty>\n" +
            "        <przelicznik>1</przelicznik>\n" +
            "        <kod_waluty>AUD</kod_waluty>\n" +
            "        <kurs_kupna>2,3292</kurs_kupna>\n" +
            "        <kurs_sprzedazy>2,3762</kurs_sprzedazy>\n" +
            "    </pozycja>\n" +
            "    <pozycja>\n" +
            "        <nazwa_waluty>dolar kanadyjski</nazwa_waluty>\n" +
            "        <przelicznik>1</przelicznik>\n" +
            "        <kod_waluty>CAD</kod_waluty>\n" +
            "        <kurs_kupna>2,4799</kurs_kupna>\n" +
            "        <kurs_sprzedazy>2,5301</kurs_sprzedazy>\n" +
            "    </pozycja>\n" +
            "    <pozycja>\n" +
            "        <nazwa_waluty>euro</nazwa_waluty>\n" +
            "        <przelicznik>1</przelicznik>\n" +
            "        <kod_waluty>EUR</kod_waluty>\n" +
            "        <kurs_kupna>3,7976</kurs_kupna>\n" +
            "        <kurs_sprzedazy>3,8744</kurs_sprzedazy>\n" +
            "    </pozycja>\n" +
            "    <pozycja>\n" +
            "        <nazwa_waluty>forint (Wêgry)</nazwa_waluty>\n" +
            "        <przelicznik>100</przelicznik>\n" +
            "        <kod_waluty>HUF</kod_waluty>\n" +
            "        <kurs_kupna>1,5457</kurs_kupna>\n" +
            "        <kurs_sprzedazy>1,5769</kurs_sprzedazy>\n" +
            "    </pozycja>\n" +
            "    <pozycja>\n" +
            "        <nazwa_waluty>frank szwajcarski</nazwa_waluty>\n" +
            "        <przelicznik>1</przelicznik>\n" +
            "        <kod_waluty>CHF</kod_waluty>\n" +
            "        <kurs_kupna>2,3163</kurs_kupna>\n" +
            "        <kurs_sprzedazy>2,3631</kurs_sprzedazy>\n" +
            "    </pozycja>\n" +
            "    <pozycja>\n" +
            "        <nazwa_waluty>funt szterling</nazwa_waluty>\n" +
            "        <przelicznik>1</przelicznik>\n" +
            "        <kod_waluty>GBP</kod_waluty>\n" +
            "        <kurs_kupna>5,5766</kurs_kupna>\n" +
            "        <kurs_sprzedazy>5,6892</kurs_sprzedazy>\n" +
            "    </pozycja>\n" +
            "    <pozycja>\n" +
            "        <nazwa_waluty>jen (Japonia)</nazwa_waluty>\n" +
            "        <przelicznik>100</przelicznik>\n" +
            "        <kod_waluty>JPY</kod_waluty>\n" +
            "        <kurs_kupna>2,3677</kurs_kupna>\n" +
            "        <kurs_sprzedazy>2,4155</kurs_sprzedazy>\n" +
            "    </pozycja>\n" +
            "    <pozycja>\n" +
            "        <nazwa_waluty>korona czeska</nazwa_waluty>\n" +
            "        <przelicznik>1</przelicznik>\n" +
            "        <kod_waluty>CZK</kod_waluty>\n" +
            "        <kurs_kupna>0,1358</kurs_kupna>\n" +
            "        <kurs_sprzedazy>0,1386</kurs_sprzedazy>\n" +
            "    </pozycja>\n" +
            "    <pozycja>\n" +
            "        <nazwa_waluty>korona duñska</nazwa_waluty>\n" +
            "        <przelicznik>1</przelicznik>\n" +
            "        <kod_waluty>DKK</kod_waluty>\n" +
            "        <kurs_kupna>0,5094</kurs_kupna>\n" +
            "        <kurs_sprzedazy>0,5196</kurs_sprzedazy>\n" +
            "    </pozycja>\n" +
            "    <pozycja>\n" +
            "        <nazwa_waluty>korona estoñska</nazwa_waluty>\n" +
            "        <przelicznik>1</przelicznik>\n" +
            "        <kod_waluty>EEK</kod_waluty>\n" +
            "        <kurs_kupna>0,2427</kurs_kupna>\n" +
            "        <kurs_sprzedazy>0,2477</kurs_sprzedazy>\n" +
            "    </pozycja>\n" +
            "    <pozycja>\n" +
            "        <nazwa_waluty>korona norweska</nazwa_waluty>\n" +
            "        <przelicznik>1</przelicznik>\n" +
            "        <kod_waluty>NOK</kod_waluty>\n" +
            "        <kurs_kupna>0,4698</kurs_kupna>\n" +
            "        <kurs_sprzedazy>0,4792</kurs_sprzedazy>\n" +
            "    </pozycja>\n" +
            "    <pozycja>\n" +
            "        <nazwa_waluty>korona szwedzka</nazwa_waluty>\n" +
            "        <przelicznik>1</przelicznik>\n" +
            "        <kod_waluty>SEK</kod_waluty>\n" +
            "        <kurs_kupna>0,4101</kurs_kupna>\n" +
            "        <kurs_sprzedazy>0,4183</kurs_sprzedazy>\n" +
            "    </pozycja>\n" +
            "    <pozycja>\n" +
            "        <nazwa_waluty>SDR (MFW)</nazwa_waluty>\n" +
            "        <przelicznik>1</przelicznik>\n" +
            "        <kod_waluty>XDR</kod_waluty>\n" +
            "        <kurs_kupna>4,2883</kurs_kupna>\n" +
            "        <kurs_sprzedazy>4,3749</kurs_sprzedazy>\n" +
            "    </pozycja>\n" +
            "</tabela_kursow>\n";


    private static String malformedXML = "<?xml version=\"1.0\" encoding=\"ISO-8859-2\"?>\n" +
            "<tabela_kursow typ=\"C\">\n" +
            "    <numer_tabeli>73/C/NBP/2007</numer_tabeli>\n" +
            "    <data_notowania>2007-04-12</data_notowania>\n" +
            "    <data_publikacji>2007-0";

    private static InputStream correctXMLStream;
    private static InputStream malformedXMLStream;

    @BeforeClass
    public static void init() throws IOException {
        correctXMLStream = IOUtils.toInputStream(sampleXML, "UTF-8");
        malformedXMLStream = IOUtils.toInputStream(malformedXML, "UTF-8");
    }

    @Test
    public void parseCorrectXml() throws IOException, SAXException, ParserConfigurationException {
        CurrencyPrice currencyPrice = XmlParser.parse(correctXMLStream, Currency.USD);
        assertEquals(currencyPrice.getPurchasePrice(), 2.8210, 0.1);

        correctXMLStream.reset();
        currencyPrice = XmlParser.parse(correctXMLStream, Currency.EUR);
        assertEquals(currencyPrice.getAskPrice(), 3.8744, 0.1);

        correctXMLStream.reset();
        currencyPrice = XmlParser.parse(correctXMLStream, Currency.CHF);
        assertEquals(currencyPrice.getAskPrice(), 2.3631, 0.1);


        correctXMLStream.reset();
        currencyPrice = XmlParser.parse(correctXMLStream, Currency.GBP);
        assertEquals(currencyPrice.getPurchasePrice(), 5.5766, 0.1);

    }

    @Test(expected=SAXParseException.class)
    public void parseMalformedXML() throws IOException, SAXException, ParserConfigurationException {
        CurrencyPrice currencyPrice = XmlParser.parse(malformedXMLStream, Currency.USD);

    }
}


//public class FooTest {
//    @Test public void readXMLToString() throws Exception {
//        java.net.URL url = MyClass.class.getResource("test/resources/abc.xml");
//
//        String xml = new java.util.Scanner(new File(url),"UTF8").useDelimiter("\\Z").next();
//    }
//}
