package pl.parser.nbp;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class XmlParser {

    public static CurrencyPrice parse(InputStream is, Currency currency) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc;

        builder = factory.newDocumentBuilder();
        doc = builder.parse(new InputSource(is));

        XPathFactory xpathFactory = XPathFactory.newInstance();

        XPath xpath = xpathFactory.newXPath();

        CurrencyPrice currencyPrice = new CurrencyPrice();
        currencyPrice.setPurchasePrice(getPurchasePrice(doc, xpath, currency));
        currencyPrice.setAskPrice(getAskPrice(doc, xpath, currency));

        return currencyPrice;
    }


    //TODO: refactor
    private static Double getPurchasePrice(Document doc, XPath xpath, Currency currency) {
        List<String> list = new ArrayList<String>();
        try {
            XPathExpression expr =
                    xpath.compile("/tabela_kursow/pozycja[kod_waluty='" + currency.toString() + "']/kurs_kupna/text()");
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++)
                list.add(nodes.item(i).getNodeValue());
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return Double.valueOf(list.get(0).replace(",", "."));
    }

    //TODO: refactor
    private static Double getAskPrice(Document doc, XPath xpath, Currency currency) {
        List<String> list = new ArrayList<String>();
        try {
            XPathExpression expr =
                    xpath.compile("/tabela_kursow/pozycja[kod_waluty='" + currency.toString() + "']/kurs_sprzedazy/text()");
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++)
                list.add(nodes.item(i).getNodeValue());
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return Double.valueOf(list.get(0).replace(",", "."));
    }


}